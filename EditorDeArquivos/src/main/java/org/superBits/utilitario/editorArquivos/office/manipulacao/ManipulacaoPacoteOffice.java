/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office.manipulacao;

import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicaoArquivo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;

/**
 *
 * @author salvio
 */
public abstract class ManipulacaoPacoteOffice {

    private final TextoOfficeSubstituivel textoSubstituivel;
    private final MapaSubstituicaoArquivo mapa;
    protected final List<String> controleListasSubstituidas;

    public ManipulacaoPacoteOffice(TextoOfficeSubstituivel textoSubstituivel, MapaSubstituicaoArquivo pMapa, List<String> pControleLista) {
        this.textoSubstituivel = textoSubstituivel;
        mapa = pMapa;
        controleListasSubstituidas = pControleLista;
    }

    protected MapaSubstituicaoArquivo getMapa() {
        return mapa;
    }

    public TextoOfficeSubstituivel getTextoSubstituivel() {
        return textoSubstituivel;
    }

    public abstract void substituir();

    public Text getTextoDaColuna(Tc pColuna) {
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

    public byte[] extractBytes(String ImageName) throws IOException {
        File file = new File(ImageName);

        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.out.println("Erro obtendo bytes da imagem");

        }

        return bos.toByteArray();

    }
}
