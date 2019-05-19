/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira;

import com.super_bits.projeto.Jira.Jira.tempo.JiraRestClientTempoPlanoTarefa;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import java.net.URI;

/**
 *
 * @author salvioF
 */
public class JiraRestClientExtendido extends AsynchronousJiraRestClient {

    private final JiraRestClientTempoPlanoTarefa clientPlanTime;

    public JiraRestClientExtendido(URI serverUri, DisposableHttpClient httpClient) {
        super(serverUri, httpClient);
        clientPlanTime = new JiraRestClientTempoPlanoTarefa(serverUri, httpClient);
    }

    public JiraRestClientTempoPlanoTarefa getClientPlanTime() {
        return clientPlanTime;
    }

}
