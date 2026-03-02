/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ItfFabConfigModulo;

/**
 *
 * @author sfurbino
 */
public enum FabConfigArquivoDeEntidadeS3 implements ItfFabConfigModulo {

    S3_CHAVE_SECRETA,
    S3_CHAVE_PUBLICA,
    S3_BUCKET,
    DOMINIO_PROXY_S3;

    @Override
    public String getValorPadrao() {
        return "?????????????";
    }

}
