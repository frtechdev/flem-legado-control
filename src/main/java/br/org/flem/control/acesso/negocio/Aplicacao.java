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
import javax.persistence.OneToMany;

/**
 *
 * @author mjpereira
 */
@Entity
public class Aplicacao extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aplicacao")
    private Integer id;
    private String nome;
    private String nome_curto;
    private String descricao;
    private String versao;
    private String linguagem;
    private String banco;
    private String servidor;
    private String observacao;
    @OneToMany(mappedBy = "aplicacao")
    private Set<Funcionalidade> funcionalidade = new HashSet<Funcionalidade>();

    /** Creates a new instance of Aplicacao */
    public Aplicacao() {
    }

    public Aplicacao(Integer id) {
        this.id = id;
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

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(Set funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public Serializable getPk() {
        return this.getId();
    }
}
