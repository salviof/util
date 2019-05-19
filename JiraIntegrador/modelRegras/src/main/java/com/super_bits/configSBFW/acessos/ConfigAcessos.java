/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.configSBFW.acessos;

import com.super_bits.InomeClienteI.JiraIntegradorModel.model.usuariosExemplo.FabGrupoUsuario;
import com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.FabMenuExemplo;
import com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.MODULOS.demonstracao_acesso_restrito.ModuloAcessoRestritoExemplo;
import com.super_bits.modulos.SBAcessosModel.ConfigPermissoesAcessoModelAbstrato;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfGrupoUsuario;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.MenusDaSessao;

/**
 *
 *
 * A classe COnfigAcesos, define a configuração de acessos do sistema.
 *
 * os acessos pode estar tanto em um banco como ser criados de maneira estática,
 * é nessa classe que tudo será definido
 *
 * Além disso os menus do sistema podem ser definidos de acordo com o grupo de
 * usuários
 *
 * @author Salvio
 */
public class ConfigAcessos extends ConfigPermissoesAcessoModelAbstrato {

    private static Class[] getClasses() {
        Class[] classes = {};
        return classes;
    }

    /**
     * No contructor do acessso as classes da camada controller (que geram
     * alteração no banco devem ser especificadas)
     */
    public ConfigAcessos() {
        super(getClasses());
    }

    /**
     *
     * Define o Menu de acorod com o grupo de usuario
     *
     * @param pGrupo
     * @return
     */
    @Override
    public MenusDaSessao definirMenu(ItfGrupoUsuario pGrupo) {

        if (pGrupo.equals(FabGrupoUsuario.USUARIO_ADMINISTRADOR.getRegistro())) {
            return new MenusDaSessao(FabMenuExemplo.MENU_RESTRITO.getRegistro());
        } else {
            return new MenusDaSessao(FabMenuExemplo.MENU_INICIAL.getRegistro());
        }
    }

}
