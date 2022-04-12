package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dao.legado.bdcolaborador.ColaboradorDAO;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.impl.ColaboradoresImpl;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.exception.LegadoException;
import br.org.flem.fwe.exception.ValidacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dbbarreto
 */
public class UsuarioBO {

    public UsuarioBO() {
    }

    public void inserir(Usuario usuario) throws AcessoDadosException, LegadoException, AplicacaoException, SQLException {
        HibernateUtil.beginTransaction();
        usuario.setAtivo(true);
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(usuario.getNome());
        funcionario.setUsuario(usuario.getLogin());
        funcionario.setDepartamento(new RHServico().obterDepartamentoPorCodigo(usuario.getLotacaoDominio().toString()));
        new ColaboradorDAO().correcaoDepartamento(funcionario, usuario.getLotacaoDominio());

        Integer idt = (Integer) new UsuarioDAO().inserir(usuario);

        funcionario.setId(idt);

        new ColaboradorDAO().inserirColaborador(funcionario);

        HibernateUtil.commitTransaction();
    }

    public void alterar(Usuario usuario) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        new UsuarioDAO().alterar(usuario);
        HibernateUtil.commitTransaction();
    }

    public void excluir(Collection<Usuario> usuarios) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        for (Usuario usuario : usuarios) {
            this.desativar(usuario);
        }
        HibernateUtil.commitTransaction();
    }

    public void desativar(Usuario usuario) throws AcessoDadosException {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.obterPorPk(usuario);
        u.setAtivo(false);
        if (u.getCodigoDominio() != null && u.getCodigoDominio() != 0) {
            new ColaboradoresImpl().desativarColaboradorPorMatricula(u.getCodigoDominio() + "");
        }
        dao.alterar(u);
    }

    public List<Usuario> obterTodos() throws AcessoDadosException {
        return new UsuarioDAO().obterTodos();
    }

    public Collection<Usuario> obterAtivos() throws AcessoDadosException {
        Usuario filtro = new Usuario();
        filtro.setAtivo(true);
        return new UsuarioDAO().obterPorFiltro(filtro);
    }

    public Usuario obterPorPk(Integer pk) throws AcessoDadosException {
        return new UsuarioDAO().obterPorPk(pk);
    }

    public Usuario obterPorLogin(String login) throws AcessoDadosException {
        return new UsuarioDAO().obterLogin(login);
    }

    public Collection<Usuario> obterPorLoginNomeLotacaoDominio(String login, String nome, Integer codigoLotacaoDominio, Boolean ativo) throws AcessoDadosException {
        return new UsuarioDAO().obterPorLoginNomeLotacaoDominio(login, nome, codigoLotacaoDominio, ativo);
    }

    public Usuario obterUsuarioPorCodigoDominio(Integer codigo) throws AcessoDadosException {
        return new UsuarioDAO().obterUsuarioPorCodigoDominio(codigo);
    }

    public void validaUsuario(String login) throws AcessoDadosException, ValidacaoException {
        Usuario loginRepetido = new UsuarioDAO().obterLogin(login);

        if (!loginRepetido.getLogin().equals(login)) {
            throw new ValidacaoException("O login escolhido já está em uso.");
        }
    }

    public void validaUsuario(Usuario usuario) throws AcessoDadosException, ValidacaoException {
        Usuario loginRepetido = new UsuarioDAO().obterLogin(usuario.getLogin());

        if (loginRepetido != null) {
            if (usuario.getId() == null || !usuario.getId().equals(loginRepetido.getId())) {
                throw new ValidacaoException("O login escolhido já está em uso.");
            }
        }
    }

    public Boolean verificaExisteUsuario(Usuario usuario) throws AcessoDadosException, ValidacaoException {
        Usuario loginRepetido = new UsuarioDAO().obterLogin(usuario.getLogin());

        if (loginRepetido != null) {
            if (usuario.getId() == null || !usuario.getId().equals(loginRepetido.getId())) {

                return true;
            }
        }
        return false;
    }

    public void desativarUsuarios() throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        Map<Integer, IFuncionario> map = new RHServico().obterMapTodos();
        UsuarioDAO dao = new UsuarioDAO();
        for (Usuario u : dao.obterAtivos()) {
            if (map.containsKey(u.getCodigoDominio())) {
                IFuncionario func = map.get(u.getCodigoDominio());
                if (func.getSituacao() != SituacaoFuncionarioEnum.ATIVO) {
                    u.setAtivo(false);
                    dao.alterar(u);
                } else if (!u.getCodigoDominio().equals(func.getDepartamento().getCodigoDominio())) {
                    u.setCodigoDominio(func.getDepartamento().getCodigoDominio());
                    dao.alterar(u);
                }
            }
        }
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }

    public List<Usuario> obterAtivosAD(String nome) throws AcessoDadosException {
        return new UsuarioDAO().obterAtivosAD(nome);
    }

    public List<Usuario> obterAtivosSE(String nome) throws AcessoDadosException {
        return new UsuarioDAO().obterAtivosSE(nome);
    }
}
