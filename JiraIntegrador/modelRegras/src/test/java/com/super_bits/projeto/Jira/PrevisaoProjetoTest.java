/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.config.ConfigPersistenciaIntegrador;
import com.super_bits.config.ConfiguradorJiraIntegradorModel;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.previsao.PrevisaoEntidade;
import com.super_bits.projeto.Jira.previsao.PrevisaoGestaoEntidade;
import com.super_bits.projeto.Jira.previsao.PrevisaoModulo;
import com.super_bits.projeto.Jira.previsao.PrevisaoProjeto;
import org.junit.Test;
import testesFW.TesteJunit;

/**
 *
 * @author desenvolvedor
 */
public class PrevisaoProjetoTest extends TesteJunit {

    public PrevisaoProjetoTest() {
    }

    @Test
    public void testCalcularValores() {

//        List<ModuloAcaoSistema> modulos = UtilSBPersistencia.getListaTodos(ModuloAcaoSistema.class);
///        System.out.println("Existem " + MapaTarefasProjeto.getTodasTarefas().size() + " tarefas cadastradas");
        PrevisaoProjeto novaPrevisao;
        novaPrevisao = new PrevisaoProjeto(SBCore.getFabricasDeAcaoDoSistema());

        for (PrevisaoModulo mp : novaPrevisao.getModuloPrevistosPorModulo().values()) {
            for (PrevisaoEntidade previsao : mp.getEntidadesPrevistas()) {
                System.out.println("Tarefas da entidade:" + previsao.getEntidadeVinculada());
                previsao.getEstruturaDaEntidade().getNomeEntidade();
                // for (TarefaSuperBits tf : previsao.getTarefasVinculadas()) {
                //   System.out.println("Tarefa de entidade :" + tf.getTarefaJiraOrigem().getNomeTarefa());
                //}
            }
            for (PrevisaoGestaoEntidade gestao : mp.getGestoesPrevistas()) {
                System.out.println("Tarefas da gestao" + gestao.getGestao().getNome());

                for (TarefaSuperBits tf : gestao.getTarefasVinculadas()) {
                    tf.getTarefaJiraOrigem();
                    System.out.println("Tarefa de gestao:" + tf.getTarefaJiraOrigem().getAcaoVinculada().getNomeAcao());
                }
            }
        }

        novaPrevisao.calcularValores();
        novaPrevisao.getCustosDesenvolvimento().getHorasTotal();
        novaPrevisao.getCustosDesenvolvimento().getValorTotal();
        System.out.println("Horas Total proxima Versao " + novaPrevisao.getCustosDesenvolvimento().getHorasTotalAlanlistaTelas());
        System.out.println("Horas total tudo" + novaPrevisao.getCustosDesenvolvimento().getHorasTotal());

    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfiguradorJiraIntegradorModel(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaIntegrador());
    }

}
