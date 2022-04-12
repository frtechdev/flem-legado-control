package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.exceptions.UsuarioSemSenhaException;
import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.StringUtil;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.log4j.Logger;

public class AutenticacaoSenhaBO {

    private Logger logger = Logger.getLogger(getClass());
    
    public boolean bloquearAcessos(Usuario usuario, boolean  bloquear) throws AplicacaoException {
        try {
            Usuario usr = new UsuarioBO().obterPorPk(usuario.getId());
            
            usr.setBloqueadoSe(bloquear);
            new UsuarioBO().alterar(usr);
            boolean bloquearExpresso = this.alterarAtributoLdapExpresso(usr.getLogin(), bloquear);
            if (!bloquearExpresso) {
                throw new AplicacaoException("Erro de comunicação com o ldap");
            }
            return bloquearExpresso;
        } catch (UsuarioSemSenhaException ex) {
            java.util.logging.Logger.getLogger(AutenticacaoSenhaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public List<Usuario> obterAgendamentosSe(List<Usuario> usuarios) throws AplicacaoException {
        Map bloqueios = new BloqueioAfastamentoSEBO().getMapBloqueios();
        for (Usuario usuario : usuarios) {
            BloqueioAfastamentoAD bloqueio = (BloqueioAfastamentoAD) bloqueios.get(usuario.getId());
            if(bloqueio != null){
                usuario.setDataInicioBloqueio(bloqueio.getDataInicio());
                usuario.setDataFimBloqueio(bloqueio.getDataFim());
            }
        }
        return usuarios;
    }

    public boolean alterarAtributoLdapExpresso(String usuarioAlteracao, boolean bloquear) throws AplicacaoException, UsuarioSemSenhaException {
        boolean alterou = false;
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("java.naming.security.authentication", "simple");
        env.put("java.naming.security.credentials", "bbcb1988");
        env.put("java.naming.security.principal", "cn=admin,dc=flem,dc=org,dc=br");
        env.put("java.naming.provider.url", "ldap://smtp.flem.org.br:389");
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(2);
            String searchFilter = "(&(objectClass=*)(uid=" + usuarioAlteracao + "))";
            NamingEnumeration answer = ctx.search("DC=flem,DC=org,DC=br", searchFilter, searchCtls);
            if (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                ModificationItem[] mods = new ModificationItem[1];
                //mods[0] = new ModificationItem(2, new BasicAttribute("accountStatus", bloquear == true ? " " : "active"));

                mods[0] = new ModificationItem(2, new BasicAttribute("phpgwAccountStatus", bloquear == true ? " " : "A"));

                ctx.modifyAttributes(sr.getName() + "," + "DC=flem,DC=org,DC=br", mods);
                alterou = true;
            }else{
                throw new UsuarioSemSenhaException("Usuário sem email");
            }
            ctx.close();
        } catch (NamingException ex) {
            this.logger.error(ex.getMessage(), ex);
            throw new AplicacaoException("Ocorreu um erro na comunicação com o servidor LDAP", ex);
        }
        return alterou;
    }
    
    private String getBinaryString(Object o) {
        Long n = new Long(o.toString());
        String s = Long.toBinaryString(n.longValue());
        return StringUtil.addCharInicioStrTrim(s, '0', 32 - s.length());
    }

    private Long getIntFromBinaryString(String binario) {
        return Long.valueOf(new BigInteger(binario, 2).longValue());
    }
}