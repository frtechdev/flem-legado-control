/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.negocio;

/**
 *
 * @author AJLima
 */
public class AplicativoAcessoIntranet {
    
    int id;
    int colaborador;
    AplicativoIntranet aplicativo;
    byte flg_liberado;

    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getColaborador() {
        return colaborador;
    }
    public void setColaborador(int colaborador) {
        this.colaborador = colaborador;
    }
    public AplicativoIntranet getAplicativo() {
        return aplicativo;
    }
    public void setAplicativo(AplicativoIntranet aplicativo) {
        this.aplicativo = aplicativo;
    }
    public byte getFlg_liberado() {
        return flg_liberado;
    }
    public void setFlg_liberado(byte flg_liberado) {
        this.flg_liberado = flg_liberado;
    }
  
}
