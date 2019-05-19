/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package org.superBits.utilitario.editorArquivos.importacao;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import com.super_bits.modulosSB.SBCore.modulos.regex.FabProcessadorRegexConhecidos;
import com.super_bits.modulosSB.SBCore.modulos.regex.ProcessadorRegexTexto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author desenvolvedor
 */
public class TesteEcomerce {

    public static String getPeso(String valor) {
        try {
            //      Precision
            return trasformaGramaEmKiloGrama(FabProcessadorRegexConhecidos.LOCALIZAR_PESO_EM_TEXTO.getRegistro().getResultado(valor));

        } catch (Throwable t) {
            System.out.println("Erro obtendo peso:" + t.getMessage());
            return "";
        }

    }

    public static List<String> getDimencaoConvertendoEmCM(String pTexto) {
        try {
            List<String> lista = FabProcessadorRegexConhecidos.LOCALIZAR_DIMENCOES_EM_TEXTO.getRegistro().getResultados(pTexto);

            if (!lista.isEmpty()) {

                if (lista.size() > 2) {
                    System.out.println("maior que 2");
                }
                boolean maiorQue54 = false;
                List<Float> valores = new ArrayList<>();

                for (String vlSTR : lista) {
                    Float valor = Float.valueOf(vlSTR.replace(",", "."));
                    if (valor > 54) {
                        maiorQue54 = true;
                    }
                    valores.add(valor);
                }

                lista.clear();
                for (Float vrFl : valores) {
                    if (maiorQue54) {
                        Float novo = vrFl / 10F;
                        lista.add(novo.toString());
                    } else {
                        lista.add(vrFl.toString());
                    }
                }
            }
            return lista;
        } catch (Throwable t) {
            System.out.println("Erro convertendo dimenções");
            return new ArrayList<>();
        }

    }

    public static String getAltura(String pTexto) {
        List<String> dimencoes = getDimencaoConvertendoEmCM(pTexto);
        if (!dimencoes.isEmpty() && dimencoes.size() >= 1) {
            return dimencoes.get(0);
        } else {
            return FabProcessadorRegexConhecidos.ALTURA_DOIS_PONTOS_VALOR.getRegistro().getResultado(pTexto);
        }

    }

    public static String getLargura(String pTexto) {
        List<String> dimencoes = getDimencaoConvertendoEmCM(pTexto);
        if (!dimencoes.isEmpty() && dimencoes.size() >= 2) {
            return dimencoes.get(1);
        } else {
            return FabProcessadorRegexConhecidos.LARGURA_DOIS_PONTOS_VALOR.getRegistro().getResultado(pTexto);
        }

    }

    public static String getProfundidade(String pTexto) {
        List<String> dimencoes = getDimencaoConvertendoEmCM(pTexto);
        if (!dimencoes.isEmpty() && dimencoes.size() >= 3) {
            return dimencoes.get(2);
        } else {
            return FabProcessadorRegexConhecidos.PROFUNDIDADE_DOIS_PONTOS_VALOR.getRegistro().getResultado(pTexto);
        }
    }

    public static String trasformaGramaEmKiloGrama(String valor) {

        try {
            Float kilo = Float.valueOf(valor.replace(",", "."));
            Float novoValor = kilo;
            if (kilo > 2) {
                novoValor = kilo / 1000;
            }
            return novoValor.toString();

        } catch (Throwable t) {
            System.out.println("Erro convertendo em KG" + t.getMessage());
            return "";
        }

    }

