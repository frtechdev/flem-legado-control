package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.PerfilBO;
import br.org.flem.control.acesso.bo.UsuarioPerfilBO;
import br.org.flem.control.acesso.negocio.Perfil;
import br.org.flem.control.acesso.negocio.UsuarioPerfil;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mppassos
 */
public class PerfilAction extends DispatchAction {

    /**
     * Creates a new instance of AplicacaoAction
     */
    public PerfilAction() {
    }

    /**
     * A Execução desse metodo ocorre quando não é passado nenhum metodo como
     * parametro.
     *
     * Leva para tela de Listar Perfil
     *
     */
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String nomePerfilForm = request.getParameter("nomePerfilForm");
        if (nomePerfilForm == null) {
            nomePerfilForm = (String) request.getSession().getAttribute("nomePerfilForm");
        }
        try {
            request.getSession().setAttribute("nomePerfilForm", nomePerfilForm);
            request.setAttribute("lista", new PerfilBO().obterFiltroNome(nomePerfilForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("lista");
    }

    /**
     *
     * Ao clicar no botão de alterar. Leva para tela de Alterar Perfil
     *
     */
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        if (GenericValidator.isInt(id)) {
            Perfil prf = new PerfilBO().obterPorPk(Integer.valueOf(id));
            PropertyUtils.copyProperties(dyna, prf);
        }
        return mapping.findForward("seleciona");
    }

    /**
     *
     * Ao clicar em novo.
     *
     * Leva para tela de novo perfil.
     *
     */
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("novo");
    }

    /**
     *
     * Adiciona um perfil novo
     *
     *
     */
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Perfil prf = new Perfil();
        PropertyUtils.copyProperties(prf, dyna);
        try {
            new PerfilBO().inserir(prf);
            ArrayList msg = new ArrayList();
            msg.add("Inclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a inclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Perfil.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        PerfilBO dao = new PerfilBO();
        Perfil prf = new Perfil();
        PropertyUtils.copyProperties(prf, dyna);
        try {
            new PerfilBO().alterar(prf);
            ArrayList msg = new ArrayList();
            msg.add("Alteração realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a alteração!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Perfil.do");
        return mapping.findForward("redirect");
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String[] id = new String[0];

        if (request.getParameterValues("ids_exclusao") != null) {
            id = request.getParameterValues("ids_exclusao");
        }

        Collection<Perfil> perfis = new ArrayList<Perfil>();

        for (int i = 0; i < id.length; i++) {
            Perfil perfil = new Perfil();
            perfil.setId(Integer.valueOf(id[i]));
            perfis.add(perfil);
        }

        try {
            new PerfilBO().excluir(perfis);
            ArrayList msg = new ArrayList();
            msg.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);

        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList erros = new ArrayList();
            erros.add("Não foi possível realizar a exclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "Perfil.do");
        return mapping.findForward("redirect");
    }

    public ActionForward funcionalidade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        Perfil prf = new Perfil();
        if (GenericValidator.isInt(id)) {
            prf = new PerfilBO().obterPorPk(Integer.valueOf(id));
            PropertyUtils.copyProperties(dyna, prf);
            request.getSession().setAttribute("perfil", dyna);
        }
        request.setAttribute("lista", prf.getFuncionalidade());
        return mapping.findForward("funcionalidadePerfil");
    }
    
    public ActionForward usuarios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        
        Perfil per =  new PerfilBO().obterPorPk(Integer.parseInt(id));
        request.setAttribute("perfil", per);
        Collection<UsuarioPerfil> user = new UsuarioPerfilBO().obterUsuarioPorPerfil(Integer.parseInt(id));
        
        request.setAttribute("lista", user);

        return mapping.findForward("usuarios");
    }
}
