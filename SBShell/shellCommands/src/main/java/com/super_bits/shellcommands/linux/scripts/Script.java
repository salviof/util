/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.linux.scripts;


import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.shellcommands.model.Comando;
import com.super_bits.shellcommands.model.RespostaCMD;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class Script {

    private String nome;
    private List<Comando> comandos;
    private Map<String, String> parametros;
    private List<RespostaCMD> respostas;
    private Date dataHoraInicio;
    private Date dataHoraFim;
    private RespostaCMD.RESULTADOCMD resultadoExecucao;

    public Script(List<Comando> pComandos) {
        comandos = pComandos;

        parametros = new HashMap<>();
        respostas = new ArrayList<>();
        comandos = pComandos;
        resultadoExecucao = RespostaCMD.RESULTADOCMD.OK;
        buildParametros();
    }

    private void buildParametros() {
        parametros.clear();
        for (Comando cm : comandos) {
            for (String pr : cm.getParametros().keySet()) {
                parametros.put(pr, (String) cm.getParametros().get(pr));
            }
        }

    }

    private void defineStatusExecucao(RespostaCMD.RESULTADOCMD pResult) {

        switch (resultadoExecucao) {
            case FALHOU:
                return;
            case ALERTA:
                if (pResult == RespostaCMD.RESULTADOCMD.FALHOU) {
                    resultadoExecucao = pResult;
                }
                break;
            case OK:

                if (pResult != RespostaCMD.RESULTADOCMD.OK) {
                    resultadoExecucao = pResult;
                }

                break;

        }

    }

    private boolean existeParametro(String pnomeParametro) {

        return parametros.get(pnomeParametro) != null;

    }

    public void configParametro(String pParametro, String pValor) {

        if (!existeParametro(pParametro)) {
            throw new UnsupportedOperationException("parametro: " + pParametro + "não existe no script" + nome);

        }
        parametros.put(pParametro, pValor);

    }

    public void executarScript() {

        respostas.clear();
        dataHoraInicio = new Date();

        for (String pr : parametros.keySet()) {
            if (parametros.get(pr).length() == 0) {
                throw new UnsupportedOperationException("o parametro: " + pr + " não foi defino, impossível executar comando");
            }
        }

        for (Comando cm : comandos) {

            try {
                for (String nomeCampo : cm.getParametros().keySet()) {
                    if (parametros.get(nomeCampo) != null) {
                        cm.configParametro(nomeCampo, parametros.get(nomeCampo));
                    }
                }
                RespostaCMD resp = cm.executarComando();
                respostas.add(resp);
                defineStatusExecucao(resp.getResultado());
                System.out.println(cm.getComando() + " executado, resultado:" + resp.getResultado());
            } catch (Throwable e) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO,"Erro executando Script", e);
                resultadoExecucao = RespostaCMD.RESULTADOCMD.FALHOU;
            }

        }
        dataHoraFim = new Date();
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    public void setComandos(List<Comando> comandos) {
        this.comandos = comandos;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public List<RespostaCMD> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaCMD> respostas) {
        this.respostas = respostas;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RespostaCMD.RESULTADOCMD getResultadoExecucao() {
        return resultadoExecucao;
    }

    public void setResultadoExecucao(RespostaCMD.RESULTADOCMD resultadoExecucao) {
        this.resultadoExecucao = resultadoExecucao;
    }

}
