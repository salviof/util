/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.config;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfigPersistenciaPadrao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;

/**
 *
 * @author desenvolvedor
 */
public class ConfigPersistenciaDemonstracaoAgenda extends ConfigPersistenciaPadrao {

    @Override
    public String bancoPrincipal() {
        return SBCore.getNomeProjeto() + "Model";
    }

    @Override
    public Class<? extends ItfFabrica>[] fabricasRegistrosIniciais() {
        return new Class[]{};
    }

    @Override
    public void criarRegraDeNegocioInicial() {
        super.criarRegraDeNegocioInicial(); //To change body of generated methods, choose Tools | Templates.
    }

}
