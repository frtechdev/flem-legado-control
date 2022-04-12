package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.CentroCustoUsuarioBO;
import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.CentroCustoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dao.legado.gem.CentroCustoDAO;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mccosta
 */
public class CentroCustoUsuarioAction extends DispatchAction {

    public CentroCustoUsuarioAction() {
    }

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String nomeCentroCustoUsuario = request.getParameter("nomeCentroCustoUsuario");
        
        if (nomeCentroCustoUsuario == null) {
            nomeCentroCustoUsuario = (String) request.getSession().getAttribute("nomeCentroCustoUsuario");
        }
        Integer idUsuario = null;
        Usuario usuario = null;

        try {
            idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
            if(id == null){
                usuario = new UsuarioDAO().obterPorPk(idUsuario);
            }else{
                usuario = new UsuarioDAO().obterPorPk(Integer.valueOf(id));
            }

            //Map map = new CentroCustoDAO().obterCentroDeCustoMap();
            Map<String, CentroResponsabilidade> mapCc = new HashMap<String, CentroResponsabilidade>();
            if (nomeCentroCustoUsuario == null || nomeCentroCustoUsuario.isEmpty()) {
                mapCc = new CentroCustoDAO().obterCentroDeCustoMap();
            } else {
                mapCc = new CentroCustoDAO().obterCentroDeCustoMapByCcs(nomeCentroCustoUsuario);
            }

            Collection<CentroCustoUsuario> usuarioCentros = new CentroCustoUsuarioBO().obterPorUsuario(usuario);

            for (CentroCustoUsuario cc : usuarioCentros) {
                if (mapCc.containsKey(cc.getCodigoCentroCusto())) {
                    mapCc.remove(cc.getCodigoCentroCusto());
                }
            }

            request.getSession().setAttribute("idUsuario", usuario.getId());
            request.setAttribute("usuario", usuario);
            request.setAttribute("listaCentroCustoUsuario", usuarioCentros);
            request.setAttribute("listaCentroCustos", mapCc.values());
            request.getSession().setAttribute("nomeCentroCustoUsuario", nomeCentroCustoUsuario);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("lista");
    }

    public ActionForward removerCentroCusto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario usuario = new Usuario();
        String id = request.getParameter("id");
        String codigo = request.getParameter("codigo");

        try {
            Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
            if (idUsuario == null) {
                usuario = new UsuarioDAO().obterPorPk(Integer.valueOf(id));
            } else {
                usuario = new UsuarioDAO().obterPorPk(idUsuario);
            }

            CentroCustoUsuarioBO centroCustoUsuarioBO = new CentroCustoUsuarioBO();
            CentroCustoUsuario centroCustoUsuario = centroCustoUsuarioBO.obterPorUsuarioCodigo(usuario, codigo);

            HibernateUtil.beginTransaction();
            centroCustoUsuarioBO.excluir(centroCustoUsuario);
            HibernateUtil.commitTransaction();

            Map map = new CentroCustoDAO().obterCentroDeCustoMap();
            Collection<CentroCustoUsuario> usuarioCentros = new CentroCustoUsuarioBO().obterPorUsuario(usuario);
            for (CentroCustoUsuario cc : usuarioCentros) {
                if (map.containsKey(cc.getCodigoCentroCusto())) {
                    map.remove(cc.getCodigoCentroCusto());
                }
            }

            request.getSession().setAttribute("idUsuario", usuario.getId());
            request.setAttribute("listaCentroCustoUsuario", usuarioCentros);
            request.setAttribute("listaCentroCustos", map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("redirect");
    }

    public ActionForward adicionarCentroCusto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario usuario = null;
        String id = request.getParameter("id");
        String codigo = request.getParameter("codigo");

        try {
            Integer idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
            if (idUsuario == null) {
                usuario = new UsuarioDAO().obterPorPk(Integer.valueOf(id));
            } else {
                usuario = new UsuarioDAO().obterPorPk(idUsuario);
            }


            CentroCustoUsuario centroCustoUsuario = new CentroCustoUsuario();
            CentroResponsabilidade centroResponsabilidade = new CentroCustoDAO().obterCentroCustoOuResponsabilidadePorId(codigo);

            centroCustoUsuario.setCodigoCentroCusto(codigo);
            centroCustoUsuario.setNomeCentroCusto(centroResponsabilidade.getDescricaoCompleta());
            centroCustoUsuario.setUsuario(usuario);

            HibernateUtil.beginTransaction();
            new CentroCustoUsuarioBO().inserir(centroCustoUsuario);
            HibernateUtil.commitTransaction();

            Map map = new CentroCustoDAO().obterCentroDeCustoMap();
            Collection<CentroCustoUsuario> usuarioCentros = new CentroCustoUsuarioBO().obterPorUsuario(usuario);
            for (CentroCustoUsuario cc : usuarioCentros) {
                if (map.containsKey(cc.getCodigoCentroCusto())) {
                    map.remove(cc.getCodigoCentroCusto());
                }
            }

            request.getSession().setAttribute("idUsuario", usuario.getId());
            request.setAttribute("listaCentroCustoUsuario", usuarioCentros);
            request.setAttribute("listaCentroCustos", map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("redirect");
    }
}
