package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.DepartamentoUsuarioBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.DepartamentoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
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
public class DepartamentoUsuarioAction extends DispatchAction {

    public DepartamentoUsuarioAction() {
    }

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String nomeDepartamento = request.getParameter("nomeDepartamento");
        if (nomeDepartamento == null) {
            nomeDepartamento = (String) request.getSession().getAttribute("nomeDepartamento");
        }
        RHServico rh =  new RHServico();

        try {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            if (usuario == null || GenericValidator.isInt(id)) {
                usuario = new UsuarioBO().obterPorPk(Integer.valueOf(id));
            } else {
                usuario = new UsuarioBO().obterPorPk(usuario.getId());
            }
            
            Map map = null;

            if (nomeDepartamento == null || nomeDepartamento.isEmpty()) {
                map = rh.obterDepartamentosErpServicoMap();
            } else {
                map = rh.obterMapDepartamentosErpServicoPorNome(nomeDepartamento);
            }
            
            
            
            Collection<DepartamentoUsuario> usuarioDepartamentos = new DepartamentoUsuarioBO().obterPorUsuario(usuario);

            for (DepartamentoUsuario d : usuarioDepartamentos) {
                if (map.containsKey(d.getCodigoDepartamentoDominio().toString())) {
                    map.remove(d.getCodigoDepartamentoDominio().toString());
                }
            }

            request.getSession().setAttribute("usuario", usuario);
            
            request.setAttribute("listaDepartamentoUsuario", usuarioDepartamentos);
            request.setAttribute("listaDepartamentos", map.values());
            request.setAttribute("mapdepartamento", rh.obterDepartamentosMapIndexCodigo());

            request.getSession().setAttribute("nomeDepartamento", nomeDepartamento);
            


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("lista");
    }

    public ActionForward removerDepartamento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario usuario = new Usuario();
        String id = request.getParameter("id");
        String codigo = request.getParameter("codigo");

        try {
            usuario = (Usuario) request.getSession().getAttribute("usuario");
            if (usuario == null) {
                usuario = new UsuarioDAO().obterPorPk(Integer.valueOf(id));
            } else {
                usuario = new UsuarioDAO().obterPorPk(usuario.getId());
            }

            DepartamentoUsuarioBO departamentoUsuarioBO = new DepartamentoUsuarioBO();
            DepartamentoUsuario departamentoUsuario = departamentoUsuarioBO.obterPorUsuarioCodigo(usuario, codigo);

            HibernateUtil.beginTransaction();
            departamentoUsuarioBO.excluir(departamentoUsuario);
            HibernateUtil.commitTransaction();

            Map map = new RHServico().obterDepartamentosMapIndexCodigo();
            Collection<DepartamentoUsuario> usuarioDepartamentos = new DepartamentoUsuarioBO().obterPorUsuario(usuario);
            for (DepartamentoUsuario d : usuarioDepartamentos) {
                if (map.containsKey(d.getCodigoDepartamento())) {
                    map.remove(d.getCodigoDepartamento());
                }
            }

            request.getSession().setAttribute("usuario", usuario);
            request.setAttribute("listaDepartamentoUsuario", usuarioDepartamentos);
            request.setAttribute("listaDepartamentos", map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("redirect");
    }

    public ActionForward adicionarDepartamento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        Integer codigoDominio = Integer.parseInt(request.getParameter("codigo"));

        try {
            RHServico rh = new RHServico();
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            if (usuario == null) {
                usuario = new UsuarioDAO().obterPorPk(Integer.valueOf(id));
            } else {
                usuario = new UsuarioDAO().obterPorPk(usuario.getId());
            }

            DepartamentoUsuario departamentoUsuario = new DepartamentoUsuario();
            Departamento departamento = rh.obterDepartamentoErpServicoPorCodigoDominio(codigoDominio);

            departamentoUsuario.setCodigoDepartamentoDominio(codigoDominio);
            departamentoUsuario.setNomeDepartamento(departamento.getNome());
            departamentoUsuario.setUsuario(usuario);

            HibernateUtil.beginTransaction();
            new DepartamentoUsuarioBO().inserir(departamentoUsuario);
            HibernateUtil.commitTransaction();

            Map map = rh.obterDepartamentosMapIndexCodigo();
            Collection<DepartamentoUsuario> usuarioDepartamentos = new DepartamentoUsuarioBO().obterPorUsuario(usuario);
            for (DepartamentoUsuario d : usuarioDepartamentos) {
                if (map.containsKey(d.getCodigoDepartamento())) {
                    map.remove(d.getCodigoDepartamento());
                }
            }
  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("redirect");
    }
}
