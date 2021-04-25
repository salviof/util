/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.importacao;

import jxl.Cell;

/**
 *
 * @author sfurbino
 */
public abstract class ProcessadorCampoPLanilhaLogicaPersonalizada {

    public ProcessadorCampoPLanilhaLogicaPersonalizada(String pNomeColuna) {
        nomeColuna = pNomeColuna;
    }

    public final String nomeColuna;

    public abstract Object processarValor(Cell pCelula);

    public String getNomeColuna() {
        return nomeColuna;
    }

}