    public static void modificar(Workbook planilha) {
        int maxColumnNum = 0;
        List<String> linhasDoArquivo = new ArrayList<>();
        String linhasColunaReferencia = "";
        String linhasColunaNome = "";
        String linhasColunaSlug = "";
        String linhasColunaDescricao = "";
        String linhasColunaResumo = "";
        String linhasColunaPreco = "";
        String linhasPrecoPromo = "";
        String linhasPeso = "";
        String linhasPrecoQuantidade = "";
        String linhasPrecoAltura = "";
        String linhasPrecoLargura = "";
        String linhasPrecoProfundidade = "";
        String linhasBloquearEstoque = "";

        for (Sheet aba : planilha.getSheets()) {

            try {

                System.out.println("Lendo Aba" + aba.getName());
                int quantidadeLinhas = aba.getRows();
                for (int i = 3; i < quantidadeLinhas; i++) {
                    String valor = "";
                    Cell codigoReferencia = aba.getCell(0, i);
                    //valor += ref.getContents();
                    Cell nome = aba.getCell(1, i);
                    //valor += nome.getContents();
                    Cell urlSlug = aba.getCell(2, i);
                    // valor += urlSlug.getContents();
                    Cell descricao = aba.getCell(3, i);
                    valor += descricao.getContents();
                    Cell resumo = aba.getCell(4, i);
                    Cell preco = aba.getCell(5, i);
                    Cell precoPromo = aba.getCell(6, i);
                    Cell peso = aba.getCell(7, i);
                    Cell quantidade = aba.getCell(8, i);
                    Cell altura = aba.getCell(9, i);
                    Cell largura = aba.getCell(10, i);
                    Cell profundidade = aba.getCell(11, i);
                    //  Cell venderSomenteComEstoque = aba.getCell(12, i);
                    String textoDescritivo = descricao.getContents().replace(";", "[pontovirgula]");
                    String textoResumo = resumo.getContents().replace(";", "[pontovirgula]");
                    System.out.println(valor);

                    String slugString = UtilSBCoreStringFiltros.gerarUrlAmigavel(nome.getContents());
                    System.out.println("SLUG----->" + slugString);
                    String pesoStr = getPeso(textoDescritivo).replace(",", ".");
                    System.out.println("PESO----->" + pesoStr);
                    String alturaStr = getAltura(textoDescritivo).replace(",", ".");
                    System.out.println("ALTURA----->" + alturaStr);
                    String larguraStr = getLargura(textoDescritivo).replace(",", ".");;
                    System.out.println("LARGURA----->" + larguraStr);
                    String profundidadeStr = getProfundidade(textoDescritivo).replace(",", ".");
                    System.out.println("PROFUNDIDADE----->" + profundidadeStr);

                    //
                    String lnCodigoReferencia = codigoReferencia.getContents() + ";";
                    linhasColunaReferencia += lnCodigoReferencia.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnNome = nome.getContents().replace(";", "[pontovirgula]") + ";";
                    linhasColunaNome += lnNome.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnSlug = slugString + ";";
                    linhasColunaSlug += lnSlug.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnDescritivo = textoDescritivo + ";";
                    linhasColunaDescricao += lnDescritivo.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnResumo = textoResumo + ";";
                    linhasColunaResumo += lnResumo.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnPreco = preco.getContents().replace(",", ".") + ";";
                    linhasColunaPreco += lnPreco.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnPrecoPromo = precoPromo.getContents().replace(",", ".") + ";";
                    linhasPrecoPromo += lnPrecoPromo.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnPeso = pesoStr.replace(",", ".") + ";";
                    linhasPeso += lnPeso.replaceAll("(\r\n|\n)", "<br />") + "\n";

                    //
                    String lnQuantidade = quantidade.getContents() + ";";
                    linhasPrecoQuantidade += lnQuantidade.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnAltura = alturaStr + ";";
                    linhasPrecoAltura += lnAltura.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnLargura = larguraStr + ";";
                    linhasPrecoLargura += lnLargura.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnProfundidade = profundidadeStr + ";";
                    linhasPrecoProfundidade += lnProfundidade.replaceAll("(\r\n|\n)", "<br />") + "\n";
                    //
                    String lnEstoqueLiberado = "1;";
                    linhasBloquearEstoque += lnEstoqueLiberado + "\n";

                    String linhaCSV
                            = lnCodigoReferencia
                            + lnNome
                            + lnSlug
                            + lnDescritivo
                            + lnResumo
                            + lnPreco
                            + lnPrecoPromo
                            + lnPeso
                            + lnQuantidade
                            + lnAltura
                            + lnLargura
                            + lnProfundidade
                            + lnEstoqueLiberado;
                    linhasDoArquivo.add(linhaCSV.replaceAll("(\r\n|\n)", "<br />") + "\n");

                }
            } catch (Throwable t) {
                System.out.println("ERRROOOO" + t.getMessage());
            }

        }
        String linhaCompĺeta = "";
        for (String linha : linhasDoArquivo) {
            linhaCompĺeta += linha;
        }
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/arqXtecEloImprts.csv", linhaCompĺeta);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/01referencia.csv", linhasColunaReferencia);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/02nome.csv", linhasColunaNome);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/03slug.csv", linhasColunaSlug);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/04descritivo.csv", linhasColunaDescricao);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/05Resumo.csv", linhasColunaResumo);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/06Preco.csv", linhasColunaPreco);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/07PrecoPromo.csv", linhasPrecoPromo);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/08Peso.csv", linhasPeso);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/09Quantidade.csv", linhasPrecoQuantidade);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/10Altura.csv", linhasPrecoAltura);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/11Largura.csv", linhasPrecoLargura);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/12Profundidade.csv", linhasPrecoProfundidade);
        UtilSBCoreArquivoTexto.escreverEmArquivoSubstituindoArqAnterior("/home/superBits/dados/partearquivo/13EstoqueLiberado.csv", linhasBloquearEstoque);

    }

    public static String getRegexDoMomento(String pParametro) {
        try {

            ProcessadorRegexTexto teste = FabProcessadorRegexConhecidos.LOCALIZAR_DIMENCOES_EM_TEXTO.getRegistro();
            return teste.getResultados(pParametro).get(0);
        } catch (Throwable t) {
            System.out.println("ERRO REGEX" + t.getMessage());
            System.out.println("ERRO REGEX" + t.getCause());
            System.out.println("ERRO REGEX" + t.getLocalizedMessage());
            return null;
        }

    }

    public void testesRegex() {

        List<String> resultado = FabProcessadorRegexConhecidos.LOCALIZAR_DIMENCOES_EM_TEXTO.getRegistro().getResultados(
                "Descricao completa do produtoCor: mel\n"
                + "Acabamento: polido\n"
                + "Dimensões da caixa F 26 (P) X 101 (L) X 245 (A)  mm\n"
                + "Peso da mochila: 300 g.\n"
                + "Modelo: barroco\n"
                + "\n"
                + "Dimensões da embalagem: A 185 x L 100 x P 40 mm. \n"
                + "Comprimento do colar: 30 - 33cm\n"
                + "Peso da mochila:300 g.\n"
                + "Tamanho das contas:20,5 - 0,8cm\n"
                + "Peso da mochila:300g\n"
                + "Peso aproximado:5,5 gr\n"
                + "Peso da mochila:3 00 g\n"
                + "Composição\n"
                + "\n"
                + "100% âmbar báltico certificado.\n"
                + "Fio 100% algodão.\n"
                + "Fecho rosca em resina plástica.\n"
                + "\n"
                + "Feito a mão\n"
                + "\n"
                + " \n"
                + "\n"
                + "Contra indicado para uso em piscinas com cloro.Produto importado dos EUA \n"
                + "Produzido nos EUA \n"
                + "Embalagem: Caixa \n"
                + "Dimensões da embalagem: A 125 x L 70 x P 40 mm. \n"
                + "Conteúdo da embalagem: 60ml que rende de 24 a 48 doses\n"
                + "Pode ser usada a partir de 14 dias de vida.\n"
                + "Inspecionados pelo FDA (Food and Drug Adminitration)"
        );

        System.out.println("ATENCAOOOO " + getPeso("Peso da mochila: 300 g."));
        Assert.assertNotNull("Regex em inconformidade Peso da mochila: 300 g", getPeso("Peso da mochila: 300 g."));
        Assert.assertNotNull("Regex em inconformidade Medidas da mochila: Medidas da mochila: 29,2 x 25,4 cm  (A-L);   ", getRegexDoMomento("Medidas da mochila: 29,2 x 25,4 cm  (A-L); "));
        Assert.assertNotNull("Regex em inconformidade askdfhaksf 17,8 x 12,5 x 9 cm. ", getRegexDoMomento("Dimensões (AxLxC): 17,8 x 12,5 x 9 cm. "));
        Assert.assertNotNull("Regex em inconformidade Dimensões da caixa F 26 (P) X 101 (L) X 245 (A)  mm ", getRegexDoMomento("Dimensões da caixa F 26 (P) X 101 (L) X 245 (A)  mm"));
        Assert.assertNotNull("Regex em inconformidade ", getPeso("Largura: 12,00 cm  Altura: 16,00 cm Profundidade: 9,00 cm"));
        Assert.assertNotNull("Regex em inconformidade ", getPeso("Dimensões aprox. da embalagem - AxLxP: 5x6x14  Dimensões aprox. do produto - AxLxP: 12x6x4"));
        Assert.assertNotNull("Regex em inconformidade ", getPeso("Dimensões aprox. do produto - AxLxP: 12x6x4"));

    }

    @Test
    public void teste() {
        try {
            Workbook planilha;
            String origem = "/home/superBits/eloImportsTeste..xls";
            String gerado = "/home/superBits/eloImportsGerado.xlsx";
            WorkbookSettings ws = new WorkbookSettings();
            if (new File(origem).exists()) {
                System.out.println("EXISTE");
            } else {
                System.out.println("NAO EXISTE");
            }
            ws.setEncoding("iso-8859-1");

            //SBCore.getCentralComunicacao().iniciarComunicacaoUsuairo_paraGrupo(FabTipoComunicacao.NOTIFICAR, pRemetente, pDestinatario, origem,)
            planilha = Workbook.getWorkbook(new File(origem), ws);
            UtilSBCoreArquivos.copiarArquivos(origem, gerado);

            WorkbookSettings wsGerada = new WorkbookSettings();
            wsGerada.setEncoding("iso-8859-1");
            Workbook planilhaGerada = Workbook.getWorkbook(new File(origem), wsGerada);
            modificar(planilhaGerada);

        } catch (IOException ex) {
            Logger.getLogger(TesteEcomerce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TesteEcomerce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
