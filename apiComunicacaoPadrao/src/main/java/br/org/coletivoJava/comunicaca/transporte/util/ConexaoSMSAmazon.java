/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.comunicaca.transporte.util;

import static br.org.coletivoJava.comunicaca.transporte.util.FabIntegracaoSMS.ENVIAR_MENSAGEM;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;

import java.util.HashMap;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author desenvolvedor
 */
public class ConexaoSMSAmazon extends ConexaoClienteWebServiceBasico {

    private static final ConfigModulo CONFIGURACAO_AMAZON = SBCore.getConfigModulo(FabConfigSMSIntegracao.class);
    private String[] parametros;

    public ConexaoSMSAmazon(FabIntegracaoSMS pServico, String... pParametros) {
        super(pServico);
        parametros = pParametros;
    }

    private String sendSMSMessage(AmazonSNS snsClient, String message,
            String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(phoneNumber)) {
            PublishResult result = snsClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phoneNumber)
                    .withMessageAttributes(smsAttributes));
            System.out.println(result); // Prints the message ID.

            return result.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getRespostaTexto() {
        FabIntegracaoSMS integracao = (FabIntegracaoSMS) fabricaIntegracao;

        switch (integracao) {
            case ENVIAR_MENSAGEM:
                BasicAWSCredentials awsCreds = new BasicAWSCredentials(CONFIGURACAO_AMAZON.getPropriedade(FabConfigSMSIntegracao.CHAVE_PUBLICA), CONFIGURACAO_AMAZON.getPropriedade(FabConfigSMSIntegracao.CHAVE_PRIVADA));
                InfoConsumoRestService infoconsumo = integracao.getInformacoesConsumo();
                AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

                String numeroTelefone = parametros[0];
                String mensagem = parametros[1];
                Map<String, MessageAttributeValue> smsAttributes
                        = new HashMap<String, MessageAttributeValue>();
                //<set SMS attributes>
                String resposta = sendSMSMessage(snsClient, mensagem, numeroTelefone, smsAttributes);
                return resposta;

            default:
                throw new UnsupportedOperationException("A api" + integracao.toString() + "Ainda não foi implementada");

        }
    }

    public JSONObject getRespostaComoObjetoJson() {
        try {

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(getRespostaTexto());

        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro interpretando Json" + t.getMessage(), t);
            return null;
        }
    }

}
