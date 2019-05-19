/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.previsao;

import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.TipoGrupoTarefa;
import com.super_bits.projeto.Jira.grupoDeTarefas.FabTipoGrupoTarefa;
import com.super_bits.projeto.Jira.grupoDeTarefas.ItfGrupoDeTarefas;
import com.super_bits.projeto.Jira.requisito.ItfRequisitoDoSistema;
import java.util.List;

/**
 *
 * @author salvioF
 */
public class PrevisaoTarefasPersonalizadas implements ItfGrupoDeTarefas {

    private List<TarefaSuperBits> tarefasIntegracoes;
    private List<TarefaSuperBits> tarefasTecnologias;
    private List<TarefaSuperBits> tarefasExtras;
    private double percentualTrabalhado;

    public PrevisaoTarefasPersonalizadas(List<TarefaSuperBits> tarefasIntegracoes, List<TarefaSuperBits> tarefasTecnologias, List<TarefaSuperBits> tarefasExtras) {
        this.tarefasIntegracoes = tarefasIntegracoes;
        this.tarefasTecnologias = tarefasTecnologias;
        this.tarefasExtras = tarefasExtras;
    }

    public PrevisaoTarefasPersonalizadas() {

    }

    public List<TarefaSuperBits> getTarefasIntegracoes() {
        return tarefasIntegracoes;
    }

    public void setTarefasIntegracoes(List<TarefaSuperBits> tarefasIntegracoes) {
        this.tarefasIntegracoes = tarefasIntegracoes;
    }

    public List<TarefaSuperBits> getTarefasTecnologias() {
        return tarefasTecnologias;
    }

    public void setTarefasTecnologias(List<TarefaSuperBits> tarefasTecnologias) {
        this.tarefasTecnologias = tarefasTecnologias;
    }

    public List<TarefaSuperBits> getTarefasExtras() {
        return tarefasExtras;
    }

    public void setTarefasExtras(List<TarefaSuperBits> tarefasExtras) {
        this.tarefasExtras = tarefasExtras;
    }

    @Override
    public int getHorasProgramadas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TarefaSuperBits> getTarefasVinculadas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CustosDesenvolvimento getCustoDesenvolvimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrevisaoProjeto getPrevisaoProjeto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNomeDoAgrupamento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIcone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescricao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoGrupoTarefa getTipoGrupoTarefa() {
        return FabTipoGrupoTarefa.PERSONALIZADA.getRegistro();
    }

    public void addTarefaIntegracao(ItfRequisitoDoSistema pRequisitoIntegracao) {

    }

    public void addTarefaTecnologia(ItfRequisitoDoSistema pRequisitoTecnologia) {

    }

    public void addTarefaExtra(ItfRequisitoDoSistema pRequisitoExtra) {

    }

    @Override
    public double getPercentualTrabalhado() {
        return percentualTrabalhado;
    }

    public void setPercentualTrabalhado(double percentualTrabalhado) {
        this.percentualTrabalhado = percentualTrabalhado;
    }

}
