package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivoJava.comunicaca.transporte.util.ConexaoSMSAmazon;
import br.org.coletivoJava.comunicaca.transporte.util.FabIntegracaoSMS;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoSms;

@MsgDisparoSms
public class MsgDisparoSmsimpl
        implements
        com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao {

    @Override
    public void dispararInicioComunicacao(ItfComunicacao itfComunicacao) {
        System.out.println(new ConexaoSMSAmazon(FabIntegracaoSMS.ENVIAR_MENSAGEM, "+5531971125577", "Olá Mundo !!! ").getRespostaTexto());
    }

    @Override
    public void dispararRespostaComunicacao(
            com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao itfComunicacao) {
    }
}
