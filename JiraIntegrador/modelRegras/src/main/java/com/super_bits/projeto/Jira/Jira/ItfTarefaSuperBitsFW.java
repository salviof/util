/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.Jira;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoFormulario;
import com.super_bits.projeto.Jira.TipoProfissional;

/**
 *
 * @author salvioF
 */
public interface ItfTarefaSuperBitsFW {

    int getMinutosPrevistosTrabalho();

    TarefaJira getTarefaJiraOrigem();

    public TipoProfissional getTipoProfissionalNescessario();

    public ItfAcaoFormulario getFormularioDetalhes();

}
