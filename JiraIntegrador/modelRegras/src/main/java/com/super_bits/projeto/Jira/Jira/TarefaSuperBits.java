/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.Jira;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoFormulario;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.projeto.Jira.Jira.tempo.DataUtilJira;
import com.super_bits.projeto.Jira.Jira.tempo.InvalidDurationException;
import com.super_bits.projeto.Jira.TipoProfissional;
import com.super_bits.projeto.controller.FabAcaoPrevisaoProjeto;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 *
 *
 * @author salvioF
 */
public class TarefaSuperBits extends ItemSimples implements ItfTarefaSuperBitsFW {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private final int id;

    private final TarefaJira tarefaJiraOrigem;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private final String nomeTarefa;
    private final TipoProfissional tipoProfissional;

    @Override
    public int getMinutosPrevistosTrabalho() {

        try {
            return (int) (DataUtilJira.getDuration(tarefaJiraOrigem.getTempoEsperado()) / 60);
        } catch (InvalidDurationException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Duração inválida", ex);
            return 0;
        }

    }

    private int getMinutosTrabalhados() {
        return 0;
    }

    private int getMinutosRestantes() {
        int minutosRestantes = getMinutosTrabalhados() - getMinutosTrabalhados();
        if (minutosRestantes < 0) {
            return minutosRestantes;
        }
        return minutosRestantes;
    }

    private int getHorasExtras() {
        int horasExtras = getMinutosTrabalhados() - getMinutosPrevistosTrabalho();
        if (horasExtras < 0) {
            return 0;
        }
        return horasExtras;

    }

    public TarefaSuperBits(TarefaJira tarefaJiraOrigem) {

        this.tarefaJiraOrigem = tarefaJiraOrigem;
        tipoProfissional = getTarefaJiraOrigem().getTipoTarefa().getTipoProfissional().getRegistro();
        String strDifIDtipoTarefa = tarefaJiraOrigem.getTipoTarefa().getSigla();
        nomeTarefa = tarefaJiraOrigem.getNomeTarefa();
        switch (tarefaJiraOrigem.getTipoOrigem()) {
            case BANCO_DE_DADOS:
                id = (tarefaJiraOrigem.getTabelaVinculada().getName() + strDifIDtipoTarefa).hashCode();

                break;
            case ACAO_DO_SISTEMA:
                id = (tarefaJiraOrigem.getAcaoVinculada().getNomeUnico() + strDifIDtipoTarefa).hashCode();

                break;
            default:
                throw new AssertionError(tarefaJiraOrigem.getTipoOrigem().name());

        }

    }

    @Override
    public final TarefaJira getTarefaJiraOrigem() {
        return tarefaJiraOrigem;
    }

    @Override
    public TipoProfissional getTipoProfissionalNescessario() {
        return tipoProfissional;
    }

    public TipoProfissional getTipoProfissional() {
        return tipoProfissional;
    }

    @Override
    public ItfAcaoFormulario getFormularioDetalhes() {
        switch (tarefaJiraOrigem.getTipoTarefa()) {
            case ACAO_TESTE_MANAGED_BEAN:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_GESTAO.getAcaoDoSistema();

            case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_GESTAO.getAcaoDoSistema();

            case ACAO_CRIAR_FORMULARIO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_FORMULARIO.getAcaoDoSistema();
            case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_FORMULARIO.getAcaoDoSistema();
            case ACAO_IMPLEMENTAR_CONTROLLER:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER.getAcaoDoSistema();
            case ACAO_TESTE_CONTROLLER:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER.getAcaoDoSistema();
            case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER.getAcaoDoSistema();
            case ACAO_TESTE_CONTROLLER_COMPLEXO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_DETALHES_ACAO_CONTROLLER.getAcaoDoSistema();
            case ACAO_TESTES_AMBIENTE_DE_DADOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_BANCO_TESTES_TIPOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_TESTES_ENTIDADE_CALCULO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_TESTES_ENTIDADE_LISTAS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_ENTIDADE_CRIAR_CALCULOS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_ENTIDADE_CRIAR_LISTAS:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();
            case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                return (ItfAcaoFormulario) FabAcaoPrevisaoProjeto.ACAO_PREVISAO_FRM_VISAO_GERAL_BANCO_DE_DADOS.getAcaoDoSistema();

            default:
                throw new AssertionError(tarefaJiraOrigem.getTipoTarefa().name());

        }
    }

    @Override
    public String toString() {
        if (id == 0) {
            return null;
        }
        return String.valueOf(id);

    }

}
