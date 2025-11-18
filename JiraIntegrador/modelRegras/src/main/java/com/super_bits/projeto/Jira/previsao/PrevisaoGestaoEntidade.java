/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.previsao;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoGerenciarEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import com.super_bits.projeto.Jira.CustosDesenvolvimento;
import com.super_bits.projeto.Jira.FabComponenteVisualRequisitos;
import com.super_bits.projeto.Jira.ItfPrevisaoGestaoEntidade;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.TipoGrupoTarefa;
import com.super_bits.projeto.Jira.grupoDeTarefas.FabTipoGrupoTarefa;
import com.super_bits.projeto.Jira.requisito.ItfRequisitoDoSistema;
import com.super_bits.projeto.SBRequisito;
import java.util.Date;
import java.util.List;
import org.coletivojava.fw.api.objetoNativo.view.componente.ComponenteVisualBase;

/**
 *
 * @author desenvolvedor
 */
public class PrevisaoGestaoEntidade extends ItemSimples implements ItfPrevisaoGestaoEntidade {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String descricao;
    private ItfAcaoGerenciarEntidade gestao;
    private ItfModuloAcaoSistema modulo;
    private final List<TarefaSuperBits> tarefasVinculadas;
    private final Class entidadeVinculada;
    private int qtdAcoesDeFormulario;
    private int qtdAoesDeController;
    private int horasProgramadas;
    private final PrevisaoProjeto previsaoProjeto;
    private CustosDesenvolvimento custoDesenvolvimento;
    private ItfRequisitoDoSistema requisitoVinculado;
    private double percentualTrabalhado;

    public PrevisaoGestaoEntidade(ItfAcaoGerenciarEntidade pAcaoGEstao, List<TarefaSuperBits> pTarefasVinculadas, PrevisaoProjeto pPrevisaoProjeto) {
        this.gestao = pAcaoGEstao;
        entidadeVinculada = pAcaoGEstao.getClasseRelacionada();
        modulo = pAcaoGEstao.getModulo();
        descricao = "Previsão de desenvolvimento para " + pAcaoGEstao.getNomeAcao();
        id = descricao.hashCode();
        for (ComoAcaoDoSistema acao : pAcaoGEstao.getAcoesVinculadas()) {
            if (acao.isUmaAcaoController()) {
                qtdAoesDeController++;
            } else {
                qtdAcoesDeFormulario++;
            }

        }
        tarefasVinculadas = pTarefasVinculadas;
        atualizaHorasProgramadas();
        previsaoProjeto = pPrevisaoProjeto;

    }

    private void recualcularCustos() {
        custoDesenvolvimento = new CustosDesenvolvimento(getTarefasVinculadas(), getPrevisaoProjeto().getAmbienteDesenvolvimento());
    }

    /**
     *
     * @return
     */
    @Override
    public final CustosDesenvolvimento getCustoDesenvolvimento() {
        if (custoDesenvolvimento == null) {
            recualcularCustos();
        }
        return custoDesenvolvimento;
    }

    @Override
    public final PrevisaoProjeto getPrevisaoProjeto() {
        return previsaoProjeto;
    }

    @Override
    public void adicionarTarefa(TarefaSuperBits tr) {
        if (!tarefasVinculadas.contains(tr)) {
            tarefasVinculadas.add(tr);
        }

    }

    @Override
    public final void atualizaHorasProgramadas() {

        int minutosPrevistos = 0;
        for (TarefaSuperBits tr : tarefasVinculadas) {
            minutosPrevistos += tr.getMinutosPrevistosTrabalho();
        }
        horasProgramadas = minutosPrevistos / 60;
    }

    @Override
    public ItfAcaoGerenciarEntidade getGestao() {
        return gestao;
    }

    @Override
    public void setGestao(ItfAcaoGerenciarEntidade gestao) {
        this.gestao = gestao;
    }

    @Override
    public ItfModuloAcaoSistema getModulo() {
        return modulo;
    }

    @Override
    public void setModulo(ItfModuloAcaoSistema modulo) {
        this.modulo = modulo;
    }

    @Override
    public final List<TarefaSuperBits> getTarefasVinculadas() {
        return tarefasVinculadas;
    }

    @Override
    public Class getEntidadeVinculada() {
        return entidadeVinculada;
    }

    @Override
    public int getQtdAcoesDeFormulario() {
        return qtdAcoesDeFormulario;
    }

    @Override
    public int getQtdAoesDeController() {
        return qtdAoesDeController;
    }

    @Override
    public int getHorasProgramadas() {

        return horasProgramadas;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getNomeDoAgrupamento() {
        return getGestao().getNomeAcao();
    }

    @Override
    public String getIcone() {
        return getGestao().getIconeAcao();
    }

    @Override
    public TipoGrupoTarefa getTipoGrupoTarefa() {
        return FabTipoGrupoTarefa.GESTAO.getRegistro();
    }

    class TimerAtualizacao extends Thread {

        private Date inicio;

        @Override
        public void run() {
            if (inicio == null) {

            }
        }

        public boolean liberado() {
            if (inicio == null) {
                return true;
            }
            if (new Date().getTime() - inicio.getTime() > 1000) {
                return true;
            } else {
                return false;
            }
        }

        public void atualizarTime() {
            inicio = new Date();
        }

    }
    private TimerAtualizacao atualizacaoRequisito = new TimerAtualizacao();

    @Override
    public ItfRequisitoDoSistema getRequisitoVinculado() {

        if (requisitoVinculado == null) {
            requisitoVinculado = SBRequisito.getRequisitoServices().getREquisitoGestao(this);
            atualizacaoRequisito.atualizarTime();
        }
        return requisitoVinculado;
    }

    public void setRequisitoVinculado(ItfRequisitoDoSistema requisitoVinculado) {
        this.requisitoVinculado = requisitoVinculado;
    }

    @Override
    public boolean isTemRequisitoVinculado() {
        if (atualizacaoRequisito.liberado()) {
            return getRequisitoVinculado() != null;
        } else {
            return requisitoVinculado != null;
        }

    }

    public ComponenteVisualBase getVisualizacaoOpcoes() {
        if (isTemRequisitoVinculado()) {

            return FabComponenteVisualRequisitos.OPCOES_ELEMENTO_VINCULADO.getRegistro();

        } else {
            return FabComponenteVisualRequisitos.OPCOES_ELEMENTO_NAO_VINCULADA.getRegistro();
        }
    }

    public ComponenteVisualBase getVisualizacaoDescricao() {
        if (isTemRequisitoVinculado()) {
            return FabComponenteVisualRequisitos.DESCRICAO_ELEMENTO_VINCULADO.getRegistro();
        } else {
            return FabComponenteVisualRequisitos.DECRICAO_ELEMENTO_NAO_VINCULADO.getRegistro();
        }
    }

    @Override
    public double getPercentualTrabalhado() {
        return percentualTrabalhado;
    }

    public void setPercentualTrabalhado(double percentualTrabalhado) {
        this.percentualTrabalhado = percentualTrabalhado;
    }

}
