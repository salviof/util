/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.InomeClienteI.JiraIntegradorModel.model.usuariosExemplo;

import com.super_bits.modulos.SBAcessosModel.fabricas.acoesDemonstracao.FabAcaoDemonstracaoSB;
import com.super_bits.modulos.SBAcessosModel.model.GrupoUsuarioSB;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;

/**
 *
 * @author desenvolvedor
 */
public enum FabGrupoUsuario implements ItfFabrica {
    USUARIO_ANONIMO, USUARIO_ADMINISTRADOR;

    @Override
    public GrupoUsuarioSB getRegistro() {
        GrupoUsuarioSB novoGrupo = new GrupoUsuarioSB();
        switch (this) {
            case USUARIO_ANONIMO:
                novoGrupo.setNome("Grupo Anonimo");
                novoGrupo.setAtivo(true);
                novoGrupo.setPaginaInicial(FabAcaoDemonstracaoSB.DEMONSTRACAO_MB_RECURSOS);
                //setXhtmlPaginaInicial("/site/exemplo/inicialAnonimo.xhtml");
                break;
            case USUARIO_ADMINISTRADOR:
                novoGrupo.setNome("Grupo Administrador");
                novoGrupo.setDescricao("Grupo com acesso ao menu Administrador");
                novoGrupo.setPaginaInicial(FabAcaoDemonstracaoSB.DEMONSTRACAO_MB_COMPONENTE);
                novoGrupo.setAtivo(true);
                break;
            default:
                throw new AssertionError(this.name());

        }
        return novoGrupo;
    }
}
