/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.configSBFW.acessos;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.controller.ModuloAgendadorDemo;
import com.super_bits.modulos.SBAcessosModel.ConfigPermissoesAcessoModelAbstrato;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfGrupoUsuario;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfMenusDeSessao;

/**
 *
 * @author desenvolvedor
 */
public class ConfigAcesosAgendaDemo extends ConfigPermissoesAcessoModelAbstrato {

    public ConfigAcesosAgendaDemo() {
        super(new Class[]{ModuloAgendadorDemo.class});
    }

    @Override
    public ItfMenusDeSessao definirMenu(ItfGrupoUsuario pGrupo) {
        return null;
    }

}
