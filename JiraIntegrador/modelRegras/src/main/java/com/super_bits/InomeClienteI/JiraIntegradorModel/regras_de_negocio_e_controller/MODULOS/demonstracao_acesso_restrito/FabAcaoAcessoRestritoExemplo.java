/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.MODULOS.demonstracao_acesso_restrito;

import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoFormulario;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;

import com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.FabModulosIProjetoTesteI;
import com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.InfoModuloInomeProjetoI;
import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
import com.super_bits.modulos.SBAcessosModel.model.acoes.UtilFabricaDeAcoesAcessosModel;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;

/**
 *
 * Cada ação do sistema corresponde a um ponto de função <br>
 *
 * Deve ser criada uma fabrica de ação por módulo, a criação por dominio de
 * entidade é desencorajada por gerar nescessidade de criar muitas anotações
 * (entretando o sistema mantem a compatibilidade com fabricas de ação para o
 * mesmo modulo
 *
 * A nomeclatura das ações são padronizadas, e dizem muito sobre ela, para mais
 * informações consulte:
 *
 * @see
 * UtilFabricaDeAcoesAcessosModel#getTipoAcaoByNome(com.super_bits.modulosSB.SBCore.fabrica.ItfFabricaAcoes)
 *
 *
 * @author Sálvio Furbino
 */
@InfoModuloInomeProjetoI(modulo = FabModulosIProjetoTesteI.DEMONSTRACAO_ACESSO_RESTRITO)
public enum FabAcaoAcessoRestritoExemplo implements ItfFabricaAcoes {

    /**
     *
     *
     * Estando anotado com MB, indica que este recurso é um recurso de
     * Gerenciamento com um managed vinculado a ele
     *
     *
     */
    @InfoTipoAcaoGestaoEntidade(entidade = AcaoDoSistema.class, icone = "fa fa-key", nomeAcao = "Gerenciar Tela Acesso restrito", precisaPermissao = true, xhtmlDaAcao = "/site/restrito/restrito.xhtml")
    RECURSO_RESTRITO_MB_GERENCIAR,
    /**
     * EStando anotado com FRM_LISTAR indica que o recurso é do tipo listagem de
     * registros , o icone e o arquivo do formulário será configurado
     * automaticamente
     *
     *
     * @see
     * UtilFabricaDeAcoesAcessosModel#getTipoAcaoByNome(com.super_bits.modulosSB.SBCore.fabrica.ItfFabricaAcoes)
     */
    @InfoTipoAcaoFormulario(campos = {"id", "nomeAcao", "precisaPermissao"}, entidade = AcaoDoSistema.class)
    RECURSO_RESTRITO_FRM_LISTAR,
    /**
     *
     *
     *
     *
     * @see
     * UtilFabricaDeAcoesAcessosModel#getTipoAcaoByNome(com.super_bits.modulosSB.SBCore.fabrica.ItfFabricaAcoes)
     */
    @InfoTipoAcaoFormulario(campos = {"id", "nomeAcao", "precisaPermissao", "cor"}, entidade = AcaoDoSistema.class)
    RECURSO_RESTRITO_FRM_VISUALIZAR,
    /**
     *
     * @see
     * UtilFabricaDeAcoesAcessosModel#getTipoAcaoByNome(com.super_bits.modulosSB.SBCore.fabrica.ItfFabricaAcoes)
     */
    @InfoTipoAcaoFormulario(campos = {"id", "nomeAcao", "precisaPermissao", "cor"}, entidade = AcaoDoSistema.class)
    RECURSO_RESTRITO_FRM_EDITAR,
    /**
     *
     *
     * @see
     * UtilFabricaDeAcoesAcessosModel#getTipoAcaoByNome(com.super_bits.modulosSB.SBCore.fabrica.ItfFabricaAcoes)
     */
    @InfoTipoAcaoController(icone = "fa fa-save", entidade = AcaoDoSistema.class)
    RECURSO_RESTRITO_CTR_SALVAR_MERGE;

    
    public AcaoDoSistema getAcaoDoSistema() {

        AcaoDoSistema acao = (AcaoDoSistema) UtilFabricaDeAcoesAcessosModel.getNovaAcao(this);

        return acao;

    }

    @Override
    public Class getEntidadeDominio() {
        return AcaoDoSistema.class;

    }

    @Override
    public String getNomeModulo() {
        return UtilFabricaDeAcoesAcessosModel.getModuloByFabrica(this).getNome();
    }

    @Override
    public AcaoDoSistema getRegistro() {
        return getAcaoDoSistema();
    }

}
