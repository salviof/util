/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.projeto.Jira.grupoDeTarefas.ItfGrupoDeTarefas;
import com.super_bits.projeto.Jira.previsao.PrevisaoGestaoEntidade;
import com.super_bits.projeto.Jira.previsao.PrevisaoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import java.util.List;

/**
 *
 * @author salvioF
 */
public interface ItfPrevisaoModulo extends ItfGrupoDeTarefas {

    List<PrevisaoEntidade> getEntidadesPrevistas();

    List<PrevisaoGestaoEntidade> getGestoesPrevistas();

    ItfModuloAcaoSistema getModuloAssociado();

}
