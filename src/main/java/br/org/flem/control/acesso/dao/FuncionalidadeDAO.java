package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.Funcionalidade;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

/**
 *
 * @author mjpereira
 */
public class FuncionalidadeDAO extends BaseDAOAb<Funcionalidade> {

    /**
     * Creates a new instance of UsuarioDAO
     */
    public FuncionalidadeDAO() throws AcessoDadosException {
    }

    protected Class<Funcionalidade> getClasseDto() {
        return Funcionalidade.class;
    }
    
    public List<Funcionalidade> obterPorIdAplicacao(Integer idAplicacao) throws AcessoDadosException {
        try {
            if(idAplicacao != null){
                return session.createQuery("from Funcionalidade f where f.aplicacao.id = :idAplicacao").setInteger("idAplicacao", idAplicacao).list();
            }else{
                return obterTodos();
            }
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public Map<Serializable,Funcionalidade> obterPorIdAplicacaoMap(Integer idAplicacao) throws AcessoDadosException {
        return convertToMap(obterPorIdAplicacao(idAplicacao));
    }
 
  
}
