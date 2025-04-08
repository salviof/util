/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos;

import org.docx4j.wml.Text;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoPacoteOffice;

/**
 *
 * @author SalvioF
 */
public class TextoOfficeSubstituivel {

    private final Object objetoOrigem;
    private final FabTipoTextoOfficeSubstituivel tipoTExto;
    private final String conteudoTexto;
    private ManipulacaoPacoteOffice manipulacao;

    public Text getObjetoOrigemComoTextoWord() {
        return (Text) objetoOrigem;
    }

    public TextoOfficeSubstituivel(Object pObjetoOrigem, String conteudo, FabTipoTextoOfficeSubstituivel pTipo) {
        this.objetoOrigem = pObjetoOrigem;
        tipoTExto = pTipo;
        conteudoTexto = conteudo;
    }

    public FabTipoTextoOfficeSubstituivel getTipoTExto() {
        return tipoTExto;
    }

    public String getConteudoTexto() {
        return conteudoTexto;
    }
    private ObjetoEmTabelaWord objEmTabela;

    public Object getObjetoOrigem() {
        return objetoOrigem;
    }

    public ObjetoEmTabelaWord getObjEmTabela() {
        if (objEmTabela == null) {
            objEmTabela = new ObjetoEmTabelaWord(getObjetoOrigemComoTextoWord());
        }
        return objEmTabela;
    }

    public Text getTextoOrigem() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
