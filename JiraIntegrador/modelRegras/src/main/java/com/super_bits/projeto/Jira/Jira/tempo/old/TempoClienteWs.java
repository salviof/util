/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira.tempo.old;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Session;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.projeto.Jira.FabConfigModuloJiraIntegrador;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author desenvolvedor
 */
public class TempoClienteWs {

    private static Map<String, String> efetuarLogin(Client client) throws URISyntaxException {

        Map<String, String> parametros = new HashMap();
        ConfigModulo configuracao = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);

        URI jiraServerUri = new URI(configuracao.getPropriedade(FabConfigModuloJiraIntegrador.SERVIDOR));
        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        //final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, configuracao.getPropriedade(FabConfigModuloJiraIntegrador.USUARIO) , configuracao.getPropriedade(FabConfigModuloJiraIntegrador.SENHA) );
        //restClient.getProjectClient().getAllProjects().claim();
        //Session sessao = restClient.getSessionClient().getCurrentSession().claim();

        String jsonParameters = "{\"username\":\"" + configuracao.getPropriedade(FabConfigModuloJiraIntegrador.USUARIO) + "\", \"password\": \"" + configuracao.getPropriedade(FabConfigModuloJiraIntegrador.SENHA) + "\"}";
        WebResource webResource = client.resource("https://vipsol.atlassian.net/rest/auth/1/session");
        ClientResponse resposta = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonParameters);

        System.out.println(resposta.getCookies());
        String idJsession = "";
        String crowdTokey = "";
        for (NewCookie cookie : resposta.getCookies()) {
            System.out.println("cookie:" + cookie.getName());
            System.out.println("Valorcookie:" + cookie.getValue());

            if (cookie.getValue().length() > 5) {
                crowdTokey = cookie.getValue();
                parametros.put(cookie.getName(), cookie.getValue());

            }

        }
        return parametros;

    }

    public static void testeCriarTarefa() {
        try {
            ConfigModulo configuracao = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);
            Client client = Client.create();

            Map<String, String> parametrosLogin = efetuarLogin(client);

            WebResource webResourceTempo = client.resource("https://vipsol.atlassian.net/rest/tempo-planning/1/allocation");

            for (Map.Entry<String, String> entry : parametrosLogin.entrySet()) {

                webResourceTempo.cookie(new Cookie(entry.getKey(), entry.getValue()));
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            BasicHttpAuthenticationHandler teste = new BasicHttpAuthenticationHandler(configuracao.getPropriedade(FabConfigModuloJiraIntegrador.USUARIO),
                    configuracao.getPropriedade(FabConfigModuloJiraIntegrador.SENHA));

            String input = ""
                    + "{ \n"
                    + "  \"planItem\": {\n"
                    + "    \"id\": 10023,\n"
                    + "    \"type\": \"PROJECT\"\n"
                    + "  },\n"
                    + "  \"scope\": {\n"
                    + "    \"id\": 5,\n"
                    + "    \"type\": \"team\"\n"
                    + "  },\n"
                    + "  \"assignee\": {\n"
                    + "    \"key\": \"admin\",\n"
                    + "    \"type\": \"user\"\n"
                    + "  },\n"
                    + "  \"commitment\": 100,\n"
                    + "  \"start\": \"2014-08-27\",\n"
                    + "  \"end\": \"2014-08-31\",\n"
                    + "    \"recurrence\": {\n"
                    + "    \"rule\": \"NEVER\"\n"
                    + "  }\n"
                    + "}"
                    + ""
                    + ""
                    + "";

            ClientResponse respostaTempo
                    = webResourceTempo
                            .type("application/json")
                            .post(ClientResponse.class, input);

            System.out.println(respostaTempo.getType());
            System.out.println(respostaTempo.getStatus());
            if (respostaTempo.getStatus() != 201) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + respostaTempo.getStatus());
            }
            System.out.println("");
            System.out.println("Output from Server .... \n");
            String output = respostaTempo.getEntity(String.class
            );
            System.out.println(output);

        } catch (Throwable t) {

            System.out.println("ERRO " + t.getMessage());
        }

    }

}
