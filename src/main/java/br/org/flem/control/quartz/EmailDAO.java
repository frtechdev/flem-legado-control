/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.quartz;

import br.org.flem.fw.persistencia.dao.base.GenericoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class EmailDAO extends GenericoDAO{
    

    /*
    public List<EmailEnvio> listaEmail() throws SQLException{
        
        List<EmailEnvio> emails = new ArrayList<EmailEnvio>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
                try {
                    
                    con = DriverManager.getConnection(urlsBDs.BD_BDAA_CONTROL());
                    String select = "SELECT nome, email,usuario FROM EmailsEnviados ";
                    pstmt = (con).prepareStatement(select);
                    rs = pstmt.executeQuery();
                    
                    while (rs.next()) {
                        EmailEnvio em = new EmailEnvio();
                        em.setNome(rs.getString("nome"));
                        em.setEmail(rs.getString("email"));
                        em.setUsuario(rs.getString("usuario"));
                        emails.add(em);
            }
        } finally {
            this.close(rs, pstmt, con);
        }
        
        
    return emails;
    }
    */
}
