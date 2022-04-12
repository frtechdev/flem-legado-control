package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.LegadoException;
import br.org.flem.fwe.exception.ValidacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.CriptografiaUtil;
import br.org.flem.fwe.validator.SenhaValidator;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.w3c.dom.events.Event;

/**
 *
 * @author mjpereira
 */
public class UsuarioAction extends DispatchAction {

    /**
     * Creates a new instance of AplicacaoAction
     */
    public UsuarioAction() {
    }

    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        //new RHServico().atualizarControl();
        String nomeFiltro = "", loginFiltro = "", lotacaoDominioFiltro = "";
        Boolean ativoFiltro = true;

        if (request.getSession().getAttribute("usuarioAction_filtro_login") != null) {
            loginFiltro = request.getSession().getAttribute("usuarioAction_filtro_login").toString();
        }

        if (request.getSession().getAttribute("usuarioAction_filtro_nome") != null) {
            nomeFiltro = request.getSession().getAttribute("usuarioAction_filtro_nome").toString();
        }

        if (request.getSession().getAttribute("usuarioAction_filtro_lotacaoDominio") != null) {
            lotacaoDominioFiltro = request.getSession().getAttribute("usuarioAction_filtro_lotacaoDominio").toString();
        }

        if (request.getSession().getAttribute("usuarioAction_filtro_situacao") != null) {
            ativoFiltro = (Boolean) request.getSession().getAttribute("usuarioAction_filtro_situacao");
        }

        try {
            RHServico rh = new RHServico();
            Map<String, Departamento> mapDepartamentos = new HashMap<String, Departamento>();
            Map<String, Departamento> todos = new HashMap<String, Departamento>();
            mapDepartamentos = rh.obterTodosDepartamentosMap();
            List<Usuario> usuariosList = new UsuarioDAO().obterTodos();

            for (Usuario u : usuariosList) {
                if (u.getLotacaoDominio() != null) {
                    for (Departamento dd : mapDepartamentos.values()) {
                        if (u.getLotacaoDominio().intValue() == dd.getCodigoDominio().intValue()) {
                            todos.put(dd.getCodigoDominio().toString(), dd);
                        }
                    }
                }

            }

            request.setAttribute("lista", new UsuarioBO().obterPorLoginNomeLotacaoDominio(loginFiltro, nomeFiltro, lotacaoDominioFiltro.isEmpty() ? null : Integer.parseInt(lotacaoDominioFiltro), ativoFiltro));
            request.setAttribute("listadepartamento", todos.values());
            request.setAttribute("mapdepartamento", rh.obterDepartamentosMapIndexCodigo());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("lista");
    }

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        String login = dyna.getString("login");
        String nome = dyna.getString("nome");
        String lotacaoDominio = dyna.getString("lotacaoDominio");

        if (dyna.getString("situacao").equalsIgnoreCase("true")) {
            request.getSession().setAttribute("usuarioAction_filtro_situacao", Boolean.TRUE);
        } else {
            request.getSession().setAttribute("usuarioAction_filtro_situacao", Boolean.FALSE);
        }

        request.getSession().setAttribute("usuarioAction_filtro_login", login);
        request.getSession().setAttribute("usuarioAction_filtro_nome", nome);
        request.getSession().setAttribute("usuarioAction_filtro_lotacaoDominio", lotacaoDominio);

        return unspecified(mapping, form, request, response);
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            request.setAttribute("listadepartamento", new RHServico().obterDepartamentos());
            request.setAttribute("tipos", TipoAutenticacao.toCollection());
            request.setAttribute("tiposS", TipoSolicitacao.toCollection());
            String id = request.getParameter("id");
            if (id == null || id.trim().length() == 0) {
                id = ((Integer) dyna.get("id")).toString();
            }
            if (GenericValidator.isInt(id)) {
                Usuario user = new UsuarioBO().obterPorPk(Integer.valueOf(id));
                dyna.set("login", user.getLogin());
                dyna.set("id", user.getId());
                dyna.set("lotacaoDominio", user.getLotacaoDominio() != null ? user.getLotacaoDominio().toString() : "");
                dyna.set("nome", user.getNome());
                dyna.set("tipoAutenticacao", user.getTipoAutenticacao() != null ? user.getTipoAutenticacao().getId() : null);
                dyna.set("tipoSolicitacao", user.getTipoSolicitacao() != null ? user.getTipoSolicitacao().getId() : null);

                return mapping.findForward("seleciona");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível recuperar os dados do usuário!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Usuario.do");
        return mapping.findForward("redirect");
    }

    public ActionForward selecionarAutenticacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            String id = request.getParameter("id");

