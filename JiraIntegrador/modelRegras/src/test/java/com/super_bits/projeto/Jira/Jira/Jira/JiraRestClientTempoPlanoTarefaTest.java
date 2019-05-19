/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira.Jira;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.projeto.Jira.FabConfigModuloJiraIntegrador;
import com.super_bits.projeto.Jira.Jira.JiraRestClientExtendido;
import com.super_bits.projeto.Jira.Jira.FabricaJiraClientExtendido;
import com.super_bits.projeto.Jira.Jira.tempo.JiraRestClientTempoPlanoTarefa;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author desenvolvedor
 */
public class JiraRestClientTempoPlanoTarefaTest {

    public JiraRestClientTempoPlanoTarefaTest() {
    }

    @Test
    public void testGetCurrentSession() {

        try {
            FabricaJiraClientExtendido fabricaExtendida = new FabricaJiraClientExtendido();
            URI jiraServerUri = new URI(SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.SERVIDOR));

            JiraRestClientExtendido jiraExtendido = fabricaExtendida.getJiraClientExtendido(jiraServerUri,
                    SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.USUARIO),
                    SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class).getPropriedade(FabConfigModuloJiraIntegrador.SENHA));
            JiraRestClientTempoPlanoTarefa testeTempoPLanoTarefa = jiraExtendido.getClientPlanTime();
            jiraExtendido.getProjectClient().getAllProjects();
            testeTempoPLanoTarefa.listarPLanosDeTrabalho();
        } catch (URISyntaxException ex) {
            Logger.getLogger(JiraRestClientTempoPlanoTarefaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
