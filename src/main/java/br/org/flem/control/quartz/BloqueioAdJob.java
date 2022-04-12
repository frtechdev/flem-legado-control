package br.org.flem.control.quartz;

import br.org.flem.control.acesso.bo.ActiveDirectoryBO;
import br.org.flem.control.acesso.bo.BloqueioAfastamentoADBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class BloqueioAdJob implements Job {

    public void execute(JobExecutionContext arg0) {
        try {
            HibernateUtil.setSessaoUnica(false);
            UsuarioBO usuarioBO = new UsuarioBO();
            ActiveDirectoryBO activeDirectoryBO = new ActiveDirectoryBO();
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Iniciando verficiacao de agendamento de bloqueios");
            BloqueioAfastamentoAD filtro = new BloqueioAfastamentoAD();
            filtro.setDataInicio(new Date());
            filtro.setDataFim(null);
            BloqueioAfastamentoADBO bo = new BloqueioAfastamentoADBO();
            Collection<BloqueioAfastamentoAD> agendamentos = bo.obterPorFiltro(filtro);
            for (BloqueioAfastamentoAD bloqueio : agendamentos) {
                HibernateUtil.beginTransaction();
                try{
                    activeDirectoryBO.alterarAtributoAD(bloqueio.getUsuario().getLogin(), true);
                    activeDirectoryBO.alterarAtributoLdapExpresso(bloqueio.getUsuario().getLogin(), true);
                    HibernateUtil.commitTransaction();
                }catch(Exception e){
                    e.printStackTrace();
                    HibernateUtil.rollbackTransactionAndKeepSession();
                }
            }
            filtro.setDataInicio(null);
            filtro.setDataFim(new Date());
            agendamentos = new BloqueioAfastamentoADBO().obterPorFiltro(filtro);
            for (BloqueioAfastamentoAD bloqueio : agendamentos) {
                HibernateUtil.beginTransaction();
                try{
                    activeDirectoryBO.alterarAtributoAD(bloqueio.getUsuario().getLogin(), false);
                    activeDirectoryBO.alterarAtributoLdapExpresso(bloqueio.getUsuario().getLogin(), false);
                    bloqueio.getUsuario().setBloqueadoSe(false);
                    usuarioBO.alterar(bloqueio.getUsuario());
                    bo.excluir(bloqueio);
                    HibernateUtil.commitTransaction();
                }catch(Exception e){
                    e.printStackTrace();
                    HibernateUtil.rollbackTransactionAndKeepSession();
                }
            }
            HibernateUtil.closeSession();
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Job de bloqueio executado com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Erro na execucao do job de bloqueios", ex);
        }
    }
    
}