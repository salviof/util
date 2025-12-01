/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package org.superBits.utilitario.editorArquivos.importacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.TIPO_PRIMITIVO;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.biff.DateRecord;
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
    private final int limiteLinhas;
    private Map<String, ProcessadorCampoPLanilhaLogicaPersonalizada> mapaprocessadoresPersonalizados = new HashMap<>();

    public ImportacaoExcel(String pCaminhoArquivo, Map<String, Integer> mapa, Class classe) {
        this(pCaminhoArquivo, mapa, classe, -1);
    }

    public ImportacaoExcel(String pCaminhoArquivo, Map<String, Integer> mapa, Class classe, int pLimiteLinhas) {
        limiteLinhas = pLimiteLinhas;
        carregarArquivo(pCaminhoArquivo);
        classeImportacao = classe;
        mapeamentoColunasPorNomeDoCampo = mapa;
        caminhoArquivo = pCaminhoArquivo;

    }

    public final void carregarArquivo(String pCaminhoArquivo) {
        try {
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("iso-8859-1");

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

    public void processarCelula(final Cell pCelulaExcelValorCampo, final String pNomeCampo, final ComoEntidadeSimples pItem) throws ErroProcessandCelula {
        TIPO_PRIMITIVO tipoPrimitivo = pItem.getCampoByNomeOuAnotacao(pNomeCampo).getTipoPrimitivoDoValor();
        try {
            if (mapaprocessadoresPersonalizados.containsKey(pNomeCampo)) {
                ProcessadorCampoPLanilhaLogicaPersonalizada processador = mapaprocessadoresPersonalizados.get(pNomeCampo);
                pItem.getCPinst(pNomeCampo).setValor(processador.processarValor(pCelulaExcelValorCampo));
            } else {
                if (pCelulaExcelValorCampo != null) {

                    switch (tipoPrimitivo) {

                        case INTEIRO:

                            if (!pCelulaExcelValorCampo.getContents().equals("")) {

                                int inteiroRecebido = Integer.parseInt(pCelulaExcelValorCampo.getContents());

                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(inteiroRecebido);

                            } else {

                                throw new ErroProcessandCelula(pCelulaExcelValorCampo.getRow(), pCelulaExcelValorCampo.getColumn(), pNomeCampo, "Valor do tipo inteiro  não identificado");

                            }

                            break;

                        case LETRAS:
                            if (pCelulaExcelValorCampo instanceof DateCell) {
                                DateCell data = (DateCell) pCelulaExcelValorCampo;
                                SimpleDateFormat datahoraSistemaFr = new SimpleDateFormat("dd/MM/yy");
                                String valor = datahoraSistemaFr.format(data.getDate());
                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(valor);
                            } else {
                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(pCelulaExcelValorCampo.getContents());
                            }
                            break;

                        case DATAS:

                            if (!pCelulaExcelValorCampo.getContents().equals("")) {

                                String dataRecebida = pCelulaExcelValorCampo.getContents();

                                Date dataFormatada = UtilCRCDataHora.converteStringDD_MM_YYYYEmData(dataRecebida);

                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(dataFormatada);

                            } else {

                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(null);
                                throw new ErroProcessandCelula(pCelulaExcelValorCampo.getRow(), pCelulaExcelValorCampo.getColumn(), pNomeCampo, "Valor do tipo data  não identificado");

                            }

                            break;

                        case BOOLEAN:

                            if (!pCelulaExcelValorCampo.getContents().equals("")) {

                                boolean booleanRecebido = Boolean.parseBoolean(pCelulaExcelValorCampo.getContents());

                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(booleanRecebido);

                            } else {
                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(false);
                                throw new ErroProcessandCelula(pCelulaExcelValorCampo.getRow(), pCelulaExcelValorCampo.getColumn(), pNomeCampo, "Valor verdadeiro ou falso não identificado");

                            }

                            break;

                        case DECIMAL:

                            if (!pCelulaExcelValorCampo.getContents().equals("")) {
                                double doubleRecebido = 0d;
                                if (pCelulaExcelValorCampo.getType().equals(CellType.NUMBER_FORMULA)) {
                                    String valorString = pCelulaExcelValorCampo.getContents();
                                    String valorSemFormatacao = valorString;
                                    if (valorString.contains("R$")) {
                                        valorSemFormatacao = valorString.substring(valorString.lastIndexOf(" ") + 1, valorString.length()).replace(".", "").replace(",", ".");
                                    } else {
                                        valorSemFormatacao = valorString.substring(valorString.lastIndexOf(" ") + 1, valorString.length());
                                    }
                                    doubleRecebido = Double.parseDouble(valorSemFormatacao);
                                } else {
                                    doubleRecebido = Double.parseDouble(pCelulaExcelValorCampo.getContents().replace(",", "."));
                                }
                                pItem.getCampoByNomeOuAnotacao(pNomeCampo).setValor(doubleRecebido);

                            } else {

                                throw new ErroProcessandCelula(pCelulaExcelValorCampo.getRow(), pCelulaExcelValorCampo.getColumn(), pNomeCampo, "Valour double é nulo");

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
            throw new ErroProcessandCelula(pCelulaExcelValorCampo.getRow(), pCelulaExcelValorCampo.getColumn(), pNomeCampo, t.getMessage());

        }

    }

    public void processar() {

        registrosSucesso = new ArrayList<>();

        registrosErro = new ArrayList<>();

        listaDeErros = new ArrayList<>();

        Sheet aba = planilha.getSheets()[0];
        try {

            System.out.println("Lendo Aba" + aba.getName());

            int limit = limiteLinhas;
            if (limiteLinhas < 0) {
                limit = aba.getRows();
            }

            for (int i = 0; i < limit; i++) {

                boolean deuErro = false;

                ComoEntidadeSimples novoRegistro = (ComoEntidadeSimples) classeImportacao.newInstance();

                for (String nomeCampo : mapeamentoColunasPorNomeDoCampo.keySet()) {

                    int coluna = mapeamentoColunasPorNomeDoCampo.get(nomeCampo) - 1;

                    Cell celulaExecelValorCampo = aba.getCell(coluna, i);

                    if (celulaExecelValorCampo == null) {
                        continue;
                    }
                    try {
                        processarCelula(celulaExecelValorCampo, nomeCampo, novoRegistro);
                    } catch (ErroProcessandCelula pErro) {
                        deuErro = true;
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
