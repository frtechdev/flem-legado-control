package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.AutenticacaoSenhaBO;
import br.org.flem.control.acesso.bo.BloqueioAfastamentoSEBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.dao.LogDAO;
import br.org.flem.control.acesso.negocio.BloqueioAfastamentoAD;
import br.org.flem.control.acesso.negocio.Log;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.service.IUsuario;
import br.org.flem.fw.util.Constante;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class AutenticacaoSenhaAction extends DispatchAction {

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        return unspecified(mapping, form, request, response);
    }

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String nomeFiltro = request.getParameter("nome");
            if ((nomeFiltro == null || nomeFiltro.isEmpty()) && request.getSession().getAttribute("seAction_filtro_nome") != null) {
                nomeFiltro = request.getSession().getAttribute("seAction_filtro_nome").toString();
            }
            request.getSession().setAttribute("seAction_filtro_nome", nomeFiltro);
            request.setAttribute("lista", new AutenticacaoSenhaBO().obterAgendamentosSe(new UsuarioBO().obterAtivosSE(nomeFiltro)));
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar listar os funcionários. Entre em contato com o NTO");
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("lista");
    }

    public ActionForward agendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            Usuario usuario = new UsuarioBO().obterPorPk(Integer.valueOf(id));
            request.setAttribute("usuario", usuario);
        } catch (AplicacaoException ex) {
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar selecionar o funcionário. Entre em contato com o NTO");
            return unspecified(mapping, form, request, response);
        }
        return mapping.findForward("novo");
    }

    public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            BloqueioAfastamentoAD bloqueio = (BloqueioAfastamentoAD) dyna.get("bloqueio");
            if (bloqueio.getId() != null && bloqueio.getId() < 1) {
                bloqueio.setId(null);
            }
            new BloqueioAfastamentoSEBO().inserir(bloqueio);
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            Log appLog = new Log();
            appLog.setLogin(usuario.getUsername());
            appLog.setDescricao(usuario.getUsername() + " agendou bloqueio do usuario " + bloqueio.getUsuario().getLogin()+" de "+ Data.formataData(bloqueio.getDataInicio())+" até "+Data.formatExtenso(bloqueio.getDataFim()));
            new LogDAO().inserir(appLog);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Agendamento salvo com sucesso!");
        } catch (AplicacaoException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar salvar o agendamento. Entre em contato com o NTO");
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward bloquear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            Usuario funcionario = new UsuarioBO().obterPorPk(Integer.valueOf(id));
            new AutenticacaoSenhaBO().bloquearAcessos(funcionario, true);
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            Log appLog = new Log();
            appLog.setLogin(usuario.getUsername());
            appLog.setDescricao(usuario.getUsername() + " bloqueou " + funcionario.getLogin());
            new LogDAO().inserir(appLog);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Funcionário bloqueado com sucesso!");
        } catch (AplicacaoException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar bloquear o funcionário. Entre em contato com o NTO");
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward desbloquear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            Usuario funcionario = new UsuarioBO().obterPorPk(Integer.valueOf(id));
            new AutenticacaoSenhaBO().bloquearAcessos(funcionario, false);
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            Log appLog = new Log();
            appLog.setLogin(usuario.getUsername());
            appLog.setDescricao(usuario.getUsername() + " desbloqueou " + funcionario.getLogin());
            new LogDAO().inserir(appLog);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Funcionário desbloqueado com sucesso!");
        } catch (AplicacaoException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar desbloquear o funcionário. Entre em contato com o NTO");
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }
    
    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
           DynaActionForm dyna = (DynaActionForm) form;
        try {
            BloqueioAfastamentoAD bloqueio = (BloqueioAfastamentoAD) dyna.get("bloqueio");
            new BloqueioAfastamentoSEBO().excluir(bloqueio);
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            Log appLog = new Log();
            appLog.setLogin(usuario.getUsername());
            appLog.setDescricao(usuario.getUsername() + " excluiu o agendamento de bloqueio do usuario " + bloqueio.getUsuario().getLogin());
            new LogDAO().inserir(appLog);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Exclusão efetuada com sucesso!");
        } catch (AplicacaoException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar excluir o agendamento. Entre em contato com o NTO");
            Logger.getLogger(AutenticacaoSenhaAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }
}