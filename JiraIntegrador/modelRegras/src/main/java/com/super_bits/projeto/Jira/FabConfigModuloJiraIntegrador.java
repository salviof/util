/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;

/**
 *
 * @author SalvioF
 */
public enum FabConfigModuloJiraIntegrador implements ItfFabConfigModulo {
    SERVIDOR,
    USUARIO,
    SENHA,
    PROJETO;

    @Override
    public String getValorPadrao() {
        switch (this) {
            case SERVIDOR:
                return "https://exemploServidorjira.com.br";

            case USUARIO:
                return "usuario@usuarioJira.com.br";

            case SENHA:
                return "senhaExempo";
            case PROJETO:
                return SBCore.getNomeProjeto().toUpperCase();

            default:
                throw new AssertionError(this.name());

        }

    }

}
