/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.previsao;

import com.super_bits.projeto.Jira.ambienteDesenvolvimento.AmbienteDesenvolvimento;
import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoClasse;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaDeAcoes;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.Jira.MapaTarefas;
import com.super_bits.projeto.Jira.Jira.MapaTarefasProjeto;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author desenvolvedor
 */
@InfoClasse(tags = {"Previsao Projeto"}, plural = "Previsões de Projeto")
public class PrevisaoProjeto implements Serializable {

    private final List<TarefaSuperBits> todasTarefas;
    private CustosDesenvolvimento custosDesenvolvimento;
    private final HashMap<ItfModuloAcaoSistema, PrevisaoModulo> modulosPrevistos = new HashMap<>();
    private final Map<String, Class> mapaDeEntidadesVinculadas = new HashMap<>();
    private final AmbienteDesenvolvimento ambienteDesenvolvimento;

    private MapaTarefas mapaDeTarefas;
    private MapaDeAcoes mpaAcoes;

    public PrevisaoProjeto(Class<? extends ItfFabricaAcoes>[] fabricas) {
        ambienteDesenvolvimento = new AmbienteDesenvolvimento();
        mpaAcoes = new MapaDeAcoes(fabricas);
        mapaDeTarefas = new MapaTarefas(mpaAcoes);
        todasTarefas = mapaDeTarefas.getTodasTarefas();
        defineModulosPrevistros();
        calcularValores();
    }

    /*
    public PrevisaoProjeto(Class<? extends ItfFabricaAcoes>[] fabricas, List<Class> entidades, TarefaSuperBits tarefaExtra) {

        for (Class fab : fabricas) {

        }

    }

    public final void defineEstruturasPrevistas() {
        for (Class entidade : mapaDeEntidadesVinculadas.values()) {
            List<TarefaSuperBits> tarefasTabela = mapaDeTarefas.getTarefasDaTabela(classeRelacionada);
            if (tarefasTabela != null) {

            }
            PrevisaoEntidade prevEntidade = new PrevisaoEntidade(modulo, tarefasTabela, acaoGestao.getClasseRelacionada(), this);
            previsoesEntidade.add(prevEntidade);
        }
    }
     */
    public final void defineModulosPrevistros() {

        //Para cada modulo do sistema
        for (ItfModuloAcaoSistema modulo : mpaAcoes.getModulos()) {
            List<PrevisaoEntidade> previsoesEntidade = new ArrayList<>();
            List<PrevisaoGestaoEntidade> previsoesGestaoEntidade = new ArrayList<>();
            //para cada ação de gestão do modulo
            for (ItfAcaoGerenciarEntidade acaoGestao : MapaAcoesSistema.getAcoesGestaoByModulo(modulo)) {
                List<TarefaSuperBits> tarefasGestao = MapaTarefasProjeto.getTarefasDaGestao(acaoGestao);
                PrevisaoGestaoEntidade prevGestao = new PrevisaoGestaoEntidade(acaoGestao, tarefasGestao, this);
                Class classeRelacionada = acaoGestao.getClasseRelacionada();
                previsoesEntidade.add(new PrevisaoEntidade(modulo, todasTarefas, classeRelacionada, this));
                previsoesGestaoEntidade.add(prevGestao);
                mapaDeEntidadesVinculadas.put(acaoGestao.getClasseRelacionada().getSimpleName(), acaoGestao.getClasseRelacionada());
            }

            PrevisaoModulo modPrev = new PrevisaoModulo(previsoesGestaoEntidade, previsoesEntidade, this);
            System.out.println("Previsto modulo " + modulo);
            System.out.println("O modulo " + modulo + " possui" + modPrev.getEntidadesPrevistas().size() + "Etidades com ações previstas");
            System.out.println("O modulo " + modulo + " possui" + modPrev.getGestoesPrevistas().size() + "Gestões com ações previstas");
            modulosPrevistos.put(modulo, modPrev);
        }

    }

    public final void calcularValores() {

        custosDesenvolvimento = new CustosDesenvolvimento(todasTarefas, ambienteDesenvolvimento);
    }

    public AmbienteDesenvolvimento getAmbienteDesenvolvimento() {
        return ambienteDesenvolvimento;
    }

    public CustosDesenvolvimento getCustosDesenvolvimento() {
        return custosDesenvolvimento;
    }

    public HashMap<ItfModuloAcaoSistema, PrevisaoModulo> getModuloPrevistosPorModulo() {
        return modulosPrevistos;
    }

    public List<PrevisaoModulo> getModulosPrevistos() {
        return Lists.newArrayList(getModuloPrevistosPorModulo().values());
    }

    public void setCustosDesenvolvimento(CustosDesenvolvimento custosDesenvolvimento) {
        this.custosDesenvolvimento = custosDesenvolvimento;
    }

}
