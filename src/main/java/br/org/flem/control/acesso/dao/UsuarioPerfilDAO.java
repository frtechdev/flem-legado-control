/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.UsuarioPerfil;
import br.org.flem.fw.persistencia.dao.base.GenericoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class UsuarioPerfilDAO  extends GenericoDAO{
    
public Collection<UsuarioPerfil> obterUsuariosPorFuncionalidade( int idfuncionalidade) throws SQLException, ParseException {//mapeado
        List<UsuarioPerfil> usr = new ArrayList<UsuarioPerfil>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
                try {
            con = DriverManager.getConnection(urlsBDs.BD_BDAA_CONTROL());
            String select = "Select U.nome, U.login,U.lotacao,P.nome [perfil],U.ativo from Usuario as U "
                    + "inner join UsuarioPerfil as UP on UP.id_usuario=U.id_usuario "
                    + "inner join Perfil as P on P.id_perfil=UP.id_perfil "
                    + "inner join PerfilFuncionalidade as PF on PF.id_perfil=P.id_perfil "
                    + "inner Join Funcionalidade as F on F.id_funcionalidade=PF.id_funcionalidade "
                    + "where F.id_funcionalidade=?  ORDER BY ativo DESC";
            pstmt = (con).prepareStatement(select);
            pstmt.setInt(1, idfuncionalidade);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UsuarioPerfil rec = new UsuarioPerfil();
                rec.setNome(rs.getString("nome"));
                rec.setLogin(rs.getString("login"));
                rec.setLotacao(rs.getString("lotacao"));
                rec.setPerfil(rs.getString("perfil"));
                rec.setAtivo(rs.getBoolean("ativo"));
                usr.add(rec);

            }

        } finally {
                    
            this.close(rs, pstmt, con);
        }
        return usr;
      }
public Collection<UsuarioPerfil> obterUsuariosPorPerfil (int idperfil) throws SQLException, ParseException{
    
    Collection<UsuarioPerfil> usr =  new ArrayDeque<UsuarioPerfil>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs= null;
    
    try{
        con = DriverManager.getConnection(urlsBDs.BD_BDAA_CONTROL());
        String select ="Select U.nome, U.login,U.lotacao,U.ativo from Usuario as U "
                + "inner join UsuarioPerfil as UP on UP.id_usuario=U.id_usuario "
                + "inner join Perfil as P on P.id_perfil=UP.id_perfil where P.id_perfil=? ";
         pstmt = (con).prepareStatement(select);
         pstmt.setInt(1, idperfil);
         rs = pstmt.executeQuery();
         while(rs.next()) {
                UsuarioPerfil rec = new UsuarioPerfil();
                rec.setNome(rs.getString("nome"));
                rec.setLogin(rs.getString("login"));
                rec.setLotacao(rs.getString("lotacao"));
                rec.setAtivo(rs.getBoolean("ativo"));
                usr.add(rec);
    
        }
    }finally
    {
        this.close(rs, pstmt, con);
    }


    return usr;
}   
}
