/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;

/**
 *
 * @author AJLima
 */
public class UsuarioPerfil  extends BaseDTOAb{
    
  private  String nome;
  private  String login;
  private  String lotacao;
  private  String perfil;
  private  boolean ativo;

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

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String Perfil) {
        this.perfil = Perfil;
    }

    @Override
    public Serializable getPk() {
        return null;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
