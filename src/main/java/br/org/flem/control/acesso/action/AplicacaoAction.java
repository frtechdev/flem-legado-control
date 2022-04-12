package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.AplicacaoBO;
import br.org.flem.control.acesso.dao.AplicacaoDAO;
import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
public class AplicacaoAction extends DispatchAction {

    /**
     * Creates a new instance of AplicacaoAction
     */
    public AplicacaoAction() {
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String nome = request.getParameter("nome");
        if (nome == null) {
            nome = (String) request.getSession().getAttribute("nome");
        }
        try{           
            request.getSession().setAttribute("nome", nome);
            request.setAttribute("lista", new AplicacaoBO().obterDepartamentosPorNomeCurtoOuPorNome(nome));
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapping.findForward("lista");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        if (GenericValidator.isInt(id)) {
            Aplicacao app = new AplicacaoBO().obterPorPk(Integer.valueOf(id));
            PropertyUtils.copyProperties(dyna, app);
        }
        return mapping.findForward("seleciona");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Aplicacao app = new Aplicacao();
        //PropertyUtils.copyProperties(app, dyna);
        
        String nome = (String) dyna.get("nome");
        String nomeCurto = (String) dyna.get("nomeCurto");
        String descricao = (String) dyna.get("descricao");
        String versao = (String) dyna.get("versao");
        String linguagem = (String) dyna.get("linguagem");
        String servidor = (String) dyna.get("servidor");
        String observacao = (String) dyna.get("observacao");

        app.setNome(nome);
        app.setNomeCurto(nomeCurto);
        app.setDescricao(descricao);
        app.setVersao(versao);
        app.setLinguagem(linguagem);
        app.setServidor(servidor);
        app.setObservacao(observacao);
        
        try {
            new AplicacaoBO().inserir(app);
            ArrayList msg = new ArrayList();
            msg.add("Inclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a inclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Aplicacao.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Aplicacao app = new Aplicacao();
        PropertyUtils.copyProperties(app, dyna);
        try {
            new AplicacaoBO().alterar(app);
            ArrayList msg = new ArrayList();
            msg.add("Alteração realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a alteração!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "AplicativoIntranet.do");
        return mapping.findForward("redirect");
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String[] id = new String[0];

        if (request.getParameterValues("ids_exclusao") != null) {
            id = request.getParameterValues("ids_exclusao");
        }

        Collection<Aplicacao> aplicacoes = new ArrayList<Aplicacao>();

        for (int i = 0; i < id.length; i++) {
            Aplicacao aplicacao = new Aplicacao();
            aplicacao.setId(Integer.valueOf(id[i]));
            aplicacoes.add(aplicacao);
        }

        try {
           new AplicacaoBO().excluir(aplicacoes);
            ArrayList msg = new ArrayList();
            msg.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);

        } catch (Exception ex) {
            ex.printStackTrace();
            ArrayList erros = new ArrayList();
            erros.add("Não foi possível realizar a exclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "Aplicacao.do");
        return mapping.findForward("redirect");
    }
}
