/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.Jira;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaDeAcoes;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import java.util.List;

/**
 *
 * @author salvioF
 */
public class MapaTarefasProjeto {

    private static boolean tarefasCriadas = false;
    private static MapaTarefas mapaTarefasDoProjeto;
    private static boolean ignorarModuloPersistencia = false;

    public static synchronized void criarTarefas() {
        if (tarefasCriadas) {
            return;
        }
        if (ignorarModuloPersistencia) {
            mapaTarefasDoProjeto = new MapaTarefas(new MapaDeAcoes(SBCore.getFabricasDeAcaoDoSistema()));
        } else {
            mapaTarefasDoProjeto = new MapaTarefas(new MapaDeAcoes(SBCore.getFabricasDeAcaoDoSistema()), SBPersistencia.getNomeBancoPadrao());
        }
        tarefasCriadas = true;
    }

    public static List<TarefaSuperBits> getTodasTarefas() {
        criarTarefas();

        return mapaTarefasDoProjeto.getTodasTarefas();
    }

    public static boolean isIgnorarModuloPersistencia() {
        return ignorarModuloPersistencia;
    }

    public static void setIgnorarModuloPersistencia(boolean ignorarModuloPersistencia) {
        MapaTarefasProjeto.ignorarModuloPersistencia = ignorarModuloPersistencia;
    }

    public static List<TarefaSuperBits> getTarefasDaGestao(ItfAcaoGerenciarEntidade pAcaoGestao) {
        criarTarefas();
        return mapaTarefasDoProjeto.getTarefasDaGestao(pAcaoGestao);
    }

    public static TarefaSuperBits getTarefaDaAcao(ItfAcaoDoSistema pAcao) {
        criarTarefas();
        List<TarefaSuperBits> tarefas = getTarefasDaGestao(pAcao.getAcaoDeGestaoEntidade());

        for (TarefaSuperBits t : tarefas) {
            if (t.getTarefaJiraOrigem().getAcaoVinculada().equals(pAcao)) {
                return t;
            }
        }
        if (tarefas != null && !tarefas.isEmpty()) {
            for (TarefaSuperBits t : tarefas) {
                if (t.getTarefaJiraOrigem().isGropoTarefas()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static List<TarefaSuperBits> getTarefasDaTabela(Class pEntidade) {
        criarTarefas();
        return mapaTarefasDoProjeto.getTarefasDaTabela(pEntidade);
    }

}
