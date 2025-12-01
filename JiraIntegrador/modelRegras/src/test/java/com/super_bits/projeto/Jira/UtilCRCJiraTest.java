/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.google.common.collect.Lists;
import com.super_bits.config.ConfigPersistenciaIntegrador;
import com.super_bits.config.ConfiguradorJiraIntegradorModel;
import com.super_bits.modulos.SBAcessosModel.fabricas.FabAcaoProjetoSB;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTILSBCoreDesktopApp;
import com.super_bits.projeto.Jira.Jira.TarefaJira;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.objetoNativo.mensagem.MensagemProgramador;
import org.junit.Test;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author desenvolvedor
 */
public class UtilCRCJiraTest extends TesteJunitSBPersistencia {

    public UtilCRCJiraTest() {
    }

    @Test
    public void testGetTarefaJiraAcaoDoSistema() {

        JiraRestClient conexao = UtilCRCJira.criarConexaoJira(SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.USUARIO), SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.SENHA), SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.PROJETO));

        SearchResult resultado = conexao.getSearchClient().searchJql("summary ~ \"Controllers\"").claim();
        List<Issue> todasTarefas = Lists.newArrayList(resultado.getIssues().iterator());

        for (Issue tarefa : todasTarefas) {
            conexao.getIssueClient().deleteIssue(tarefa.getKey(), true);
            System.out.println("removeu" + tarefa.getSummary());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UtilCRCJiraTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        User usuario = UtilCRCJira.getUsuarioPorNome(conexao, "salvio");
        User usuario2 = UtilCRCJira.getUsuarioPorNome(conexao, "cristopherAmaral");
        UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemProgramador(usuario.getName()));
        UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemProgramador(usuario2.getName()));

        TarefaJira tarefa = UtilCRCJira.getTarefaJiraAcaoDoSistema(UtilCRCJira.TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTACAO_MANAGED_BEAN, FabAcaoProjetoSB.PROJETO_MB_GERENCIAR.getRegistro());
        //   UtilCRCJira.criarTarefafasDaAcao(conexao, tarefa);

    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfiguradorJiraIntegradorModel(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        //   SBPersistencia.configuraJPA(new ConfigPersistenciaIntegrador());
    }

}
