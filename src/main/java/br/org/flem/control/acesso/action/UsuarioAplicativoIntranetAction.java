/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.dao.AplicacaoAcessoIntranetDAO;
import br.org.flem.control.acesso.dao.AplicacaoIntranetDAO;
import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import br.org.flem.control.acesso.negocio.Perfil;
import br.org.flem.control.acesso.negocio.Usuario;
import java.util.Collection;
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
public class UsuarioAplicativoIntranetAction extends DispatchAction {

    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usr = new Usuario();

        String NomeIntranet = request.getParameter("nomeAplicacaoIntranet");

        if (NomeIntranet == null) {
            NomeIntranet = (String) request.getSession().getAttribute("nomeAplicacaoIntranet");
        }

        if (GenericValidator.isInt(id)) {
            usr = dao.obterPorPk(Integer.valueOf(id));
        } else {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usr");
            usr = dao.obterPorPk(usuario.getId());
        }



        request.getSession().setAttribute("usr", usr);

        Collection<AplicativoIntranet> app = new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(usr.getId());


        if (NomeIntranet == null || NomeIntranet.isEmpty()) {
            Collection<AplicativoIntranet> appn = new AplicacaoAcessoIntranetDAO().ObterAcessoNaoCadastrado(usr.getId());
            request.setAttribute("listaSemAplicativos", appn);
        }else{
            request.setAttribute("listaSemAplicativos", new AplicacaoAcessoIntranetDAO().obterFiltroPorNomeIntranet(NomeIntranet) );
        }


        request.setAttribute("listaComAplicativos", app);
        request.getSession().setAttribute("nomeAplicacaoIntranet", NomeIntranet);
        return mapping.findForward("listaIntranet");
    }

    public ActionForward removerPerfilIntranet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario usr = (Usuario) request.getSession().getAttribute("usr");
        String idapp = request.getParameter("idapp");


        AplicativoIntranet aplicativo = new AplicativoIntranet();
        AplicacaoIntranetDAO api = new AplicacaoIntranetDAO();
        aplicativo = api.ObterPorPK(Integer.parseInt(idapp));
        AplicacaoAcessoIntranetDAO aplic = new AplicacaoAcessoIntranetDAO();
        aplic.RemoveAcesso(usr.getId(), aplicativo);


        Collection<AplicativoIntranet> app = new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(usr.getId());
        Collection<AplicativoIntranet> appn = new AplicacaoAcessoIntranetDAO().ObterAcessoNaoCadastrado(usr.getId());

        request.getSession().setAttribute("usr", usr);
        request.setAttribute("listaSemAplicativos", appn);
        request.setAttribute("listaComAplicativos", app);
        return mapping.findForward("redirect");
    }

    public ActionForward adicionarPerfilIntranet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario usr = (Usuario) request.getSession().getAttribute("usr");
        String idapp = request.getParameter("idapp");


        AplicativoIntranet aplicativo = new AplicativoIntranet();
        AplicacaoIntranetDAO api = new AplicacaoIntranetDAO();
        aplicativo = api.ObterPorPK(Integer.parseInt(idapp));
        AplicacaoAcessoIntranetDAO aplic = new AplicacaoAcessoIntranetDAO();
        aplic.AdicionarAcesso(usr.getId(), aplicativo);


        Collection<AplicativoIntranet> app = new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(usr.getId());
        Collection<AplicativoIntranet> appn = new AplicacaoAcessoIntranetDAO().ObterAcessoNaoCadastrado(usr.getId());


        request.getSession().setAttribute("usr", usr);
        request.setAttribute("listaSemAplicativos", appn);
        request.setAttribute("listaComAplicativos", app);
        return mapping.findForward("redirect");
    }
}
