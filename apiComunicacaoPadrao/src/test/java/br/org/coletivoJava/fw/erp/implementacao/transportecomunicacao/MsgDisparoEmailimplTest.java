/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivojava.erp.comunicacao.transporte.ERPTipoCanalComunicacao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.ItensGenericos.basico.UsuarioAnonimo;

/**
 *
 * @author desenvolvedor
 */
public class MsgDisparoEmailimplTest extends TestesComunicacaoVip {

    public MsgDisparoEmailimplTest() {
    }

    public void testDispararInicioComunicacao() {
        try {
            MsgDisparoEmailimpl emailTeste = new MsgDisparoEmailimpl();

        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

    public void testeContexto() {
        try {
            ItfDisparoComunicacao disparo = ERPTipoCanalComunicacao.EMAIL.getImplementacaoDoContexto();

            if (disparo == null) {
                throw new UnsupportedOperationException("Não foi possível determinar o comunicador");
            }
            UsuarioAnonimo usuarioAnononimo = new UsuarioAnonimo();
            SBCore.getServicoComunicacao().
                    dispararComunicacao(
                            SBCore.getServicoComunicacao().gerarComunicacaoSistema_UsuarioLogado(FabTipoComunicacao.NOTIFICAR, "teste"),
                            ERPTipoCanalComunicacao.EMAIL);
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

}
