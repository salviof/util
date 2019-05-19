/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.model;

import java.util.Date;

/**
 *
 * @author sfurbino
 */
public class RespostaCMD {

    public enum RESULTADOCMD {

        OK, ALERTA, FALHOU
    }

    private String retornoErro;
    private String retornoPadrao;

    private RESULTADOCMD resultado;
    private String comando;
    private Date dataHora;

    /**
     *
     * @return Retorno do Console normal e console De erro
     */
    public String getRetorno() {

        return retornoPadrao + "\n" + retornoErro;
    }

    /**
     *
     * @return Resultado da execução do comando que pode ser : OK (Obteve o
     * resultado esperado) Alerta (executou mas não obeteve resultado esperado)
     * Erro (não foi possível executar
     */
    public RESULTADOCMD getResultado() {
        return resultado;
    }

    public void setResultado(RESULTADOCMD resultado) {
        this.resultado = resultado;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getRetornoErro() {
        return retornoErro;
    }

    public void setRetornoErro(String retornoErro) {
        this.retornoErro = retornoErro;
    }

    public String getRetornoPadrao() {
        return retornoPadrao;
    }

    public void setRetornoPadrao(String retornoPadrao) {
        this.retornoPadrao = retornoPadrao;
    }

}
