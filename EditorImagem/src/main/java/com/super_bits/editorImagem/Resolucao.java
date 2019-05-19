/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.editorImagem;

/**
 *
 * ATENÇÃO A DOCUMENTAÇÃO DA CLASSE É OBRIGATÓRIA O JAVADOC DOS METODOS PUBLICOS
 * SÓ SÃO OBRIGATÓRIOS QUANDO NÃO EXISTIR UMA INTERFACE DOCUMENTADA, DESCREVA DE
 * FORMA OBJETIVA E EFICIENTE, NÃO ESQUEÇA QUE VOCÊ FAZ PARTE DE UMA EQUIPE.
 *
 *
 * @author <a href="mailto:salviof@gmail.com">Salvio Furbino</a>
 * @since 13/04/2015
 * @version 1.0
 */
public class Resolucao implements ItfResolucao {

    public Resolucao(int pAltura, int pLargura) {
        altura = pAltura;
        largura = pLargura;
    }
    private int largura;
    private int altura;

    @Override
    public int getLargura() {
        return largura;
    }

    @Override
    public int getAltura() {
        return altura;
    }

}
