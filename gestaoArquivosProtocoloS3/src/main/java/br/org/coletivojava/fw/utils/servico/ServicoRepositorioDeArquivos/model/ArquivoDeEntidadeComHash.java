/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.EntidadeSimples;
import java.io.Serializable;

/**
 *
 * @author sfurbino
 */
@InfoObjetoSB(tags = {"Arquivo com hash de enteidade"}, plural = "Arquivos")
public class ArquivoDeEntidadeComHash extends EntidadeSimples implements Serializable {

    public ArquivoDeEntidadeComHash(Long pId, String pNome, String pHash) {
        id = pId;
        nome = pNome;
        identificacaoHash = pHash;
    }

    public ArquivoDeEntidadeComHash() {

    }

    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nome;
    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES)
    private String identificacaoHash;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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
