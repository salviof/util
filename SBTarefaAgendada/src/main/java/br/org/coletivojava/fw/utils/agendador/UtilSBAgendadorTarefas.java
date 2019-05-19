/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.util.Date;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.quartz.JobDetail;
import org.quartz.JobKey;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBAgendadorTarefas {

    private static Scheduler agendador;

    private synchronized static void criarAgenda() {
        if (agendador == null) {
            try {
                agendador = StdSchedulerFactory.getDefaultScheduler();
                agendador.start();
            } catch (Throwable t) {

            }
        }

    }

    public static Scheduler getAgendador() {
        if (agendador == null) {
            criarAgenda();
        }
        return agendador;
    }

    public static void agendarTarefa(ItfFabricaAcoes pAcao, Date pData) {
        agendarTarefa(new AcaoAgendada(pAcao, pData));
    }

    public static void agendarTarefa(ItfFabricaAcoes pAcao, Date pData, ItfBeanSimples pParametro) {
        agendarTarefa(new AcaoAgendada(pAcao, pData, pParametro));
    }

    public static void agendarTarefa(AcaoAgendada pAcaoAgendada) {
        JobDetail tarefa = pAcaoAgendada.getJobDetailQuartz();
        Trigger gatilho = pAcaoAgendada.getTriggerQuartz();
        try {
            if (!(getAgendador().checkExists(new JobKey(pAcaoAgendada.getIdentificacaoTarefa(), SBCore.getGrupoProjeto())))) {
                getAgendador().scheduleJob(tarefa, gatilho);
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro agendando tarefa", t);
        }
    }

}
