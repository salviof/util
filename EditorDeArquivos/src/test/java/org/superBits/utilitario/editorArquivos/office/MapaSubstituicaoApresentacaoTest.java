/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import java.io.File;
import java.util.List;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.superBits.utilitario.editorArquivos.ConfiguradorCoreEditor;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoPacoteOffice;
import org.superBits.utilitario.editorArquivos.util.UtilSBEditorArquivosConversor;

/**
 *
 * @author salvio
 */
public class MapaSubstituicaoApresentacaoTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Test
    public void testLerArquivoArquivoPacoteOffice() throws Exception {
        String teste = "adfsdfasdf[33]";

        SBCore.configurar(new ConfiguradorCoreEditor(), SBCore.ESTADO_APP.DESENVOLVIMENTO);

        if (!teste.replaceAll("\\[[0-9]", "-").equals(teste)) {
            System.out.println("Tem Colchete com numero");
        }

        String diretorioApp = "/home/superBits/projetos/coletivoJava/source/fw/util/EditorDeArquivos/";
        String arqExemplo = diretorioApp + "src/test/resources/exemplos/apresentacaoTeste.pptx";

        String arqModificado = diretorioApp + "src/test/resources/exemplos/apresentacaoTesteMod.pptx";
        UtilSBCoreArquivos.copiarArquivos(arqExemplo, arqModificado);
        // UtilSBEditorArquivosConversor.converterWordEmPDF(arqExemplo, arqExemplo + ".pdf");
        MapaSubstituicaoApresentacao novoMapa = new MapaSubstituicaoApresentacao(new File(arqModificado));
        novoMapa.adicionarPalavraChave("[nome]", "coléeeeeeeeeeeeeeeeeeee");
        novoMapa.adicionarImagem("[logo]", diretorioApp + "src/test/resources/exemplos/Gnu.png");
        novoMapa.adicionarImagem("[logoCliente]", diretorioApp + "src/test/resources/exemplos/Gnu.png");
        novoMapa.adicionarImagem("[logoProspecto]", diretorioApp + "src/test/resources/exemplos/Gnu.png");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[1].nome]", "contrato1");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[1].valor]", "R$ 100,00");
        novoMapa.adicionarPalavraChave("[SITE]", "www.teste.com.br  [nome] ");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[2].nome]", "contrato2");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[2].valor]", "R$ 500,00");

        for (int i = 1; i < 3; i++) {
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].descricao]", "Descricao solucao teste  " + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].valorSetup]", "R$5.000,00");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].valorMensal]", "R$500,00");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.nome]", "Tipo solução Teste" + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.descricao]", "DEscricao tipo solução teste");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.url]", "www.teste.com.br");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.descricaoApresentacao]", "Descricao apresentação");
        }

        novoMapa.substituirEmArquivo();

        UtilSBEditorArquivosConversor.converterWordEmPDF(arqModificado, arqModificado + ".pdf");
    }

    /**
     * Test of listaTextosSubstituiveis method, of class
     * MapaSubstituicaoApresentacao.
     */
    @Test
    public void testListaTextosSubstituiveis() throws Exception {
        System.out.println("listaTextosSubstituiveis");
        OpcPackage pArquivoApresentecao = null;
        MapaSubstituicaoApresentacao instance = null;
        List<TextoOfficeSubstituivel> expResult = null;
        List<TextoOfficeSubstituivel> result = instance.listaTextosSubstituiveis(pArquivoApresentecao);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gerarManipulador method, of class MapaSubstituicaoApresentacao.
     */
    @Test
    public void testGerarManipulador() {
        System.out.println("gerarManipulador");
        TextoOfficeSubstituivel pTextoSubstituivel = null;
        List<String> pControleListas = null;
        OpcPackage pArquivoProcessado = null;
        MapaSubstituicaoApresentacao instance = null;
        ManipulacaoPacoteOffice expResult = null;
        ManipulacaoPacoteOffice result = instance.gerarManipulador(pTextoSubstituivel, pControleListas, pArquivoProcessado);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
