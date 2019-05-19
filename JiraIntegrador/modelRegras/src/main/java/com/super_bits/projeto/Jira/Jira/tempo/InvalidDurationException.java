/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.Jira.tempo;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvioF
 */
public class InvalidDurationException extends Exception {

    public InvalidDurationException(String pMensagem, Throwable t) {
        SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, pMensagem, t);
    }

    public InvalidDurationException(String pMensagem) {
        SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, pMensagem, this);
    }

}
