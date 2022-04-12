package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.AplicacaoBO;
import br.org.flem.control.acesso.bo.FuncionalidadeBO;
import br.org.flem.control.acesso.bo.UsuarioPerfilBO;
import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.control.acesso.negocio.Funcionalidade;
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
 * @author mjpereira
 */
public class FuncionalidadeAction extends DispatchAction {

    public FuncionalidadeAction() {
    }

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String idAplicacao = request.getParameter("idAplicacao");
        request.setAttribute("lista", new FuncionalidadeBO().obterPorIdAplicacao(GenericValidator.isInt(idAplicacao) ? Integer.parseInt(idAplicacao) : null));
        
        request.setAttribute("listaDeAplicacoes", new AplicacaoBO().obterTodos());
        return mapping.findForward("lista");
    }

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return unspecified(mapping, form, request, response);
    }
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        if (GenericValidator.isInt(id)) {
            Funcionalidade fun = new FuncionalidadeBO().obterPorPk(Integer.valueOf(id));
            PropertyUtils.copyProperties(dyna, fun);
            dyna.set("listaAplicacao", new AplicacaoBO().obterTodos());
            request.setAttribute("aplicacao", fun.getAplicacao());

        }
        return mapping.findForward("seleciona");
    }
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        dyna.set("listaAplicacao", new AplicacaoBO().obterTodos());
        return mapping.findForward("novo");
    }
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Funcionalidade fun = new Funcionalidade();
        PropertyUtils.copyProperties(fun, dyna);
        try {
            fun.setAplicacao(new Aplicacao((Integer) dyna.get("idAplicacao")));
            new FuncionalidadeBO().inserir(fun);
            ArrayList msg = new ArrayList();
            msg.add("Inclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a inclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Funcionalidade.do");
        return mapping.findForward("redirect");
    }
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Funcionalidade fun = new Funcionalidade();
        PropertyUtils.copyProperties(fun, dyna);
        try {
            fun.setAplicacao(new Aplicacao((Integer) dyna.get("idAplicacao")));
            new FuncionalidadeBO().alterar(fun);
            ArrayList msg = new ArrayList();
            msg.add("Alteração realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a alteração!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Funcionalidade.do");
        return mapping.findForward("redirect");
    }
    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id = new String[0];

        if (request.getParameterValues("ids_exclusao") != null) {
            id = request.getParameterValues("ids_exclusao");
        }

        Collection<Funcionalidade> funcionalidades = new ArrayList<Funcionalidade>();

        for (int i = 0; i < id.length; i++) {
            Funcionalidade funcionalidade = new Funcionalidade();
            funcionalidade.setId(Integer.valueOf(id[i]));
            funcionalidades.add(funcionalidade);
        }

        try {
            new FuncionalidadeBO().excluir(funcionalidades);
            ArrayList msg = new ArrayList();
            msg.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);

        } catch (AcessoDadosException ade) {
            ade.printStackTrace();
            ArrayList erros = new ArrayList();
            erros.add("Não foi possível realizar a exclusão! Deve está associado a algum perfil.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "Funcionalidade.do");
        return mapping.findForward("redirect");
    }
    public ActionForward usuarios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        Funcionalidade fun = new FuncionalidadeBO().obterPorPk(Integer.parseInt(id));
        request.setAttribute("funcionalidade", fun);
        Collection<UsuarioPerfil> user = new UsuarioPerfilBO().obterUsuariosPorFuncionalidade(Integer.parseInt(id));
        
        request.setAttribute("lista", user);

        return mapping.findForward("usuarios");
    }
    
    
    
}
