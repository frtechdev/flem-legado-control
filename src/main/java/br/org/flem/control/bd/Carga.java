package br.org.flem.control.bd;

import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dao.base.GenericoDAO;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.RH;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mjpereira
 */
public class Carga extends GenericoDAO{

    /** Creates a new instance of Carga */
    public Carga() {
    }

    public static void inserir() {
        ResultSet result = null;
        Connection con = null;
        PreparedStatement stmt = null;
        UsuarioDAO dao = null;
        RH rh = new RHServico();
        Map<Integer, IFuncionario> map = rh.obterMapIdFuncionarioAtivo();

        try {
            dao = new UsuarioDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            for (Usuario u : dao.obterTodos()) {
                if (map.containsKey(u.getCodigoDominio())){
                    IFuncionario colab = map.remove(u.getCodigoDominio());
                    u.setNome(colab.getNome());
                    dao.alterar(u);
                }
            }
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }

        try {
            con = java.sql.DriverManager.getConnection(urlsBDs.BD_BDCOLABORADOR_FLEM());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        List<Usuario> cad = new ArrayList<Usuario>();
        for (IFuncionario colaboradorHR : map.values()) {
            try {
                stmt = con.prepareStatement("SELECT idt_colab, nom_username FROM Colaborador WHERE (cod_matricula = ?) AND (nom_username <> '')");
                stmt.setString(1, Carga.formataMatricula(colaboradorHR.getMatriculaHR().toString()));
                result = stmt.executeQuery();
                if (result.next()) {
                    Integer codigo = result.getInt("idt_colab");
                    String username = result.getString("nom_username").toLowerCase();
                    Usuario dto = new Usuario();
                    dto.setId(codigo);
                    dto.setLogin(username);
                    dto.setMatricula(colaboradorHR.getMatriculaHR());
                    dto.setNome(colaboradorHR.getNome_curto());
                    dto.setCriacao(new Date());
                    dto.setAtivo(true);
                    dto.setLotacao(colaboradorHR.getDepartamento().getCodigoHR());
                    cad.add(dto);
                }
                try {
                    result.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            HibernateUtil.beginTransaction();
            for (Usuario u : cad) {
                try {
                    dao.inserir(u);
                } catch (AcessoDadosException ex) {
                    ex.printStackTrace();
                }
            }

            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }

    }

    public static String formataMatricula(String mat) {
        if (mat.length() == 1) {
            return "00" + mat;
        } else if (mat.length() == 2) {
            return "0" + mat;
        } else {
            return mat;
        }
    }
}
