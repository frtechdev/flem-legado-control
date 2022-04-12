/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import br.org.flem.fw.persistencia.dao.base.GenericoDAO;
import br.org.flem.fw.persistencia.dto.trilhasec.CompromissoTrilha;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class AplicacaoAcessoIntranetDAO extends GenericoDAO {

    public Collection<AplicativoIntranet> ObterAcessoPorCodUsuario(int codigo) throws SQLException, ParseException {
        List<AplicativoIntranet> retorno = new ArrayList<AplicativoIntranet>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "SELECT IDT_APLICATIVO,NOM_APLICATIVO,NOM_TITULO,DES_LOCAL FROM APLICATIVO WHERE IDT_APLICATIVO  IN (SELECT IDT_APLICATIVO FROM APLICATIVOACESSO WHERE IDT_COLAB=?) ";
            pstmt = (con).prepareStatement(select);
            pstmt.setInt(1, codigo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AplicativoIntranet rec = new AplicativoIntranet();
                rec.setId(rs.getInt("IDT_APLICATIVO"));
                rec.setNome(rs.getString("NOM_APLICATIVO"));
                rec.setTitulo(rs.getString("NOM_TITULO"));
                rec.setUrl(rs.getString("DES_LOCAL"));

                retorno.add(rec);
            }
        } finally {
            this.close(rs, pstmt, con);
        }
        return retorno;
    }

    public Collection<AplicativoIntranet> ObterAcessoNaoCadastrado(int codigo) throws SQLException, ParseException {

        List<AplicativoIntranet> retorno = new ArrayList<AplicativoIntranet>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "SELECT IDT_APLICATIVO,NOM_APLICATIVO,NOM_TITULO FROM APLICATIVO WHERE IDT_APLICATIVO NOT IN (SELECT IDT_APLICATIVO FROM APLICATIVOACESSO WHERE IDT_COLAB=?)  AND FLG_SEGURANCA=1 AND FLG_SEGURANCA=1 ORDER BY NOM_APLICATIVO ";
            pstmt = (con).prepareStatement(select);
            pstmt.setInt(1, codigo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AplicativoIntranet rec = new AplicativoIntranet();
                rec.setId(rs.getInt("IDT_APLICATIVO"));
                rec.setNome(rs.getString("NOM_APLICATIVO"));
                rec.setTitulo(rs.getString("NOM_TITULO"));
                retorno.add(rec);
            }
        } finally {
            this.close(rs, pstmt, con);
        }
        return retorno;
    }

    public void AdicionarAcesso(int codusuario, AplicativoIntranet app) throws SQLException, ParseException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String insert = "insert into aplicativoAcesso (idt_aplicativo,idt_colab,flg_liberado) values (?,?,1)";
            pstmt = (con).prepareStatement(insert);
            pstmt.setInt(1, app.getId());
            pstmt.setInt(2, codusuario);
            pstmt.execute();

        } finally {

            this.close(pstmt, con);
        }

    }

    public void RemoveAcesso(int codusuario, AplicativoIntranet app) throws SQLException, ParseException {

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String insert = "delete aplicativoAcesso where idt_aplicativo=? and idt_colab=?";
            pstmt = (con).prepareStatement(insert);
            pstmt.setInt(1, app.getId());
            pstmt.setInt(2, codusuario);
            pstmt.execute();

        } finally {

            this.close(pstmt, con);
        }
    }

    public List<AplicativoIntranet> obterFiltroPorNomeIntranet(String NomeIntranet) throws SQLException {
        List<AplicativoIntranet> aplicacoes = new ArrayList<AplicativoIntranet>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "SELECT IDT_APLICATIVO,NOM_APLICATIVO,NOM_TITULO FROM APLICATIVO WHERE NOM_APLICATIVO LIKE ?";
            pstmt = (con).prepareStatement(select);
            pstmt.setString(1, "%" + NomeIntranet + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AplicativoIntranet rec = new AplicativoIntranet();
                rec.setId(rs.getInt("IDT_APLICATIVO"));
                rec.setNome(rs.getString("NOM_APLICATIVO"));
                rec.setTitulo(rs.getString("NOM_TITULO"));
                aplicacoes.add(rec);
            }
        } finally {
            this.close(rs, pstmt, con);
        }
        return aplicacoes;
    }
}
