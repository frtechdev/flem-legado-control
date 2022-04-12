package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author mjpereira
 */
public class UsuarioDAO extends BaseDAOAb<Usuario> {

    /**
     * Creates a new instance of UsuarioDAO
     */
    public UsuarioDAO() throws AcessoDadosException {
    }

    protected Class<Usuario> getClasseDto() {
        return Usuario.class;
    }

    public Usuario obterLogin(String login) throws AcessoDadosException {
        try {
            return (Usuario) session.createQuery("from Usuario u where u.login = :login").setString("login", login).setMaxResults(1).uniqueResult();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }

    public Usuario obterId(Integer id) throws AcessoDadosException {
        try {
            Usuario hibernate = (Usuario) session.createQuery("from Usuario u where u.id = :id").setInteger("id", id).setMaxResults(1).uniqueResult();
            /**
             * Usuario usuario = new Usuario();
             * usuario.setId(hibernate.getId());
             * usuario.setNome(hibernate.getNome());
             */
            session.delete(hibernate);

            return hibernate;
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }

    public Collection<Usuario> obterPorLoginNomeLotacaoDominio(String login, String nome, Integer lotacaoDominio, Boolean ativo) throws AcessoDadosException {
        try {
            StringBuilder sql = new StringBuilder("from Usuario u where u.login like :login and u.nome like :nome and u.ativo = :ativo");
            if(lotacaoDominio != null){
                sql.append(" and u.lotacaoDominio = :lotacaoDominio");
            }
            Query query = session.createQuery(sql.toString()).setString("login", "%" + login + "%").setString("nome", "%" + nome + "%").setBoolean("ativo", ativo);
            if(lotacaoDominio != null){
                query.setInteger("lotacaoDominio", lotacaoDominio);
            }
            return query.list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public Usuario obterUsuarioPorCodigoDominio(Integer codigo) throws AcessoDadosException{
        
       try {
            return (Usuario) session.createQuery("from Usuario u where u.codigoDominio = :codigo").setInteger("codigo", codigo).setMaxResults(1).uniqueResult();
                
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    @SuppressWarnings(value = "unchecked")
    public List<Usuario> obterAtivos() throws AcessoDadosException {
        try {
            return session.createQuery("from Usuario u where u.ativo = :sim").setBoolean("sim", true).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }

    public Integer proximoId() throws AcessoDadosException {
        try {
            Integer id = (Integer) session.createQuery("select max(id) from Usuario u").setMaxResults(1).uniqueResult();
            return (id + 1);
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<Usuario> obterAtivosAD(String nome) throws AcessoDadosException {
        try {
            return session.createQuery("from Usuario u where u.ativo = :sim and u.tipoAutenticacao = :tipoAcesso and u.nome like :nome")
                    .setBoolean("sim", true)
                    .setParameter("tipoAcesso", TipoAutenticacao.ACTIVE_DIRECTORY)
                    .setParameter("nome", (nome != null && !nome.isEmpty() ? nome + "%" : "%"))
                    .list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Usuario> obterAtivosSE(String nome) throws AcessoDadosException {
        try {
            return session.createQuery("from Usuario u where u.ativo = :sim and u.tipoAutenticacao = :tipoAcesso and u.nome like :nome")
                    .setBoolean("sim", true)
                    .setParameter("tipoAcesso", TipoAutenticacao.SENHA)
                    .setParameter("nome", (nome != null && !nome.isEmpty() ? nome + "%" : "%"))
                    .list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
}
