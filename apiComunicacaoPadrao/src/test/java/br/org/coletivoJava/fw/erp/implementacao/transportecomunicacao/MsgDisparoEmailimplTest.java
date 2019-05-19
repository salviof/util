/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import br.org.coletivojava.erp.comunicacao.transporte.ERPTransporteComunicacao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreEmail;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.FabTipoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfDisparoComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.ItensGenericos.basico.UsuarioAnonimo;
import org.junit.Test;

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
            ItfDisparoComunicacao disparo = ERPTransporteComunicacao.EMAIL.getImplementacaoDoContexto();

            if (disparo == null) {
                throw new UnsupportedOperationException("Não foi possível determinar o comunicador");
            }
            UsuarioAnonimo usuarioAnononimo = new UsuarioAnonimo();
            disparo.dispararInicioComunicacao(SBCore.getCentralComunicacao().
                    iniciarComunicacaoSistema_Usuairo(
                            FabTipoComunicacao.NOTIFICAR,
                            SBCore.getUsuarioLogado(), "Teste",
                            ERPTransporteComunicacao.EMAIL));
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

}
