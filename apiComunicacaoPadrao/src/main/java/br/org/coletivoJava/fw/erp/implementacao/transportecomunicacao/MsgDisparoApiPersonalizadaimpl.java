package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoApiPersonalizada;

@MsgDisparoApiPersonalizada
public class MsgDisparoApiPersonalizadaimpl
        implements
        com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao {

    @Override
    public void dispararInicioComunicacao(ItfComunicacao itfComunicacao) {
    }

    @Override
    public void dispararRespostaComunicacao(
            com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao itfComunicacao) {
    }
}
