package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class BloqueioAfastamentoADDAO extends BaseDAOAb<BloqueioAfastamentoAD> {

    public BloqueioAfastamentoADDAO()
            throws AcessoDadosException {
    }

    public Class<BloqueioAfastamentoAD> getClasseDto() {
        return BloqueioAfastamentoAD.class;
    }
    
    public List<BloqueioAfastamentoAD> obterFimAgendamentoAte(Date dataFimAgendamento){
        Criteria criteria = session.createCriteria(getClasseDto());
        criteria.add(Restrictions.le("dataFim", dataFimAgendamento));
        return criteria.list();
    }
    
    public List<BloqueioAfastamentoAD> obterInicioAgendamentoAte(Date dataInicioAgendamento){
        Criteria criteria = session.createCriteria(getClasseDto());
        criteria.add(Restrictions.le("dataInicio", dataInicioAgendamento));
        criteria.add(Restrictions.gt("dataFim", new Date()));
        return criteria.list();
    }
}