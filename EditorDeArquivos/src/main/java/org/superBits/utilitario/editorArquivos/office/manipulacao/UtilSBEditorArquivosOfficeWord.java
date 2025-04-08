/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office.manipulacao;

import javax.xml.bind.JAXBElement;
import org.docx4j.wml.Text;

/**
 *
 * @author salvio
 */
public class UtilSBEditorArquivosOfficeWord {

    public static Text getTextoDoElemento(JAXBElement elemento) {
        Object valor = elemento.getValue();
        if (valor instanceof Text) {
            return (Text) valor;
        }
        return null;
    }
}
