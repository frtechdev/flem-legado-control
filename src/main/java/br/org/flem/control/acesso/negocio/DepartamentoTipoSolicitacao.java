package br.org.flem.control.acesso.negocio;

import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author emsilva
 */
@Entity
public class DepartamentoTipoSolicitacao extends BaseDTOAb {

    @Id
    private String lotacao;
    
    private Integer lotacaoDominio;

    private TipoSolicitacao tipoSolicitacao;
    
    private Boolean liberacaoViagemRetroativo;

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public Integer getLotacaoDominio() {
        return lotacaoDominio;
    }

    public void setLotacaoDominio(Integer lotacaoDominio) {
        this.lotacaoDominio = lotacaoDominio;
    }

    public TipoSolicitacao getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public Boolean getLiberacaoViagemRetroativo() {
        return liberacaoViagemRetroativo;
    }

    public void setLiberacaoViagemRetroativo(Boolean liberacaoViagemRetroativo) {
        this.liberacaoViagemRetroativo = liberacaoViagemRetroativo;
    }

    @Override
    public Serializable getPk() {
        return getLotacao();
    }

}
