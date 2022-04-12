package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.DepartamentoTipoSolicitacaoDAO;
import br.org.flem.control.acesso.negocio.DepartamentoTipoSolicitacao;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;

/**
 *
 * @author emsilva
 */
public class DepartamentoTipoSolicitacaoBO extends BaseBOAb<DepartamentoTipoSolicitacao> {

    public DepartamentoTipoSolicitacaoBO() throws AplicacaoException {
        super(new DepartamentoTipoSolicitacaoDAO());
    }

    public DepartamentoTipoSolicitacao obterPorLotacaoDominio(Integer lotacaoDominio) throws AcessoDadosException {
        return ((DepartamentoTipoSolicitacaoDAO)dao).obterPorLotacaoDominio(lotacaoDominio);
    }

}