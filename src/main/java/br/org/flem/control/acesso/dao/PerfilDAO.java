package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.Perfil;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mjpereira
 */
public class PerfilDAO extends BaseDAOAb<Perfil> {

    /**
     * Creates a new instance of UsuarioDAO
     */
    public PerfilDAO() throws AcessoDadosException {
    }

    protected Class<Perfil> getClasseDto() {
        return Perfil.class;
    }

    public List<Perfil> obterFiltroNome(String nome) {
        return session.createQuery("SELECT pf FROM Perfil pf WHERE nome LIKE ?").setString(0, "%"+nome+"%").list();
    }
}
