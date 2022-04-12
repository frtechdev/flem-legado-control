package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.DepartamentoCentroCusto;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class DepartamentoCentroCustoDAO extends BaseDAOAb<DepartamentoCentroCusto> {

    public DepartamentoCentroCustoDAO() throws AplicacaoException {
    }

    @Override
    protected Class<DepartamentoCentroCusto> getClasseDto() {
        return DepartamentoCentroCusto.class;
    }

    public Collection<DepartamentoCentroCusto> obterTodosDistinctDepartamento() {
        return (Collection<DepartamentoCentroCusto>) session.createQuery("DISTINCT codigoDepartamentoDominio FROM DepartamentoCentroCusto").list();
    }

    public DepartamentoCentroCusto obterPorDepartamentoDominio(Integer codigoDominio) {
        return (DepartamentoCentroCusto) session.createQuery("FROM DepartamentoCentroCusto WHERE codigoDepartamentoDominio = :codigoDominio").setInteger("codigoDominio", codigoDominio).uniqueResult();
    }

    public Collection<DepartamentoCentroCusto> obterTodosPorDepartamentoDominio(Integer codigoDominio) {
        return (Collection<DepartamentoCentroCusto>) session.createQuery("FROM DepartamentoCentroCusto WHERE codigoDepartamentoDominio = :codigoDominio").setInteger("codigoDominio", codigoDominio).list();
    }

    public DepartamentoCentroCusto obterPorDepartamentoDominioCentroCusto(String codigoDominio, String codCentro) {
        return (DepartamentoCentroCusto) session.createQuery("FROM DepartamentoCentroCusto WHERE codigoDepartamentoDominio = :codigoDominio AND codigoCentroCusto = :codCentro").setString("codigoDominio", codigoDominio).setString("codCentro", codCentro).uniqueResult();
    }
}
