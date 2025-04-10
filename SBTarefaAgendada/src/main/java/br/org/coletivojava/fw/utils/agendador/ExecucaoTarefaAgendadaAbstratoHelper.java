/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.parametroAcaoController.ParametroAcaoControllerSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 *
 * @author desenvolvedor
 */
public abstract class ExecucaoTarefaAgendadaAbstratoHelper implements Job {

    protected ParametroAcaoControllerSimples gerarParametroSimples(JobExecutionContext context) {
        try {
            if (context.getJobDetail().getJobDataMap().get(AtributoDeTarefa.ENTIDADE.toString()) == null) {
                return null;
            }
            String entidadeSTR = (String) context.getJobDetail().getJobDataMap().get(AtributoDeTarefa.ENTIDADE.toString());
            String idEntidadeSTR = String.valueOf(context.getJobDetail().getJobDataMap().get(AtributoDeTarefa.ID_ENTIDADE_PRINCIPAL.toString()));
            Class entidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(entidadeSTR);

            return new ParametroAcaoControllerSimples(entidadeSTR, Long.valueOf(idEntidadeSTR));
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo dados de ação agendada", t);
            return null;
        }

    }
}