            request.setAttribute("tipos", TipoAutenticacao.toCollection());
            if (id == null || id.trim().length() == 0) {
                id = ((Integer) dyna.get("id")).toString();
            }
            if (GenericValidator.isInt(id)) {
                Usuario user;

                user = new UsuarioBO().obterPorPk(Integer.valueOf(id));

                if (user.getTipoAutenticacao() != null) {
                    dyna.set("tipoAutenticacao", user.getTipoAutenticacao().getId());
                    request.setAttribute("tipoAutenticacao", user.getTipoAutenticacao().getId());
                }
            }
        } catch (AcessoDadosException ex) {
            Logger.getLogger(UsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("alterarAutenticacao");

    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        List<Departamento> lista = new RHServico().obterDepartamentos();
        request.setAttribute("tipos", TipoAutenticacao.toCollection());
        request.setAttribute("tiposS", TipoSolicitacao.toCollection());
        request.setAttribute("listadepartamento", lista);
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Usuario user = new Usuario();
            user.setLogin(dyna.getString("login"));
            user.setLotacaoDominio(Integer.parseInt(dyna.getString("lotacaoDominio")));
            user.setNome(dyna.getString("nome"));

            TipoAutenticacao ta = TipoAutenticacao.getById((Integer) dyna.get("tipoAutenticacao"));
            user.setTipoAutenticacao(ta);

            if (ta.equals(TipoAutenticacao.SENHA)) {
                user.setSenha(CriptografiaUtil.stringToMD5(dyna.getString("senha").trim()));

                user.setAlterarSenha(true);
                if (!SenhaValidator.isSenhaValida(dyna.getString("senha"))) {
                    throw new ValidacaoException("A senha especificada não segue a política de segurança da FLEM");
                }
            }
            TipoSolicitacao ts = TipoSolicitacao.getById((Integer) dyna.get("tipoSolicitacao"));
            user.setTipoSolicitacao(ts);

            UsuarioBO bo = new UsuarioBO();

            user.setCriacao(new Date());
            bo.validaUsuario(user);
            bo.inserir(user);
            ArrayList msg = new ArrayList();
            msg.add("Inclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (ValidacaoException ve) {
            ArrayList msg = new ArrayList();
            msg.add(ve.getMessage());
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
            return novo(mapping, form, request, response);
        } catch (LegadoException le) {
            List lista = new ArrayList();
            lista.add(le.getMessage());
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, lista);
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a inclusão!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }
        request.setAttribute("acao", "Usuario.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            UsuarioBO bo = new UsuarioBO();
            Usuario user = bo.obterPorPk((Integer) dyna.get("id"));

            user.setLogin(dyna.getString("login"));
            user.setLotacaoDominio(Integer.parseInt(dyna.getString("lotacaoDominio")));
            user.setNome(dyna.getString("nome"));
            user.setTipoAutenticacao(TipoAutenticacao.getById((Integer) dyna.get("tipoAutenticacao")));
            user.setTipoSolicitacao(TipoSolicitacao.getById((Integer) dyna.get("tipoSolicitacao")));
            if (!user.getTipoAutenticacao().equals(dyna.get("tipoAutenticacao"))) {
                TipoAutenticacao ta = TipoAutenticacao.getById((Integer) dyna.get("tipoAutenticacao"));
                if (ta.equals(TipoAutenticacao.ACTIVE_DIRECTORY)) {
                    user.setSenha(null);
                    user.setAlterarSenha(false);
                }
            }

            bo.validaUsuario(user);
            bo.alterar(user);
            ArrayList msg = new ArrayList();
            msg.add("Alteração realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        } catch (ValidacaoException ve) {
            ArrayList msg = new ArrayList();
            msg.add("O login escolhido já está em uso!");
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
            return selecionar(mapping, form, request, response);
        } catch (Exception ade) {
            ade.printStackTrace();
            ArrayList msg = new ArrayList();
            msg.add("Não foi possível realizar a alteração!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
        }

        request.setAttribute("acao", "Usuario.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterarAutenticacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            DynaActionForm dyna = (DynaActionForm) form;

            UsuarioBO bo = new UsuarioBO();
            Usuario user;

            user = bo.obterPorPk((Integer) dyna.get("id"));

            if (!SenhaValidator.isSenhaValida(dyna.getString("senha").trim())) {
                ArrayList msg = new ArrayList();
                msg.add("A senha especificada não segue a política de segurança da FLEM");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
            } else {
                user.setSenha(CriptografiaUtil.stringToMD5(dyna.getString("senha").trim()));
                user.setAlterarSenha(true);
                new UsuarioBO().alterar(user);
                ArrayList msg = new ArrayList();
                msg.add("Alteração realizada com sucesso!");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);
            }

        } catch (AcessoDadosException ex) {
            Logger.getLogger(UsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("acao", "Usuario.do");
        return mapping.findForward("redirect");

    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        //DynaActionForm dyna = (DynaActionForm) form;
        String[] id = new String[0];

        if (request.getParameterValues("ids_exclusao") != null) {
            id = request.getParameterValues("ids_exclusao");
        }

        Collection<Usuario> usuarios = new ArrayList<Usuario>();

        for (int i = 0; i < id.length; i++) {
            Usuario usuario = new Usuario();
            usuario.setId(Integer.valueOf(id[i]));
            usuarios.add(usuario);
        }

        try {
            new UsuarioBO().excluir(usuarios);
            ArrayList msg = new ArrayList();
            msg.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, msg);

        } catch (AcessoDadosException ade) {
            try {
                ade.printStackTrace();
                ArrayList erros = new ArrayList();
                erros.add("Não foi possível realizar a exclusão!");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex) {
                Logger.getLogger(UsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("acao", "Usuario.do");
        return mapping.findForward("redirect");
    }

    public ActionForward atualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<Integer, IFuncionario> listas = new RHServico().obterMapTodos();
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> usuarios = dao.obterTodos();
            for (Usuario usuario : usuarios) {
                if (listas.containsKey(usuario.getCodigoDominio())) {
                    IFuncionario ifun = listas.get(usuario.getCodigoDominio());
                    usuario.setLotacao(ifun.getDepartamento().getCodigoHR());
                    if (ifun.getSituacao() != SituacaoFuncionarioEnum.ATIVO) {
                        usuario.setAtivo(false);
                    }
                    dao.alterar(usuario);
                }
            }

        } catch (AcessoDadosException ex) {
            Logger.getLogger(UsuarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO: Fazer
        return null;
    }
}
