/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package org.superBits.utilitario.editorArquivos.importacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.TIPO_PRIMITIVO;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellType;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class ImportacaoExcel<T> implements Serializable {

    private Workbook planilha;

    private final Class classeImportacao;

    private final Map<String, Integer> mapeamentoColunasPorNomeDoCampo;
    private List<T> registrosSucesso;
    private List<T> registrosErro;
    private final String caminhoArquivo;
    private List<String> listaDeErros;
    private String mensagemErro;

    private Map<String, ProcessadorCampoPLanilhaLogicaPersonalizada> mapaprocessadoresPersonalizados = new HashMap<>();

    public ImportacaoExcel(String pCaminhoArquivo, Map<String, Integer> mapa, Class classe) {

        carregarArquivo(pCaminhoArquivo);
        classeImportacao = classe;
        mapeamentoColunasPorNomeDoCampo = mapa;
        caminhoArquivo = pCaminhoArquivo;

    }

    public final void carregarArquivo(String pCaminhoArquivo) {
        try {
            WorkbookSettings ws = new WorkbookSettings();
            //   ws.setEncoding("iso-8859-1");

            planilha = Workbook.getWorkbook(new File(pCaminhoArquivo), ws);
        } catch (BiffException ex) {
            SBCore.RelatarErro(FabErro.LANCAR_EXCECÃO, "Erro Tentando carregar planilha", ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportacaoExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPlanilhaCarregada() {
        return planilha != null;
    }

    public void adicionarProcessadorPersonalizado(ProcessadorCampoPLanilhaLogicaPersonalizada pProcessador) {
        mapaprocessadoresPersonalizados.put(pProcessador.getNomeColuna(), pProcessador);
    }

    public void processar() {

        registrosSucesso = new ArrayList<>();

        registrosErro = new ArrayList<>();

        listaDeErros = new ArrayList<>();

        for (Sheet aba : planilha.getSheets()) {

            try {

                System.out.println("Lendo Aba" + aba.getName());

                for (int i = 0; i < aba.getRows(); i++) {

                    boolean deuErro = false;

                    ItfBeanSimples novoRegistro = (ItfBeanSimples) classeImportacao.newInstance();

                    for (String nomeCampo : mapeamentoColunasPorNomeDoCampo.keySet()) {

                        int coluna = mapeamentoColunasPorNomeDoCampo.get(nomeCampo) - 1;

                        TIPO_PRIMITIVO tipoPrimitivo = novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).getTipoPrimitivoDoValor();

                        Cell celulaExecelValorCampo = aba.getCell(coluna, i);

                        try {
                            if (mapaprocessadoresPersonalizados.containsKey(nomeCampo)) {
                                ProcessadorCampoPLanilhaLogicaPersonalizada processador = mapaprocessadoresPersonalizados.get(nomeCampo);
                                novoRegistro.getCPinst(nomeCampo).setValor(processador.processarValor(celulaExecelValorCampo));
                            } else {
                                if (celulaExecelValorCampo != null) {

                                    switch (tipoPrimitivo) {

                                        case INTEIRO:

                                            if (!celulaExecelValorCampo.getContents().equals("")) {

                                                int inteiroRecebido = Integer.parseInt(celulaExecelValorCampo.getContents());

                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(inteiroRecebido);

                                            } else {

                                                deuErro = true;
                                                mensagemErro = "\nNão foi possivel setar o valor do campo: " + nomeCampo + "\n" + "\nTipo do campo: " + tipoPrimitivo + "\n" + "\nValor recebido: " + celulaExecelValorCampo.getContents() + "\n" + "\nPOSIÇÃO NO XML" + "\n" + "\nLinha: " + (i + 1) + "\n" + "\nColuna: " + (coluna + 1) + "\n" + "\nCausa provavel: CAMPO EM BRANCO\n";
                                                listaDeErros.add(mensagemErro);
                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(0);

                                            }

                                            break;

                                        case LETRAS:

                                            novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(celulaExecelValorCampo.getContents());

                                            break;

                                        case DATAS:

                                            if (!celulaExecelValorCampo.getContents().equals("")) {

                                                String dataRecebida = celulaExecelValorCampo.getContents();

                                                Date dataFormatada = UtilSBCoreDataHora.converteStringDD_MM_YYYYEmData(dataRecebida);

                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(dataFormatada);

                                            } else {

                                                deuErro = true;
                                                mensagemErro = "\nNão foi possivel setar o valor do campo: " + nomeCampo + "\n" + "\nTipo do campo: " + tipoPrimitivo + "\n" + "\nValor recebido: " + celulaExecelValorCampo.getContents() + "\n" + "\nPOSIÇÃO NO XML" + "\n" + "\nLinha: " + (i + 1) + "\n" + "\nColuna: " + (coluna + 1) + "\n" + "\nCausa provavel: CAMPO EM BRANCO\n";
                                                listaDeErros.add(mensagemErro);
                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(null);

                                            }

                                            break;

                                        case BOOLEAN:

                                            if (!celulaExecelValorCampo.getContents().equals("")) {

                                                boolean booleanRecebido = Boolean.parseBoolean(celulaExecelValorCampo.getContents());

                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(booleanRecebido);

                                            } else {

                                                deuErro = true;
                                                mensagemErro = "\nNão foi possivel setar o valor do campo: " + nomeCampo + "\n" + "\nTipo do campo: " + tipoPrimitivo + "\n" + "\nValor recebido: " + celulaExecelValorCampo.getContents() + "\n" + "\nPOSIÇÃO NO XML" + "\n" + "\nLinha: " + (i + 1) + "\n" + "\nColuna: " + (coluna + 1) + "\n" + "\nCausa provavel: CAMPO EM BRANCO\n";
                                                listaDeErros.add(mensagemErro);
                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(false);

                                            }

                                            break;

                                        case DECIMAL:

                                            if (!celulaExecelValorCampo.getContents().equals("")) {
                                                double doubleRecebido = 0d;
                                                if (celulaExecelValorCampo.getType().equals(CellType.NUMBER_FORMULA)) {
                                                    String valorString = celulaExecelValorCampo.getContents();
                                                    String valorSemFormatacao = valorString;
                                                    if (valorString.contains("R$")) {
                                                        valorSemFormatacao = valorString.substring(valorString.lastIndexOf(" ") + 1, valorString.length()).replace(".", "").replace(",", ".");
                                                    } else {
                                                        valorSemFormatacao = valorString.substring(valorString.lastIndexOf(" ") + 1, valorString.length());
                                                    }
                                                    doubleRecebido = Double.parseDouble(valorSemFormatacao);
                                                } else {
                                                    doubleRecebido = Double.parseDouble(celulaExecelValorCampo.getContents().replace(",", "."));
                                                }
                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(doubleRecebido);

                                            } else {

                                                deuErro = true;
                                                mensagemErro = "\nNão foi possivel setar o valor do campo: " + nomeCampo + "\n" + "\nTipo do campo: " + tipoPrimitivo + "\n" + "\nValor recebido: " + celulaExecelValorCampo.getContents() + "\n" + "\nPOSIÇÃO NO XML" + "\n" + "\nLinha: " + (i + 1) + "\n" + "\nColuna: " + (coluna + 1) + "\n" + "\nCausa provavel: CAMPO EM BRANCO\n";
                                                listaDeErros.add(mensagemErro);
                                                novoRegistro.getCampoByNomeOuAnotacao(nomeCampo).setValor(0.0D);

                                            }

                                        case ENTIDADE:

                                            break;

                                        case OUTROS_OBJETOS:

                                            break;

                                        default:

                                            throw new AssertionError(tipoPrimitivo.name());

                                    }

                                }
                            }
                        } catch (Throwable t) {

                            deuErro = true;
                            mensagemErro = "\nNão foi possivel setar o valor do campo: " + nomeCampo + "\n" + "\nTipo do campo: " + tipoPrimitivo + "\n" + "\nValor recebido: " + celulaExecelValorCampo.getContents() + "\n" + "\nPOSIÇÃO NO XML" + "\n" + "\nLinha: " + (i + 1) + "\n" + "\nColuna: " + (coluna + 1) + "\n" + "\nCausa provavel: TIPO INCORRETO\n";
                            listaDeErros.add(mensagemErro);

                        }

                    }
                    if (deuErro) {

                        registrosErro.add((T) novoRegistro);

                    } else {

                        registrosSucesso.add((T) novoRegistro);

                    }

                }

            } catch (InstantiationException | IllegalAccessException ex) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro ao ler o arquivo", ex);
            }

        }

    }

    public List<T> getRegistrosSucesso() {
        if (registrosSucesso == null) {
            processar();
        }
        return registrosSucesso;
    }

    public List<T> getRegistrosErro() {

        if (registrosErro == null) {
            processar();
        }
        return registrosErro;

    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public List<String> getListaDeErros() {
        return listaDeErros;
    }

    public String getRelatorioImportacao() {

        if (registrosErro == null || registrosSucesso == null) {
            processar();
        }

        String mensagemFinal = "Relatório de importação<br><br>\n\nSucessos: " + registrosSucesso.size() + "\nFalhas: " + registrosErro.size() + "\n\nTotal de registros: " + (registrosErro.size() + registrosSucesso.size()) + "<br><br>\n\n";

        for (int l = 0; l < listaDeErros.size(); l++) {

            mensagemFinal += "Erro n°: " + (l + 1) + "<br><br>\n" + listaDeErros.get(l) + "<br>" + "<br>" + "<div class=\"Separator\"/>" + "\n" + "\n";

        }

        return mensagemFinal;

    }

}
