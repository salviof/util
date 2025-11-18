/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.controller;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoFormulario;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;
import com.super_bits.modulos.SBAcessosModel.controller.FabModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.controller.InfoModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
import com.super_bits.modulos.SBAcessosModel.model.acoes.UtilFabricaDeAcoesAcessosModel;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabricaAcoes;
import com.super_bits.projeto.Jira.previsao.PrevisaoProjeto;

/**
 *
 * @author salvioF
 */
@InfoModulosSistemaSB(modulo = FabModulosSistemaSB.PAGINAS_DO_SISTEMA)
public enum FabAcaoPrevisaoProjeto implements ComoFabricaAcoes {

    /**
     * Gestão da previsão do projeto
     */
    @InfoTipoAcaoGestaoEntidade(nomeAcao = "Requisitos do sitema ",
            icone = "fa fa-codepen",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "gerenciarProjetoAtual.xhtml")
    ACAO_PREVISAO_MB_GESTAO,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Resumo Geral",
            icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "resumoGeral.xhtml"
    )
    ACAO_PREVISAO_FRM_RESUMO_GERAL,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Ambiente de Desenvolvimento",
            icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "ambiente.xhtml"
    )
    ACAO_PREVISAO_FRM_AMBIENTE_DESENVOLVIMENTO,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Banco de Dados", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "visaoGeralBanco.xhtml"
    )
    ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Gestão do Sistema", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "visaoGeralGestao.xhtml"
    )
    ACAO_PREVISAO_FRM_VISAO_GERAL_GESTAO,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Modulos", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "visaoGeralModulos.xhtml"
    )
    ACAO_PREVISAO_FRM_VISAO_GERAL_MODULOS,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes ação do sistema", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "acaoControllerDetalhes.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER,
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes ação do sistema", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "acaoControllerDetalhes.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_ACAO_NOVA_TECNOLOGIA,
    @InfoTipoAcaoFormulario(nomeAcao = "Integração do sistema", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "acaoControllerDetalhes.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_ACAO_INTEGRACAO,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes sistema de gestão", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "acaoGestaoDetalhes.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_ACAO_GESTAO,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes sistema do Modulo", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "detalhesModulo.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_MODULO,
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes da Tabela", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "detalhesTabela.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_TABELA_BANCO_DADDOS,
    /**
     *
     */
    @InfoTipoAcaoFormulario(nomeAcao = "Detalhes do formulário", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "acaoFormularioDetalhes.xhtml"
    )
    ACAO_PREVISAO_FRM_DETALHES_ACAO_FORMULARIO,
    @InfoTipoAcaoFormulario(nomeAcao = "Tipo Navegacao", icone = "fa fa-bars",
            xhtmlDaAcao = FabAcaoPrevisaoProjeto.ARQUIVOS_DE_FORMULARIOMODULO + "tipoNavegacao.xhtml"
    )
    ACAO_PREVISAO_FRM_ESCOLHER_TIPO_NAVEGACAO;
    ;


    public static final String ARQUIVOS_DE_FORMULARIO = "/site/modulos/projetoAtual/";
    public static final String ARQUIVOS_DE_FORMULARIOMODULO = "/resources/site/modulos/projetoAtual/";

    public ComoAcaoDoSistema getAcaoDoSistema() {
        AcaoDoSistema acao = (AcaoDoSistema) UtilFabricaDeAcoesAcessosModel.getNovaAcao(this);
        return acao;
    }

    @Override
    public Class getEntidadeDominio() {
        return PrevisaoProjeto.class;
    }

    @Override
    public String getNomeModulo() {
        return UtilFabricaDeAcoesAcessosModel.getModuloByFabrica(this).getNome();
    }

    @Override
    public ComoAcaoDoSistema getRegistro() {
        return getAcaoDoSistema();
    }
}
