/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.quartz;

import br.org.flem.control.acesso.action.ColaboradorAction;
import br.org.flem.control.acesso.bo.ColaboradorBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.email.EmailTrocaSenha;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.RH;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.util.CriptografiaUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author AJLima
 */
public class CriarUsuarioJob implements Job {

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        RH rh = null;
        Map<Integer, IFuncionario> map = null;
        List<Usuario> lista = null;
        List<Funcionario> cadastrados = new ArrayList<Funcionario>();
        List<Funcionario> problema = new ArrayList<Funcionario>();
        Collection<IFuncionario> funcionarios = null;
        try {
            rh = new RHServico();
            map = rh.obterMapIdFuncionarioAtivo();
            lista = new UsuarioBO().obterTodos();
            for (Usuario u : lista) {
                map.remove(u.getCodigoDominio());
            }
            funcionarios = map.values();
            for (IFuncionario f : funcionarios) {

                if (f.getDepartamento().getCodigoDominio() == 1000) {

                    boolean valido = new EmailTrocaSenha().checkEmail(f.getEmail());

                    if (valido && f.getEmail() != null) {
                        Usuario dto = new Usuario();
                        dto.setLogin("flem" + f.getCodigoDominio());
                        dto.setCriacao(new Date());
                        dto.setTipoAutenticacao(TipoAutenticacao.SENHA);
                        dto.setTipoSolicitacao(TipoSolicitacao.FLEM);
                        dto.setNome(f.getNome());
                        dto.setSenha(CriptografiaUtil.stringToMD5("Flemflem123456"));
                        dto.setAlterarSenha(true);
                        dto.setMatricula(f.getMatriculaHR());
                        dto.setCodigoDominio(f.getId());
                        dto.setAtivo(true);
                        dto.setLotacao(f.getDepartamento().getCodigoHR());
                        dto.setLotacaoDominio(f.getDepartamento().getCodigoDominio());
                        dto.getPerfil().add(new PerfilDAO().obterPorPk(17));
                        try {
                            new UsuarioBO().validaUsuario(dto);
                            Funcionario f1 = (Funcionario) rh.obterFuncionarioPorMatricula(f.getCodigoDominio());
                            new ColaboradorBO().inserirColaboradorSemLogin(f1, dto);
                            cadastrados.add(f1);
                            new EmailTrocaSenha().enviaNotificacaoUsuarioSenha(f1);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Logger.getLogger(ColaboradorAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            Logger.getLogger(getClass().getName()).log(Level.INFO, "Erro ao Criar usuário");
                        }
                    } else {

                        Funcionario prob = (Funcionario) rh.obterFuncionarioPorMatricula(f.getCodigoDominio());
                        problema.add(prob);

                    }
                }
            }
            if (cadastrados.size() > 0) {

                Collections.sort(cadastrados, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Funcionario comp1 = (Funcionario) o1;
                        Funcionario comp2 = (Funcionario) o2;
                        int comparacao = comp1.getNome().compareTo(comp2.getNome());
                        if (comparacao == 0) {
                            comparacao = comp1.getNome().compareTo(comp2.getNome());
                            if (comparacao == 0) {
                                comparacao = comp1.getNome().compareTo(comp2.getNome());
                                if (comparacao == 0) {
                                    comparacao = comp1.getNome().compareTo(comp2.getNome());
                                }
                            }
                        }
                        return comparacao;
                    }
                });

                Collections.sort(problema, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Funcionario comp1 = (Funcionario) o1;
                        Funcionario comp2 = (Funcionario) o2;
                        int comparacao = comp1.getNome().compareTo(comp2.getNome());
                        if (comparacao == 0) {
                            comparacao = comp1.getNome().compareTo(comp2.getNome());
                            if (comparacao == 0) {
                                comparacao = comp1.getNome().compareTo(comp2.getNome());
                                if (comparacao == 0) {
                                    comparacao = comp1.getNome().compareTo(comp2.getNome());
                                }
                            }
                        }
                        return comparacao;
                    }
                });

            }
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            Logger.getLogger(ColaboradorAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Erro no processamento.");
        }

        if (cadastrados.size() > 0) {
            new EmailTrocaSenha().enviaEmailUsuariosCriados(cadastrados);
        }

        if (problema.size() > 0) {
            new EmailTrocaSenha().enviaEmailUsuarioNaoCriados(problema);
        }

    }

}
