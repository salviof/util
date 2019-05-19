/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.config;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.controller.FabAcoesDemosntrativoAgendador;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfiguradorCoreDeProjetoJarPersistenciaAbstrato;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;

/**
 *
 * @author desenvolvedor
 */
public class ConfigCoreTestesAgendamento extends ConfiguradorCoreDeProjetoJarPersistenciaAbstrato {

    @Override
    public void defineClassesBasicas(ItfConfiguracaoCoreCustomizavel pConfiguracao) {
        super.defineClassesBasicas(pConfiguracao);
        setIgnorarConfiguracaoPermissoes(false);
    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        pConfig.setFabricaDeAcoes(new Class[]{FabAcoesDemosntrativoAgendador.class});
    }

}
