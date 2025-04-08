/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.pptx4j.Pptx4jException;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoApresentacao;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoPacoteOffice;

/**
 *
 * @author salvio
 */
public class MapaSubstituicaoApresentacao extends MapaSubstituicaoOffice {

    public MapaSubstituicaoApresentacao(File pArquivo) {
        super(pArquivo);
    }

    @Override
    public OpcPackage lerArquivoArquivoPacoteOffice(String pCaminhoArquivo) throws ErroAbrindoArquivoPacoteOffice {
        try {
            PresentationMLPackage documentoApresentacaoInterpretado = PresentationMLPackage.load(UTilSBCoreInputs.getStreamByLocalFile(pCaminhoArquivo));
            return documentoApresentacaoInterpretado;
        } catch (Docx4JException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha abrindo arquivo", ex);
            throw new ErroAbrindoArquivoPacoteOffice("Falha abrindo " + pCaminhoArquivo + " " + ex.getMessage());
        }
    }

    public static List<CTTextParagraph> getAllTextParagraphs(PresentationMLPackage presentationMLPackage) {
        List<CTTextParagraph> textParagraphs = new ArrayList<>();

        // Obtém todos os SlideParts da apresentação
        List<SlidePart> slideParts;
        try {
            slideParts = presentationMLPackage.getMainPresentationPart().getSlideParts();
            // Percorre cada SlidePart
            for (SlidePart slidePart : slideParts) {
                try {
                    // Obtém o conteúdo do slide (Sld)
                    org.pptx4j.pml.Sld slide = slidePart.getJaxbElement();
                    findTextParagraphs(slide, textParagraphs);
                } catch (Exception e) {
                    e.printStackTrace(); // Trate a exceção conforme necessário
                }
            }
        } catch (Pptx4jException ex) {
            Logger.getLogger(MapaSubstituicaoApresentacao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return textParagraphs;
    }

    private static void findTextParagraphs(Object element, List<CTTextParagraph> textParagraphs) {
        // Verifica se o elemento atual é um CTTextParagraph
        if (element instanceof CTTextParagraph) {
            textParagraphs.add((CTTextParagraph) element);
            return;
        }

        // Trata diferentes elementos do PPTX
        if (element instanceof org.pptx4j.pml.Sld) { // Slide
            org.pptx4j.pml.Sld slide = (org.pptx4j.pml.Sld) element;
            if (slide.getCSld() != null && slide.getCSld().getSpTree() != null) {
                for (Object shape : slide.getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame()) {
                    findTextParagraphs(shape, textParagraphs);
                }
            }
        } else if (element instanceof org.pptx4j.pml.Shape) { // Forma com texto
            org.pptx4j.pml.Shape shape = (org.pptx4j.pml.Shape) element;
            if (shape.getTxBody() != null) {
                // Aqui os parágrafos estão na lista de CTTextParagraph
                for (Object paragraph : shape.getTxBody().getP()) {
                    findTextParagraphs(paragraph, textParagraphs);
                }
            }
        }
    }
    private static final Pattern BRACKET_PATTERN = Pattern.compile("\\[.*?\\]");

    public static List<CTRegularTextRun> findTextRunsWithBrackets(CTTextParagraph paragraph) {
        List<CTRegularTextRun> textRunsWithBrackets = new ArrayList<>();

        // Verifica se o parágrafo tem runs de texto
        if (paragraph != null && paragraph.getEGTextRun() != null) {
            for (Object textRun : paragraph.getEGTextRun()) {
                if (textRun instanceof CTRegularTextRun) { // CTR é um text run
                    CTRegularTextRun run = (CTRegularTextRun) textRun;
                    if (run.getT() != null) { // Verifica se há texto no run
                        String text = run.getT();
                        // Usa regex para verificar se há texto entre colchetes
                        Matcher matcher = BRACKET_PATTERN.matcher(text);
                        if (matcher.find()) {
                            textRunsWithBrackets.add(run); // Adiciona o CTR inteiro
                        }
                    }
                }
            }
        }

        return textRunsWithBrackets;
    }

    @Override
    public List<TextoOfficeSubstituivel> listaTextosSubstituiveis(OpcPackage pArquivoApresentecao) throws ErroModificandoArquivosPacoteOffice, ErroAbrindoArquivoPacoteOffice {
        // Percorre todos os slides
        // Percorre todos os slides
        PresentationMLPackage documentoApresentacaoInterpretado = (PresentationMLPackage) pArquivoApresentecao;
        List<CTTextParagraph> paragrafros = getAllTextParagraphs(documentoApresentacaoInterpretado);
        List<TextoOfficeSubstituivel> listaTexto = new ArrayList<>();

        for (CTTextParagraph paragrafo : paragrafros) {
            List<CTRegularTextRun> textos = findTextRunsWithBrackets(paragrafo);
            for (CTRegularTextRun texto : textos) {
                String conteudo = texto.getT();
                TextoOfficeSubstituivel textoSub = getTextoSubstituivel(texto, conteudo);
                listaTexto.add(textoSub);
            }

        }

        return listaTexto;
    }

    @Override
    public ManipulacaoPacoteOffice gerarManipulador(TextoOfficeSubstituivel pTextoSubstituivel, List<String> pControleListas, OpcPackage pArquivoProcessado) {
        return new ManipulacaoApresentacao((PresentationMLPackage) pArquivoProcessado, pTextoSubstituivel, this, pControleListas);
    }

}
