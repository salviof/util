/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.previsao;

import com.super_bits.projeto.Jira.ambienteDesenvolvimento.AmbienteDesenvolvimento;
import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaDeAcoes;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemGenerico;
import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.Jira.MapaTarefas;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.ambienteDesenvolvimento.FabAmbienteDesenvolvimento;
import com.super_bits.projeto.Jira.requisito.ItfRequisitoDoSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;

/**
 *
 * @author desenvolvedor
 */
@InfoObjetoSB(tags = {"Previsao Projeto"}, plural = "Previsões de Projeto")
public class PrevisaoProjeto extends ItemGenerico implements Serializable {

    private final Map<String, TarefaSuperBits> todasTarefas = new HashMap<>();
    private CustosDesenvolvimento custosDesenvolvimento;
    private final AmbienteDesenvolvimento ambienteDesenvolvimento;
    private final HashMap<ItfModuloAcaoSistema, PrevisaoModulo> previsoesModulos = new HashMap<>();
    private final Map<Class, PrevisaoEntidade> previsoesEntidade = new HashMap<>();
    private final PrevisaoTarefasPersonalizadas previsoesPersonalizadas;

    private MapaTarefas mapaDeTarefas;
    private MapaDeAcoes mapaAcoes;

    public PrevisaoProjeto() {
        this.ambienteDesenvolvimento = null;
        this.previsoesPersonalizadas = null;
    }

    private void addEntidadePrevista(Class pEntidade) {

        List<TarefaSuperBits> tarefasTabela = mapaDeTarefas.getTarefasDaTabela(pEntidade);

        if (tarefasTabela != null) {
            PrevisaoEntidade prevEntidade = new PrevisaoEntidade(tarefasTabela, pEntidade, this);
            previsoesEntidade.put(pEntidade, prevEntidade);
        }

    }

    private void adicionarTarefa(TarefaSuperBits pTarefa) {
        todasTarefas.put(pTarefa.getTarefaJiraOrigem().getNomeUnicoTarefa(), pTarefa);
    }

    private void adicionarTarefas(List<TarefaSuperBits> pTarefas) {
        pTarefas.stream().forEach((t) -> {
            adicionarTarefa(t);

        });

    }

    public PrevisaoProjeto(MapaDeAcoes pMapaAcoes, List<Class> entidades) {
        mapaAcoes = pMapaAcoes;
        mapaDeTarefas = new MapaTarefas(mapaAcoes, entidades);
        adicionarTarefas(mapaDeTarefas.getTodasTarefas());

        defineModulosPrevistros();
        if (entidades != null) {
            entidades.stream().forEach((entidade) -> {
                try {
                    ItemGenerico entiInst = (ItemGenerico) entidade.newInstance();

                    addEntidadePrevista(entidade);

                } catch (Throwable t) {
                    if (!SBCore.isEmModoProducao()) {
                        System.out.println(entidade.getClass().getSimpleName() + "foi  ignorada nos requisitos do projeto");
                    }
                }
            });
        }

        ambienteDesenvolvimento = FabAmbienteDesenvolvimento.AMBIENTE_PADRAO.getRegistro();
        previsoesPersonalizadas = new PrevisaoTarefasPersonalizadas();
        calcularValores();
    }

    public PrevisaoProjeto(Class<? extends ItfFabricaAcoes>[] fabricas) {
        this(new MapaDeAcoes(fabricas), null);
    }

    public PrevisaoProjeto(Class<? extends ItfFabricaAcoes>[] fabricas, List<Class> entidades, List<ItfRequisitoDoSistema> requisitosPersonalizados) {
        this(new MapaDeAcoes(fabricas), entidades);

        for (ItfRequisitoDoSistema req : requisitosPersonalizados) {
            addRequisitosPersonalizados(requisitosPersonalizados);
        }

    }

    private void addRequisitosPersonalizados(List<ItfRequisitoDoSistema> requisitosPersonalizados) {
        for (ItfRequisitoDoSistema req : requisitosPersonalizados) {
            if (req.getTipoRequisitoSTR().contains("tegra")) {
                previsoesPersonalizadas.addTarefaIntegracao(req);
            }
            if (req.getTipoRequisitoSTR().contains("tecno")) {
                previsoesPersonalizadas.addTarefaTecnologia(req);
            }
            if (req.getTipoRequisitoSTR().contains("generi")) {
                previsoesPersonalizadas.addTarefaExtra(req);
            }
        }
    }

    public List<PrevisaoEntidade> getEstruturasPrevistas() {
        return Lists.newArrayList(previsoesEntidade.values());
    }

    public final void defineModulosPrevistros() {

        //Para cada modulo do sistema
        for (ItfModuloAcaoSistema modulo : mapaAcoes.getModulos()) {
            List<PrevisaoEntidade> previsoesEntidadeDoModulo = new ArrayList<>();
            List<PrevisaoGestaoEntidade> previsoesGestaoEntidade = new ArrayList<>();
            //para cada ação de gestão do modulo
            for (ItfAcaoGerenciarEntidade acaoGestao : mapaAcoes.getAcoesGestaoByModulo(modulo)) {
                try {
                    Class entidadeVinculada = acaoGestao.getClasseRelacionada();
                    if (entidadeVinculada.getAnnotation(Entity.class) != null) {
                        previsoesGestaoEntidade.add(new PrevisaoGestaoEntidade(acaoGestao, mapaDeTarefas.getTarefasDaGestao(acaoGestao), this));
                        previsoesEntidadeDoModulo.add(new PrevisaoEntidade(mapaDeTarefas.getTarefasDaTabela(entidadeVinculada), entidadeVinculada, this));
                        addEntidadePrevista(acaoGestao.getClasseRelacionada());
                    }
                } catch (Throwable t) {
                    if (!SBCore.isEmModoProducao()) {
                        System.out.println("Entidade " + acaoGestao.getClasseRelacionada().getSimpleName() + " ignorada");
                    }
                }
            }

            PrevisaoModulo modPrev = new PrevisaoModulo(previsoesGestaoEntidade, previsoesEntidadeDoModulo, this);
            System.out.println("Previsto modulo " + modulo);
            System.out.println("O modulo " + modulo + " possui" + modPrev.getEntidadesPrevistas().size() + "Etidades com ações previstas");
            System.out.println("O modulo " + modulo + " possui" + modPrev.getGestoesPrevistas().size() + "Gestões com ações previstas");
            previsoesModulos.put(modulo, modPrev);
        }

    }

    public final void calcularValores() {

        custosDesenvolvimento = new CustosDesenvolvimento(mapaDeTarefas.getTodasTarefas(), ambienteDesenvolvimento);
    }

    public AmbienteDesenvolvimento getAmbienteDesenvolvimento() {
        return ambienteDesenvolvimento;
    }

    public CustosDesenvolvimento getCustosDesenvolvimento() {
        return custosDesenvolvimento;
    }

    public HashMap<ItfModuloAcaoSistema, PrevisaoModulo> getModuloPrevistosPorModulo() {
        return previsoesModulos;
    }

    public List<PrevisaoModulo> getModulosPrevistos() {
        return Lists.newArrayList(getModuloPrevistosPorModulo().values());
    }

    public void setCustosDesenvolvimento(CustosDesenvolvimento custosDesenvolvimento) {
        this.custosDesenvolvimento = custosDesenvolvimento;
    }

    @Override
    public String getImgPequena() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
