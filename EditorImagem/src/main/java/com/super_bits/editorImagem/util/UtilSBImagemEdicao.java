//Sistema desenvolvido sob encomenda para processamento de placas veiculares em tempo real pela Super Bits Sistemas sob encomnda da Sphera Secucurity
package com.super_bits.editorImagem.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * Classe com métodos estaticos para executar algorítimos mais relevantes sobre
 * imágens
 *
 * @author Sálvio Furbino <salviof@gmail.com>
 * @since 29/05/2014
 *
 */
public abstract class UtilSBImagemEdicao {

    public static void redimencionaImagem() {

    }

    public static BufferedImage redimencionaImagem(BufferedImage originalImage, int porcent) {
        float altura = originalImage.getHeight(null);
        float largura = originalImage.getWidth(null);
        altura = ((altura / 100f) * porcent);
        largura = ((largura / 100f) * porcent);
        try {

            return redimencionarImagemAvancado(originalImage, Math.round(largura), Math.round(altura));
        } catch (Throwable ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro redimencinando imagens", ex);

        }
        return originalImage;

    }

    public static BufferedImage converterPNGParaJpg(BufferedImage originalImage, Color corAlpha) {
        Color fillColor = Color.WHITE; // just to verify
        BufferedImage bi = originalImage;
        BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        bi2.getGraphics().drawImage(bi, 0, 0, fillColor, null);
        // you can do a more complex saving to tune the compression  parameters

        return bi2;

    }

    public static BufferedImage reduzirProporcionalAlturaMaxima(BufferedImage originalImage, int pMaximoAltura, String extencao) {
        int alturaOriginal = originalImage.getHeight();

        if (alturaOriginal <= pMaximoAltura) {
            return originalImage;
        } else {

            float porcentagemDiferenca = (pMaximoAltura * 100 / alturaOriginal);
            return redimencionaImagem(originalImage, Math.round(porcentagemDiferenca));
        }

    }

    public static BufferedImage reduzirProporcionalLarguraMaxima(BufferedImage originalImage, int pLarguraMAximo) {
        int larguraOriginal = originalImage.getWidth();

        if (larguraOriginal <= pLarguraMAximo) {
            return originalImage;
        } else {
            int diferenca = larguraOriginal - pLarguraMAximo;
            int novaLargura = (larguraOriginal - diferenca);
            int porcentagemDiferenca = (100 - (novaLargura / (larguraOriginal / 100) - 100));
            return redimencionaImagem(originalImage, porcentagemDiferenca);
        }
    }

    public static InputStream reduzImagemTamanhoEspecifico(Image originalImage, int pLargurapx, int pAlturaPx) {
        int largura = originalImage.getHeight(null);
        int altura = originalImage.getWidth(null);
        if (pAlturaPx < altura) {
            altura = pAlturaPx;
        }
        if (pLargurapx < largura) {
            largura = pLargurapx;
        }
        System.out.println("Altura:" + altura + "Largura:" + largura);

        return redimencionaImagem(originalImage, largura, altura);
    }

    public static InputStream redimencionaImagem(Image originalImage,
            int pLarguraPx, int pAlturaPx) {
        return redimencionaImagem(originalImage, pLarguraPx, pAlturaPx, "jpg");
    }

    public static BufferedImage redimencionarImagemAvancado(BufferedImage image, int areaWidth, int areaHeight) {

        float scaleX = (float) areaWidth / image.getWidth();
        float scaleY = (float) areaHeight / image.getHeight();
        float scale = Math.min(scaleX, scaleY);
        int w = Math.round(image.getWidth() * scale);
        int h = Math.round(image.getHeight() * scale);

        int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        boolean scaleDown = scale < 1;

        if (scaleDown) {
            // multi-pass bilinear div 2
            int currentW = image.getWidth();
            int currentH = image.getHeight();
            BufferedImage resized = image;
            while (currentW > w || currentH > h) {
                currentW = Math.max(w, currentW / 2);
                currentH = Math.max(h, currentH / 2);

                BufferedImage temp = new BufferedImage(currentW, currentH, type);
                Graphics2D g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(resized, 0, 0, currentW, currentH, null);
                g2.dispose();
                resized = temp;
            }
            return resized;
        } else {
            Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC : RenderingHints.VALUE_INTERPOLATION_BILINEAR;

            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(image, 0, 0, w, h, null);
            g2.dispose();
            return resized;
        }

    }

