/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.AplicacaoAcessoIntranetDAO;
import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author AJLima
 */
public class AplicacaoAcessoIntranetBO {
    
    public void AdicionarAcesso(int codusuario,AplicativoIntranet app)throws SQLException, ParseException{
        new AplicacaoAcessoIntranetDAO().AdicionarAcesso(codusuario, app);
    }
    public void RemoverAcesso(int codusuario,AplicativoIntranet app)throws SQLException, ParseException{
        new AplicacaoAcessoIntranetDAO().RemoveAcesso(codusuario, app);
    }
    public void ObterAcessoPorCodUsuario(int codigo)throws SQLException, ParseException{
        new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(codigo);
    }
    public void ObterAcessoNaoCadastrado(int codigo)throws SQLException, ParseException{
        new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(codigo);
    }
}
