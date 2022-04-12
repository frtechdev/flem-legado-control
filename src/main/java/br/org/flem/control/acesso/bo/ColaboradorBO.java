package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.UsuarioDAO;
import br.org.flem.control.acesso.negocio.DepartamentoUsuario;
import br.org.flem.control.acesso.negocio.Usuario;
import br.org.flem.fw.persistencia.dao.legado.bdcolaborador.ColaboradorDAO;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.exception.LegadoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.sql.SQLException;

/**
 *
 * @author dbbarreto
 */
public class ColaboradorBO {

    public void inserirColaboradorSemLogin(Funcionario funcionario, Usuario usuario) throws AcessoDadosException, LegadoException, AplicacaoException, SQLException {
            HibernateUtil.beginTransaction();
            funcionario.setUsuario(usuario.getLogin());

            ColaboradorDAO colaboradorDAO= new ColaboradorDAO();
            
            colaboradorDAO.correcaoCargo(funcionario);
            colaboradorDAO.correcaoDepartamento(funcionario, usuario.getLotacaoDominio());
            
            
            Departamento departamentoDominio = new RHServico().obterDepartamentoErpServicoPorCodigoDominio(usuario.getLotacaoDominio());

            if(departamentoDominio != null && departamentoDominio.getCodigoDominio() != null){
                usuario.setLotacao(departamentoDominio.getCodigoHR());
                funcionario.getDepartamento().setCodigoHR(departamentoDominio.getCodigoHR());
            }

            Integer idt = (Integer) new UsuarioDAO().inserir(usuario);

            

            //Inclusão do departamento que o funcionário trabalna lista de departamentos associadas ao seu usuario
            if(departamentoDominio != null && departamentoDominio.getCodigoDominio() != null){
                DepartamentoUsuario departamentoUsuario = new DepartamentoUsuario();

                departamentoUsuario.setCodigoDepartamento(departamentoDominio.getCodigoHR());
                departamentoUsuario.setCodigoDepartamentoDominio(departamentoDominio.getCodigoDominio());
                departamentoUsuario.setNomeDepartamento(departamentoDominio.getNome());
                departamentoUsuario.setUsuario(usuario);

                new DepartamentoUsuarioBO().inserir(departamentoUsuario);
            }

            
            
            funcionario.setId(idt);
            new ColaboradorDAO().inserirColaborador(funcionario);
            HibernateUtil.commitTransaction();
    }
}
