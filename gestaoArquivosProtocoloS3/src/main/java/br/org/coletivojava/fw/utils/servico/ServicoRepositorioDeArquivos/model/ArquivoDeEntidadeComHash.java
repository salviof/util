/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemSimples;
import java.io.Serializable;

/**
 *
 * @author sfurbino
 */
@InfoObjetoSB(tags = {"Arquivo com hash de enteidade"}, plural = "Arquivos")
public class ArquivoDeEntidadeComHash extends ItemSimples implements Serializable {

    public ArquivoDeEntidadeComHash(int pId, String pNome, String pHash) {
        id = pId;
        nome = pNome;
        identificacaoHash = pHash;
    }

    public ArquivoDeEntidadeComHash() {

    }

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private int id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    private String nome;
    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES)
    private String identificacaoHash;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdentificacaoHash() {
        return identificacaoHash;
    }

    public void setIdentificacaoHash(String identificacaoHash) {
        this.identificacaoHash = identificacaoHash;
    }

}
