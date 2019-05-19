/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoFormulario;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.cep.ItfRegiao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.projeto.controller.FabAcaoPrevisaoProjeto;
import com.super_bits.modulosSB.SBCore.modulos.view.formulario.ItfFormulario;

/**
 *
 * @author salvioF
 */
public enum FabTipoPrevisao implements ItfFabrica {

    PREVISAO_GESTAO,
    PREVISAO_TABELA,
    PREVISAO_BANCO_DADOS,
    PREVISAO_CONTROLLER,
    PREVISAO_NOVA_TECNOLOGIA,
    PREVISAO_INTEGRACAO;

    @Override
    public Object getRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ItfAcaoFormulario getFormulario() {
        switch (this) {
            case PREVISAO_GESTAO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_GESTAO.getAcaoDoSistema();
            case PREVISAO_TABELA:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_GESTAO.getAcaoDoSistema();
            case PREVISAO_BANCO_DADOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_TABELA_BANCO_DADDOS.getAcaoDoSistema();
            case PREVISAO_CONTROLLER:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER.getAcaoDoSistema();
            case PREVISAO_NOVA_TECNOLOGIA:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_NOVA_TECNOLOGIA.getAcaoDoSistema();
            case PREVISAO_INTEGRACAO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_INTEGRACAO.getAcaoDoSistema();
            default:
                throw new AssertionError(this.name());

        }
    }

}
