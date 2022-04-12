package br.org.flem.control.acesso.action;

import br.org.flem.control.acesso.bo.ColaboradorBO;
import br.org.flem.control.acesso.bo.DepartamentoTipoSolicitacaoBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.dao.PerfilDAO;
import br.org.flem.control.acesso.negocio.DepartamentoTipoSolicitacao;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.RH;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.ValidacaoException;
import br.org.flem.fwe.util.CriptografiaUtil;
import br.org.flem.fwe.util.StringUtil;
import br.org.flem.fwe.validator.SenhaValidator;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author mppassos
 */
public class ColaboradorAction extends DispatchAction {

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String nomeColaborador = request.getParameter("nome");
        RH rh = null;
        Map<Integer, IFuncionario> map = null;
        List<Usuario> lista = null;
        Collection<IFuncionario> funcionarios = null;

        if (nomeColaborador == null) {
            nomeColaborador = (String) request.getSession().getAttribute("nome");
        }
        try {
            rh = new RHServico();
            map = rh.obterMapIdFuncionarioAtivo();
            lista = new UsuarioBO().obterTodos();
            for (Usuario u : lista) {
                map.remove(u.getCodigoDominio());
            }
            funcionarios =  map.values();
            if (nomeColaborador != null && !nomeColaborador.trim().isEmpty()) {
                Collection<IFuncionario> removeFuncs = new ArrayList<IFuncionario>();
                for (IFuncionario fun : funcionarios) {
                    if (!fun.getNome().toUpperCase().contains(nomeColaborador.toUpperCase())) {
                        removeFuncs.add(fun);
                    }
                }
                funcionarios.removeAll(removeFuncs);
            }
            request.setAttribute("lista", funcionarios);
        } catch (AcessoDadosException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar obter os funcionários");
            Logger.getLogger(ColaboradorAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        request.getSession().setAttribute("nome", nomeColaborador);
        return mapping.findForward("lista");
    }

    public ActionForward criar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        String m = request.getParameter("mat");
        RH rh = new RHServico();
        IFuncionario funcionario = rh.obterFuncionarioPorMatricula(Integer.valueOf(m));
        request.getSession().setAttribute("colaborador", funcionario);
        dyna.set("nome", funcionario.getNome());
        dyna.set("login", StringUtil.sugestaoLogin(funcionario.getNome()));
        DepartamentoTipoSolicitacao departamentoTipoSolicitacao = new DepartamentoTipoSolicitacaoBO().obterPorLotacaoDominio(funcionario.getDepartamento().getCodigoDominio());
        if(departamentoTipoSolicitacao != null){
            dyna.set("tipoSolicitacao", departamentoTipoSolicitacao.getTipoSolicitacao().getId());
        }
        request.setAttribute("tipos", TipoAutenticacao.toCollection());
        request.setAttribute("tiposS", TipoSolicitacao.toCollection());
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        Funcionario c = (Funcionario) request.getSession().getAttribute("colaborador");
        String login = dyna.getString("login");
        String nome = dyna.getString("nome");
        String senha = dyna.getString("senha").trim();
        TipoAutenticacao tipoAutenticacao = TipoAutenticacao.getById((Integer) dyna.get("tipoAutenticacao"));
        try {
            Usuario dto = new Usuario();
            if (tipoAutenticacao.equals(TipoAutenticacao.SENHA)) {
                if (!SenhaValidator.isSenhaValida(senha)) {
                    throw new ValidacaoException("A senha especificada não segue a política de segurança da FLEM");
                } else {
                    dto.setSenha(CriptografiaUtil.stringToMD5(senha));
                    dto.setAlterarSenha(true);
                }
            } else {
                dto.setAlterarSenha(false);
            }
            dto.setCriacao(new Date());
            dto.setLogin(login);
            dto.setMatricula(c.getMatriculaHR());
            dto.setCodigoDominio(c.getCodigoDominio());
            dto.setNome(nome);
            dto.setAtivo(true);
            dto.setLotacao(c.getDepartamento().getCodigoHR());
            dto.setLotacaoDominio(c.getDepartamento().getCodigoDominio());
            dto.setTipoAutenticacao(tipoAutenticacao);
            dto.setTipoSolicitacao(TipoSolicitacao.getById((Integer) dyna.get("tipoSolicitacao")));
            dto.getPerfil().add(new PerfilDAO().obterPorPk(17));//Adicionando o Perfil Básico por padrão
            new UsuarioBO().validaUsuario(dto);
            new ColaboradorBO().inserirColaboradorSemLogin(c, dto);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Colaborador inserido com sucesso!");
        } catch (Exception ade) {
            ade.printStackTrace();
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Ocorreu um erro ao tentar inserir o colaborador: " + ade.getMessage());
        }
        return unspecified(mapping, form, request, response);
    }
/*
    public ActionForward nova(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("cargaUsuario");
    }
    
    public ActionForward enviar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
        
            DynaActionForm dyna = (DynaActionForm) form;
            FormFile arquivo = (FormFile) dyna.get("arquivo");
            String mat;
            String login;
            Boolean existe;
            Boolean existeNoControl=false;
            
          try {
              
              if(arquivo.getFileName() == "" || arquivo.getFileName() == null)
              {
                  MensagemTagUtil.adicionarMensagem(request.getSession(), "Nenhum arquivo foi selecionado.");
              } else {
                  
                    Workbook workbook = new XSSFWorkbook(arquivo.getInputStream());
                    Sheet datatypeSheet = workbook.getSheetAt(0);
                    Iterator<Row> iteratorDeLinhas = datatypeSheet.iterator();
                    Row row = iteratorDeLinhas.next();
                    List<Usuario> todos = new UsuarioBO().obterTodos();
                    List<Usuario> usuariosExistentes = new ArrayList<Usuario>();
                    List<Usuario> inseridos = new ArrayList<Usuario>();
                    
                    while (iteratorDeLinhas.hasNext()) {
                    row = iteratorDeLinhas.next();
                        try {
                                mat = row.getCell(0).toString();
                                Integer m = Integer.valueOf(mat);
                                Usuario usr = new UsuarioBO().obterUsuarioPorCodigoDominio(m);
                                for(Usuario u : todos){           
                                    if(u.getCodigoDominio() == usr.getCodigoDominio() ){
                                        MensagemTagUtil.adicionarMensagem(request.getSession(), "Colaborador Já existe no sistema");
                                        usuariosExistentes.add(u);
                                        existeNoControl=true;
                                        
                                    }
                                    existeNoControl=false;
                                }
                            
                            if(!existeNoControl){
                                    RH rh = new RHServico();
                                    Funcionario funcionario = (Funcionario) rh.obterFuncionarioPorMatricula(Integer.valueOf(mat));
                                    login = "flem"+funcionario.getCodigoDominio().toString();
                                    Usuario dto = new Usuario();
                                    dto.setCriacao(new Date());
                                    dto.setTipoAutenticacao(TipoAutenticacao.SENHA);
                                    dto.setTipoSolicitacao(TipoSolicitacao.FLEM);
                                    dto.setNome(funcionario.getNome());
                                    dto.setLogin(login);
                                    dto.setSenha(CriptografiaUtil.stringToMD5("Flemflem12345"));
                                    dto.setAlterarSenha(true);
                                    dto.setMatricula(funcionario.getMatriculaHR());
                                    dto.setCodigoDominio(funcionario.getId());
                                    dto.setAtivo(true);
                                    dto.setLotacao(funcionario.getDepartamento().getCodigoHR());
                                    dto.setLotacaoDominio(funcionario.getDepartamento().getCodigoDominio());
                                    dto.getPerfil().add(new PerfilDAO().obterPorPk(17));//Adicionando o Perfil Básico por padrão

                                    new UsuarioBO().validaUsuario(dto);
                                    new ColaboradorBO().inserirColaboradorSemLogin(funcionario, dto);
                                    inseridos.add(dto);
                                    MensagemTagUtil.adicionarMensagem(request.getSession(), "Colaborador inserido com sucesso!");
                            }
                        } catch (Exception e) {

                        }
                    
                    }
              }
            
        } catch (Exception e) {
            
        }
                          

            
                    
            
        return mapping.findForward("cargaUsuario");
    }
*/
    
}
