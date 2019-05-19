/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.requisito;

import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;

import java.util.Date;
import java.util.List;

/**
 *
 * @author salvioF
 */
public interface ItfRequisitoDoSistema {

    public CustosDesenvolvimento getCustoDesenvolvimento();

    public Date getDataRequisicao();

    public int getId();

    public ModuloAcaoSistema getModulo();

    public String getDescricaoRequisito();

    public ItfStatusRequisito getStatus();

    public List<TarefaSuperBits> getTarefasVinculadas();

    public ItfTipoRequisito getTipoRequisito();

    public String getTipoRequisitoSTR();

    public double getValorRevenda();

    public void setDataRequisicao(Date dataRequisicao);

    public void setId(int id);

    public void setModulo(ModuloAcaoSistema modulo);

    public void setDescricaoRequisito(String requisito);

    public void setStatus(ItfStatusRequisito status);

    public void setTipoRequisitoSTR(String tipoRequisitoSTR);

    public void setValorRevenda(double valorRevenda);

    public String getTituloRequisito();

    public void setTituloRequisito(String requisito);

    public ItfRequisitoSolicitado getRequisitoSolicitado();
}
