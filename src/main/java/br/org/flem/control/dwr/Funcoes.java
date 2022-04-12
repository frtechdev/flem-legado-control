package br.org.flem.control.dwr;

import br.org.flem.control.acesso.bo.DepartamentoTipoSolicitacaoBO;
import br.org.flem.control.acesso.bo.UsuarioBO;
import br.org.flem.control.acesso.negocio.DepartamentoTipoSolicitacao;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import java.io.Serializable;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 *
 * @author emsilva
 */
@RemoteProxy
public class Funcoes implements Serializable {

    @RemoteMethod
    public Integer tipoSolicitacaoPorLotacao(String lotacaoDominio) {
        try {
            DepartamentoTipoSolicitacao departamentoTipoSolicitacao = new DepartamentoTipoSolicitacaoBO().obterPorLotacaoDominio(Integer.parseInt(lotacaoDominio));
            if (departamentoTipoSolicitacao != null) {
                return departamentoTipoSolicitacao.getTipoSolicitacao().getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TipoSolicitacao.FLEM.getId();
    }

    @RemoteMethod
    public boolean verificarLogin(String login) {
        try {
                new UsuarioBO().validaUsuario(login);
                return true;  
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }
}