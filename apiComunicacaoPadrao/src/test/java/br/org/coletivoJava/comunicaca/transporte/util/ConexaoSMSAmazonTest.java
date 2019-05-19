/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.comunicaca.transporte.util;

import br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao.TestesComunicacaoVip;
import org.junit.Test;

/**
 *
 * @author desenvolvedor
 */
public class ConexaoSMSAmazonTest extends TestesComunicacaoVip {

    public ConexaoSMSAmazonTest() {
    }

    @Test
    public void testGetRespostaComoObjetoJson() {
        new ConexaoSMSAmazon(FabIntegracaoSMS.ENVIAR_MENSAGEM, "+5531971125577", "Olá Mundo !!! ").getRespostaTexto();
    }

    @Test
    public void testGetRespostaTexto() {
    }

}
