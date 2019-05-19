/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService;

import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfiguradorCoreDeProjetoJarAbstrato;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;

/**
 *
 * @author desenvolvedor
 */
public class ConfiguradorCoreWsRequisitos extends ConfiguradorCoreDeProjetoJarAbstrato {

    public ConfiguradorCoreWsRequisitos() {
        setIgnorarConfiguracaoPermissoes(true);
    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        setIgnorarConfiguracaoPermissoes(true);
        pConfig.setFabricaDeAcoes(new Class[]{FabAcaoServidorRequisito.class});
    }

}
