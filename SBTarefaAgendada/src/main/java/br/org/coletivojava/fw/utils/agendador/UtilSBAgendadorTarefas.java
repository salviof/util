/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoControllerAutoExecucao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        Trigger gatilhoNovo = pAcaoAgendada.getTriggerQuartz();

        JobKey chaveTarefa = new JobKey(pAcaoAgendada.getIdentificacaoTarefa(), SBCore.getGrupoProjeto());

        try {
            if (!(getAgendador().checkExists(chaveTarefa))) {

                getAgendador().scheduleJob(tarefa, gatilhoNovo);
            } else {
                List<? extends Trigger> triggers = getAgendador().getTriggersOfJob(chaveTarefa);
                for (Trigger gatilho : triggers) {
                    System.out.println("Gatilho inicio=" + gatilho.getStartTime());
                    System.out.println("Gatilho fim=" + gatilho.getEndTime());
                    gatilho.getFinalFireTime();

                }
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro agendando tarefa", t);
        }
    }

    public static void agendarAutoExecucaoAcaoProxima(ItfAcaoControllerAutoExecucao pAcao) {
        if (pAcao == null) {
            System.out.println("Enviou nulo para autoexecução de ação");
            return;
        }
        Date proximoHorario = pAcao.getTipoAutoExecucao().getEstrategia().getEstrategiaImplementacao(new Date(), pAcao.getTipoAutoExecucao()).getProximoHorarioAgendamento();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Ação autoexecução agendada para " + formatoData.format(proximoHorario) + " " + pAcao.getNomeUnico());
        if (proximoHorario != null) {
            agendarTarefa(pAcao.getEnumAcaoDoSistema(), proximoHorario);
        }

    }

}
