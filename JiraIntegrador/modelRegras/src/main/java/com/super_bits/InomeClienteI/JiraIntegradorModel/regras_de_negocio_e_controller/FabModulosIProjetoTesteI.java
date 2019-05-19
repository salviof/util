/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.modulo.ItfFabricaModulo;
import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfFabricaMenu;

/**
 *
 *
 * ARQUIVO DE EXEMPLO, PARA INICIO DE APLICAÇÃO.
 *
 * REFATORE ESTA CLASSE COM OS MODULOS DO SEU SISTEMA
 *
 *
 * @author Salvio Furbino
 */
public enum FabModulosIProjetoTesteI implements ItfFabricaModulo {

    DEMONSTRACAO_BASICA, DEMONSTRACAO_ACESSO_RESTRITO;

    @Override
    public ItfModuloAcaoSistema getModulo() {
        ModuloAcaoSistema modulo = new ModuloAcaoSistema();
        switch (this) {
            case DEMONSTRACAO_BASICA:
                modulo.setId(1);
                modulo.setNome("Modulo demonstração");
                modulo.setDescricao("Modulo demonstração Teste");
                break;
            case DEMONSTRACAO_ACESSO_RESTRITO:
                modulo.setId(2);
                modulo.setNome("Modulo Acesso Restrito");
                modulo.setDescricao("");
                break;
            default:
                throw new AssertionError(this.name());

        }
        return modulo;
    }

    @Override
    public ItfFabricaMenu getMenuPadrao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
