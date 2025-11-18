/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabrica;

/**
 *
 * @author salvioF
 */
public enum FabTipoSubstituicao implements ComoFabrica {

    MANUAL, CAMPOS_DOCUMENTO;

    @Override
    public TipoSubistituicao getRegistro() {

        TipoSubistituicao tipo = new TipoSubistituicao();

        switch (this) {
            case MANUAL:
                tipo.setNome("Substituição Manual");
                tipo.setDescricao("Percorre cada objeto do arquivo, e substitui (Mais Lento)");
                break;
            case CAMPOS_DOCUMENTO:
                tipo.setNome("Variaveis Field");
                tipo.setDescricao("Busca e altera os Fields do documento");
                break;
            default:
                throw new AssertionError(this.name());

        }
        return tipo;
    }

}
