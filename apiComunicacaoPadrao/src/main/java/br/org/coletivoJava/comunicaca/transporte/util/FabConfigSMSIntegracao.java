/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.comunicaca.transporte.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;

/**
 *
 * @author desenvolvedor
 */
public enum FabConfigSMSIntegracao implements ItfFabConfigModulo {
    CHAVE_PUBLICA,
    CHAVE_PRIVADA;

    @Override
    public String getValorPadrao() {
        return "naodefinido";

    }

}
