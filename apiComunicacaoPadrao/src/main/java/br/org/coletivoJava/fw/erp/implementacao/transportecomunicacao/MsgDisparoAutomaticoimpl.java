package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfComunicacao;
import br.org.coletivoJava.fw.api.erp.transportecomunicacao.MsgDisparoAutomatico;

@MsgDisparoAutomatico
public class MsgDisparoAutomaticoimpl
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