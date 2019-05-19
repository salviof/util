/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBEditorArquivosConversorTest {

    @Test
    public void testConverterHTMLEmPDF() {

        String cabecalho = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
                + "<html>\n"
                + "\n"
                + "    <head>  <meta charset=\"utf-8\"/>\n"
                + "<title> Teste </title>"
                + "        <style  type=\"text/css\">\n"
                + "        * { font-family: Arial; }\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body>";
        UtilSBEditorArquivosConversor.converterHTMLEmPDF(cabecalho + "<b> Olá cidadão do Mundo </b>"
                + "</body></html>", "/home/superBits/teste.pdf");

    }

    //@Test
    public void testConverterWordEmPDF() {
    }

    //@Test
    public void testConverterWordEmPDFnovo() {
    }

}
