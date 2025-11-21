/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import static org.junit.Assert.*;
import org.superBits.utilitario.editorArquivos.ConfiguradorCoreEditor;

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
        // UtilSBEditorArquivosConversor.converterHTMLEmPDF(cabecalho + "<b> Olá cidadão do Mundo </b>"
        //           + "</body></html>", "/home/superBits/teste.pdf");

    }

    @Test
    public void testConverterWordEmPDF() {
        SBCore.configurar(new ConfiguradorCoreEditor(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        if (UtilSBEditorArquivosConversor.converterWordEmPDF("/home/superBits/desenvolvedor/configModuloTestes/HOMOLOGACAO/Intranet_Marketing_Digital/arquivos/-2009385540/arqTemp.docx",
                "/home/superBits/desenvolvedor/configModuloTestes/HOMOLOGACAO/Intranet_Marketing_Digital/arquivos/-2009385540/teste.pdf")) {
            System.out.println("GErou com sucesso");
        }

    }

    //@Test
    public void testConverterWordEmPDFnovo() {
    }

}
