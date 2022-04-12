/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.BloqueioAfastamentoADDAO;
import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ILFernandes
 */
public class BloqueioAfastamentoADBO extends BaseBOAb<BloqueioAfastamentoAD> {

    public BloqueioAfastamentoADBO() throws AplicacaoException {
        super(new BloqueioAfastamentoADDAO());
    }

    public Object schedularBloqueio(BloqueioAfastamentoAD schedule) throws AplicacaoException {
        try {
            HibernateUtil.beginTransaction();
            ((BloqueioAfastamentoADDAO) dao).inserirOuAlterar(schedule);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date data = cal.getTime();
            if (schedule.getDataInicio().before(data)) {
                ActiveDirectoryBO adbo = new ActiveDirectoryBO();
                adbo.bloquearAcessos(schedule.getUsuario(), true);
            }
            HibernateUtil.commitTransaction();
            return schedule;
        } catch (AplicacaoException ex) {
            HibernateUtil.rollbackTransaction();
            Logger.getLogger(BloqueioAfastamentoADBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new AplicacaoException("Erro ao tentar cadastrar agendamento, schedularBloqueio");
        }
    }

    @Override
    public Object inserir(BloqueioAfastamentoAD objeto) throws AplicacaoException {
        return this.schedularBloqueio(objeto);
    }

    public Map<Integer, BloqueioAfastamentoAD> getMapBloqueios() throws AplicacaoException {
        Map<Integer, BloqueioAfastamentoAD> map = new HashMap<Integer, BloqueioAfastamentoAD>();
        for (BloqueioAfastamentoAD bl : this.obterTodos()) {
            if(bl.getUsuario().getTipoAutenticacao().equals(TipoAutenticacao.ACTIVE_DIRECTORY)){
                map.put(bl.getUsuario().getId(), bl);
            }
        }
        return map;
    }

    public List<BloqueioAfastamentoAD> obterFimAgendamentoAte(Date dataFimAgendamento){
        return ((BloqueioAfastamentoADDAO)dao).obterFimAgendamentoAte(dataFimAgendamento);
    }
    
    public List<BloqueioAfastamentoAD> obterInicioAgendamentoAte(Date dataInicioAgendamento){
        return ((BloqueioAfastamentoADDAO)dao).obterInicioAgendamentoAte(dataInicioAgendamento);
    }

}
