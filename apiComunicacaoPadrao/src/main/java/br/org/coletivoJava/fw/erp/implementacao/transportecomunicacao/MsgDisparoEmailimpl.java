package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoEmail;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreEmail;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabStatusComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import org.coletivojava.fw.api.objetoNativo.comunicacao.RespostaComunicacao;

@MsgDisparoEmail
public class MsgDisparoEmailimpl
        implements
        com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao {

    @Override
    public void dispararInicioComunicacao(ItfComunicacao comunicacao) {

        if (UtilSBCoreEmail.enviarPorServidorPadrao(comunicacao.getDestinatario().getEmailsConcatenados(),
                comunicacao.getMensagem(), comunicacao.getAssunto())) {
            comunicacao.setStatusComunicacao(FabStatusComunicacao.ENVIADO);
        } else {
            comunicacao.setStatusComunicacao(FabStatusComunicacao.SELADO);
        }
    }

    @Override
    public void dispararRespostaComunicacao(
            com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao comunicacao) {
        RespostaComunicacao resp = new RespostaComunicacao(comunicacao, FabTipoRespostaComunicacao.ENTENDIDO.getRegistro());
        if (SBCore.getCentralDeComunicacao().responderComunicacao(comunicacao, resp)) {
            comunicacao.setStatusComunicacao(FabStatusComunicacao.RESPONDIDO);
        }
    }
}
