/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.requisito;

import java.io.Serializable;

/**
 *
 * @author salvioF
 */
public interface ItfStatusRequisito extends Serializable {

    String getDescricao();

    int getId();

    void setDescricao(String descricao);

    void setId(int id);

}
