package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.negocio.Perfil;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author dbbarreto
 */
public class PerfilBO {
    
    public PerfilBO() {
    }
    
    public void inserir(Perfil perfil) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        new PerfilDAO().inserir(perfil);
        HibernateUtil.commitTransaction();
    }
    
    public void alterar(Perfil perfil) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        new PerfilDAO().alterar(perfil);
        HibernateUtil.commitTransaction();
    }
    
    public void excluir(Collection<Perfil> perfis) throws AcessoDadosException {
        PerfilDAO dao = new PerfilDAO();
        HibernateUtil.beginTransaction();
        dao.excluir(perfis);
        HibernateUtil.commitTransaction();
    }
    
    public Collection<Perfil> obterTodos() throws AcessoDadosException {
        return new PerfilDAO().obterTodos();
    }
    
    public Perfil obterPorPk(Integer pk) throws AcessoDadosException {
        return new PerfilDAO().obterPorPk(pk);
    }

    public List<Perfil> obterFiltroNome(String nome) throws AcessoDadosException {
        if (nome == null || nome.isEmpty()) {
            return new PerfilDAO().obterTodos();
        }
        return new PerfilDAO().obterFiltroNome(nome); 
    }
}
