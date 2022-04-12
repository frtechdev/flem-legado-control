package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.FuncionalidadeDAO;
import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.control.acesso.negocio.Funcionalidade;
import br.org.flem.control.acesso.negocio.UsuarioPerfil;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author dbbarreto
 */
public class FuncionalidadeBO extends BaseBOAb<Funcionalidade> {

    public FuncionalidadeBO() throws AplicacaoException {
        super(new FuncionalidadeDAO());
    }
    
    public List<Funcionalidade> obterPorIdAplicacao(Integer idAplicacao) throws AcessoDadosException {
        return ((FuncionalidadeDAO)dao).obterPorIdAplicacao(idAplicacao);
    }
    
}
