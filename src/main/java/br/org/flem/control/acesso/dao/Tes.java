/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.bo.AplicacaoAcessoIntranetBO;
import br.org.flem.control.acesso.negocio.AplicativoAcessoIntranet;
import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class Tes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ParseException {
        
     Collection<AplicativoIntranet> app =  new AplicacaoAcessoIntranetDAO().ObterAcessoPorCodUsuario(205);
    }
}
