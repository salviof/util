/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.atlassian.jira.rest.client.api.AuditRestClient;
import com.atlassian.jira.rest.client.api.ComponentRestClient;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.MyPermissionsRestClient;
import com.atlassian.jira.rest.client.api.ProjectRestClient;
import com.atlassian.jira.rest.client.api.ProjectRolesRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.SessionRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.VersionRestClient;
import java.io.IOException;

/**
 *
 * @author desenvolvedor
 */
public class ConexaoJiraProjetoSB implements JiraRestClient {

    private final String nomeProjeto;
    private final JiraRestClient conexao;

    public ConexaoJiraProjetoSB(JiraRestClient pConexao, String pNomeProjeto) {
        conexao = pConexao;
        nomeProjeto = pNomeProjeto;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    @Override
    public IssueRestClient getIssueClient() {
        return conexao.getIssueClient();
    }

    @Override
    public SessionRestClient getSessionClient() {
        return conexao.getSessionClient();
    }

    @Override
    public UserRestClient getUserClient() {
        return conexao.getUserClient();
    }

    @Override
    public ProjectRestClient getProjectClient() {
        return conexao.getProjectClient();
    }

    @Override
    public ComponentRestClient getComponentClient() {
        return conexao.getComponentClient();
    }

    @Override
    public MetadataRestClient getMetadataClient() {
        return conexao.getMetadataClient();
    }

    @Override
    public SearchRestClient getSearchClient() {
        return conexao.getSearchClient();
    }

    @Override
    public VersionRestClient getVersionRestClient() {
        return conexao.getVersionRestClient();
    }

    @Override
    public ProjectRolesRestClient getProjectRolesRestClient() {
        return conexao.getProjectRolesRestClient();
    }

    @Override
    public AuditRestClient getAuditRestClient() {
        return conexao.getAuditRestClient();
    }

    @Override
    public MyPermissionsRestClient getMyPermissionsRestClient() {
        return conexao.getMyPermissionsRestClient();
    }

    @Override
    public void close() throws IOException {
        conexao.close();
    }

}
