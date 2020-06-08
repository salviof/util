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

    ARQUIVOS_ENTIDADE_S3_CHAVE_SECRETA,
    ARQUIVOS_ENTIDADE_S3_CHAVE_PUBLICA,
    ARQUIVOS_ENTIDADE_S3_BUCKET,
    ARQUIVOS_ENTIDADE_DOMINIO_PROXY_S3;

    @Override
    public String getValorPadrao() {
        return "?????????????";
    }

}
