/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.requisito;

import com.super_bits.projeto.Jira.previsao.PrevisaoProjeto;
import com.super_bits.projeto.Jira.ambienteDesenvolvimento.AmbienteDesenvolvimento;

import java.util.List;

/**
 *
 * @author salvioF
 */
public interface ItfRequisitoSolicitado {

    AmbienteDesenvolvimento getAmbienteDesenvolvimento();

    String getCodigoDocumentacaoo();

    double getDescontoConcedido();

    String getDescricao();

    int getId();

    String getNome();

    PrevisaoProjeto getPrevisaoProjeto();

    double getValorTaxaAdministrativa();

    void setAmbienteDesenvolvimento(AmbienteDesenvolvimento ambienteDesenvolvimento);

    void setCodigoDocumentacaoo(String codigoDocumentacaoo);

    void setDescontoConcedido(double descontoConcedido);

    void setDescricao(String descricao);

    /**
     *
     * @param id
     */
    void setId(int id);

    void setNome(String nome);

    void setValorTaxaAdministrativa(double valorTaxaAdministrativa);

}
