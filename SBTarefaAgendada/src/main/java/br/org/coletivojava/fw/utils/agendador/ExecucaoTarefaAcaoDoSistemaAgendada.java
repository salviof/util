/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoControllerAutoExecucao;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilSBController;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.DIARIO;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.GATILHO;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.HORAS;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.LOOP;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.MENSAL;
import static com.super_bits.modulosSB.SBCore.modulos.Controller.acoesAutomatizadas.FabEstrategiaAutoexecucao.MINUTOS;
import com.super_bits.modulosSB.SBCore.modulos.Controller.parametroAcaoController.ParametroAcaoControllerSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 *
 * @author Salvio Furbino
 */
public class ExecucaoTarefaAcaoDoSistemaAgendada extends ExecucaoTarefaAgendadaAbstratoHelper {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ItfAcaoController acao = null;
        try {
            String acaoSTR = (String) context.getJobDetail().getJobDataMap().get(AtributoDeTarefa.ACAO.toString());
            if (acaoSTR == null) {
                throw new UnsupportedOperationException("Erro localizando ação relacionada ao agendamento");
            }
            acao = (ItfAcaoController) MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(acaoSTR);
            ParametroAcaoControllerSimples pr = gerarParametroSimples(context);
            Method metodo = UtilSBController.getMetodoByAcaoController(acao);
            if (pr != null) {
                Class classeEntidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(pr.getEntidadePrincial());
                Object entidade = SBCore.getServicoRepositorio().getRegistroByID(null, classeEntidade, pr.getIdEntidadePrincipal());

                metodo.invoke(null, entidade);
            } else {
                metodo.invoke(null);
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha executando tarefa agendada", t);
        } finally {

            try {
                context.getScheduler().unscheduleJob(context.getTrigger().getKey());

                if (acao != null) {
                    if (acao.isUmaAcaoControllerAutoExecucao()) {

                        ItfAcaoControllerAutoExecucao acaoAutoexec = (ItfAcaoControllerAutoExecucao) acao;
                        switch (acaoAutoexec.getTipoAutoExecucao().getEstrategia()) {
                            case GATILHO:
                                break;
                            case DIARIO:
                            case MENSAL:
                            case LOOP:
                            case MINUTOS:
                            case HORAS:
                                UtilSBAgendadorTarefas.agendarAutoExecucaoAcaoProxima(acaoAutoexec);
                                break;
                            default:
                                throw new AssertionError(acaoAutoexec.getTipoAutoExecucao().getEstrategia().name());

                        }

                    }
                }
            } catch (SchedulerException ex) {
                Logger.getLogger(ExecucaoTarefaAcaoDoSistemaAgendada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
