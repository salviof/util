/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.importacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author SalvioF
 */
public class ImportacaoExcelTest {

    @Test
    public void testCarregarArquivo() {

        //   SBCore.configurar(new ConfiguradorCoreDeProjetoJarAbstrato , SBCore.ESTADO_APP.DESENVOLVIMENTO);
        ComoEntidadeSimples Teste = new ItemSimplesTeste();

        Map<String, Integer> parametros = new HashMap<>();

        parametros.put("id", 1);
        parametros.put("nome", 2);
        parametros.put("descricao", 3);

        System.out.println(SBCore.getCaminhoDesenvolvimento());

        ImportacaoExcel<ItemSimplesTeste> importador = new ImportacaoExcel<>(SBCore.getCaminhoDesenvolvimento() + "/src/test/resources/excelTesteXLS.xls", parametros, ItemSimplesTeste.class);

        for (ItemSimplesTeste reg : importador.getRegistrosSucesso()) {

            System.out.println(reg.getId());

            System.out.println(reg.getNome());

            System.out.println(reg.getDescricao());

            System.out.println("_________________________");

        }

        System.out.println(importador.getRelatorioImportacao());

        UtilSBCoreArquivoTexto.escreverEmArquivo("/home/desenvolvedor/relatorio.html", importador.getRelatorioImportacao());

    }

}
