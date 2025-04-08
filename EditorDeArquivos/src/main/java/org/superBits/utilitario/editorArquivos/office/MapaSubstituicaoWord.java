/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoPacoteOffice;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoWord;

/**
 *
 * @author salvio
 */
public class MapaSubstituicaoWord extends MapaSubstituicaoOffice {

    public MapaSubstituicaoWord(File pArquivo) {
        super(pArquivo);

    }

    @Override
    public WordprocessingMLPackage lerArquivoArquivoPacoteOffice(String pCaminhoArquivo) throws ErroAbrindoArquivoPacoteOffice {
        //  Mapper fontMapper = new IdentityPlusMapper();
        //    fontMapper.put("Calibri", PhysicalFonts.get("Calibri"));
        WordprocessingMLPackage mlp;
        try {
            mlp = WordprocessingMLPackage.load(new File(pCaminhoArquivo));
        } catch (Docx4JException ex) {
            throw new ErroAbrindoArquivoPacoteOffice("Falha Lendo arquivo pCaminho arquivo" + ex.getMessage());
        }

        try {
            mlp = WordprocessingMLPackage.load(arquivo);
//            mlp.setFontMapper(fontMapper);
            if (mlp == null) {
                throw new ErroAbrindoArquivoPacoteOffice("Falha Lendo arquivo " + pCaminhoArquivo);
            }
            return mlp;
        } catch (Exception ex) {
            throw new ErroAbrindoArquivoPacoteOffice("Falha Lendo arquivo " + pCaminhoArquivo + " " + ex.getMessage());
        }

    }

    @Override
    public List<TextoOfficeSubstituivel> listaTextosSubstituiveis(OpcPackage pArquivoWord) throws ErroModificandoArquivosPacoteOffice, ErroAbrindoArquivoPacoteOffice {
        WordprocessingMLPackage arquivoWordInterpretado = (WordprocessingMLPackage) pArquivoWord;
        if (arquivoWordInterpretado == null) {
            throw new ErroModificandoArquivosPacoteOffice("Arquivo nulo enviado");
        }
        ContentAccessor acessoAoConteudo = arquivoWordInterpretado.getMainDocumentPart();

        try {
            List<Text> lista = obterTodosObjetosDoTIpoTexto(acessoAoConteudo, arquivoWordInterpretado, new ArrayList<>());

            List<TextoOfficeSubstituivel> listaTexto = new ArrayList<>();
            for (Text texto : lista) {

                String conteudoTexto = texto.getValue();
                TextoOfficeSubstituivel textoSubstituivel = getTextoSubstituivel(texto, conteudoTexto);
                if (textoSubstituivel != null) {
                    listaTexto.add(textoSubstituivel);
                }
            }
            return listaTexto;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro lendo documento pesquisando textos modificaveis", t);
            return new ArrayList<>();
        }
    }

    private List<Text> obterTodosObjetosDoTIpoTexto(ContentAccessor c, WordprocessingMLPackage mlp, List<Text> pLista)
            throws Exception {
        if (pLista == null) {
            pLista = new ArrayList();
        }
        for (Object p : c.getContent()) {

            if (p instanceof ContentAccessor) {

                obterTodosObjetosDoTIpoTexto((ContentAccessor) p, mlp, pLista);
            } else if (p instanceof JAXBElement) {
                Object obJetoEmelementoXML = ((JAXBElement) p).getValue();

                if (obJetoEmelementoXML instanceof ContentAccessor) {
                    obterTodosObjetosDoTIpoTexto((ContentAccessor) obJetoEmelementoXML, mlp, pLista);
                } else if (obJetoEmelementoXML instanceof Text) {
                    pLista.add((Text) obJetoEmelementoXML);
                }
            }
        }
        return pLista;

    }

    @Override
    public ManipulacaoPacoteOffice gerarManipulador(TextoOfficeSubstituivel textoSubstituivel, List<String> pControleListas, OpcPackage arquivoProcessado) {
        return new ManipulacaoWord((WordprocessingMLPackage) arquivoProcessado, textoSubstituivel, this, pControleListas);
    }

}
