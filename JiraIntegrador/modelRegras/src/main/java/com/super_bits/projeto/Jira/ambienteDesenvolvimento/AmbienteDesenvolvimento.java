/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.ambienteDesenvolvimento;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.EntidadeSimples;
import com.super_bits.projeto.Jira.DesenvolvedorProjetoSB;
import com.super_bits.projeto.Jira.FabTipoProfissional;
import com.super_bits.projeto.Jira.TipoProfissional;
import java.util.ArrayList;
import java.util.List;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreFabrica;

/**
 *
 *
 *
 * @author desenvolvedor
 */
public class AmbienteDesenvolvimento extends EntidadeSimples {

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nomeAmbiente = "Ambiente Padrão";
    private int taxaAdminsitrativa = 20;
    private int adicionalHoraExtra = 50;
    private int adicionalEntregaAntecipada = 40;
    private int valorPadraoHorasDiarias = 6;
    private int fatorProdutividade = 1;

    private List<DesenvolvedorProjetoSB> desenvolvedores;
    private final List<TipoProfissional> tiposProfissional = (List) UtilSBCoreFabrica.listaRegistros(FabTipoProfissional.class);

    public AmbienteDesenvolvimento() {
        desenvolvedores = new ArrayList<>();
    }

    private TipoProfissional getDetalhesProfissionalByTipo(FabTipoProfissional pTipo) {
        for (TipoProfissional p : tiposProfissional) {
            if (p.getFabrica().equals(pTipo)) {
                return p;
            }
        }
        return pTipo.getRegistro();
    }

    public TipoProfissional getDetalhesProfissionalTDD() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_LOGICA_TDD);
    }

    public TipoProfissional getDetalhesProfissionalImplementacao() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_IMPLEMENTACAO);
    }

    public TipoProfissional getDetalhesProfissionalRequistos() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_REQUISITOS);
    }

    public TipoProfissional getDetalhesProfissionalAndroid() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_ANDROID);
    }

    public TipoProfissional getDetalhesProfissionalTelas() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_TELAS);
    }

    public TipoProfissional getDetalhesProfissionalBancoDeDados() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.ANALISTA_BANCO_DE_DADOS);
    }

    public TipoProfissional getDetalhesProfissionalDesigner() {
        return getDetalhesProfissionalByTipo(FabTipoProfissional.DESIGNER);
    }

    public int getTaxaAdminsitrativa() {
        return taxaAdminsitrativa;
    }

    public void setTaxaAdminsitrativa(int taxaAdminsitrativa) {
        this.taxaAdminsitrativa = taxaAdminsitrativa;
    }

    public int getAdicionalHoraExtra() {
        return adicionalHoraExtra;
    }

    public void setAdicionalHoraExtra(int adicionalHoraExtra) {
        this.adicionalHoraExtra = adicionalHoraExtra;
    }

    public int getAdicionalEntregaAntecipada() {
        return adicionalEntregaAntecipada;
    }

    public void setAdicionalEntregaAntecipada(int adicionalEntregaAntecipada) {
        this.adicionalEntregaAntecipada = adicionalEntregaAntecipada;
    }

    public int getValorPadraoHorasDiarias() {
        return valorPadraoHorasDiarias;
    }

    public void setValorPadraoHorasDiarias(int valorPadraoHorasDiarias) {
        this.valorPadraoHorasDiarias = valorPadraoHorasDiarias;
    }

    public List<DesenvolvedorProjetoSB> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<DesenvolvedorProjetoSB> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public String getNomeAmbiente() {
        return nomeAmbiente;
    }

    public void setNomeAmbiente(String nomeAmbiente) {
        this.nomeAmbiente = nomeAmbiente;
    }

    public int getFatorProdutividade() {
        return fatorProdutividade;
    }

    public void setFatorProdutividade(int fatorProdutividade) {
        this.fatorProdutividade = fatorProdutividade;
    }

}
