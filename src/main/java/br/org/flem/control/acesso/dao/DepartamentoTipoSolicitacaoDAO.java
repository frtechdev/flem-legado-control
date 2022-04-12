package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.DepartamentoTipoSolicitacao;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;

/**
 *
 * @author emsilva
 */
public class DepartamentoTipoSolicitacaoDAO extends BaseDAOAb<DepartamentoTipoSolicitacao> {
    
    public DepartamentoTipoSolicitacaoDAO() throws AplicacaoException {
    }

    @Override
    protected Class<DepartamentoTipoSolicitacao> getClasseDto() {
        return DepartamentoTipoSolicitacao.class;
    }

    public DepartamentoTipoSolicitacao obterPorLotacaoDominio(Integer lotacaoDominio) {
        return (DepartamentoTipoSolicitacao)session.createQuery("FROM DepartamentoTipoSolicitacao app WHERE lotacaoDominio = ?").setInteger(0, lotacaoDominio).uniqueResult();
    }

}