/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coletivoJava.TesteComunicacaoVip;

import testesFW.geradorDeCodigo.erp.GeradorERPImplementacaoContexto;
import br.org.coletivojava.erp.comunicacao.transporte.ERPTransporteComunicacao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import testesFW.ConfigCoreJunitPadrao;
import testesFW.TesteJunit;

/**
 *
 * @author desenvolvedor
 */
public class GerarImplementacoes extends TesteJunit {

    @Test
    public void gerar() {
        try {

            for (ERPTransporteComunicacao cm : ERPTransporteComunicacao.values()) {
                new GeradorERPImplementacaoContexto(cm).salvarEmDiretorioPadraCASO_NAO_EXISTA();
            }
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreJunitPadrao(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
    }

}
