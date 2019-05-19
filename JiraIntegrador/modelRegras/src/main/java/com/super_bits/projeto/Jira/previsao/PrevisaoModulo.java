/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.previsao;

import com.super_bits.modulos.SBAcessosModel.controller.FabModulosSistemaSB;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.ItfPrevisaoModulo;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.TipoGrupoTarefa;
import com.super_bits.projeto.Jira.grupoDeTarefas.FabTipoGrupoTarefa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desenvolvedor
 */
public class PrevisaoModulo extends ItemSimples implements ItfPrevisaoModulo {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private final int id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    private final String nome;
    List<TarefaSuperBits> todasTarefas = new ArrayList<>();
    private final List<PrevisaoGestaoEntidade> gestoesPrevistas;
    private final List<PrevisaoEntidade> entidadesPrevistas;
    private final PrevisaoProjeto previsaoProjeto;
    private CustosDesenvolvimento custoDesenvolvimento;
    private final List<TarefaSuperBits> tarefasVinculadas;
    private double percentualTrabalhado;

    public PrevisaoModulo(List<PrevisaoGestaoEntidade> gestoesPrevistas,
            List<PrevisaoEntidade> entidadesPrevistas, PrevisaoProjeto pPrevisaoProjeto) {
        this.gestoesPrevistas = gestoesPrevistas;
        this.entidadesPrevistas = entidadesPrevistas;
        nome = getModuloAssociado().getNome();
        id = nome.hashCode();
        previsaoProjeto = pPrevisaoProjeto;

        this.entidadesPrevistas.stream().forEach((prevEntidade) -> {
            todasTarefas.addAll(prevEntidade.getTarefasVinculadas());
        });
        gestoesPrevistas.stream().forEach((prevGestao) -> {
            todasTarefas.addAll(prevGestao.getTarefasVinculadas());
        });
        tarefasVinculadas = todasTarefas;

    }

    @Override
    public PrevisaoProjeto getPrevisaoProjeto() {
        return previsaoProjeto;
    }

    @Override
    public List<PrevisaoGestaoEntidade> getGestoesPrevistas() {
        return gestoesPrevistas;
    }

    @Override
    public List<PrevisaoEntidade> getEntidadesPrevistas() {
        return entidadesPrevistas;
    }

    @Override
    public final ItfModuloAcaoSistema getModuloAssociado() {
        PrevisaoGestaoEntidade gestaoQualquer = getGestoesPrevistas().get(0);
        if (gestaoQualquer == null) {
            return FabModulosSistemaSB.PAGINAS_DO_SISTEMA.getRegistro();
        } else {
            return gestaoQualquer.getModulo();
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    private void recalcularCustos() {
        custoDesenvolvimento = new CustosDesenvolvimento(todasTarefas, previsaoProjeto.getAmbienteDesenvolvimento());
    }

    @Override
    public CustosDesenvolvimento getCustoDesenvolvimento() {
        if (custoDesenvolvimento == null) {
            recalcularCustos();
        }
        return custoDesenvolvimento;
    }

    /**
     *
     * @return
     */
    @Override
    public List<TarefaSuperBits> getTarefasVinculadas() {
        return tarefasVinculadas;
    }

    @Override
    public int getHorasProgramadas() {
        return getCustoDesenvolvimento().getHorasTotal();
    }

    @Override
    public String getNomeDoAgrupamento() {
        return getModuloAssociado().getNome();
    }

    @Override
    public String getIcone() {
        return getModuloAssociado().getIconeDaClasse();
    }

    @Override
    public String getDescricao() {
        return getModuloAssociado().getDescricao();
    }

    @Override
    public TipoGrupoTarefa getTipoGrupoTarefa() {
        return FabTipoGrupoTarefa.MODULO.getRegistro();
    }

    /**
     *
     * @return
     */
    @Override
    public double getPercentualTrabalhado() {
        return percentualTrabalhado;
    }

    public void setPercentualTrabalhado(double percentualTrabalhado) {
        this.percentualTrabalhado = percentualTrabalhado;
    }

}
