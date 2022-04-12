/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.AplicacaoBO;
import br.org.flem.control.acesso.bo.AplicacaoIntraneteBO;
import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.control.acesso.negocio.AplicativoIntranet;
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
public class AplicativoIntranetAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String nomeAplicacaoIntranet = request.getParameter("nomeAplicacaoIntranet");
        if (nomeAplicacaoIntranet == null) {
            nomeAplicacaoIntranet = (String) request.getSession().getAttribute("nomeAplicacaoIntranet");
        }
        try {
            request.getSession().setAttribute("nomeAplicacaoIntranet", nomeAplicacaoIntranet);
            request.setAttribute("listaintranet", new AplicacaoIntraneteBO().ObterNomeAplicativoPorFiltro(nomeAplicacaoIntranet));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("listaintranet");

    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("novo");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        if (GenericValidator.isInt(id)) {
            AplicativoIntranet app = new AplicacaoIntraneteBO().ObterPorPK(Integer.valueOf(id));
            PropertyUtils.copyProperties(dyna, app);
        }
        return mapping.findForward("seleciona");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        AplicativoIntranet app = new AplicativoIntranet();

        //PropertyUtils.copyProperties(app,dyna);
        String nome = (String) dyna.get("nome");
        String titulo = (String) dyna.get("titulo");
        String url = (String) dyna.get("url");
        String target = (String) dyna.get("target");
        String imagem = (String) dyna.get("imagem");
        String ordem = (String) dyna.get("ordem");
        String ativo = (String) dyna.get("ativo");
        String seguranca = (String) dyna.get("seguranca");

        app.setNome(nome);
        app.setTitulo(titulo);
        app.setUrl(url);
        app.setTarget(target);
        app.setImagem(imagem);
        app.setOrdem(ordem);
        app.setAtivo(ativo);
        app.setSeguranca(seguranca);

        try {
            new AplicacaoIntraneteBO().Inserir(app);
            ArrayList msg = new ArrayList();
            msg.add("Inclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a inclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "AplicacaoIntranet.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        AplicativoIntranet app = new AplicativoIntranet();
        PropertyUtils.copyProperties(app, dyna);
        try {
            new AplicacaoIntraneteBO().Atualizar(app);
            ArrayList msg = new ArrayList();
            msg.add("Alteração realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a alteração!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Aplicacao.do");
        return mapping.findForward("redirect");
    }
}
