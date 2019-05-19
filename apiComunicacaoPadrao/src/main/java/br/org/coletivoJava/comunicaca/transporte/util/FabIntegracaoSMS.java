/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package br.org.coletivoJava.comunicaca.transporte.util;

import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.ItfFabricaIntegracaoRestBasico;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.conexaoWebServiceClient.InfoConsumoRestService;

/**
 *
 * @author desenvolvedor
 */
public enum FabIntegracaoSMS implements ItfFabricaIntegracaoRestBasico {
    @InfoConsumoRestService(getCaminho = "", parametrosPost = {"Mensagem", "id", "ClienteID"})
    ENVIAR_MENSAGEM;
}
