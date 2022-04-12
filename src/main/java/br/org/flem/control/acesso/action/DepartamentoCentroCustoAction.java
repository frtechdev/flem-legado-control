package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.DepartamentoCentroCustoBO;
import br.org.flem.control.acesso.bo.DepartamentoTipoSolicitacaoBO;
import br.org.flem.control.acesso.negocio.DepartamentoCentroCusto;
import br.org.flem.control.acesso.negocio.DepartamentoTipoSolicitacao;
import br.org.flem.fw.persistencia.dao.legado.gem.CentroCustoDAO;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mppassos
 */
public class DepartamentoCentroCustoAction extends DispatchAction {

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String nomeDepartamentoCentroCusto = request.getParameter("nomeDepartamentoCentroCusto");
        List<Departamento> lista;

        if (nomeDepartamentoCentroCusto == null) {
            nomeDepartamentoCentroCusto = (String) request.getSession().getAttribute("nomeDepartamentoCentroCusto");
        }
        try {
            if (nomeDepartamentoCentroCusto == null || nomeDepartamentoCentroCusto.isEmpty()) {
                lista = new RHServico().obterDepartamentosERPServico();
            } else {
                lista = new RHServico().obterDepartamentosErpServicoPorCodigo(nomeDepartamentoCentroCusto);
            }


            request.getSession().setAttribute("nomeDepartamentoCentroCusto", nomeDepartamentoCentroCusto);
            request.setAttribute("listadepartamento", lista);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("lista");
    }
    

    public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String ccs = request.getParameter("centroCusto");

        if (ccs == null) {
            ccs = (String) request.getSession().getAttribute("centroCusto");
        }

        try {
            String depId = null;
            Departamento departamento;
            
            depId = (String) request.getSession().getAttribute("depId");
            
            if (id != null) {
                departamento = new RHServico().obterDepartamentoErpServicoPorCodigoDominio(Integer.parseInt(id));
            } else {
                departamento = new RHServico().obterDepartamentoErpServicoPorCodigoDominio(Integer.parseInt(depId));
            }

            Map<String, CentroResponsabilidade> mapCc;
            if (ccs == null || ccs.isEmpty()) {
                mapCc = new CentroCustoDAO().obterCentroDeCustoMap();
            } else {
                mapCc = new CentroCustoDAO().obterCentroDeCustoMapByCcs(ccs);
            }

            Collection<DepartamentoCentroCusto> departamentoCentroCustos = new DepartamentoCentroCustoBO().obterTodosPorDepartamento(departamento.getCodigoDominio());

            for (DepartamentoCentroCusto d : departamentoCentroCustos) {
                if (mapCc.containsKey(d.getCodigoCentroCusto())) {
                    mapCc.remove(d.getCodigoCentroCusto());
                }
            }

            request.setAttribute("depCcNome", departamento.getNome());
            request.getSession().setAttribute("depId", departamento.getCodigoDominio().toString());
            request.setAttribute("listaCentroCustosDepartamento", departamentoCentroCustos);
            request.setAttribute("listaCentroCustos", mapCc.values());
            request.getSession().setAttribute("centroCusto", ccs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("editar");
    }

    public ActionForward tipo_solicitacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String codigoDominio = request.getParameter("codigo");

        DepartamentoTipoSolicitacao departamentoTipoSolicitacao = new DepartamentoTipoSolicitacaoBO().obterPorLotacaoDominio(Integer.parseInt(codigoDominio));

        dyna.set("tipoSolicitacao", departamentoTipoSolicitacao != null ? departamentoTipoSolicitacao.getTipoSolicitacao().getId() : null);
        dyna.set("liberacaoViagemRetroativo", departamentoTipoSolicitacao != null ? departamentoTipoSolicitacao.getLiberacaoViagemRetroativo() : null);

        request.setAttribute("tiposSolicitacao", TipoSolicitacao.toCollection());
        request.setAttribute("codigo", codigoDominio);
        request.setAttribute("mapDep", new RHServico().obterDepartamentosMapIndexCodigo());

        return mapping.findForward("tipo_solicitacao");
    }

    public ActionForward salvar_tipo_solicitacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String codigoDominio = request.getParameter("codigo");
        DepartamentoTipoSolicitacaoBO departamentoTipoSolicitacaoBO = new DepartamentoTipoSolicitacaoBO();
        DepartamentoTipoSolicitacao departamentoTipoSolicitacao = null;

        departamentoTipoSolicitacao = departamentoTipoSolicitacaoBO.obterPorLotacaoDominio(Integer.parseInt(codigoDominio));
        if (departamentoTipoSolicitacao == null) {
            departamentoTipoSolicitacao = new DepartamentoTipoSolicitacao();
            departamentoTipoSolicitacao.setLotacaoDominio(Integer.parseInt(codigoDominio));
        }
        departamentoTipoSolicitacao.setTipoSolicitacao(TipoSolicitacao.getById((Integer) ((DynaActionForm) form).get("tipoSolicitacao")));
        departamentoTipoSolicitacao.setLiberacaoViagemRetroativo((Boolean) ((DynaActionForm) form).get("liberacaoViagemRetroativo"));

        try {
            departamentoTipoSolicitacaoBO.inserirOuAlterar(departamentoTipoSolicitacao);
            ArrayList msg = new ArrayList();
            msg.add("Departamento alterado com sucesso");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Erro ao salvar");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        return mapping.findForward("redirect_lista");
    }

    public ActionForward removerCentroCusto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = (String) request.getSession().getAttribute("depId");
        String codigo = request.getParameter("codigo");

        DepartamentoCentroCusto departamentoCentroCusto = new DepartamentoCentroCustoBO().obterPorDepartamentoCentroCusto(id, codigo);

        HibernateUtil.beginTransaction();
        new DepartamentoCentroCustoBO().excluir(departamentoCentroCusto);
        HibernateUtil.commitTransaction();

        return mapping.findForward("redirect");
    }

    public ActionForward adicionarCentroCusto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = (String) request.getSession().getAttribute("depId");
        String codigo = request.getParameter("codigo");

        DepartamentoCentroCusto departamentoCentroCusto = new DepartamentoCentroCusto();
        Departamento departamento = new RHServico().obterDepartamentoPorCodigo(id);
        CentroResponsabilidade centroResponsabilidade = new CentroCustoDAO().obterCentroCustoOuResponsabilidadePorId(codigo);

        departamentoCentroCusto.setCodigoDepartamento(departamento.getCodigoHR());
        departamentoCentroCusto.setCodigoDepartamentoDominio(departamento.getCodigoDominio());
        departamentoCentroCusto.setNomeDepartamento(departamento.getNome());
        departamentoCentroCusto.setCodigoCentroCusto(centroResponsabilidade.getId());
        departamentoCentroCusto.setNomeCentroCusto(centroResponsabilidade.getDescricaoCompleta());

        HibernateUtil.beginTransaction();
        new DepartamentoCentroCustoBO().inserir(departamentoCentroCusto);
        HibernateUtil.commitTransaction();

        return mapping.findForward("redirect");
    }
}