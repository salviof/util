/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.editorImagem.util;

import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreInputOutputConversoes;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreOutputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringNomeArquivosEDiretorios;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.opencv.core.Mat;

/**
 *
 * @author SalvioF
 */
public class UtilSBImagemEdicaoTest {

    public UtilSBImagemEdicaoTest() {
    }

    /**
     * Test of redimencionaImagem method, of class UtilSBImagemEdicao.
     */
    public void testRedimencionaImagem_0args() {
        System.out.println("redimencionaImagem");
        UtilSBImagemEdicao.redimencionaImagem();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redimencionaImagem method, of class UtilSBImagemEdicao.
     */
    public void testRedimencionaImagem_BufferedImage_int() {
        System.out.println("redimencionaImagem");
        BufferedImage originalImage = null;
        int porcent = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.redimencionaImagem(originalImage, porcent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduzirProporcionalAlturaMaxima method, of class
     * UtilSBImagemEdicao.
     */
    public void testReduzirProporcionalAlturaMaxima() {
        System.out.println("reduzirProporcionalAlturaMaxima");
        BufferedImage originalImage = null;
        int pMaximoAltura = 0;
        String extencao = "";
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.reduzirProporcionalAlturaMaxima(originalImage, pMaximoAltura, extencao);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduzirProporcionalLarguraMaxima method, of class
     * UtilSBImagemEdicao.
     */
    public void testReduzirProporcionalLarguraMaxima() {
        System.out.println("reduzirProporcionalLarguraMaxima");
        BufferedImage originalImage = null;
        int pLarguraMAximo = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.reduzirProporcionalLarguraMaxima(originalImage, pLarguraMAximo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduzImagemTamanhoEspecifico method, of class UtilSBImagemEdicao.
     */
    public void testReduzImagemTamanhoEspecifico() {
        System.out.println("reduzImagemTamanhoEspecifico");
        Image originalImage = null;
        int pLargurapx = 0;
        int pAlturaPx = 0;
        InputStream expResult = null;
        InputStream result = UtilSBImagemEdicao.reduzImagemTamanhoEspecifico(originalImage, pLargurapx, pAlturaPx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redimencionaImagem method, of class UtilSBImagemEdicao.
     */
    public void testRedimencionaImagem_3args() {
        System.out.println("redimencionaImagem");
        Image originalImage = null;
        int pLarguraPx = 0;
        int pAlturaPx = 0;
        InputStream expResult = null;
        InputStream result = UtilSBImagemEdicao.redimencionaImagem(originalImage, pLarguraPx, pAlturaPx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redimencionarImagemAvancado method, of class UtilSBImagemEdicao.
     */
    public void testRedimencionarImagemAvancado() {
        System.out.println("redimencionarImagemAvancado");
        BufferedImage image = null;
        int areaWidth = 0;
        int areaHeight = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.redimencionarImagemAvancado(image, areaWidth, areaHeight);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redimencionaImagem method, of class UtilSBImagemEdicao.
     */
    @Test
    public void testRedimencionaImagem_4args() {
        try {
            String CaminhoArquivoTeste = "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/imagemTesteBlack.png";
            BufferedImage imgemTesteConversao = ImageIO.read(new File(CaminhoArquivoTeste));
            BufferedImage alfaConvertito = UtilSBImagemEdicao.converterPNGParaJpg(imgemTesteConversao, Color.WHITE);
            ImageIO.write(alfaConvertito, "jpg", new File("/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/conversaoSimples.jpg"));

            BufferedImage imagemComAlpha = UTilSBCoreInputs.getImagemBufferedByLocalFile(CaminhoArquivoTeste);
            //   BufferedImage imagem = UtilSBImagemEdicao.redimencionaImagem(imagemComAlpha, 50, "png");
            String extencao = UtilSBCoreStringNomeArquivosEDiretorios.getExtencaoNomeArquivoSemPonto(CaminhoArquivoTeste);
            BufferedImage imagem = UtilSBImagemEdicao.reduzirProporcionalAlturaMaxima(imagemComAlpha, 50, extencao);
            InputStream inputImagem = UtilSBCoreInputOutputConversoes.BufferedImageToInputStream(imagem, extencao);

            UtilSBCoreOutputs.salvarArquivoByte(IOUtils.toByteArray(inputImagem), "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/imagemTesteBlackReduziada.png");
            BufferedImage img = ImageIO.read(new File("/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/imagemTesteBlackReduziada.png"));
            ImageIO.write(img, "jpg", new File("/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/imagemTesteBlackReduziada.jpg"));
        } catch (Throwable t) {

        }
        //UtilSBCoreOutputs.salvarArquivoInputStream(inputImagem, "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork/utilitarios/EditorImagem/imagemTesteBlackReduziada.png");
    }

    /**
     * Test of aplicarBlur method, of class UtilSBImagemEdicao.
     */
    public void testAplicarBlur() {
        System.out.println("aplicarBlur");
        BufferedImage pImagem = null;
        int pIntensidade = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.aplicarBlur(pImagem, pIntensidade);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aplicarGama method, of class UtilSBImagemEdicao.
     */
    public void testAplicarGama() {
        System.out.println("aplicarGama");
        BufferedImage pImagem = null;
        int intensidade = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.aplicarGama(pImagem, intensidade);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtractBackground method, of class UtilSBImagemEdicao.
     */
    public void testSubtractBackground() {
        System.out.println("subtractBackground");
        BufferedImage pImagem = null;
        int intensidade = 0;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.subtractBackground(pImagem, intensidade);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of matTOImg method, of class UtilSBImagemEdicao.
     */
    public void testMatTOImg() {
        System.out.println("matTOImg");
        Mat in = null;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.matTOImg(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contornarFace method, of class UtilSBImagemEdicao.
     */
    public void testContornarFace() {
        System.out.println("contornarFace");
        BufferedImage pImage = null;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.contornarFace(pImage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of localizarRetangulo method, of class UtilSBImagemEdicao.
     */
    public void testLocalizarRetangulo() {
        System.out.println("localizarRetangulo");
        BufferedImage pImage = null;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.localizarRetangulo(pImage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotacionarParaEsquerda method, of class UtilSBImagemEdicao.
     */
    public void testRotacionarParaEsquerda() {
        System.out.println("rotacionarParaEsquerda");
        BufferedImage pImagem = null;
        Integer graus = null;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.rotacionarParaEsquerda(pImagem, graus);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotacionarParaDireita method, of class UtilSBImagemEdicao.
     */
    public void testRotacionarParaDireita() {
        System.out.println("rotacionarParaDireita");
        BufferedImage pImagem = null;
        Integer graus = null;
        BufferedImage expResult = null;
        BufferedImage result = UtilSBImagemEdicao.rotacionarParaDireita(pImagem, graus);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
