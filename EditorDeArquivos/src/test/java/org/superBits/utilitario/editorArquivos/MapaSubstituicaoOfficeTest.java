/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.superBits.utilitario.editorArquivos.office.MapaSubstituicaoWordOld;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import org.junit.Test;
import org.superBits.utilitario.editorArquivos.util.UtilSBEditorArquivosConversor;

/**
 *
 * @author salvioF
 */
public class MapaSubstituicaoOfficeTest {

    @Test
    public void testSubstituirEmArquivo() {

        String teste = "adfsdfasdf[33]";

        if (!teste.replaceAll("\\[[0-9]", "-").equals(teste)) {
            System.out.println("Tem Colchete com numero");
        }
        SBCore.configurar(new ConfiguradorCoreEditor(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        String diretorioApp = "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorDeArquivos/";
        String arqExemplo = diretorioApp + "contratoTeste2.docx";
        String arqModificado = diretorioApp + "modificado.docx";
        UtilSBCoreArquivos.copiarArquivos(arqExemplo, arqModificado);
        // UtilSBEditorArquivosConversor.converterWordEmPDF(arqExemplo, arqExemplo + ".pdf");
        MapaSubstituicaoWordOld novoMapa = new MapaSubstituicaoWordOld(arqModificado);
        novoMapa.adicionarPalavraChave("[nome]", "coléeeeeeeeeeeeeeeeeeee [nome]");
        novoMapa.adicionarImagem("[logoCliente]", "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorDeArquivos/Gnu.png");
        novoMapa.adicionarImagem("[logoProspecto]", "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorDeArquivos/Gnu.png");
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

}
