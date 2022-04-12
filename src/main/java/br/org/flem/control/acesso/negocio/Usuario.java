package br.org.flem.control.acesso.negocio;

import br.org.flem.fw.persistencia.recurso.TipoAutenticacao;
import br.org.flem.fw.persistencia.recurso.TipoSolicitacao;
import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author mjpereira
 */
@Entity
public class Usuario extends BaseDTOAb {

    /**
     * idt colab
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    /**
     * idt colab
     */
    private String nome;
    /**
     * usuario da rede
     */
    private String login;
    /**
     * matricula hr
     */
    private Integer matricula;
    
    @Column(nullable=true)
    private Integer codigoDominio;
    /**
     * criacao
     */
    @Temporal(TemporalType.DATE)
    private Date criacao;
    private Boolean ativo;
    private String lotacao;
    private Integer lotacaoDominio;
    @ManyToMany
    @JoinTable(name = "UsuarioPerfil",
    joinColumns =
    @JoinColumn(name = "id_usuario"),
    inverseJoinColumns =
    @JoinColumn(name = "id_perfil"))
    private Set<Perfil> perfil = new HashSet<Perfil>();
    @Enumerated
    @Column(name = "tipoAutenticacao", nullable = false)
    private TipoAutenticacao tipoAutenticacao;
    @Enumerated
    @Column(name = "tipoSolicitacao")
    private TipoSolicitacao tipoSolicitacao;
    private String senha;
    private Boolean alterarSenha;
    private boolean bloqueadoAd;
    private boolean bloqueadoSe;
    @OneToOne(mappedBy = "usuario")
    private BloqueioAfastamentoAD agendamento;
    @Transient
    private Date dataInicioBloqueio;
    @Transient
    private Date dataFimBloqueio;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getCodigoDominio() {
        return codigoDominio;
    }

    public void setCodigoDominio(Integer codigoDominio) {
        this.codigoDominio = codigoDominio;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public Set<Perfil> getPerfil() {
        return perfil;
    }

    public void setPerfil(Set<Perfil> perfil) {
        this.perfil = perfil;
    }

    public Serializable getPk() {
        return this.id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

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

    public TipoAutenticacao getTipoAutenticacao() {
        return tipoAutenticacao;
    }

    public void setTipoAutenticacao(TipoAutenticacao tipoAutenticacao) {
        this.tipoAutenticacao = tipoAutenticacao;
    }

    public TipoSolicitacao getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public Boolean getAlterarSenha() {
        return alterarSenha;
    }

    public void setAlterarSenha(Boolean alterarSenha) {
        this.alterarSenha = alterarSenha;
    }

    public boolean isBloqueadoAd() {
        return bloqueadoAd;
    }

    public void setBloqueadoAd(boolean bloqueadoAd) {
        this.bloqueadoAd = bloqueadoAd;
    }

    public boolean isBloqueadoSe() {
        return bloqueadoSe;
    }

    public void setBloqueadoSe(boolean bloqueadoSe) {
        this.bloqueadoSe = bloqueadoSe;
    }

    public Date getDataInicioBloqueio() {
        return dataInicioBloqueio;
    }

    public void setDataInicioBloqueio(Date dataInicioBloqueio) {
        this.dataInicioBloqueio = dataInicioBloqueio;
    }

    public Date getDataFimBloqueio() {
        return dataFimBloqueio;
    }

    public void setDataFimBloqueio(Date dataFimBloqueio) {
        this.dataFimBloqueio = dataFimBloqueio;
    }

    public BloqueioAfastamentoAD getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(BloqueioAfastamentoAD agendamento) {
        this.agendamento = agendamento;
    }
}
