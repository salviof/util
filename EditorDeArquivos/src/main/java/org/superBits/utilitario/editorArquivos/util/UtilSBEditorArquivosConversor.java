/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.docx4j.Docx4J;

import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBEditorArquivosConversor {

    public static boolean converterHTMLEmPDF(String pConteudoHTML, String localDestino) {
        try {
            UtilSBCoreArquivos.criarDiretorioParaArquivo(localDestino);
            Tidy tidy = new Tidy();
            tidy.setInputEncoding("utf-8");
            tidy.setOutputEncoding("utf-8");
            Document doc = tidy.parseDOM(new ByteArrayInputStream(pConteudoHTML.getBytes(Charset.forName("UTF-8"))), null);

            ITextRenderer renderer = new ITextRenderer();
            //    renderer.getFontResolver().addFont("fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            // renderer.getFontResolver().addFont(fontResourceURL.getPath(),
            //          BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocument(doc, null);
            renderer.layout();
            renderer.createPDF(new FileOutputStream(new File(localDestino)));
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean converterWordEmPDF(String arquivoOrigem, String pArquivoDestino) {
        String regex = ".*(Courier New|Arial|Times New Roman|Comic Sans|Georgia|Impact|Lucida Console|Lucida Sans Unicode|Palatino Linotype|Tahoma|Trebuchet|Verdana|Symbol|Webdings|Wingdings|MS Sans Serif|MS Serif).*";

        //    PhysicalFonts.setRegex(regex);
        WordprocessingMLPackage wordMLPackage;
        try {
            wordMLPackage = WordprocessingMLPackage.load(new File(arquivoOrigem));

            //     Mapper fontMapper = new BestMatchingMapper();
            //    wordMLPackage.setFontMapper(fontMapper);
            //     PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Arial");
            //    fontMapper.getFontMappings().put("Calibri", font);
            OutputStream os = new java.io.FileOutputStream(new File(pArquivoDestino));
            Docx4J.toPDF(wordMLPackage, os);

            //     PdfConversion c = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordMLPackage);
            //   OutputStream os = new java.io.FileOutputStream(new File(pArquivoDestino));
            // c.output(os, new PdfSettings());
            return true;
        } catch (Docx4JException | FileNotFoundException ex) {
            Logger.getLogger(UtilSBEditorArquivosConversor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UtilSBEditorArquivosConversor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public static boolean converterWordEmPDFnovo(String arquivoOrigem, String ArquivoDestino) {

        Date now = new Date();
        WordprocessingMLPackage wordMLPackage;
        try {
            wordMLPackage = WordprocessingMLPackage.load(new File(arquivoOrigem));
            // Fonts identity mapping – best on Microsoft Windows
            wordMLPackage.setFontMapper(new IdentityPlusMapper());
            // Set up converter

            //// org.docx4j.convert.out.pdf.PdfConversion c
            ////          = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordMLPackage);
            // Write to output stream
            ////   OutputStream os = new java.io.FileOutputStream(ArquivoDestino);
            ////  c.output(os, new PdfSettings());
            OutputStream os = new java.io.FileOutputStream(new File(ArquivoDestino));
            Docx4J.toPDF(wordMLPackage, os);
            return true;
        } catch (Docx4JException ex) {
            Logger.getLogger(UtilSBEditorArquivosConversor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UtilSBEditorArquivosConversor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
}
