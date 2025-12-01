/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.io.IOException;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvioF
 */
public class ProjetoJiraSuperBitsAbstrato {

    private String usuario;
    private String senha;
    private final ConexaoJiraProjetoSB conexao;
    private boolean conexaoAberta;
    private String nomeProjeto;

    public ProjetoJiraSuperBitsAbstrato(String pUsuario, String pSenha, String pNomeProjeto) {
        this.conexao = UtilCRCJira.criarConexaoJira(pUsuario, pSenha, pNomeProjeto);
        if (conexao != null) {
            conexaoAberta = true;
        }
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public boolean isConexaoAberta() {
        return conexaoAberta;
    }

    public ConexaoJiraProjetoSB getConexao() {
        if (!conexaoAberta) {

            throw new UnsupportedOperationException("A conexão com o Jira não está ativa");
        }
        return conexao;
    }

    public void fecharConexao() {
        conexaoAberta = false;
        try {
            conexao.close();
        } catch (IOException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, senha, ex);
        }
    }

}
