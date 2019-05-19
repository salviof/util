/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.MODULOS.demonstracao_acesso_restrito.FabAcaoAcessoRestritoExemplo;
import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.projeto.Jira.Jira.MapaTarefasProjeto;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import org.junit.Test;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author salvioF
 */
public class ProjetoJiraSuperBitsTest extends TesteJunitSBPersistencia {

    public ProjetoJiraSuperBitsTest() {
    }

    @Test
    public void testeCriacaoProjeto() {
        try {

            //JiraRestClient conexao = UtilSBCoreJira.criarConexaoJira("", "");
            Class entidade = ModuloAcaoSistema.class;

            ProjetoJiraSuperBits testeOFicialProjeto = new ProjetoJiraSuperBits(SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class));

            //testeOFicialProjeto.buildAcoesJira();
//            testeOFicialProjeto.atualizaAcoesJira();
            testeOFicialProjeto.atualizaAcoesJira();
            for (TarefaSuperBits tarefa : MapaTarefasProjeto.getTodasTarefas()) {
                System.out.println("Tarefa:" + tarefa.getTarefaJiraOrigem().getReferencia());
                //for (Worklog trampo : testeOFicialProjeto.getWorkLogDaTarefa(tarefa)) {
                //   System.out.println("Trampo por" + trampo.getAuthor().getDisplayName());
                //  System.out.println("Minutos:" + trampo.getMinutesSpent());
                //}
            }
            //    testeOFicialProjeto.atualizaAcoesJira();
            //testeOFicialProjeto.buil4dAcoesJira();
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        AcaoDoSistema acaoTeste3 = (AcaoDoSistema) FabAcaoAcessoRestritoExemplo.RECURSO_RESTRITO_FRM_EDITAR.getAcaoDoSistema();
        acaoTeste3.getComoFormulario().getCampos();

//        SBCore.configurar(new ConfiguradorCoreSuperKomprasModel(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
//        SBPersistencia.configuraJPA(new ConfigPersistenciaSuperCompras());
    }

}
