package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoIntranetModal;

@MsgDisparoIntranetModal
public class MsgDisparoIntranetModalimpl
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