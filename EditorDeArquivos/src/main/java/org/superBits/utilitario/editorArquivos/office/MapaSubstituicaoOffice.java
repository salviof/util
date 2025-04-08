/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.office;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicaoArquivo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.superBits.utilitario.editorArquivos.FabTipoTextoOfficeSubstituivel;
import org.superBits.utilitario.editorArquivos.TextoOfficeSubstituivel;
import org.superBits.utilitario.editorArquivos.office.manipulacao.ManipulacaoPacoteOffice;

/**
 *
 * @author salvio
 */
public abstract class MapaSubstituicaoOffice extends MapaSubstituicaoArquivo {

    public MapaSubstituicaoOffice(File pArquivo) {
        super(pArquivo);
    }

    public abstract OpcPackage lerArquivoArquivoPacoteOffice(String pCaminhoArquivo) throws ErroAbrindoArquivoPacoteOffice;

    public abstract List<TextoOfficeSubstituivel> listaTextosSubstituiveis(OpcPackage pArquivoWord) throws ErroModificandoArquivosPacoteOffice, ErroAbrindoArquivoPacoteOffice;

    public abstract ManipulacaoPacoteOffice gerarManipulador(TextoOfficeSubstituivel textoSubstituivel, List<String> pControleListas, OpcPackage arquivoProcessado);

    public TextoOfficeSubstituivel getTextoSubstituivel(Object origem, String pConteudo) {

        if (isConteudoEncontrado(pConteudo)) {

            return new TextoOfficeSubstituivel(origem, pConteudo, FabTipoTextoOfficeSubstituivel.SIMPLES);
        }
        if (isImagemEncontrada(pConteudo)) {
            return new TextoOfficeSubstituivel(origem, pConteudo, FabTipoTextoOfficeSubstituivel.IMAGEM);
        }
        if (isCampoListaEncontrador(pConteudo)) {
            return new TextoOfficeSubstituivel(origem, pConteudo, FabTipoTextoOfficeSubstituivel.LISTAGEM);
        }
        return null;
    }

    @Override
    public void substituirEmArquivo() {
        substituirPalavrasChaveNoDocumento();
    }

    private boolean isConteudoEncontrado(String pParte) {
        for (String chave : getMapaSubstituicao().keySet()) {
            if (pParte.contains(chave)) {
                return true;
            }
        }
        return false;

    }

    private boolean isCampoListaEncontrador(String parte) {
        return parte.contains("[].");

    }

    private boolean isImagemEncontrada(String pParte) {
        for (String chave : mapaSubstituicaoImagem.keySet()) {
            if (chave.equalsIgnoreCase(pParte)) {
                System.out.println("Imagem Encontrada:" + pParte);
                return true;
            }

        }

        return false;
    }

    public void substituirPalavrasChaveNoDocumento() {

        //  List<TextoOfficeSubstituivel> textoSubstituivel = getTodosElementosDesteTipoNoObjeto(mlp, Tex)
        try {
            OpcPackage pacote = lerArquivoArquivoPacoteOffice(arquivo.getAbsolutePath());

            List<TextoOfficeSubstituivel> textosSubstituiveis = listaTextosSubstituiveis(pacote);
            final List<String> controleListasSubstituidas = new ArrayList<>();
            for (TextoOfficeSubstituivel textoSub : textosSubstituiveis) {

                try {

                    ManipulacaoPacoteOffice manipulacao = gerarManipulador(textoSub, controleListasSubstituidas, pacote);
                    manipulacao.substituir();

                } catch (Throwable t) {
                    SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro substituindo palavra chave" + textoSub.getConteudoTexto(), t);
                    SBCore.getServicoMensagens().enviarMsgAlertaAoUsuario("Erro aplicando palavra chave em documento" + textoSub.getConteudoTexto());
                }
            }

            // substituirTexto(acessoAoConteudo, mlp, null);
            pacote.save(arquivo);

        } catch (Docx4JException ex) {
            Logger.getLogger(MapaSubstituicaoWordOld.class
                    .getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.PARA_TUDO, "Falha processando " + Docx4JException.class.getSimpleName() + " " + arquivo.getAbsolutePath(), ex);

        } catch (Exception ex) {
            Logger.getLogger(MapaSubstituicaoWordOld.class
                    .getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.PARA_TUDO, "Ecessão processando " + arquivo.getAbsolutePath(), ex);
        }

    }

}
