package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.CentroCustoUsuarioDAO;
import br.org.flem.control.acesso.negocio.CentroCustoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class CentroCustoUsuarioBO extends BaseBOAb<CentroCustoUsuario> {

    public CentroCustoUsuarioBO() throws AplicacaoException {
        super(new CentroCustoUsuarioDAO());
    }

    public Collection<CentroCustoUsuario> obterPorUsuario(Usuario usuario) {
        return ((CentroCustoUsuarioDAO) dao).obterPorUsuario(usuario);
    }

    public CentroCustoUsuario obterPorUsuarioCodigo(Usuario usuario, String codigo) {
        return ((CentroCustoUsuarioDAO) dao).obterPorUsuarioCodigo(usuario, codigo);
    }
}
