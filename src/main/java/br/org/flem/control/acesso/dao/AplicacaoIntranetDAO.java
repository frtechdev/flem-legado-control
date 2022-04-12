/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.dao;

import br.org.flem.control.acesso.negocio.AplicativoIntranet;
import br.org.flem.fw.persistencia.dao.base.GenericoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class AplicacaoIntranetDAO extends GenericoDAO {

    public List<AplicativoIntranet> obterTodosIntranetAtivos() throws SQLException, ParseException {//mapeado
        List<AplicativoIntranet> retorno = new ArrayList<AplicativoIntranet>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "Select idt_aplicativo,nom_aplicativo, nom_titulo,des_local,nom_target,des_imagem,num_ordem,"
                    + "      flg_ativo,flg_seguranca "
                    + "      from Aplicativo where flg_ativo=1";
            pstmt = (con).prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AplicativoIntranet rec = new AplicativoIntranet();
                rec.setId(rs.getInt("idt_aplicativo"));
                rec.setNome(rs.getString("nom_aplicativo"));
                rec.setTitulo(rs.getString("nom_titulo"));
                rec.setUrl(rs.getString("des_local"));
                rec.setTarget(rs.getString("nom_target"));
                rec.setImagem(rs.getString("des_imagem"));
                rec.setOrdem(rs.getString("num_ordem"));
                rec.setAtivo(rs.getString("flg_ativo"));
                rec.setSeguranca(rs.getString("flg_seguranca"));

                retorno.add(rec);

            }
        } finally {
            this.close(rs, pstmt, con);
        }
        return retorno;
    }

    public void AdicionarAplicacao(AplicativoIntranet p) throws SQLException, ParseException {


        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String insert = "insert into aplicativo (nom_aplicativo, nom_titulo,des_local,nom_target,des_imagem,num_ordem,flg_ativo,flg_seguranca) values (?,?,?,?,?,?,?,?)";
            pstmt = (con).prepareStatement(insert);
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getTitulo());
            pstmt.setString(3, p.getUrl());
            pstmt.setString(4, p.getTarget());
            pstmt.setString(5, p.getImagem());
            pstmt.setString(6, p.getOrdem());
            pstmt.setString(7, p.getAtivo());
            pstmt.setString(8, p.getSeguranca());
            pstmt.execute();

        } finally {

            this.close(pstmt, con);
        }

    }

    public void AlterarAplicacao(AplicativoIntranet p) throws SQLException, ParseException {


        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String update = "update aplicativo set nom_aplicativo=?, nom_titulo=?,des_local=?,nom_target=?,des_imagem=?,num_ordem=?,flg_ativo=?,flg_seguranca=? where idt_aplicativo=?";
            pstmt = (con).prepareStatement(update);
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getTitulo());
            pstmt.setString(3, p.getUrl());
            pstmt.setString(4, p.getTarget());
            pstmt.setString(5, p.getImagem());
            pstmt.setString(6, p.getOrdem());
            pstmt.setString(7, p.getAtivo());
            pstmt.setString(8, p.getSeguranca());
            pstmt.setInt(9, p.getId());
            pstmt.execute();
        } finally {
            this.close(pstmt, con);
        }

    }

    public AplicativoIntranet ObterPorPK(int id) throws SQLException, ParseException {
        AplicativoIntranet rec = new AplicativoIntranet();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "Select idt_aplicativo,nom_aplicativo,nom_titulo,des_local,nom_target,des_imagem,num_ordem,flg_ativo,flg_seguranca from Aplicativo where idt_aplicativo=?";
            pstmt = (con).prepareStatement(select);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {

                rec.setId(rs.getInt("idt_aplicativo"));
                rec.setNome(rs.getString("nom_aplicativo"));
                rec.setTitulo(rs.getString("nom_titulo"));
                rec.setUrl(rs.getString("des_local"));
                rec.setTarget(rs.getString("nom_target"));
                rec.setImagem(rs.getString("des_imagem"));
                rec.setOrdem(rs.getString("num_ordem"));
                rec.setAtivo(rs.getString("flg_ativo"));
                rec.setSeguranca(rs.getString("flg_seguranca"));


            }
        } finally {
            this.close(rs, pstmt, con);
        }
        return rec;
    }

    public List ObterPorFiltro(String nome) throws SQLException, ParseException {
        List<AplicativoIntranet> aplicacoes = new ArrayList<AplicativoIntranet>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            con = DriverManager.getConnection(urlsBDs.BD_FLEMINTRANET_FLEM());
            String select = "Select * from Aplicativo where nom_aplicativo like ? ";
            ps = (con).prepareStatement(select);
            ps.setString(1, "%"+nome+"%");
            result = ps.executeQuery();
            while (result.next()) {
                AplicativoIntranet api = new AplicativoIntranet();
                api.setId(result.getInt("idt_aplicativo"));
                api.setNome(result.getString("nom_aplicativo"));
                api.setTitulo(result.getString("nom_titulo"));
                api.setUrl(result.getString("des_local"));
                aplicacoes.add(api);
            }

        } finally {
            this.close(result, ps, con);
        }
        return aplicacoes;
    }
}
