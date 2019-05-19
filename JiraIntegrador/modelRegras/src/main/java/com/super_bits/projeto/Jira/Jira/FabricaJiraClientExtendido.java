/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import java.net.URI;

/**
 *
 * @author desenvolvedor
 */
public class FabricaJiraClientExtendido implements JiraRestClientFactory {

    public JiraRestClientExtendido getJiraClientExtendido(URI jiraServerUri, String pNome, String pSenha) {
        return createJiraClientExtendido(jiraServerUri, new BasicHttpAuthenticationHandler(pNome, pSenha));
    }

    public JiraRestClientExtendido createJiraClientExtendido(final URI serverUri, final AuthenticationHandler authenticationHandler) {
        final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory()
                .createClient(serverUri, authenticationHandler);
        return new JiraRestClientExtendido(serverUri, httpClient);
    }

    @Override
    public JiraRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler) {
        final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory()
                .createClient(serverUri, authenticationHandler);
        return new AsynchronousJiraRestClient(serverUri, httpClient);
    }

    @Override
    public JiraRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password) {
        return create(serverUri, new BasicHttpAuthenticationHandler(username, password));
    }

    @Override
    public JiraRestClient create(final URI serverUri, final HttpClient httpClient) {
        final DisposableHttpClient disposableHttpClient = new AsynchronousHttpClientFactory().createClient(httpClient);
        return new AsynchronousJiraRestClient(serverUri, disposableHttpClient);
    }

}
