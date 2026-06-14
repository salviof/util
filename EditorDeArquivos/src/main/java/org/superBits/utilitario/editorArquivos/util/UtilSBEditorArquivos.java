/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilCRCArquivos;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.superBits.utilitario.editorArquivos.office.MapaSubstituicaoWord;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBEditorArquivos {

    public static boolean gerarNovoArquivoSubstituindoPalavraChave(String arquivoOrigem, String arquivoDestino, MapaSubstituicaoWord pMapaSubistituicao) {
        try {
            UtilCRCArquivos.copiarArquivos(arquivoOrigem, arquivoDestino);

            //MapaSubstituicaoWord novoMapa = new MapaSubstituicaoWord(arquivoDestino);
            //novoMapa.substituirEmArquivo();
            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro gerando arquivo a partir de modelo, de" + arquivoOrigem + "-" + arquivoDestino, t);
            return false;
        }

    }

}
