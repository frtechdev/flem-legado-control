
import br.org.flem.control.acesso.bo.ActiveDirectoryBO;
import br.org.flem.control.acesso.bo.BloqueioAfastamentoADBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Collection;
import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author emsilva
 */
public class TesteBloqueioADTEst{// extends TestCase {
/*
    public void testBloqueio(){
        try {
            UsuarioBO usuarioBO = new UsuarioBO();
            ActiveDirectoryBO activeDirectoryBO = new ActiveDirectoryBO();
            System.out.println("Iniciando verficiacao de agendamento de bloqueios");
            BloqueioAfastamentoAD filtro = new BloqueioAfastamentoAD();
            filtro.setDataInicio(new Date());
            filtro.setDataFim(null);
            BloqueioAfastamentoADBO bo = new BloqueioAfastamentoADBO();
            //Collection<BloqueioAfastamentoAD> agendamentos = bo.obterInicioAgendamentoAte(new Date());
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
            agendamentos = new BloqueioAfastamentoADBO().obterFimAgendamentoAte(new Date());
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
            System.out.println("Job de bloqueio executado com sucesso");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro na execucao do job de bloqueios");
        }
        //assertTrue(true);
    }*/
}