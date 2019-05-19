/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.grupoDeTarefas;

import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.TipoGrupoTarefa;
import com.super_bits.projeto.Jira.previsao.PrevisaoProjeto;
import java.util.List;

/**
 *
 * @author salvioF
 */
public interface ItfGrupoDeTarefas {

    int getHorasProgramadas();

    public List<TarefaSuperBits> getTarefasVinculadas();

    public CustosDesenvolvimento getCustoDesenvolvimento();

    public PrevisaoProjeto getPrevisaoProjeto();

    public String getNomeDoAgrupamento();

    public String getIcone();

    public String getDescricao();

    public TipoGrupoTarefa getTipoGrupoTarefa();

    public double getPercentualTrabalhado();

}
