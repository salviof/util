/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;

import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisitoEnvioNovo;
import com.super_bits.modulosSB.SBCore.modulos.requisito.FabConfigModuloJiraRequisitos;
import com.super_bits.projeto.Jira.FabConfigModuloJiraIntegrador;
import com.super_bits.projeto.Jira.UtilSBCoreJira;

import spark.Spark;
import static br.org.coletivoJava.webService.InicioSBWS.SERVICO_COMENTARIO;
import com.super_bits.projeto.Jira.ConexaoJiraProjetoSB;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class SBWSServidorRequisito {

    private static final Gson gsonResposta = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private static final ConfigModulo CONFIGURACOESJIRASERVER = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);
    private static final ConfigModulo CONFIGURACOESSERVIDOR = SBCore.getConfigModulo(FabConfigModuloJiraRequisitos.class);

    public SBWSServidorRequisito() {
    }

    public static void iniciaServidor() {
        try {

            String porta = CONFIGURACOESSERVIDOR.getPropriedade(FabConfigModuloJiraRequisitos.PORTA_WEBSERVICE);
            Spark.port(Integer.valueOf(porta));
            ConfigModulo tete = new ConfigModulo(FabConfigModuloJiraRequisitos.class);

            Spark.get("/hello", (req, res) -> {
                try {
                    //res.
                    return "Olá Cidadão";
                } catch (Throwable t) {
                    return "Erro Maluco" + t.getMessage();
                }

            });

            Spark.post("/infoRequisito", (req, res) -> {
                res.type("application/json");
                String codigoAcao = req.body();
                ItfResposta resp = SERVICO_COMENTARIO.obterRequisitoDeAcaoDoProjeto(codigoAcao);
                String jsonResp = gsonResposta.toJson(resp);

                return jsonResp;
            });

            Spark.post("/addComentario", (request, response) -> {
                response.type("application/json");

                ComentarioRequisitoEnvioNovo novoComent = new Gson().fromJson(request.body(), ComentarioRequisitoEnvioNovo.class);

                ConexaoJiraProjetoSB conexao = UtilSBCoreJira.criarConexaoJira(CONFIGURACOESJIRASERVER.getPropriedade(FabConfigModuloJiraIntegrador.USUARIO), CONFIGURACOESJIRASERVER.getPropriedade(FabConfigModuloJiraIntegrador.SENHA), CONFIGURACOESJIRASERVER.getPropriedade(FabConfigModuloJiraIntegrador.PROJETO));
                Class entidade = ModuloAcaoSistema.class;
                //Limpara tarefas Recentes UtilSBCoreJira.limparPorJiraQuery(conexao, "created >= -1w order by created DESC");

                String jsonResp = "Erro tratando NovoComentario de" + novoComent.getUsuario();
                try {

                    ItfResposta novoComentGerado = SERVICO_COMENTARIO.adicionarComentario(novoComent);
                    jsonResp = gsonResposta.toJson(novoComentGerado);
                } catch (Throwable t) {
                    System.out.println("Erro" + t.getMessage());
                }
                return jsonResp;
            });

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro iniciando servidor", t);
        }
        //Spark.set
        Spark.awaitInitialization();
    }

}
