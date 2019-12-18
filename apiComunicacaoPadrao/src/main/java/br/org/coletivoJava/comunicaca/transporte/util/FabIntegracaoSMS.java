/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.comunicaca.transporte.util;

/**
 *
 * @author desenvolvedor
 */
public enum FabIntegracaoSMS implements ItfFabricaIntegracaoRestBasico {
    @InfoConsumoRestService(getCaminho = "", parametrosPost = {"Mensagem", "id", "ClienteID"})
    ENVIAR_MENSAGEM;
}
