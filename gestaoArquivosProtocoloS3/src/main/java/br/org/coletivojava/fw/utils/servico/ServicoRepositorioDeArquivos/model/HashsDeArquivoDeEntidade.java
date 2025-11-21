/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimplesORM;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoPreparacaoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import org.coletivojava.fw.api.tratamentoErros.ErroPreparandoObjeto;

/**
 *
 * @author sfurbino
 */
@SqlResultSetMapping(name = "QrArquivoDeEntidadeComHash", classes = {
    @ConstructorResult(targetClass = ArquivoDeEntidadeComHash.class,
            columns = {
                @ColumnResult(name = "id"),
                @ColumnResult(name = "nome"),
                @ColumnResult(name = "identificacaoHash")
            })
})
@Entity
@Table(indexes = {
    @Index(name = "HashDeArquivo", columnList = "hashCalculado")})
@InfoObjetoSB(tags = "Hash de Arquivo", plural = "Hashs de arquivos")
public class HashsDeArquivoDeEntidade extends EntidadeSimplesORM {

    @Id
    @GeneratedValue
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;

    @Column(nullable = false)
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String hashCalculado;
    @Column(nullable = false)
    private String entidade;
    @Column(nullable = false)
    private String atributo;
    @Column(nullable = false)
    private Long idEntidade;

    @Override
    @InfoPreparacaoObjeto(classesPrConstructorPrincipal = ItfCampoInstanciado.class)
    public void prepararNovoObjeto(Object... parametros) throws ErroPreparandoObjeto {

        ItfCampoInstanciado campoinstanciado = (ItfCampoInstanciado) parametros[0];
        entidade = campoinstanciado.getObjetoRaizDoAtributo().getClass().getSimpleName();
        atributo = campoinstanciado.getNomeCamponaClasse();
        idEntidade = campoinstanciado.getObjetoRaizDoAtributo().getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getHashCalculado() {
        return hashCalculado;
    }

    public void setHashCalculado(String hashCalculado) {
        this.hashCalculado = hashCalculado;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public Long getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Long idEntidade) {
        this.idEntidade = idEntidade;

    }

}
