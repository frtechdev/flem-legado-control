package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.CentroCustoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class CentroCustoUsuarioDAO extends BaseDAOAb<CentroCustoUsuario> {

    public CentroCustoUsuarioDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<CentroCustoUsuario> getClasseDto() {
        return CentroCustoUsuario.class;
    }

    public Collection<CentroCustoUsuario> obterPorUsuario(Usuario usuario) {
        return (Collection<CentroCustoUsuario>) session.createQuery("FROM CentroCustoUsuario WHERE usuario = :usuario").setEntity("usuario", usuario).list();
    }

    public CentroCustoUsuario obterPorUsuarioCodigo(Usuario usuario, String codigo) {
        return (CentroCustoUsuario) session.createQuery("FROM CentroCustoUsuario WHERE usuario = :usuario AND codigoCentroCusto = :codigo").setEntity("usuario", usuario).setString("codigo", codigo).uniqueResult();
    }
}
