/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package requisito;

import com.google.common.util.concurrent.Futures;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;

import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisitoEnvioNovo;
import com.super_bits.modulosSB.SBCore.modulos.requisito.FabConfigModuloJiraRequisitos;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ItfServiceComentariosJira;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class ClienteComentarioRequisito implements ItfServiceComentariosJira {

    private final String caminhoServidor;
    private final ConfigModulo configuracoes;
    private static final Gson GSON_RESPOSTA = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public ClienteComentarioRequisito() {

        configuracoes = SBCore.getConfigModulo(FabConfigModuloJiraRequisitos.class);
        caminhoServidor = configuracoes.getPropriedade(FabConfigModuloJiraRequisitos.URL_WEBSERVICE) + ":" + configuracoes.getPropriedade(FabConfigModuloJiraRequisitos.PORTA_WEBSERVICE);
    }

    @Override
    public ItfResposta adicionarComentario(ComentarioRequisitoEnvioNovo novoComentario) {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter());
        WebResource service = client.resource(caminhoServidor);

        String texto = new Gson().toJson(novoComentario);
        try {
            RespostaWebService resp = service.path("addComentario").post(RespostaWebService.class, texto);
            return resp;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro Obtendo resposta do servidor", t);
            return null;
        }

    }

    @Override
    public ItfResposta obterRequisitoDeAcaoDoProjeto(String nomeUnicoAcao) {
        try {

            URL url = new URL(caminhoServidor + "/infoRequisito");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Accept", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(nomeUnicoAcao.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            String respostaStr = "";
            while ((output = br.readLine()) != null) {
                respostaStr += output;
            }
            System.out.println(respostaStr);

            conn.disconnect();
            RespostaWebService resp = GSON_RESPOSTA.fromJson(respostaStr, RespostaWebService.class);

            //    ClientConfig config = new DefaultClientConfig();
            //   config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            //Client client = Client.create(config);/
            // client.addFilter(new LoggingFilter());
            // WebResource service = client.resource("http://localhost:4567/");
            //String texto = nomeUnicoAcao;
            //RespostaWebService resp = service.path("infoRequisito").accept(MediaType.APPLICATION_JSON).
            //      post(RespostaWebService.class, texto);
            return resp;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo requisito em" + caminhoServidor, t);
            return null;
        }
    }

    @Override
    public ItfResposta obterTabelaDeAcaoDoProjeto(String classe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
