package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.Perfil;
import br.org.flem.control.acesso.negocio.Usuario;
import java.util.List;
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
 * @author mppassos
 */
public class UsuarioPerfilAction extends DispatchAction {

    /**
     * Creates a new instance of PerfilFuncionalidadeAction
     */
    public UsuarioPerfilAction() {
    }

    /**
     * A Execução desse metodo ocorre quando não é passado nenhum metodo como
     * parametro.
     *
     * Leva para tela de Listar Perfil
     *
     */
    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usr = new Usuario();

        String NomePerfil = request.getParameter("nomePerfilForm");

        if (NomePerfil == null) {
            NomePerfil = (String) request.getSession().getAttribute("nomePerfilForm");
        }
        
        Map map = new PerfilDAO().obterTodosMap();
        if (NomePerfil == null || NomePerfil.isEmpty()) {
            if (usr.getPerfil() != null) {
                for (Perfil p : usr.getPerfil()) {
                    if (map.containsKey(p.getId())) {
                        map.remove(p.getId());
                    }
                }
            }
            request.setAttribute("listaPerfis", map.values());
        }else{
            request.setAttribute("listaPerfis", new PerfilDAO().obterFiltroNome(NomePerfil));
        }
        if (GenericValidator.isInt(id)) {
            usr = dao.obterPorPk(Integer.valueOf(id));
        } else {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usr");
            usr = dao.obterPorPk(usuario.getId());
        }
        request.getSession().setAttribute("usr", usr);

        request.setAttribute("listaUsuario", usr.getPerfil());
        request.setAttribute("id", id);
        request.getSession().setAttribute("nomePerfilForm", NomePerfil);
        return mapping.findForward("lista");
    }

    public ActionForward removerPerfil(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String NomePerfil = request.getParameter("nomePerfilForm");
        if (NomePerfil == null) {
            NomePerfil = (String) request.getSession().getAttribute("nomePerfilForm");
        }
        Usuario usr = (Usuario) request.getSession().getAttribute("usr");
        String prf = request.getParameter("prf");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PerfilDAO perfilDAO = new PerfilDAO();
        usr = usuarioDAO.obterPorPk(usr.getId());
        Perfil perfil = perfilDAO.obterPorPk(Integer.valueOf(prf));
        usr.getPerfil().remove(perfil);
        usuarioDAO.alterar(usr);
        usr = usuarioDAO.obterPorPk(usr);
        
        request.getSession().setAttribute("usr", usr);
        request.setAttribute("listaUsuario", usr.getPerfil());
        request.setAttribute("listaPerfis",  new PerfilDAO().obterFiltroNome(NomePerfil));
        request.getSession().setAttribute("nomePerfilForm", NomePerfil);
        return mapping.findForward("lista");
    }

    public ActionForward adicionarPerfil(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String NomePerfil = request.getParameter("nomePerfilForm");
        if (NomePerfil == null) {
            NomePerfil = (String) request.getSession().getAttribute("nomePerfilForm");
        }
        Usuario usr = (Usuario) request.getSession().getAttribute("usr");
        String prf = request.getParameter("prf");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PerfilDAO perfilDAO = new PerfilDAO();
        usr = usuarioDAO.obterPorPk(usr.getId());
        Perfil perfil = perfilDAO.obterPorPk(Integer.valueOf(prf));
        usr.getPerfil().add(perfil);
        usuarioDAO.alterar(usr);
        usr = usuarioDAO.obterPorPk(usr);

        request.getSession().setAttribute("usr", usr);
        request.setAttribute("listaUsuario", usr.getPerfil());
        request.setAttribute("listaPerfis",   new PerfilDAO().obterFiltroNome(NomePerfil));
        request.getSession().setAttribute("nomePerfilForm", NomePerfil);
        return mapping.findForward("lista");
    }
}
