/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.ItemListaComSubcampos;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilCRCArquivos;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.superBits.utilitario.editorArquivos.ConfiguradorCoreEditor;
import org.superBits.utilitario.editorArquivos.util.UtilSBEditorArquivosConversor;

/**
 *
 * @author salvio
 */
public class MapaSubstituicaoOfficeTest {

    @Test
    public void testSubstituirEmArquivo() {

        String teste = "adfsdfasdf[33]";

        if (!teste.replaceAll("\\[[0-9]", "-").equals(teste)) {
            System.out.println("Tem Colchete com numero");
        }
        SBCore.configurar(new ConfiguradorCoreEditor(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        String diretorioApp = "/home/superBits/projetos/coletivoJava/source/fw/util/EditorDeArquivos/";
        String arqExemplo = diretorioApp + "contratoTeste2.docx";
        String arqModificado = diretorioApp + "modificado.docx";
        UtilCRCArquivos.copiarArquivos(arqExemplo, arqModificado);
        // UtilSBEditorArquivosConversor.converterWordEmPDF(arqExemplo, arqExemplo + ".pdf");
        MapaSubstituicaoWord novoMapa = new MapaSubstituicaoWord(new File(arqModificado));
        novoMapa.adicionarPalavraChave("[nome]", "coléeeeeeeeeeeeeeeeeeee");
        novoMapa.adicionarImagem("[logoCliente]", "/home/superBits/projetos/coletivoJava/source/fw/util/EditorDeArquivos/Gnu.png");
        novoMapa.adicionarImagem("[logoProspecto]", "/home/superBits/projetos/coletivoJava/source/fw/util/EditorDeArquivos/Gnu.png");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[0].nome]", "contrato1");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[0].valor]", "R$ 100,00");
        novoMapa.adicionarPalavraChave("[SITE]", "www.teste.com.br  [nome] ");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[1].nome]", "contrato2");
        novoMapa.adicionarPalavraChave("[Cliente.contratos[1].valor]", "R$ 500,00");

        novoMapa.adicionarPalavraChave("dados.FORMA_PAGAMENTO", "Pagarei com amor");
        novoMapa.adicionarPalavraChave("dados.CONTRATO_PARCELAMENTO", "A parcelas a deusdará");

        for (int i = 0; i < 3; i++) {
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].descricao]", "Descricao solucao teste  " + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].valorSetup]", "R$5.000,0" + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].valorMensal]", "R$500,0" + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.nome]", "Tipo solução Teste" + i);
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.descricao]", "DEscricao tipo solução teste");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.url]", "www.teste.com.br");
            novoMapa.adicionarPalavraChave("[solucao[" + i + "].tipoSolucao.descricaoApresentacao]", "Descricao apresentação");
        }
        List<ItemListaComSubcampos> lista = novoMapa.getListaOrdenada("[solucao[]");

        for (ItemListaComSubcampos item : lista) {
            System.out.println(item.getSubCampos());
        }

        novoMapa.substituirEmArquivo();

        UtilSBEditorArquivosConversor.converterWordEmPDF(arqModificado, arqModificado + ".pdf");
    }

}
