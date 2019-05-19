/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilSBController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.parametroAcaoController.ParametroAcaoControllerSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import java.lang.reflect.Method;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Salvio Furbino
 */
public class ExecucaoTarefaAcaoDoSistemaAgendada extends ExecucaoTarefaAgendadaAbstratoHelper {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            String acaoSTR = (String) context.getJobDetail().getJobDataMap().get(AtributoDeTarefa.ACAO.toString());
            if (acaoSTR == null) {
                throw new UnsupportedOperationException("Erro localizando ação relacionada ao agendamento");
            }
            ItfAcaoController acao = (ItfAcaoController) MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(acaoSTR);
            ParametroAcaoControllerSimples pr = gerarParametroSimples(context);
            Method metodo = UtilSBController.getMetodoByAcaoController(acao);
            if (pr != null) {
                Class classeEntidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(pr.getEntidadePrincial());
                Object entidade = UtilSBPersistencia.getRegistroByID(classeEntidade, Integer.valueOf(pr.getIdEntidadePrincipal()));
                metodo.invoke(null, entidade);
            } else {
                metodo.invoke(null);
            }

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha executando tarefa agendada", t);
        }
    }

}
