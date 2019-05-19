/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.projeto.Jira.grupoDeTarefas.ItfGrupoDeTarefas;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.requisito.ItfRequisitoDoSistema;
import java.util.List;

/**
 *
 * @author salvioF
 */
public interface ItfPrevisaoGestaoEntidade extends ItfGrupoDeTarefas {

    void adicionarTarefa(TarefaSuperBits tr);

    void atualizaHorasProgramadas();

    Class getEntidadeVinculada();

    ItfAcaoGerenciarEntidade getGestao();

    ItfModuloAcaoSistema getModulo();

    int getQtdAcoesDeFormulario();

    int getQtdAoesDeController();

    void setGestao(ItfAcaoGerenciarEntidade gestao);

    void setModulo(ItfModuloAcaoSistema modulo);

    public boolean isTemRequisitoVinculado();

    public ItfRequisitoDoSistema getRequisitoVinculado();

}
