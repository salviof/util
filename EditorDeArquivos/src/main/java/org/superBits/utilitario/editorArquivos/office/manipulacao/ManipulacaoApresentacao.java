/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office.manipulacao;

import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicaoArquivo;
import java.util.List;
import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTextBody;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;

/**
 *
 * @author salvio
 */
public class ManipulacaoApresentacao extends ManipulacaoPacoteOffice {

    public ManipulacaoApresentacao(PresentationMLPackage pDocumento, TextoOfficeSubstituivel textoSubstituivel, MapaSubstituicaoArquivo pMapa, List<String> pControleLista) {
        super(textoSubstituivel, pMapa, pControleLista);
    }

    @Override
    public void substituir() {
        CTRegularTextRun texto = (CTRegularTextRun) getTextoSubstituivel().getObjetoOrigem();
        String conteudoTexto = getTextoSubstituivel().getConteudoTexto();
        String novoConteudo = getMapa().substituirEmString(getTextoSubstituivel().getConteudoTexto());
        texto.setT(novoConteudo);
    }

}
