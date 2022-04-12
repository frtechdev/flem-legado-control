package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.Log;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;

public class LogDAO extends BaseDAOAb<Log> {

    public LogDAO()
            throws AplicacaoException {
    }

    public Class<Log> getClasseDto() {
        return Log.class;
    }
}