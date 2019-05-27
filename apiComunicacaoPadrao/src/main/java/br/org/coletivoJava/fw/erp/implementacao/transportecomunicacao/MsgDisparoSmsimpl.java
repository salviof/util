package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivoJava.comunicaca.transporte.util.ConexaoSMSAmazon;
import br.org.coletivoJava.comunicaca.transporte.util.FabIntegracaoSMS;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoSms;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;

@MsgDisparoSms
public class MsgDisparoSmsimpl
        implements
        com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao {

    @Override
    public void dispararInicioComunicacao(ItfComunicacao itfComunicacao) {
        if (SBCore.isEmModoProducao()) {
            System.out.println(new ConexaoSMSAmazon(FabIntegracaoSMS.ENVIAR_MENSAGEM, "+5531971125577", "Olá Mundo !!! ").getRespostaTexto());
        } else {
            System.out.println("Simulação sms enviado para" + itfComunicacao.getDestinatario().getUsuario() + "" + itfComunicacao.getNome());
        }
    }

    @Override
    public void dispararRespostaComunicacao(
            com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao itfComunicacao) {
    }
}
