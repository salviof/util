/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testesFW.ConfigCoreJunitPadraoDevLib;

/**
 *
 * @author salvio
 */
public class FabConfigArquivoDeEntidadeS3Test {

    public FabConfigArquivoDeEntidadeS3Test() {
    }

    /**
     * Test of values method, of class FabConfigArquivoDeEntidadeS3.
     */
    @Test
    public void testValues() {
        SBCore.configurar(new ConfigCoreJunitPadraoDevLib(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        System.out.println(FabConfigArquivoDeEntidadeS3.DOMINIO_PROXY_S3.getNomeVariavelDeAmbienteNoSistema());
        System.out.println(FabConfigArquivoDeEntidadeS3.S3_BUCKET.getNomeVariavelDeAmbienteNoSistema());
        System.out.println(FabConfigArquivoDeEntidadeS3.S3_CHAVE_PUBLICA.getNomeVariavelDeAmbienteNoSistema());
        System.out.println(FabConfigArquivoDeEntidadeS3.S3_CHAVE_SECRETA.getNomeVariavelDeAmbienteNoSistema());
    }

}
