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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author mjpereira
 */
@Entity
public class Perfil extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer id;
    private String nome;
    private String nome_curto;
    private String descricao;
    @ManyToMany(mappedBy = "perfil")
    private Set<Usuario> usuario = new HashSet<Usuario>();
    @ManyToMany
    @JoinTable(name = "PerfilFuncionalidade",
    joinColumns =
    @JoinColumn(name = "id_perfil"),
    inverseJoinColumns =
    @JoinColumn(name = "id_funcionalidade"))
    private Set<Funcionalidade> funcionalidade = new HashSet<Funcionalidade>();

    /** Creates a new instance of Perfil */
    public Perfil() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Set<Usuario> usuario) {
        this.usuario = usuario;
    }

    public Set<Funcionalidade> getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Set<Funcionalidade> funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Serializable getPk() {
        return this.getId();
    }
}
