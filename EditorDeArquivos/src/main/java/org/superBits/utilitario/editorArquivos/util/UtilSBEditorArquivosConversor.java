/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreShellBasico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBEditorArquivosConversor {

    public static boolean converterWordEmPDF(String caminhoDocx, String caminhoPdfDesejado) {
        try {
            File docx = new File(caminhoDocx);
            if (!docx.exists()) {
                throw new FileNotFoundException("DOCX não encontrado: " + caminhoDocx);
            }

            // Pasta onde o LibreOffice vai jogar o PDF (ele sempre usa o mesmo nome do .docx)
            String pastaSaida = new File(caminhoPdfDesejado).getParent();
            String nomeBase = docx.getName().replaceFirst("\\.docx$", "");

            // Remove PDF antigo se existir
            File pdfGerado = new File(pastaSaida, nomeBase + ".pdf");
            if (pdfGerado.exists()) {
                pdfGerado.delete();
            }

            String saidaCompleta = UtilSBCoreShellBasico.executeCommand("libreoffice",
                    "--headless",
                    "--convert-to", "pdf",
                    "--outdir", pastaSaida,
                    caminhoDocx);
            Thread.sleep(1000);
            String saidaLower = saidaCompleta.toLowerCase();
            if (saidaLower.contains("error")
                    || saidaLower.contains("fatal")
                    || saidaLower.contains("exception")
                    || saidaLower.contains("password")
                    || saidaLower.contains("corrupt")
                    || saidaLower.contains("cannot")
                    || saidaLower.contains("failed")) {

                throw new RuntimeException("LibreOffice reportou erro:\n" + saidaCompleta);
            }

            // -----------------------------------------------------------------
            // 2) O arquivo foi realmente criado?
            // -----------------------------------------------------------------
            if (!pdfGerado.exists()) {
                throw new RuntimeException("LibreOffice terminou com sucesso, mas o PDF não foi criado");
            }

            // -----------------------------------------------------------------
            // 3) O PDF tem o cabeçalho correto? (teste definitivo)
            // -----------------------------------------------------------------
            if (!isPdfValido(pdfGerado)) {
                pdfGerado.delete();
                throw new RuntimeException("PDF gerado é corrompido (cabeçalho inválido)");
            }

            // -----------------------------------------------------------------
            // 4) Renomeia para o nome que você quiser (opcional, mas quase todo mundo quer)
            // -----------------------------------------------------------------
            File pdfFinal = new File(caminhoPdfDesejado);
            if (!pdfGerado.getAbsolutePath().equals(pdfFinal.getAbsolutePath())) {
                if (pdfFinal.exists()) {
                    pdfFinal.delete();
                }
                if (!pdfGerado.renameTo(pdfFinal)) {
                    throw new RuntimeException("Falha ao renomear o PDF");
                }
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "falha gerando arquivo " + caminhoPdfDesejado + " a partir de " + caminhoDocx, t);
            return false;
        }
        return true; // ← suces
    }

    private static boolean isPdfValido(File file) throws IOException {
        if (file.length() < 8) {
            return false;
        }
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            byte[] magic = new byte[5];
            raf.readFully(magic);
            return new String(magic, StandardCharsets.US_ASCII).equals("%PDF-");
        }
    }
}
