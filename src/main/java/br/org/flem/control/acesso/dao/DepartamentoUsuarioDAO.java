package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.DepartamentoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class DepartamentoUsuarioDAO extends BaseDAOAb<DepartamentoUsuario> {

    public DepartamentoUsuarioDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<DepartamentoUsuario> getClasseDto() {
        return DepartamentoUsuario.class;
    }

    public Collection<DepartamentoUsuario> obterPorUsuario(Usuario usuario) {
        return (Collection<DepartamentoUsuario>) session.createQuery("FROM DepartamentoUsuario WHERE usuario = :usuario").setEntity("usuario", usuario).list();
    }

    public DepartamentoUsuario obterPorUsuarioCodigo(Usuario usuario, String codigo) {
        return (DepartamentoUsuario) session.createQuery("FROM DepartamentoUsuario WHERE usuario = :usuario AND codigoDepartamentoDominio = :codigo").setEntity("usuario", usuario).setString("codigo", codigo).uniqueResult();
    }
}
