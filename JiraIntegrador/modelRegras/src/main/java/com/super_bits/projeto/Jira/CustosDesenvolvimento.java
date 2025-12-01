/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.projeto.Jira.ambienteDesenvolvimento.AmbienteDesenvolvimento;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.modulos.tempo.ContagemRegressivaQtdTempo;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import java.util.Date;
import java.util.List;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class CustosDesenvolvimento {

    private int minutosTotalAnalistaBancoDeDados;
    private int minutosTotalAnalistaLogicaTDD;
    private int minutosTotalAnalistaImplementacao;
    private int minutosTotalAnalistaTelas;
    private int minutosTotalAnalistaDesiger;
    private int minutosTotalAnalistaRequisitos;
    private int minutosTotalAnalistaAndroid;
    private int horasTotal;
    private final List<TarefaSuperBits> tarefas;
    private final AmbienteDesenvolvimento ambienteDesenvolvimento;

    public int getValorGastoAnalistaBancoDeDados() {
        return getHorasTotalAnalistaBancoDeDados() * getAmbienteDesenvolvimento().getDetalhesProfissionalTelas().getValorHoraTecnica();
    }

    public CustosDesenvolvimento(List<TarefaSuperBits> pTarefas, AmbienteDesenvolvimento pAmbiente) {
        if (pTarefas == null) {
            throw new UnsupportedOperationException("as tarefas precisam ser definidas para calculo de custos");
        }
        tarefas = pTarefas;
        ambienteDesenvolvimento = pAmbiente;
        if (ambienteDesenvolvimento == null) {
            throw new UnsupportedOperationException("O ambiente de desenvolvimento não pode ser nulo");
        }
        atualizaValores();

    }

    public double getValorGastoAnalistaTDD() {
        return getHorasTotalAnalistaLogicaTDD() * getAmbienteDesenvolvimento().getDetalhesProfissionalTDD().getValorHoraTecnica();
    }

    public double getValorGastoAnalistaTelas() {
        return getHorasTotalAlanlistaTelas() * getAmbienteDesenvolvimento().getDetalhesProfissionalTelas().getValorHoraTecnica();
    }

    public double getValorGastoAnalistaDesigner() {
        return getHorasTotalAnalistaDesigner() * getAmbienteDesenvolvimento().getDetalhesProfissionalDesigner().getValorHoraTecnica();
    }

    public double getValorGastroAnalistaRequisito() {
        return getHorasTotalAnalistaRequisitos() * getAmbienteDesenvolvimento().getDetalhesProfissionalRequistos().getValorHoraTecnica();
    }

    public double getValorGastoAnalistaAndroid() {
        TipoProfissional tipoProfissional = getAmbienteDesenvolvimento().getDetalhesProfissionalAndroid();

        double valorHoratecnica = tipoProfissional.getValorHoraTecnica();
        return getHorasTotalAnalistaAndroid() * valorHoratecnica;
    }

    public int getHorasTotalAnalistaBancoDeDados() {
        return minutosTotalAnalistaBancoDeDados / 60;
    }

    public int getHorasTotalAnalistaAndroid() {
        return minutosTotalAnalistaBancoDeDados / 60;
    }

    public int getHorasTotalAnalistaLogicaTDD() {
        return minutosTotalAnalistaLogicaTDD / 60;
    }

    public int getHorasTotalAnalistaDesigner() {
        return minutosTotalAnalistaDesiger / 60;
    }

    public int getHorasTotalAnalistaImplementacao() {
        return minutosTotalAnalistaImplementacao / 60;
    }

    public int getHorasTotalAlanlistaTelas() {
        return minutosTotalAnalistaTelas / 60;
    }

    public int getHorasTotalAnalistaRequisitos() {
        return minutosTotalAnalistaRequisitos / 60;
    }

    public int getMinutosTotalAnalistaRequisitos() {
        return minutosTotalAnalistaRequisitos / 60;
    }

    public Date getDataFimProjeto() {
        try {
            int horasDisponiveisDia = 0;
            horasDisponiveisDia = getAmbienteDesenvolvimento().getDesenvolvedores().stream().map((dev) -> dev.getHorasSemanais() / 5).reduce(horasDisponiveisDia, Integer::sum);
            int horasNescessarias = getHorasTotal();

            int diasTotalTrabalho = horasNescessarias / horasDisponiveisDia;
            int diasTrabalho = diasTotalTrabalho;
            Date dataEntrega = new Date();
            int i = diasTrabalho;
            while (i > 0) {
                dataEntrega = UtilCRCDataHora.incrementaDias(dataEntrega, 1);
                if (!(dataEntrega.getDay() == 1 || dataEntrega.getDay() == 6)) {
                    i--;
                }
            }
            return dataEntrega;
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro calculando contagem regressiva do projeto", e);
            return null;
        }

    }

    public ContagemRegressivaQtdTempo getContagemRegressiva() {
        ContagemRegressivaQtdTempo contagem = new ContagemRegressivaQtdTempo();
        return contagem;
    }

    public double getValorTotal() {
        return getValorGastoAnalistaAndroid()
                + getValorGastoAnalistaBancoDeDados()
                + getValorGastoAnalistaDesigner()
                + getValorGastoAnalistaTDD()
                + getValorGastoAnalistaTelas()
                + getValorGastroAnalistaRequisito();
    }

    public int getMinutosTotalAnalistaBancoDeDados() {
        return minutosTotalAnalistaBancoDeDados;
    }

    public void setMinutosTotalAnalistaBancoDeDados(int minutosTotalAnalistaBancoDeDados) {
        this.minutosTotalAnalistaBancoDeDados = minutosTotalAnalistaBancoDeDados;
    }

    public int getMinutosTotalAnalistaLogicaTDD() {
        return minutosTotalAnalistaLogicaTDD;
    }

    public void setMinutosTotalAnalistaLogicaTDD(int minutosTotalAnalistaLogicaTDD) {
        this.minutosTotalAnalistaLogicaTDD = minutosTotalAnalistaLogicaTDD;
    }

    public int getMinutosTotalAnalistaTelas() {
        return minutosTotalAnalistaTelas;
    }

    public void setMinutosTotalAnalistaTelas(int minutosTotalAnalistaTelas) {
        this.minutosTotalAnalistaTelas = minutosTotalAnalistaTelas;
    }

    public void setMinutosTotalAnalistaRequisitos(int minutosTotalAnalistaRequisitos) {
        this.minutosTotalAnalistaRequisitos = minutosTotalAnalistaRequisitos;
    }

    public int getMinutosTotalAnalistaAndroid() {
        return minutosTotalAnalistaAndroid;
    }

    public void setMinutosTotalAnalistaAndroid(int minutosTotalAnalistaAndroid) {
        this.minutosTotalAnalistaAndroid = minutosTotalAnalistaAndroid;
    }

    public int getHorasTotal() {
        return horasTotal;
    }

    public void setHorasTotal(int horasTotal) {
        this.horasTotal = horasTotal;
    }

    public int getMinutosTotalAnalistaImplementacao() {
        return minutosTotalAnalistaImplementacao;
    }

    public void setMinutosTotalAnalistaImplementacao(int minutosTotalAnalistaImplementacao) {
        this.minutosTotalAnalistaImplementacao = minutosTotalAnalistaImplementacao;
    }

    public int getMinutosTotalAnalistaDesiger() {
        return minutosTotalAnalistaDesiger;
    }

    public void setMinutosTotalAnalistaDesiger(int minutosTotalAnalistaDesiger) {
        this.minutosTotalAnalistaDesiger = minutosTotalAnalistaDesiger;
    }

    private void atualizaValores() {

        int minutosTotal = 0;
        for (TarefaSuperBits tr : tarefas) {

            switch (tr.getTarefaJiraOrigem().getTipoTarefa().getTipoProfissional()) {
                case ANALISTA_BANCO_DE_DADOS:
                    minutosTotalAnalistaBancoDeDados += tr.getMinutosPrevistosTrabalho();
                    break;
                case ANALISTA_LOGICA_TDD:
                    minutosTotalAnalistaLogicaTDD += tr.getMinutosPrevistosTrabalho();
                    break;
                case ANALISTA_IMPLEMENTACAO:
                    minutosTotalAnalistaImplementacao += tr.getMinutosPrevistosTrabalho();
                    break;
                case ANALISTA_TELAS:
                    minutosTotalAnalistaTelas += tr.getMinutosPrevistosTrabalho();
                    break;
                case DESIGNER:
                    minutosTotalAnalistaDesiger += tr.getMinutosPrevistosTrabalho();
                    break;
                case ANALISTA_REQUISITOS:
                    minutosTotalAnalistaRequisitos += tr.getMinutosPrevistosTrabalho();
                    break;
                case ANALISTA_ANDROID:
                    minutosTotalAnalistaAndroid += tr.getMinutosPrevistosTrabalho();
                    break;
                default:
                    throw new AssertionError(tr.getTarefaJiraOrigem().getTipoTarefa().getTipoProfissional().name());

            }
            minutosTotal += tr.getMinutosPrevistosTrabalho();
        }
        horasTotal = minutosTotal / 60;
    }

    public AmbienteDesenvolvimento getAmbienteDesenvolvimento() {
        return ambienteDesenvolvimento;
    }

}
