/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreOutputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringBuscaTrecho;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringNomeArquivosEDiretorios;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringVariaveisEmTexto;

import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicaoArquivo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.ContentAccessor;
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
import org.jvnet.jaxb2_commons.ppp.Child;

/**
 *
 * @author salvioF
 */
public class MapaSubstituicaoOffice extends MapaSubstituicaoArquivo {

    private enum METODO_SUBISTITUICAO {
        MANUAL, CAMPOS_FIELD
    }

    private METODO_SUBISTITUICAO metodoEscolido = METODO_SUBISTITUICAO.MANUAL;

    public MapaSubstituicaoOffice(File pArquivo) {
        super(pArquivo);
    }

    public MapaSubstituicaoOffice(String pCaminho) {
        super(pCaminho);
    }

    private static List getTodosElementosDesteTipoNoObjeto(Object obj, Class toSearch) {
        List result = new ArrayList();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement) obj).getValue();
        }

        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getTodosElementosDesteTipoNoObjeto(child, toSearch));
            }

        }
        return result;
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

    /**
     * public boolean substituirImagensEmArquivo(String caminhoImagem) {
     *
     * WordprocessingMLPackage mlp; try { mlp =
     * WordprocessingMLPackage.load(arquivo); ContentAccessor acessoAoConteudo =
     * mlp.getMainDocumentPart();
     *
     * for (Object obj : acessoAoConteudo.getContent()) {
     *
     * if (obj instanceof Tbl) { Tbl table = (Tbl) obj; List rows =
     * getTodosElementosDesteTipoNoObjeto(table, Tr.class); for (Object trObj :
     * rows) { Tr tr = (Tr) trObj; List cols =
     * getTodosElementosDesteTipoNoObjeto(tr, Tc.class); for (Object tcObj :
     * cols) { Tc tc = (Tc) tcObj; List texts =
     * getTodosElementosDesteTipoNoObjeto(tc, Text.class); for (Object textObj :
     * texts) { Text text = (Text) textObj; if
     * (text.getValue().contains("[logoCliente]")) { File file = new
     * File(caminhoImagem); P paragraphWithImage =
     * createInlineImageToParagraph(createInlineImage(file, mlp));
     * tc.getContent().remove(0);
     *
     * tc.getContent().add(paragraphWithImage); } } System.out.println("here");
     * } } System.out.println("here"); } } mlp.save(arquivo); return true; }
     * catch (Throwable t) {
     * Logger.getLogger(MapaSubstituicaoOffice.class.getName()).log(Level.SEVERE,
     * null, t); return false; } }
     */
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

    private void aplicarVariaveisEmParametros() {

        for (String chave : mapaSubstituicao.keySet()) {
            String valor = mapaSubstituicao.get(chave);

            List<String> variaveisEncontradas = UtilSBCoreStringVariaveisEmTexto.extrairVariaveisEntreColchete(valor);
            if (!variaveisEncontradas.isEmpty()) {
                for (String variavel : variaveisEncontradas) {
                    if (mapaSubstituicao.containsKey(variavel)) {
                        valor = valor.replace(variavel, mapaSubstituicao.get(variavel));
                    }
                }
                mapaSubstituicao.put(chave, valor);
            }

        }

        for (String chaveprincipal : mapaSubstituicaoListas.keySet()) {

            Map<String, String> mapaSubVariaveis = mapaSubstituicaoListas.get(chaveprincipal);
            for (String chave : mapaSubVariaveis.keySet()) {
                String valor = mapaSubVariaveis.get(chave);
                List<String> variaveisEncontradas = UtilSBCoreStringVariaveisEmTexto.extrairVariaveisEntreColchete(valor);
                if (!variaveisEncontradas.isEmpty()) {
                    for (String variavel : variaveisEncontradas) {
                        if (mapaSubstituicao.containsKey(variavel)) {
                            valor = valor.replace(variavel, mapaSubstituicao.get(variavel));
                        }
                    }
                    mapaSubstituicaoListas.get(chaveprincipal).put(chave, valor);
                }
            }

        }

    }

    @Override
    public void substituirEmArquivo() {
        aplicarVariaveisEmParametros();
        switch (tipoArquivo) {
            case IMAGEM_WEB:
                super.substituirEmArquivo();
            case VIDEO:
                break;
            case DOCUMENTO_WORD_XDOC2007:
                switch (metodoEscolido) {
                    case MANUAL:
                        substituirOfficeModoManual();
                        break;
                    case CAMPOS_FIELD:
                        break;
                    default:
                        throw new AssertionError(metodoEscolido.name());

                }
                break;
            case DOCUMENTO_PDF:
                super.substituirEmArquivo();
            case ARQUIVO_TEXTO_SIMPLES:
                super.substituirEmArquivo();
            case IMAGE_REPRESENTATIVA_ENTIDADE_PEQUENO:
                break;
            case IMAGE_REPRESENTATIVA_ENTIDADE_MEDIO:
                break;
            case IMAGE_REPRESENTATIVA_ENTIDADE_GRANDE:
                break;
            default:
                throw new AssertionError(tipoArquivo.name());

        }

    }

    private void substituirOfficeModoField() {
        //mlp.getMainDocumentPart().variableReplace;
    }

    private boolean isConteudoEncontrado(String pParte) {
        for (String chave : getMapaSubstituicao().keySet()) {
            if (pParte.contains(chave)) {
                return true;
            }
        }
        return false;

    }

    private boolean isCampoListaEncontrador(String parte) {
        return parte.contains("[].");

    }

    private boolean isImagemEncontrada(String pParte) {
        for (String chave : mapaSubstituicaoImagem.keySet()) {
            if (chave.equalsIgnoreCase(pParte)) {
                System.out.println("Imagem Encontrada:" + pParte);
                return true;
            }
        }

        return false;
    }

    private List<TextoOfficeSubstituivel> listaTextosSubstituiveis(WordprocessingMLPackage pArquivoWord) {
        ContentAccessor acessoAoConteudo = pArquivoWord.getMainDocumentPart();

        try {
            List<Text> lista = obterTodosObjetosDoTIpoTexto(acessoAoConteudo, pArquivoWord, new ArrayList<>());

            List<TextoOfficeSubstituivel> listaTexto = new ArrayList<>();
            for (Text texto : lista) {
                String conteudoTexto = texto.getValue();
                if (isConteudoEncontrado(conteudoTexto)) {
                    listaTexto.add(new TextoOfficeSubstituivel(texto, FabTipoTextoOfficeSubstituivel.SIMPLES));
                }
                if (isImagemEncontrada(conteudoTexto)) {
                    listaTexto.add(new TextoOfficeSubstituivel(texto, FabTipoTextoOfficeSubstituivel.IMAGEM));
                }
                if (isCampoListaEncontrador(conteudoTexto)) {

                    listaTexto.add(new TextoOfficeSubstituivel(texto, FabTipoTextoOfficeSubstituivel.LISTAGEM));
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

    private List<Integer> gerarLinhasSubLista(Map<String, String> valores) {
        List<Integer> resposta = new ArrayList<>();
        if (valores != null) {
            for (String valorChave : valores.keySet()) {
                String numeroLinha = UtilSBCoreStringFiltros.getNumericosDaString(valorChave);
                if (numeroLinha != null && !numeroLinha.isEmpty()) {
                    if (!resposta.contains(Integer.parseInt(numeroLinha))) {
                        resposta.add(Integer.parseInt(numeroLinha));
                    }
                }
            }
        }
        return resposta;

    }

    private Text getTextoDoElemento(JAXBElement elemento) {
        Object valor = elemento.getValue();
        if (valor instanceof Text) {
            return (Text) valor;
        }
        return null;
    }

    private Text getTextoDaColuna(Tc pColuna) {
        //desafio: Este código pode ser melhorado utilizando padrão de autoreferencias, atingindo todos os níveis de pesquisa existente na coluna  forka isso! www.coletivojava.com.br
        for (Object filho : pColuna.getContent()) {
            if (filho instanceof ContentAccessor) {
                for (Object filhoLevel1 : ((ContentAccessor) filho).getContent()) {
                    if (filhoLevel1 instanceof JAXBElement) {
                        if (getTextoDoElemento((JAXBElement) filhoLevel1) != null) {
                            return getTextoDoElemento((JAXBElement) filhoLevel1);
                        }
                    }
                    if (filhoLevel1 instanceof ContentAccessor) {
                        for (Object filhoLevel2 : ((ContentAccessor) filhoLevel1).getContent()) {
                            if (filhoLevel2 instanceof JAXBElement) {
                                if (filhoLevel2 instanceof JAXBElement) {
                                    if (getTextoDoElemento((JAXBElement) filhoLevel2) != null) {
                                        return getTextoDoElemento((JAXBElement) filhoLevel2);
                                    }
                                }
                            }
                            if (filhoLevel2 instanceof ContentAccessor) {
                                for (Object filhoLevel3 : ((ContentAccessor) filhoLevel2).getContent()) {
                                    if (filhoLevel3 instanceof JAXBElement) {
                                        if (filhoLevel3 instanceof JAXBElement) {
                                            if (getTextoDoElemento((JAXBElement) filhoLevel3) != null) {
                                                return getTextoDoElemento((JAXBElement) filhoLevel3);
                                            }
                                        }
                                    }
                                    if (filhoLevel3 instanceof ContentAccessor) {
                                        for (Object filhoLevel4 : ((ContentAccessor) filhoLevel3).getContent()) {
                                            if (filhoLevel4 instanceof JAXBElement) {
                                                if (filhoLevel4 instanceof JAXBElement) {
                                                    if (getTextoDoElemento((JAXBElement) filhoLevel4) != null) {
                                                        return getTextoDoElemento((JAXBElement) filhoLevel4);
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

    private void substituirOfficeModoManual() {

        WordprocessingMLPackage mlp = null;

        //  List<TextoOfficeSubstituivel> textoSubstituivel = getTodosElementosDesteTipoNoObjeto(mlp, Tex)
        try {
            System.out.println("Processando" + arquivo.getAbsolutePath());
            mlp = WordprocessingMLPackage.load(arquivo);

            List<TextoOfficeSubstituivel> textosSubstituiveis = listaTextosSubstituiveis(mlp);
            List<String> controleListasSubstituidas = new ArrayList<>();
            for (TextoOfficeSubstituivel textoSub : textosSubstituiveis) {
                try {
                    Text textoDocumentoWord = textoSub.getTextoOrigem();
                    switch (textoSub.getTipoTExto()) {
                        case SIMPLES:
                            textoDocumentoWord.setSpace("preserve"); // needed?
                            textoDocumentoWord.setValue(substituirEmString(textoSub.getTexto()));
                            break;
                        case IMAGEM:
                            Tc coluna = textoSub.getCooluna();
                            String caminhoArquivo = mapaSubstituicaoImagem.get(textoSub.getTexto());
                            P paragraphWithImage = null;
                            if (caminhoArquivo.startsWith("http")) {
                                String imagemTempLocal = SBCore.getServicoSessao().getSessaoAtual().getPastaTempDeSessao() + "/" + UtilSBCoreStringNomeArquivosEDiretorios.getNomeArquivo(caminhoArquivo + ".jpg");
                                UtilSBCoreOutputs.salvarArquivoInput(UTilSBCoreInputs.getStreamBuffredByURL(caminhoArquivo), imagemTempLocal);
                                paragraphWithImage = createInlineImageToParagraph(createInlineImage(new File(imagemTempLocal), mlp));
                            } else {
                                coluna.getContent().removeAll(coluna.getContent());
                                paragraphWithImage = createInlineImageToParagraph(createInlineImage(new File(caminhoArquivo), mlp));
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

                            Tbl tabela = textoSub.getTabela();

                            String chaveLista = getChaveListas(textoSub.getTexto());

                            Map<String, String> valores = mapaSubstituicaoListas.get(chaveLista);
                            //List<Integer> lista = gerarLinhasSubLista(valores);
                            Map<Integer, List<String>> estruturaTabela = ordemMapaSubstituicaoListas.get(chaveLista);
                            if (estruturaTabela != null) {

                                List<Integer> listaOrdenada = Lists.newArrayList(estruturaTabela.keySet());
                                Collections.sort(listaOrdenada);
                                if (!controleListasSubstituidas.contains(chaveLista)) {
                                    for (Integer linha : listaOrdenada) {

                                        controleListasSubstituidas.add(chaveLista);

                                        Tr copia = (Tr) XmlUtils.deepCopy(textoSub.getLinha());
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

                                                        String chaveValorNovo = chaveLista + "[" + linha + restante;
                                                        String novoValor = mapaSubstituicaoListas.get(chaveLista).get(chaveValorNovo);
                                                        texto.setValue(novoValor);
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
                            /**
                             * if
                             * (!controleListasSubstituidas.contains(chaveLista))
                             * { controleListasSubstituidas.add(chaveLista); for
                             * (int linha : lista) { Tr copia = (Tr)
                             * XmlUtils.deepCopy(textoSub.getLinha());
                             *
                             * for (Object campoLinha : copia.getContent()) { if
                             * (campoLinha instanceof JAXBElement) { JAXBElement
                             * elemento = (JAXBElement) campoLinha;
                             *
                             * Object valorElemento = elemento.getValue(); if
                             * (valorElemento instanceof Tc) {
                             * //valores.get(colunaDaLinha);
                             *
                             * Tc col = (Tc) valorElemento;
                             *
                             * Text texto = getTextoDaColuna(col); if (texto !=
                             * null) { texto.setSpace("preserve");
                             * System.out.println("Valor Antigo=" +
                             * texto.getValue()); String valorCompleto =
                             * texto.getValue(); String restante =
                             * UtilSBCoreStrings.getStringAPartirDisto(valorCompleto,
                             * "[]");
                             *
                             * String chaveValorNovo = chaveLista + "[" + linha
                             * + restante; String novoValor =
                             * mapaSubstituicaoListas.get(chaveLista).get(chaveValorNovo);
                             *
                             * texto.setValue(novoValor); } }
                             *
                             * }
                             *
                             * System.out.println(campoLinha.getClass().getSimpleName());
                             * System.out.println(campoLinha.toString()); }
                             *
                             * tabela.getContent().add(copia);
                             *
                             * }
                             * }
                             */
                            tabela.getContent().remove(textoSub.getLinha());

                            break;

                        default:
                            throw new AssertionError(textoSub.getTipoTExto().name());

                    }
                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro substituindo palavra chave", t);
                    SBCore.getCentralDeMensagens().enviarMsgAlertaAoUsuario("Erro aplicando palavra chave em documento" + textoSub.getTexto());
                }
            }

            // substituirTexto(acessoAoConteudo, mlp, null);
            mlp.save(arquivo);

        } catch (Docx4JException ex) {
            Logger.getLogger(MapaSubstituicaoOffice.class
                    .getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.PARA_TUDO, "Falha processando " + Docx4JException.class.getSimpleName() + " " + arquivo.getAbsolutePath(), ex);

        } catch (Exception ex) {
            Logger.getLogger(MapaSubstituicaoOffice.class
                    .getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.PARA_TUDO, "Ecessão processando " + arquivo.getAbsolutePath(), ex);
        }

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

    private void substituirTexto(ContentAccessor c, WordprocessingMLPackage mlp, Tc ultimaColuna)
            throws Exception {
        Map<String, Tr> tabelasComListaDeRegistro = new HashMap<>();
        for (Object p : c.getContent()) {

            if (p instanceof ContentAccessor) {

                substituirTexto((ContentAccessor) p, mlp, ultimaColuna);
            } else if (p instanceof JAXBElement) {
                Object obJetoEmelementoXML = ((JAXBElement) p).getValue();

                if (obJetoEmelementoXML instanceof Tc) {
                    ultimaColuna = (Tc) obJetoEmelementoXML;
                }
                if (obJetoEmelementoXML instanceof Tc) {
                    ultimaColuna = (Tc) obJetoEmelementoXML;
                }

                if (obJetoEmelementoXML instanceof ContentAccessor) {
                    substituirTexto((ContentAccessor) obJetoEmelementoXML, mlp, ultimaColuna);
                } else if (obJetoEmelementoXML instanceof Text) {
                    org.docx4j.wml.Text textoDocumentoWord = (Text) obJetoEmelementoXML;
                    String texto = textoDocumentoWord.getValue();
                    System.out.println("Texto:" + texto);
                    if (texto != null) {
                        if (isConteudoEncontrado(texto)) {
                            textoDocumentoWord.setSpace("preserve"); // needed?
                            textoDocumentoWord.setValue(substituirEmString(texto));
                        }
                        if (isImagemEncontrada(texto)) {
                            if (ultimaColuna == null) {
                                System.out.println("Nenhuma coluna parece ter sido associada a imagem");
                            } else {
                                P paragraphWithImage = createInlineImageToParagraph(createInlineImage(new File(mapaSubstituicaoImagem.get(texto)), mlp));
                                ultimaColuna.getContent().removeAll(ultimaColuna.getContent());
                                ultimaColuna.getContent().add(paragraphWithImage);
                            }

                        }
                        if (isCampoListaEncontrador(texto)) {
                            System.out.println("Encontrou");

                            if (ultimaColuna == null) {
                                System.out.println("Nenhuma coluna parece ter sido associada a Lista de itens");
                            } else {

                                Tr linhaPai = (Tr) ultimaColuna.getParent();
                                tabelasComListaDeRegistro.put(linhaPai.toString(), linhaPai);

                                //        ultimaColuna.getContent().removeAll(ultimaColuna.getContent());
                            }

                        }
                    }
                }
            }
        }

    }

    private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
        Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
        List<?> textElements = getTodosElementosDesteTipoNoObjeto(workingRow, Text.class
        );
        for (Object object : textElements) {
            Text text = (Text) object;
            String replacementValue = (String) replacements.get(text.getValue());
            if (replacementValue != null) {
                text.setValue(replacementValue);
            }
        }

        reviewtable.getContent().add(workingRow);
    }

    private static Object getPaiDoElementoDesteTipoNoObjeto(org.jvnet.jaxb2_commons.ppp.Child itemDoDocumento, Class tipoProcurado) {
        List result = new ArrayList();
        boolean encontrou = false;
        while (!encontrou) {
            try {
                if (itemDoDocumento instanceof TipoSubistituicao) {
                    return itemDoDocumento;
                } else {
                    itemDoDocumento = (Child) ((JAXBElement) itemDoDocumento.getParent()).getValue();

                    System.out.println("ITem tipo" + itemDoDocumento.getClass().getSimpleName());

                }
            } catch (Throwable t) {
                return null;
            }

        }

        return result;
    }

}
