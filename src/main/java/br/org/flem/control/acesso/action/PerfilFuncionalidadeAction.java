package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.AplicacaoBO;
import br.org.flem.control.acesso.dao.FuncionalidadeDAO;
import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.negocio.Funcionalidade;
import br.org.flem.control.acesso.negocio.Perfil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mjpereira
 */
public class PerfilFuncionalidadeAction extends DispatchAction {
    String idGlobal = null;
    /**
     * Creates a new instance of PerfilFuncionalidadeAction
     */
    public PerfilFuncionalidadeAction() {
    }

    /**
     * A Execução desse metodo ocorre quando não é passado nenhum metodo como parametro.
     *
     * Leva para tela de Listar Perfil
     *
     */
    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        PerfilDAO dao = new PerfilDAO();
        Perfil prf = new Perfil();
        if (GenericValidator.isInt(id)) {
            prf = dao.obterPorPk(Integer.valueOf(id));
        } else {
            Perfil perfil = (Perfil) request.getSession().getAttribute("prf");
            prf = dao.obterPorPk(perfil.getId());
        }
        request.getSession().setAttribute("prf", prf);
        //Map map = new FuncionalidadeDAO().obterTodosMap();
        String idAplicacao = request.getParameter("idAplicacao");
        idGlobal = idAplicacao;
        Map map = new FuncionalidadeDAO().obterPorIdAplicacaoMap(GenericValidator.isInt(idAplicacao) ? Integer.parseInt(idAplicacao) : null);
        for (Funcionalidade f : prf.getFuncionalidade()) {
            if (map.containsKey(f.getId())) {
                map.remove(f.getId());
            }
        }
        request.setAttribute("listaPerfil", prf.getFuncionalidade());
        request.setAttribute("listaFuncionalidades", map.values());
        request.setAttribute("listaDeAplicacoes", new AplicacaoBO().obterTodos());
        return mapping.findForward("lista");
    }

    public ActionForward removerAcesso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Perfil perfil = (Perfil) request.getSession().getAttribute("prf");
        String fun = request.getParameter("fun");
        PerfilDAO perfilDAO = new PerfilDAO();
        FuncionalidadeDAO funcionalidadeDAO = new FuncionalidadeDAO();
        perfil = perfilDAO.obterPorPk(perfil.getId());
        Funcionalidade funcionalidade = funcionalidadeDAO.obterPorPk(Integer.valueOf(fun));
        perfil.getFuncionalidade().remove(funcionalidade);
        perfilDAO.alterar(perfil);
        perfil = perfilDAO.obterPorPk(perfil);
        Map map = funcionalidadeDAO.obterTodosMap();
        for (Funcionalidade f : perfil.getFuncionalidade()) {
            if (map.containsKey(f.getId())) {
                map.remove(f.getId());
            }
        }
        request.getSession().setAttribute("prf", perfil);
        request.setAttribute("listaPerfil", perfil.getFuncionalidade());
        request.setAttribute("listaFuncionalidades", map.values());
        request.setAttribute("listaDeAplicacoes", new AplicacaoBO().obterTodos());
        return mapping.findForward("lista");
    }

    public ActionForward adicionarAcesso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Perfil perfil = (Perfil) request.getSession().getAttribute("prf");
        String fun = request.getParameter("fun");
        PerfilDAO perfilDAO = new PerfilDAO();
        FuncionalidadeDAO funcionalidadeDAO = new FuncionalidadeDAO();
        perfil = perfilDAO.obterPorPk(perfil.getId());
        Funcionalidade funcionalidade = funcionalidadeDAO.obterPorPk(Integer.valueOf(fun));
        perfil.getFuncionalidade().add(funcionalidade);
        perfilDAO.alterar(perfil);
        perfil = perfilDAO.obterPorPk(perfil);
        Map map = new FuncionalidadeDAO().obterPorIdAplicacaoMap(GenericValidator.isInt(idGlobal) ? Integer.parseInt(idGlobal) : null);
        for (Funcionalidade f : perfil.getFuncionalidade()) {
            if (map.containsKey(f.getId())) {
                map.remove(f.getId());
            }
        }
        request.getSession().setAttribute("prf", perfil);
        request.setAttribute("listaPerfil", perfil.getFuncionalidade());
        request.setAttribute("listaFuncionalidades", map.values());
        request.setAttribute("listaDeAplicacoes", new AplicacaoBO().obterTodos());
        return mapping.findForward("lista");
    }
}
