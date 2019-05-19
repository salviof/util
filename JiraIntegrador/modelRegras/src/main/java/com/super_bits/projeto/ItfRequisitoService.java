/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto;

import com.super_bits.projeto.Jira.ItfPrevisaoEntidade;
import com.super_bits.projeto.Jira.ItfPrevisaoGestaoEntidade;
import com.super_bits.projeto.Jira.requisito.ItfRequisitoDoSistema;

/**
 *
 * @author salvioF
 */
public interface ItfRequisitoService {

    public ItfRequisitoDoSistema getRequisitoEntidade(ItfPrevisaoEntidade pPrevisaoEntidade);

    public ItfRequisitoDoSistema getREquisitoGestao(ItfPrevisaoGestaoEntidade pGestao);

}
