package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author mjpereira
 */
public class AplicacaoDAO extends BaseDAOAb<Aplicacao>{

    /**
     * Creates a new instance of UsuarioDAO
     */
    public AplicacaoDAO() throws AcessoDadosException {
    }

    protected Class<Aplicacao> getClasseDto() {
        return Aplicacao.class;
    }

    /**
     * SQL EQUIVALENTE AO HQL UTILIZADO SELECT DISTINCT Aplicacao.nome_curto,
     * Usuario.nome_curto FROM Funcionalidade INNER JOIN Aplicacao ON
     * Funcionalidade.id_aplicacao = Aplicacao.id_aplicacao INNER JOIN
     * PerfilFuncionalidade ON Funcionalidade.id_funcionalidade =
     * PerfilFuncionalidade.id_funcionalidade INNER JOIN Usuario INNER JOIN
     * UsuarioPerfil ON Usuario.id_usuario = UsuarioPerfil.id_usuario INNER JOIN
     * Perfil ON UsuarioPerfil.id_perfil = Perfil.id_perfil ON
     * PerfilFuncionalidade.id_perfil = Perfil.id_perfil WHERE
     * (Usuario.nome_curto = N'mjpereira')
     */
    public List obterPorUsuarioNomeCurto(String nomeCurto) throws AcessoDadosException {
        try {
            return session.createQuery("select distinct app.nomeCurto from Aplicacao app inner join app.funcionalidade fcn inner join fcn.perfil prf inner join prf.usuario usr where usr.nomeCurto = :nomecurto").setString("nomecurto", nomeCurto).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
   public List<Aplicacao> obterDepartamentosPorNomeCurtoOuPorNome(String nome) throws AcessoDadosException {
        return session.createQuery("SELECT app FROM Aplicacao app WHERE nome like ?").setString(0,"%"+nome+"%").list();
    }
}