    public static InputStream redimencionaImagem(Image originalImage,
            int pLarguraPx, int pAlturaPx, String pExtencao) {
        System.out.println("resizing...");
        // preserve image true
        @SuppressWarnings("unused")
        int imageType = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(pLarguraPx, pAlturaPx, imageType);
        Graphics2D g = scaledBI.createGraphics();
        // preservando o Alpha
        g.setComposite(AlphaComposite.Clear);

        g.drawImage(originalImage, 0, 0, pLarguraPx, pAlturaPx, null);
        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(scaledBI, pExtencao, os);
        } catch (IOException e) {

            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro redimencionando imagem", e);
        }
        InputStream fis = new ByteArrayInputStream(os.toByteArray());

        return fis;

    }

    public static BufferedImage aplicarBlur(BufferedImage pImagem, int pIntensidade) {
        try {
            ImagePlus img = new ImagePlus("teste", pImagem);
            ImageProcessor proc = img.getProcessor();
            proc.blurGaussian(pIntensidade);
            return proc.getBufferedImage();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro aplicando blur em imagem", e);
            return pImagem;
        }

    }

    public static BufferedImage aplicarGama(BufferedImage pImagem, int intensidade) {
        try {
            System.out.println("aplicando Subtract" + intensidade);
            ImagePlus img = new ImagePlus("teste", pImagem);
            ImageProcessor proc = img.getProcessor();
            //   proc.subtract(intensidade);
            RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
            rescaleOp.filter(pImagem, pImagem);
            return proc.getBufferedImage();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro aplicando blur em imagem", e);
            return pImagem;
        }
    }

    public static BufferedImage subtractBackground(BufferedImage pImagem, int intensidade) {
        try {
            ImagePlus img = new ImagePlus("teste", pImagem);
            ImageProcessor proc = img.getProcessor();
            proc.subtract(intensidade);
            return proc.getBufferedImage();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro aplicando blur em imagem", e);
            return pImagem;
        }
    }

    public static BufferedImage matTOImg(Mat in) {
        BufferedImage out;
        byte[] data = new byte[320 * 240 * (int) in.elemSize()];
        int type;
        in.get(0, 0, data);

        if (in.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        out = new BufferedImage(320, 240, type);

        out.getRaster().setDataElements(0, 0, 320, 240, data);
        return out;
    }

    /**
     *
     * Localiza uma face na imagem, e desenha um retangulo em volta
     *
     * @param pImage
     * @return
     */
    public static BufferedImage contornarFace(BufferedImage pImage) {
        System.out.println("\nRunning DetectFaceDemo");

        // Create a face detector from the cascade file in the resources
        // directory.
        int rows = pImage.getWidth();
        int cols = pImage.getHeight();
        int type = CvType.CV_16UC1;
        Mat image = new Mat(rows, cols, type);

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect
        CascadeClassifier faceDetector = new CascadeClassifier();
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        Rect[] facesArray = faceDetections.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Core.rectangle(image, facesArray[i].tl(), facesArray[i].br(), new Scalar(200, 200, 200), 3);
        }
        return matTOImg(image);

    }

    private static BufferedImage convert(Mat m) {
        Mat image_tmp = m;

        MatOfByte matOfByte = new MatOfByte();

        Highgui.imencode(".JPG", image_tmp, matOfByte);

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;

        try {

            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return bufImage;
        }
    }

    /**
     *
     * Localiza retangulos na imagem os desenha na imagem
     *
     * @param pImage
     * @return
     */
    public static BufferedImage localizarRetangulo(BufferedImage pImage) {
        int rows = pImage.getWidth();
        int cols = pImage.getHeight();
        int type = CvType.CV_16UC1;

        Mat image = new Mat(rows, cols, type);
        Mat hierarchy = new Mat();
        Mat gray = null;
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_GRAY2BGR);
        Imgproc.Canny(gray, gray, 100, 200, 3, false);
        List<MatOfPoint> contornos = new ArrayList<>();

        Imgproc.findContours(gray, contornos, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        /// Draw contours
        Mat drawing = new Mat();

        for (int i = 0; i < contornos.size(); i++) {
            Scalar color = new Scalar(200);
            Imgproc.drawContours(drawing, contornos, i, color, 2, 8, hierarchy, 0, new Point());
        }
        return convert(drawing);

    }

    public static BufferedImage rotacionarParaEsquerda(BufferedImage pImagem, Integer graus) {
        try {
            ImagePlus img = new ImagePlus("teste", pImagem);
            ImageProcessor proc = img.getProcessor();
            proc.rotate(graus * -1);

            return proc.getBufferedImage();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro aplicando blur em imagem", e);
            return pImagem;
        }
    }

    public static BufferedImage rotacionarParaDireita(BufferedImage pImagem, Integer graus) {
        try {
            ImagePlus img = new ImagePlus("teste", pImagem);
            ImageProcessor proc = img.getProcessor();
            proc.rotate(graus);

            return proc.getBufferedImage();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro aplicando blur em imagem", e);
            return pImagem;
        }
    }

}
