/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.requisito;

/**
 *
 * @author salvioF
 */
public interface ItfTipoRequisito {

    String getDescricao();

    int getId();

    String getNome();

    void setDescricao(String descricao);

    void setId(int id);

    void setNome(String nome);

}
