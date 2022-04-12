package br.org.flem.control.acesso.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mccosta
 */
@Entity
public class DepartamentoCentroCusto extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamentocentrocusto")
    private Integer id;
    private String codigoDepartamento;
    private Integer codigoDepartamentoDominio;
    private String nomeDepartamento;
    private String codigoCentroCusto;
    private String nomeCentroCusto;

    @Override
    public Serializable getPk() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoCentroCusto() {
        return codigoCentroCusto;
    }

    public void setCodigoCentroCusto(String codigoCentroCusto) {
        this.codigoCentroCusto = codigoCentroCusto;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public Integer getCodigoDepartamentoDominio() {
        return codigoDepartamentoDominio;
    }

    public void setCodigoDepartamentoDominio(Integer codigoDepartamentoDominio) {
        this.codigoDepartamentoDominio = codigoDepartamentoDominio;
    }

    public String getNomeCentroCusto() {
        return nomeCentroCusto;
    }

    public void setNomeCentroCusto(String nomeCentroCusto) {
        this.nomeCentroCusto = nomeCentroCusto;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
}
