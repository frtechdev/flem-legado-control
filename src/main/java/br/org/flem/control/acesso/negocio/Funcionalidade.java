package br.org.flem.control.acesso.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author mjpereira
 */
@Entity
public class Funcionalidade extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionalidade")
    private Integer id;
    private String nome;
    private String nome_curto;
    private String descricao;
    @ManyToMany(mappedBy = "funcionalidade")
    private Set<Perfil> perfil = new HashSet<Perfil>();
    @ManyToOne
    @JoinColumn(name = "id_aplicacao", nullable = false)
    private Aplicacao aplicacao;

    /** Creates a new instance of Funcionalidade */
    public Funcionalidade() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCurto() {
        return nome_curto;
    }

    public void setNomeCurto(String nomeCurto) {
        this.nome_curto = nomeCurto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set getPerfil() {
        return perfil;
    }

    public void setPerfil(Set perfil) {
        this.perfil = perfil;
    }

    public Aplicacao getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
    }

    public Serializable getPk() {
        return this.getId();
    }
}
