/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos;

import org.jvnet.jaxb2_commons.ppp.Child;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

/**
 *
 * @author SalvioF
 */
public class TextoOfficeSubstituivel {

    private final Text textoOrigem;
    private final FabTipoTextoOfficeSubstituivel tipoTExto;
    private final String texto;
    private Tr linha;
    private Tc coluna;
    private Tbl tabela;

    public TextoOfficeSubstituivel(Text textoOrigem, FabTipoTextoOfficeSubstituivel pTipo) {
        this.textoOrigem = textoOrigem;
        tipoTExto = pTipo;
        texto = textoOrigem.getValue();
    }

    public Text getTextoOrigem() {
        return textoOrigem;
    }

    public FabTipoTextoOfficeSubstituivel getTipoTExto() {
        return tipoTExto;
    }

    public String getTexto() {
        return texto;
    }

    public Tc getCooluna() {
        if (coluna == null) {
            boolean terminouPesquisa = false;
            Object objeto = textoOrigem.getParent();
            while (!terminouPesquisa) {
                if (objeto == null) {
                    terminouPesquisa = true;

                } else {
                    if (objeto instanceof Tc) {
                        coluna = (Tc) objeto;
                        return coluna;
                    } else if (objeto instanceof Child) {
                        objeto = ((Child) objeto).getParent();
                    } else {
                        terminouPesquisa = true;
                    }
                }

            }

        }
        return coluna;
    }

    public Tr getLinha() {

        if (linha == null) {
            boolean terminouPesquisa = false;
            Object objeto = textoOrigem.getParent();
            while (!terminouPesquisa) {
                if (objeto == null) {
                    terminouPesquisa = true;

                } else {
                    if (objeto instanceof Tr) {
                        linha = (Tr) objeto;
                        return linha;
                    } else if (objeto instanceof Child) {
                        objeto = ((Child) objeto).getParent();
                    } else {
                        terminouPesquisa = true;
                    }
                }
            }

        }
        return linha;

    }

    public Tbl getTabela() {

        if (tabela == null) {
            boolean terminouPesquisa = false;
            Object objeto = textoOrigem.getParent();
            while (!terminouPesquisa) {
                if (objeto == null) {
                    terminouPesquisa = true;

                } else {
                    if (objeto instanceof Tbl) {
                        tabela = (Tbl) objeto;
                        return tabela;
                    } else if (objeto instanceof Child) {
                        objeto = ((Child) objeto).getParent();
                    } else {
                        terminouPesquisa = true;
                    }
                }
            }

        }
        return tabela;

    }

}
