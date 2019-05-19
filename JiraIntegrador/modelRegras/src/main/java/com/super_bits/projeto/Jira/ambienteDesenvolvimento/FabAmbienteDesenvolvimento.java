/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.ambienteDesenvolvimento;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.projeto.Jira.DesenvolvedorProjetoSB;
import com.super_bits.projeto.Jira.FabTipoProfissional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salvioF
 */
public enum FabAmbienteDesenvolvimento implements ItfFabrica {
    AMBIENTE_PADRAO;

    @Override
    public AmbienteDesenvolvimento getRegistro() {
        AmbienteDesenvolvimento ambiente = new AmbienteDesenvolvimento();
        switch (AMBIENTE_PADRAO) {
            case AMBIENTE_PADRAO:
                ambiente.setAdicionalEntregaAntecipada(40);
                ambiente.setAdicionalHoraExtra(20);
                DesenvolvedorProjetoSB arquiteto = new DesenvolvedorProjetoSB("Christopher", FabTipoProfissional.ANALISTA_LOGICA_TDD.getRegistro(), 25);
                DesenvolvedorProjetoSB desenvolvedor = new DesenvolvedorProjetoSB("Raphael", FabTipoProfissional.ANALISTA_IMPLEMENTACAO.getRegistro(), 25);
                List<DesenvolvedorProjetoSB> desenvolvedores = new ArrayList<>();
                desenvolvedores.add(arquiteto);
                desenvolvedores.add(desenvolvedor);
                ambiente.setDesenvolvedores(desenvolvedores);

                break;
            default:
                throw new AssertionError(AMBIENTE_PADRAO.name());

        }
        return ambiente;
    }

}
