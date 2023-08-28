/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.docx4j.wml.CTWebSettings;

/**
 *
 * @author salvio
 */
public class UtilSBEditorPlanilha {

    public static boolean gerarPlanilha(String pCaminhoPlanilha, String pNomeAba, List<ItfBeanSimples> pItens) {

        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("pt", "BR"));
        ws.setEncoding("Cp1254");
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(new File(pCaminhoPlanilha), ws);

            WritableSheet sheet = workbook.createSheet(pNomeAba, 0);
            //WritableCell
            //sheet.addCell(wc);
            int linha = 0;
            if (!pItens.isEmpty()) {

                ItfBeanSimples itemColuna = pItens.get(0);
                int col = 0;
                for (ItfCampoInstanciado campo : itemColuna.getCamposInstanciados()) {
                    if (!campo.getNome().startsWith("this")) {
                        Label l = new Label(col, linha, campo.getLabel());
                        sheet.addCell(l);
                    }
                    col++;
                }
                linha++;
                for (ItfBeanSimples itemSimples : pItens) {
                    int coluna = 0;
                    for (ItfCampoInstanciado campo : itemSimples.getCamposInstanciados()) {

                        if (!campo.getNome().startsWith("this")) {
                            Label l = new Label(coluna, linha, campo.getValorTextoFormatado());
                            sheet.addCell(l);

                        }
                        coluna++;
                    }
                    linha++;

                }
            }
            // putting value at specific position
            //cell.setCellValue("Geeks");
            // Finding index value of row and column of given
            // cell
            //int rowIndex = cell.getRowIndex();
            // int columnIndex = cell.getColumnIndex();
            // Writing the content to Workbook
            //   wb.write(os);
            //    s.getWritableCell(0, 0);
            workbook.write();
            workbook.close();

        } catch (IOException | WriteException ex) {
            Logger.getLogger(UtilSBEditorPlanilha.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

}
