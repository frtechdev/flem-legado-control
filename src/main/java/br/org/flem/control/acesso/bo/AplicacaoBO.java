package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.AplicacaoDAO;
import br.org.flem.control.acesso.negocio.Aplicacao;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author dbbarreto
 */
public class AplicacaoBO {

    public void inserir(Aplicacao aplicacao) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        new AplicacaoDAO().inserir(aplicacao);
        HibernateUtil.commitTransaction();
    }
    public void alterar(Aplicacao aplicacao) throws AcessoDadosException {
        HibernateUtil.beginTransaction();
        new AplicacaoDAO().alterar(aplicacao);
        HibernateUtil.commitTransaction();
    }
    public void excluir(Collection<Aplicacao> aplicacoes) throws AcessoDadosException {
        AplicacaoDAO dao = new AplicacaoDAO();
        HibernateUtil.beginTransaction();
        dao.excluir(aplicacoes);
        HibernateUtil.commitTransaction();
    }
    public List<Aplicacao> obterTodos() throws AcessoDadosException {
        return new AplicacaoDAO().obterTodos();
    }
    public Aplicacao obterPorPk(Integer pk) throws AcessoDadosException {
        return new AplicacaoDAO().obterPorPk(pk);
    }
    
    public List<Aplicacao> obterDepartamentosPorNomeCurtoOuPorNome(String nome) throws AcessoDadosException {
        if(nome == null || nome.isEmpty()){
            return obterTodos();
        }
        return new AplicacaoDAO().obterDepartamentosPorNomeCurtoOuPorNome(nome);
    }
}
