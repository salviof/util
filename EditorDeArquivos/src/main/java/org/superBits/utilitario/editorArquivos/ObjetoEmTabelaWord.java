/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos;

import javax.xml.bind.JAXBElement;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.docx4j.wml.Text;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.superBits.utilitario.editorArquivos.office.manipulacao.UtilSBEditorArquivosOfficeWord;

/**
 *
 * @author salvio
 */
public class ObjetoEmTabelaWord {

    private Tr linha;
    private Tc coluna;
    private Tbl tabela;
    private final Text origem;

    public ObjetoEmTabelaWord(Text pOrigem) {
        origem = pOrigem;
    }

    public Tc getCooluna() {
        if (coluna == null) {
            boolean terminouPesquisa = false;
            Object objeto = origem.getParent();
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
            Object objeto = origem.getParent();
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
            Object objeto = origem.getParent();
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

    private Text getTextoDaColuna(Tc pColuna) {
        //desafio: Este código pode ser melhorado utilizando padrão de autoreferencias, atingindo todos os níveis de pesquisa existente na coluna  forka isso! www.coletivojava.com.br
        for (Object filho : pColuna.getContent()) {
            if (filho instanceof ContentAccessor) {
                for (Object filhoLevel1 : ((ContentAccessor) filho).getContent()) {
                    if (filhoLevel1 instanceof JAXBElement) {
                        if (UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel1) != null) {
                            return UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel1);
                        }
                    }
                    if (filhoLevel1 instanceof ContentAccessor) {
                        for (Object filhoLevel2 : ((ContentAccessor) filhoLevel1).getContent()) {
                            if (filhoLevel2 instanceof JAXBElement) {
                                if (filhoLevel2 instanceof JAXBElement) {
                                    if (UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel2) != null) {
                                        return UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel2);
                                    }
                                }
                            }
                            if (filhoLevel2 instanceof ContentAccessor) {
                                for (Object filhoLevel3 : ((ContentAccessor) filhoLevel2).getContent()) {
                                    if (filhoLevel3 instanceof JAXBElement) {
                                        if (filhoLevel3 instanceof JAXBElement) {
                                            if (UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel3) != null) {
                                                return UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel3);
                                            }
                                        }
                                    }
                                    if (filhoLevel3 instanceof ContentAccessor) {
                                        for (Object filhoLevel4 : ((ContentAccessor) filhoLevel3).getContent()) {
                                            if (filhoLevel4 instanceof JAXBElement) {
                                                if (filhoLevel4 instanceof JAXBElement) {
                                                    if (UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel4) != null) {
                                                        return UtilSBEditorArquivosOfficeWord.getTextoDoElemento((JAXBElement) filhoLevel4);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
        return null;

    }
}
