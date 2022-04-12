package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.DepartamentoCentroCustoDAO;
import br.org.flem.control.acesso.negocio.DepartamentoCentroCusto;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class DepartamentoCentroCustoBO extends BaseBOAb<DepartamentoCentroCusto> {

    public DepartamentoCentroCustoBO() throws AplicacaoException {
        super(new DepartamentoCentroCustoDAO());
    }

    public Collection<DepartamentoCentroCusto> obterTodosDistinctDepartamento() {
        return ((DepartamentoCentroCustoDAO) dao).obterTodosDistinctDepartamento();
    }

    public DepartamentoCentroCusto obterPorDepartamento(Integer codigoDominio) {
        return ((DepartamentoCentroCustoDAO) dao).obterPorDepartamentoDominio(codigoDominio);
    }

    public Collection<DepartamentoCentroCusto> obterTodosPorDepartamento(Integer codigoDominio) {
        return ((DepartamentoCentroCustoDAO) dao).obterTodosPorDepartamentoDominio(codigoDominio);
    }

    public DepartamentoCentroCusto obterPorDepartamentoCentroCusto(String codDep, String codCentro) {
        return ((DepartamentoCentroCustoDAO) dao).obterPorDepartamentoDominioCentroCusto(codDep, codCentro);
    }
}
