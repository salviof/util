package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoEmail;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreEmail;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabStatusComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoRespostaComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDialogo;
import org.coletivojava.fw.api.objetoNativo.comunicacao.RespostaComunicacao;

@MsgDisparoEmail
public class MsgDisparoEmailimpl
        implements
        com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao {

    @Override
    public String dispararInicioComunicacao(ItfDialogo pComunicacao) {

        String codigoEnvio = UtilSBCoreEmail.enviarPorServidorPadraoV2(pComunicacao.getDestinatario().getEmailsConcatenados(),
                pComunicacao.getMensagem(), pComunicacao.getAssunto());
        if (codigoEnvio != null) {
            pComunicacao.setStatusComunicacao(FabStatusComunicacao.ENVIADO);
            return codigoEnvio;
        } else {
            pComunicacao.setStatusComunicacao(FabStatusComunicacao.SELADO);
            return null;
        }

    }

    @Override
    public void dispararRespostaComunicacao(ItfDialogo pComunicacao) {

        RespostaComunicacao resp = new RespostaComunicacao(pComunicacao, FabTipoRespostaComunicacao.ENTENDIDO.getRegistro());
        if (SBCore.getServicoComunicacao().responderComunicacao(pComunicacao.getCodigoSelo(), resp)) {
            pComunicacao.setStatusComunicacao(FabStatusComunicacao.RESPONDIDO);
        }
    }
}
