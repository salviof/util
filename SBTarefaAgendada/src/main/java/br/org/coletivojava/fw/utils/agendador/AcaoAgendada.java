/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import java.io.Serializable;
import java.util.Date;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 *
 * @author desenvolvedor
 */
public class AcaoAgendada implements Serializable {

    private ComoFabricaAcoes fabAcao;
    private Date dataHora;
    private ComoEntidadeSimples parametroBeanPrincipal;

    public AcaoAgendada(ComoFabricaAcoes pAcao, Date pDataHora, ComoEntidadeSimples pParametroPrincipal) {
        fabAcao = pAcao;
        dataHora = pDataHora;
        parametroBeanPrincipal = pParametroPrincipal;

    }

    public AcaoAgendada(ComoFabricaAcoes pAcao, Date pDataHora) {
        fabAcao = pAcao;
        dataHora = pDataHora;

    }

    public void agendar() {

        UtilSBAgendadorTarefas.agendarTarefa(this);

    }

    public JobDetail getJobDetailQuartz() {
        JobDetail detalhesTarefa = JobBuilder.newJob(ExecucaoTarefaAcaoDoSistemaAgendada.class)
                .withIdentity(getIdentificacaoTarefa(), SBCore.getGrupoProjeto())
                .build();
        detalhesTarefa.getJobDataMap().put(AtributoDeTarefa.ACAO.toString(), fabAcao.getNomeUnico());
        if (parametroBeanPrincipal != null) {
            detalhesTarefa.getJobDataMap().put(AtributoDeTarefa.ENTIDADE.toString(), parametroBeanPrincipal.getClass().getSimpleName());
            detalhesTarefa.getJobDataMap().put(AtributoDeTarefa.ID_ENTIDADE_PRINCIPAL.toString(), parametroBeanPrincipal.getId());
        }

        return detalhesTarefa;
    }

    public Trigger getTriggerQuartz() {

        return newTrigger()
                .withIdentity("trigger" + getIdentificacaoTarefa(), SBCore.getGrupoProjeto())
                .startAt(dataHora)
                .withSchedule(simpleSchedule().withIntervalInSeconds((int) 10).withRepeatCount(0)).build();
    }

    public String getIdentificacaoTarefa() {
        if (parametroBeanPrincipal != null) {
            return fabAcao.toString() + parametroBeanPrincipal.getNomeUnicoSlug();
        } else {
            return fabAcao.toString();
        }
    }

}
