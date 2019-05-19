/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.editorImagem;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * O Editor de imagem permite aplicar uma sequencia de efeitos em uma imágem.
 *
 *
 * @author <a href="mailto:salviof@gmail.com">Salvio Furbino</a>
 * @since 02/03/2015
 * @version 1.0
 */
public class EditorImagem implements Serializable {

    private List<EfeitoImagem> efeitos;
    private BufferedImage imagem;

    public EditorImagem() {
        efeitos = new ArrayList<>();
    }

    public EditorImagem(BufferedImage pImagem) {
        efeitos = new ArrayList<>();
        imagem = pImagem;
    }

    public void adcionarEfeitos(EfeitoImagem pEfeito) {
        if (pEfeito.getParametros().size() > 0) {
            for (EfeitoImagem.PARAMETRO_EFEITO pr : pEfeito.getParametros().keySet()) {
                if (pEfeito.getParametros().get(pr).equals("0")) {
                    SBCore.enviarAvisoAoUsuario("Parametro obrigatório: '" + pr + "' do efeito " + pEfeito.getTipoEfeito().toString() + " não foi configurado");
                } else {
                    efeitos.add(pEfeito);
                }
            }
        } else {
            efeitos.add(pEfeito);
        }

    }

    public void processarImagem() {
        for (EfeitoImagem efeito : efeitos) {
            try {
                imagem = efeito.processarImagem(imagem);
            } catch (Throwable e) {
                SBCore.getCentralDeMensagens().enviarMsgErroAoUsuario("Erro aplicando efeito" + efeito.getNomeCurto());
            }

        }
    }

    public List<EfeitoImagem> getEfeitos() {
        return efeitos;
    }

    public void setEfeitos(List<EfeitoImagem> efeitos) {
        this.efeitos = efeitos;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }

}
