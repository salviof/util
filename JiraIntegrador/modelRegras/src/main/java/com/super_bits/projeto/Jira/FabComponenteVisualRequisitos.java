/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.modulosSB.SBCore.modulos.view.fabricasCompVisual.FabFamiliaCompVisual;
import com.super_bits.modulosSB.SBCore.modulos.view.fabricasCompVisual.InfoComponenteVisual;
import com.super_bits.modulosSB.SBCore.modulos.view.fabricasCompVisual.ComoFabTipoComponenteVisual;
import org.coletivojava.fw.api.objetoNativo.view.componente.ComponenteVisualBase;

import org.coletivojava.fw.utilCoreBase.UtilSBFabricaComponenteVisual;

/**
 *
 * @author salvioF
 */
public enum FabComponenteVisualRequisitos implements ComoFabTipoComponenteVisual {
    @InfoComponenteVisual(classesCSS = "opcoesElementoVinculado",
            xhtmlJSF = FabComponenteVisualRequisitos.PASTACOMPONENTES + "/elementoNaoVinculadoDetalhes.xhtml",
            descricao = "", nome = "")
    OPCOES_ELEMENTO_VINCULADO,
    @InfoComponenteVisual(classesCSS = "opcoesElementoNaoVinculado",
            xhtmlJSF = FabComponenteVisualRequisitos.PASTACOMPONENTES + "/elementoNaoVinculadoOpcoes.xhtml",
            descricao = "", nome = "")
    OPCOES_ELEMENTO_NAO_VINCULADA,
    @InfoComponenteVisual(classesCSS = "descricaoElementoVinculado",
            xhtmlJSF = FabComponenteVisualRequisitos.PASTACOMPONENTES + "/elementoNaoVinculadoDetalhes.xhtml",
            descricao = "", nome = "")
    DESCRICAO_ELEMENTO_VINCULADO,
    @InfoComponenteVisual(classesCSS = "descricaoElementoNaoVinculado",
            xhtmlJSF = FabComponenteVisualRequisitos.PASTACOMPONENTES + "/elementoVinculadoDetalhes.xhtml",
            descricao = "", nome = "")
    DECRICAO_ELEMENTO_NAO_VINCULADO;
    public static final String PASTACOMPONENTES = "/site/modulos/projetoAtual/";

    @Override
    public FabFamiliaCompVisual getFamilia() {
        return FabFamiliaCompVisual.ITENS_BEAN_SIMPLES;
    }

    @Override
    public ComponenteVisualBase getRegistro() {
        return (ComponenteVisualBase) UtilSBFabricaComponenteVisual.getComponenteVisualPersonalizado(this);
    }

}
