package org.superBits.utilitario.editorArquivos.office.manipulacao;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreOutputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringBuscaTrecho;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringNomeArquivosEDiretorios;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicaoArquivo;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import static org.superBits.utilitario.editorArquivos.FabTipoTextoOfficeSubstituivel.IMAGEM;
import static org.superBits.utilitario.editorArquivos.FabTipoTextoOfficeSubstituivel.LISTAGEM;
import static org.superBits.utilitario.editorArquivos.FabTipoTextoOfficeSubstituivel.SIMPLES;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;

/**
 *
 * @author salvio
 */
public class ManipulacaoWord extends ManipulacaoPacoteOffice {

    private final WordprocessingMLPackage documento;

    public ManipulacaoWord(WordprocessingMLPackage pDocumento, TextoOfficeSubstituivel textoSubstituivel, MapaSubstituicaoArquivo pMapa, List<String> pControleLista) {
        super(textoSubstituivel, pMapa, pControleLista);
        documento = pDocumento;

    }

    @Override
    public void substituir() {
        Text textoDocumentoWord = getTextoSubstituivel().getObjetoOrigemComoTextoWord();

        switch (getTextoSubstituivel().getTipoTExto()) {
            case SIMPLES:
                textoDocumentoWord.setSpace("preserve"); // needed?
                textoDocumentoWord.setValue(getMapa().substituirEmString(getTextoSubstituivel().getConteudoTexto()));
                break;
            case IMAGEM:
                Tc coluna = getTextoSubstituivel().getObjEmTabela().getCooluna();
                String caminhoArquivo = getMapa().getValorImagem(getTextoSubstituivel().getConteudoTexto());
                P paragraphWithImage = null;
                if (caminhoArquivo.startsWith("http")) {
                    String imagemTempLocal = SBCore.getServicoSessao().getSessaoAtual().getPastaTempDeSessao() + "/" + UtilSBCoreStringNomeArquivosEDiretorios.getNomeArquivo(caminhoArquivo + ".jpg");
                    UtilSBCoreOutputs.salvarArquivoInput(UTilSBCoreInputs.getStreamBuffredByURL(caminhoArquivo, 10000, 60000), imagemTempLocal);
                    try {
                        paragraphWithImage = createInlineImageToParagraph(createInlineImage(new File(imagemTempLocal), documento));
                    } catch (Exception ex) {
                        Logger.getLogger(ManipulacaoWord.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    coluna.getContent().removeAll(coluna.getContent());
                    try {
                        paragraphWithImage = createInlineImageToParagraph(createInlineImage(new File(caminhoArquivo), documento));
                    } catch (Exception ex) {
                        Logger.getLogger(ManipulacaoWord.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (paragraphWithImage != null) {
                    coluna.getContent().removeAll(coluna.getContent());
                    coluna.getContent().add(paragraphWithImage);
                }
                if (coluna == null) {
                    throw new UnsupportedOperationException("A Imagem deve estar dentro de uma coluna");
                }

                break;
            case LISTAGEM:

                Tbl tabela = getTextoSubstituivel().getObjEmTabela().getTabela();

                String chaveLista = getMapa().getChaveiLstasByTextoencontrado(getTextoSubstituivel().getConteudoTexto());

                Map<String, String> valores = getMapa().getValoresListas(chaveLista); //mapaSubstituicaoListas.get(chaveLista);
                //List<Integer> lista = gerarLinhasSubLista(valores);
                Map<Integer, List<String>> estruturaTabela = getMapa().getOrdemItensLista(chaveLista); // ordemMapaSubstituicaoListas.get(chaveLista);
                if (estruturaTabela != null) {

                    List<Integer> listaOrdenada = Lists.newArrayList(estruturaTabela.keySet());
                    Collections.sort(listaOrdenada);

                    if (!controleListasSubstituidas.contains(chaveLista)) {
                        for (Integer linha : listaOrdenada) {

                            controleListasSubstituidas.add(chaveLista);

                            Tr copia = (Tr) XmlUtils.deepCopy(getTextoSubstituivel().getObjEmTabela().getLinha());
                            for (Object campoLinha : copia.getContent()) {
                                if (campoLinha instanceof JAXBElement) {
                                    JAXBElement elemento = (JAXBElement) campoLinha;

                                    Object valorElemento = elemento.getValue();
                                    if (valorElemento instanceof Tc) {
                                        //valores.get(colunaDaLinha);

                                        Tc col = (Tc) valorElemento;

                                        Text texto = getTextoDaColuna(col);
                                        if (texto
                                                != null) {
                                            texto.setSpace("preserve");
                                            System.out.println("Valor Antigo="
                                                    + texto.getValue());
                                            String valorCompleto = texto.getValue();
                                            String restante = UtilSBCoreStringBuscaTrecho.getStringAPartirDisto(valorCompleto, "[]");
                                            String nomeLista = UtilSBCoreStringBuscaTrecho.getStringAteEncontrarIsto(valorCompleto, "[]");
                                            String chaveValorNovo = chaveLista + "[" + linha + restante;
                                            try {
                                                Map<String, String> valoresDaLista = getMapa().getValoresListas(nomeLista);

                                                String novoValor = valoresDaLista.get(chaveValorNovo);
                                                texto.setValue(novoValor);
                                            } catch (Throwable t) {
                                                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha substituindo " + chaveValorNovo, t);
                                            }
                                        }
                                    }

                                }

                                System.out.println(campoLinha.getClass().getSimpleName());
                                System.out.println(campoLinha.toString());
                            }

                            tabela.getContent().add(copia);
                        }
                    }
                }

                tabela.getContent().remove(getTextoSubstituivel().getObjEmTabela().getLinha());

                break;

            default:
                throw new AssertionError(getTextoSubstituivel().getTipoTExto().name());

        }
    }

    private P createInlineImageToParagraph(Inline inline) {
        // Now add the in-line image to a paragraph
        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();
        PPrBase.TextAlignment ta = new PPrBase.TextAlignment();
        PPr propriedadesParagrafo = factory.createPPr();
        ta.setVal("center");
        propriedadesParagrafo.setTextAlignment(ta);
        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        propriedadesParagrafo.setJc(justification);
        R run = factory.createR();
        paragraph.setPPr(propriedadesParagrafo);

        paragraph.getContent().add(run);

        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
    }

    private Inline createInlineImage(File file, WordprocessingMLPackage wordMLPackage) throws Exception {

        byte[] bytes = extractBytes(file.getAbsolutePath());

        BinaryPartAbstractImage imagePart
                = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);

        int docPrId = 1;
        int cNvPrId = 2;

        return imagePart.createImageInline("Filename hint",
                "Alternative text", docPrId, cNvPrId, false);
    }

}
