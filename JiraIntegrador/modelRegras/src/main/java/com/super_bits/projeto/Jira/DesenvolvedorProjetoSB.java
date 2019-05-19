/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;

/**
 *
 * @author desenvolvedor
 */
public class DesenvolvedorProjetoSB extends ItemSimples {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private int id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    private String nome;
    private TipoProfissional tipoProfissional;
    private int horasSemanais;

    public DesenvolvedorProjetoSB() {
    }

    public DesenvolvedorProjetoSB(String nome, TipoProfissional tipoProfissional, int horasSemanais) {

        this.nome = nome;
        this.tipoProfissional = tipoProfissional;
        this.horasSemanais = horasSemanais;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public TipoProfissional getTipoProfissional() {
        return tipoProfissional;
    }

    public int getHorasSemanais() {
        return horasSemanais;
    }

}
