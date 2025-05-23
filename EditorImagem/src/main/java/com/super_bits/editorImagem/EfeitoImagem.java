/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.editorImagem;

import com.super_bits.editorImagem.util.UtilSBImagemEdicao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemGenerico;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * Tipos de Efeitos para aplicar em imágens conhecidos pelo Sistema
 *
 *
 *
 * @author <a href="mailto:salviof@gmail.com">Salvio Furbino</a>
 * @since 02/03/2015
 * @version 1.0
 */
public class EfeitoImagem extends ItemGenerico implements ItfBeanSimplesSomenteLeitura {

    @Override
    public String getImgPequena() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNome() {
        return getNomeCurto();
    }

    @Override
    public String getNomeCurto() {
        String nome = getTipoEfeito().toString();

        for (PARAMETRO_EFEITO pr : parametros.keySet()) {
            nome = nome + " -" + pr + ":" + parametros.get(pr);
        }
        return nome;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIconeDaClasse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getXhtmlVisao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getXhtmlVisaoMobile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getXhtmlVisao(int numeroColunas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static enum TIPO_EFEITO {

        BLUR, CORTAR_RETANGULO, GAMA, REDUZIR, SUBTRACTBACKGROUND, RETANGULO_FACE, RETANGULOSIMAGEM, GIRAR_ESQUERDA, GIRAR_DIREITA, BRILHO, CONTRASTE
    }

    public static enum PARAMETRO_EFEITO {

        VALOR, PT_SUP_ESQUERDA, PT_SUP_DIREITA, PT_INFERIOR_EQUERDA, PT_INFERIOR_DIREITA,
    }

    public EfeitoImagem(TIPO_EFEITO pTipoEfeito) {
        parametros = new HashMap<>();
        tipoEfeito = pTipoEfeito;
        switch (pTipoEfeito) {
            case BLUR:
            case GAMA:
            case BRILHO:
            case CONTRASTE:
            case REDUZIR:
            case GIRAR_DIREITA:
            case GIRAR_ESQUERDA:
                parametros.put(PARAMETRO_EFEITO.VALOR, "0");
                break;
            case CORTAR_RETANGULO:
                parametros.put(PARAMETRO_EFEITO.PT_SUP_ESQUERDA, "0");
                parametros.put(PARAMETRO_EFEITO.PT_SUP_DIREITA, "0");
                parametros.put(PARAMETRO_EFEITO.PT_INFERIOR_EQUERDA, "0");
                parametros.put(PARAMETRO_EFEITO.PT_SUP_DIREITA, "0");
                break;

        }

    }

    private TIPO_EFEITO tipoEfeito;

    private Map<PARAMETRO_EFEITO, String> parametros;

    public TIPO_EFEITO getTipoEfeito() {
        return tipoEfeito;
    }

    public void setTipoEfeito(TIPO_EFEITO tipoEfeito) {
        this.tipoEfeito = tipoEfeito;
    }

    public Map<PARAMETRO_EFEITO, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<PARAMETRO_EFEITO, String> parametros) {
        this.parametros = parametros;
    }

    public BufferedImage processarImagem(BufferedImage pImagem) {

        try {
            switch (tipoEfeito) {
                case BLUR:
                    if (!parametros.get(PARAMETRO_EFEITO.VALOR).equals("0")) {
                        return UtilSBImagemEdicao.aplicarBlur(pImagem, Integer.parseInt(parametros.get(PARAMETRO_EFEITO.VALOR)));
                    }
                    break;
                case CORTAR_RETANGULO:
                    throw new UnsupportedOperationException("Efeito corte não foi implementado");
                case GAMA:
                    if (!parametros.get(PARAMETRO_EFEITO.VALOR).equals("0")) {
                        return UtilSBImagemEdicao.aplicarGama(pImagem, Integer.parseInt(parametros.get(PARAMETRO_EFEITO.VALOR)));
                    }
                    break;
                case REDUZIR:
                    if (!parametros.get(PARAMETRO_EFEITO.VALOR).equals("0")) {
                        return UtilSBImagemEdicao.redimencionaImagem(pImagem, Integer.parseInt(parametros.get(PARAMETRO_EFEITO.VALOR)));
                    }
                    break;
                case RETANGULOSIMAGEM:
                    return UtilSBImagemEdicao.localizarRetangulo(pImagem);
                case RETANGULO_FACE:
                    return UtilSBImagemEdicao.contornarFace(pImagem);
                case GIRAR_DIREITA:
                    return UtilSBImagemEdicao.rotacionarParaDireita(pImagem, Integer.parseInt(parametros.get(PARAMETRO_EFEITO.VALOR)));
                case GIRAR_ESQUERDA:
                    return UtilSBImagemEdicao.rotacionarParaEsquerda(pImagem, Integer.parseInt(parametros.get(PARAMETRO_EFEITO.VALOR)));
                case BRILHO:
                    throw new UnsupportedOperationException("não foi implementado ainda");
                case CONTRASTE:
                    throw new UnsupportedOperationException("não foi implementado ainda");

            }
        } catch (Throwable e) {
            SBCore.getCentralDeMensagens().enviarMsgErroAoUsuario("Erro aplicando efeito em imagem");
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro tentando aplicar Efeito", e);

        }
        return pImagem;
    }

}
