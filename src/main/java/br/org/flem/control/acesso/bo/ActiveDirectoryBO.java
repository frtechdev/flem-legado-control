package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.activedirectory.LoginAD;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.StringUtil;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.log4j.Logger;

public class ActiveDirectoryBO {

    private TreeMap<Integer, Long> adValues = new TreeMap();
    private String userName = "rhadm";
    private String userPassword = "Ys0cn0bo5zvg";
    private Logger logger = Logger.getLogger(getClass());

    public ActiveDirectoryBO() {
        for (int i = 0; i < 5; i++) {
            this.adValues.put(Integer.valueOf(i), Long.valueOf(0L));
        }

        Long iniValue = Long.valueOf(67108864L);
        for (int i = 5; i < 32; i++) {
            this.adValues.put(Integer.valueOf(i), iniValue);
            iniValue = Long.valueOf(iniValue.longValue() / 2L);
        }
    }

    public boolean bloquearAcessos(Usuario usuario, boolean bloquear) throws AplicacaoException {
        
        Usuario usr = new UsuarioBO().obterPorPk(usuario.getId());
        
        boolean bloquearAD = this.alterarAtributoAD(usr.getLogin(), bloquear);
        boolean bloquearExpresso = this.alterarAtributoLdapExpresso(usr.getLogin(), bloquear);
        if (bloquearAD) {
            usr.setBloqueadoAd(bloquear);
            new UsuarioBO().alterar(usr);
        }
        if (!bloquearAD && bloquearExpresso) {
            throw new AplicacaoException("Erro de comunicacao com o LDAP, bloquearAcessos");
        }
        return bloquearAD && bloquearExpresso;
    }

    public boolean alterarAtributoAD(String usuarioAlteracao, boolean bloquear) throws AplicacaoException {
        boolean alterou = false;
        Hashtable env = new Hashtable();
        LoginAD.getIntancia().iniciarParametros(this.userName, this.userPassword, env);
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(2);
            String searchFilter = "(&(objectClass=*)(samaccountname=" + usuarioAlteracao + "))";
            NamingEnumeration answer = ctx.search("DC=flem,DC=intranet", searchFilter, searchCtls);
            if (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attributes = ctx.getAttributes(sr.getName() + "," + "DC=flem,DC=intranet");

                Attribute attr = attributes.get("userAccountControl");
                StringBuffer binario = new StringBuffer(getBinaryString(attr.get()));
                binario.setCharAt(30, bloquear == true ? '1' : '0');

                ModificationItem[] mods = new ModificationItem[1];
                mods[0] = new ModificationItem(2, new BasicAttribute("userAccountControl", getIntFromBinaryString(binario.toString()) + ""));

                ctx.modifyAttributes(sr.getName() + "," + "DC=flem,DC=intranet", mods);
                alterou = true;
                binario = null;
            }
            ctx.close();
        } catch (NamingException ex) {
            this.logger.error(ex.getMessage(), ex);
            throw new AplicacaoException("Ocorreu um erro na comunicacao com o servidor LDAP, alterarAtributoAD", ex);
        }
        return alterou;
    }

    public boolean alterarAtributoLdapExpresso(String usuarioAlteracao, boolean bloquear) throws AplicacaoException {
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
            }
            ctx.close();
        } catch (NamingException ex) {
            this.logger.error(ex.getMessage(), ex);
            throw new AplicacaoException("Ocorreu um erro na comunicacao com o servidor LDAP, alterarAtributoLdapExpresso", ex);
        }
        return alterou;
    }

    public List<Usuario> obterStatusUsuariosNoAD(List<Usuario> usuarios) throws AplicacaoException {
        Hashtable env = new Hashtable();
        LoginAD.getIntancia().iniciarParametros(this.userName, this.userPassword, env);
        Map bloqueios = new BloqueioAfastamentoADBO().getMapBloqueios();
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(2);
            UsuarioBO usuarioBO = new UsuarioBO();
            for (Usuario usuario : usuarios) {
                String searchFilter = "(&(objectClass=*)(samaccountname=" + usuario.getLogin() + "))";
                NamingEnumeration answer = ctx.search("DC=flem,DC=intranet", searchFilter, searchCtls);
                if (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    Attributes attributes = ctx.getAttributes(sr.getName() + "," + "DC=flem,DC=intranet");

                    Attribute attr = attributes.get("userAccountControl");
                    String binario = getBinaryString(attr.get());


                    if (binario.charAt(30) == '1') {
                        if (!usuario.isBloqueadoAd()) {
                            usuario.setBloqueadoAd(true);
                            usuarioBO.alterar(usuario);
                        } else {
                            usuario.setBloqueadoAd(true);
                        }
                    } else {
                        if (usuario.isBloqueadoAd()) {
                            usuario.setBloqueadoAd(false);
                            usuarioBO.alterar(usuario);
                        }
                        usuario.setBloqueadoAd(false);
                    }
                    if (bloqueios.containsKey(usuario.getId())) {
                        BloqueioAfastamentoAD bloqueioAd = (BloqueioAfastamentoAD) bloqueios.get(usuario.getId());
                        usuario.setDataInicioBloqueio(bloqueioAd.getDataInicio());
                        usuario.setDataFimBloqueio(bloqueioAd.getDataFim());
                    }
                }
            }
            ctx.close();
        } catch (NamingException ex) {
            this.logger.error(ex.getMessage(), ex);
            throw new AplicacaoException("Ocorreu um erro na comunicacao com o servidor LDAP, obterStatusUsuariosNoAD", ex);
        }
        return usuarios;
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