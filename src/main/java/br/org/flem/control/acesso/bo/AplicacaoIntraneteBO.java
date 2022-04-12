/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.AplicacaoIntranetDAO;
import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class AplicacaoIntraneteBO {

    public void Inserir(AplicativoIntranet p) throws SQLException, ParseException {
        new AplicacaoIntranetDAO().AdicionarAplicacao(p);
    }
    public void Atualizar(AplicativoIntranet p) throws SQLException, ParseException {
        new AplicacaoIntranetDAO().AlterarAplicacao(p);
    }
    public List<AplicativoIntranet> ObterTodos() throws SQLException, ParseException{
        return new AplicacaoIntranetDAO().obterTodosIntranetAtivos();
    }   
    public AplicativoIntranet ObterPorPK(int id) throws SQLException, ParseException{
        return new AplicacaoIntranetDAO().ObterPorPK(id);
    }
     public List ObterNomeAplicativoPorFiltro(String nome) throws SQLException, ParseException{
        if(nome == null || nome.isEmpty()){
            return ObterTodos();
        }
         return new AplicacaoIntranetDAO().ObterPorFiltro(nome);
    }   
}
