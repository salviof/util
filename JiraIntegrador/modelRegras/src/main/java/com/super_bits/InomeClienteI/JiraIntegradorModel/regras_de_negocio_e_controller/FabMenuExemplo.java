/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller;

import com.super_bits.modulos.SBAcessosModel.fabricas.acoesDemonstracao.FabAcaoDemonstracaoSB;
import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ComoFabricaMenu;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ComoMenuSB;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfSessaoDeMenuSB;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.SessaoMenuSB;
import java.util.ArrayList;
import java.util.List;
import org.coletivojava.fw.api.objetoNativo.view.menu.MenuSBFW;

/**
 *
 * FAbricas de menu, devem ser usadas para construção de menus do sistema.
 *
 * Voce pode adicionar sessoes e ações no menu
 *
 *
 * @author desenvolvedor
 */
public enum FabMenuExemplo implements ComoFabricaMenu {
    MENU_INICIAL, MENU_RESTRITO;

    @Override
    public List<ComoMenuSB> getTodosMenus() {
        List<MenuSBFW> menus = new ArrayList<>();
        for (ComoFabricaMenu menu : this.getClass().getEnumConstants()) {
            menus.add((MenuSBFW) menu.getRegistro());
        }
        return (List) menus;
    }

    @Override
    public MenuSBFW getRegistro() {
        MenuSBFW menu = new MenuSBFW();
        AcaoDoSistema acao = new AcaoDoSistema();
        acao.setNomeAcao("Exemplos do Framework");
        SessaoMenuSB sessaoVisaoGeral = new SessaoMenuSB(acao);
        sessaoVisaoGeral.addAcao(FabAcaoDemonstracaoSB.DEMONSTRACAO_MB_COMPONENTE.getRegistro());
        sessaoVisaoGeral.addAcao(FabAcaoDemonstracaoSB.DEMONSTRACAO_MB_VALIDACAO.getRegistro());
        menu.addSessao((ItfSessaoDeMenuSB) sessaoVisaoGeral);
        switch (this) {
            case MENU_INICIAL:

                return menu;
            case MENU_RESTRITO:
                AcaoDoSistema acaosessao = new AcaoDoSistema();
                acaosessao.setNome("Permissões ");
                SessaoMenuSB adminUsuario = new SessaoMenuSB(acaosessao);
                //    adminUsuario.addAcao(FabAcaoSeguranca.GRUPO_MB_GERENCIAR.getRegistro());
                //    adminUsuario.addAcao(FabAcaoSeguranca.USUARIO_MB_GERENCIAR.getRegistro());
                menu.addSessao((ItfSessaoDeMenuSB) adminUsuario);
                return menu;
            default:
                throw new AssertionError(this.name());

        }
    }

    @Override
    public MenuSBFW getMenuPrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MenuSBFW getMenuSecundario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
