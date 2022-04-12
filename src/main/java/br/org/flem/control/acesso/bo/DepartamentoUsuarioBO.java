package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.DepartamentoUsuarioDAO;
import br.org.flem.control.acesso.negocio.DepartamentoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class DepartamentoUsuarioBO extends BaseBOAb<DepartamentoUsuario> {

    public DepartamentoUsuarioBO() throws AplicacaoException {
        super(new DepartamentoUsuarioDAO());
    }

    public Collection<DepartamentoUsuario> obterPorUsuario(Usuario usuario) {
        return ((DepartamentoUsuarioDAO) dao).obterPorUsuario(usuario);
    }

    public DepartamentoUsuario obterPorUsuarioCodigo(Usuario usuario, String codigo) {
        return ((DepartamentoUsuarioDAO) dao).obterPorUsuarioCodigo(usuario, codigo);
    }
}
