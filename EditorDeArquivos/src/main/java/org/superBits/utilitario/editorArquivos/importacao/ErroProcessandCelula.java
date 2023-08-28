/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.importacao;

import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.ErroSBCoreFW;

/**
 *
 * @author salvio
 */
public class ErroProcessandCelula extends ErroSBCoreFW {

    private int linha;
    private int coluna;
    private String campo;
    private String Erro;

    public ErroProcessandCelula(int pLinha, int pColuna, String pCampo, String pErro) {

        super("Erro lendo dado da planilha, linha " + pLinha + " coluna" + pColuna + " aramazenando em " + pCampo + " erro" + pErro);
        linha = pLinha;
        coluna = pColuna;
        campo = pCampo;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return Erro;
    }

}
