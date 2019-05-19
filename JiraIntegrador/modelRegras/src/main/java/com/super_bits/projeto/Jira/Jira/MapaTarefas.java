/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.Jira;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaDeAcoes;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.projeto.Jira.UtilSBCoreJira;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvioF
 */
public class MapaTarefas {

    private final Map<String, List<TarefaSuperBits>> TAREFAS_PROJETO_ATUAL = new HashMap<>();

    private void cadastrarEntidades(List<Class> pEntidades) {
        pEntidades.stream().forEach((entidade) -> {
            addTarefaBancoDeDAdos(entidade);
        });
    }

    public MapaTarefas(MapaDeAcoes acoes, String nomePersistenceUnit) {
        this(acoes, UtilSBPersistencia.getTodasEntidades(nomePersistenceUnit));

    }

    public MapaTarefas(MapaDeAcoes acoes, List<Class> entidades) {
        this(acoes);
        if (entidades != null) {
            cadastrarEntidades(entidades);
        }
    }

    public MapaTarefas(MapaDeAcoes acoes) {
        Map<String, Class> mapaClasse = new HashMap();
        for (ItfAcaoDoSistema acao : acoes.getListaTodasAcoes()) {
            if (acao.getTipoAcaoGenerica() == null) {
                throw new UnsupportedOperationException("A ação generica para" + acao.getNomeUnico() + " não foi especificada");
            }
            for (UtilSBCoreJira.TIPOS_DE_TAREFA_JIRA tipoTarefa : UtilSBCoreJira.getTiposTarefaPorTipoAcao(acao.getTipoAcaoGenerica())) {
                addTarefa(acao);
            }
            mapaClasse.put(acao.getEnumAcaoDoSistema().getEntidadeDominio().getSimpleName(), acao.getEnumAcaoDoSistema().getEntidadeDominio());
        }

        cadastrarEntidades(Lists.newArrayList(mapaClasse.values()));

    }

    private void addTarefa(ItfAcaoDoSistema pAcao) {
        try {
            String chaveAcaoGestao;
            chaveAcaoGestao = pAcao.getAcaoDeGestaoEntidade().getNomeUnico();
            if (!TAREFAS_PROJETO_ATUAL.containsKey(chaveAcaoGestao)) {
                TAREFAS_PROJETO_ATUAL.put(chaveAcaoGestao, new ArrayList<>());
            }
            for (UtilSBCoreJira.TIPOS_DE_TAREFA_JIRA tipoTarefa : UtilSBCoreJira.getTiposTarefaPorTipoAcao(pAcao.getTipoAcaoGenerica())) {
                TarefaJira tarefa = UtilSBCoreJira.getTarefaJiraAcaoDoSistema(tipoTarefa, pAcao);
                TarefaSuperBits novaTarefa = new TarefaSuperBits(tarefa);
                if (!TAREFAS_PROJETO_ATUAL.get(chaveAcaoGestao).contains(novaTarefa)) {
                    TarefaSuperBits novaTarefaadd = new TarefaSuperBits(tarefa);
                    if (!TAREFAS_PROJETO_ATUAL.get(chaveAcaoGestao).contains(novaTarefaadd)) {
                        TAREFAS_PROJETO_ATUAL.get(chaveAcaoGestao).add(novaTarefaadd);
                    }
                }

            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro adicionando tarefa para ação" + pAcao.getNomeUnico(), t);
        }

    }

    private void addTarefaBancoDeDAdos(Class pEntidade) {

        if (!TAREFAS_PROJETO_ATUAL.containsKey(pEntidade.getSimpleName())) {
            TAREFAS_PROJETO_ATUAL.put(pEntidade.getSimpleName(), new ArrayList<>());
        }

        for (UtilSBCoreJira.TIPOS_DE_TAREFA_JIRA tipoTarefa : UtilSBCoreJira.getTiposTarefaPorEntidade(pEntidade)) {

            TarefaJira tarefaEntidade = UtilSBCoreJira.getTarefaJiraEntidade(tipoTarefa, pEntidade);
            TarefaSuperBits novaTarefaAdd = new TarefaSuperBits(tarefaEntidade);
            if (!TAREFAS_PROJETO_ATUAL.get(pEntidade.getSimpleName()).contains(novaTarefaAdd)) {
                TAREFAS_PROJETO_ATUAL.get(pEntidade.getSimpleName()).add(novaTarefaAdd);
            }
            /*  if (!UtilSBCoreJira.criarTarefafasDaAcao(getConexao(), tarefaEntidade, getAnalistaBancoDados())) {
                throw new UnsupportedOperationException("Erro criando ação para " + entidade.getSimpleName());
                } */
        }

    }

    public List<TarefaSuperBits> getTodasTarefas() {

        List<TarefaSuperBits> todasTarefas = new ArrayList<>();
        TAREFAS_PROJETO_ATUAL.values().stream().forEach((List<TarefaSuperBits> tarefasDogrupo) -> {
            tarefasDogrupo.stream().forEach((tr) -> {
                todasTarefas.add(tr);
            });
        });

        return todasTarefas;
    }

    public List<TarefaSuperBits> getTarefasDaGestao(ItfAcaoGerenciarEntidade pAcaoGestao) {
        return TAREFAS_PROJETO_ATUAL.get(pAcaoGestao.getNomeUnico());
    }

    public List<TarefaSuperBits> getTarefasDaTabela(Class pEntidade) {
        return TAREFAS_PROJETO_ATUAL.get(pEntidade.getSimpleName());
    }

}
