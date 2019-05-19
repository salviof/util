/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.servidor.admin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import spark.Request;
import spark.Spark;

/**
 *
 *
 *
 *
 * @author desenvolvedor
 */
public class InicioServidor {

    private static final HashMap<String, String> COORSHEADERS = new HashMap<String, String>();
    private static String pastaBaseEmpresa;
    private static String pastaBaseRecursosEstaticos;
    private static final String RELATIVO_BASE_ARQUIVO_IP_ADMIN = "/seguranca/adminIP";
    private static final String RELATIVO_BASE_ARQUIVO_IP_GERENTE = "/seguranca/gerenteIP";

    static {
        COORSHEADERS.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        COORSHEADERS.put("Access-Control-Allow-Origin", "*");
        COORSHEADERS.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        COORSHEADERS.put("Access-Control-Allow-Credentials", "true");
    }

    public static List<String> stringsDoArquivo(String pCaminhoArquivoLocal) {

        File arquivo = new File(pCaminhoArquivoLocal);
        List<String> conteudo = new ArrayList();
        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                conteudo.add(line);
            }
            scanner.close();
            return conteudo;
        } catch (Throwable t) {
            return new ArrayList<>();
        }

    }

    public static boolean isIpNaListaAdmin(Request requisicao) {
        List<String> ipsAdmin = stringsDoArquivo(pastaBaseEmpresa + RELATIVO_BASE_ARQUIVO_IP_ADMIN);
        ipsAdmin.addAll(stringsDoArquivo(pastaBaseRecursosEstaticos + RELATIVO_BASE_ARQUIVO_IP_ADMIN));
        return ipsAdmin.contains(requisicao.ip());
    }

    public static boolean isIpNaListaGerente(Request requisicao) {
        List<String> ipsAdmin = stringsDoArquivo(pastaBaseEmpresa + RELATIVO_BASE_ARQUIVO_IP_GERENTE);
        return ipsAdmin.contains(requisicao.ip());
    }

    public static String getMensagemAcessoNegadoAdmin() {
        return "Acesso negado! você precisa renovar Seu Acesso";
    }

    public static String getMensagemAcessoNegadoGerente(Request request) {
        return "Acesso negado! você precisa renovar Seu Acesso, "
                + "renove em: <a href='" + request.ip() + ":" + request.port() + "/renovarAcesso'>";
    }

    public static void main(String[] args) {
        pastaBaseEmpresa = "/home/superBits/projetos/coletivoJava/source/fw/util/servidor/AdminServerColetivoJava/src/test/resources/exemplo";
        if (args != null & args.length > 0) {
            pastaBaseEmpresa = args[0];
            if (args.length > 1) {
                pastaBaseRecursosEstaticos = args[0];
            }
        }
        Spark.port(6661);
        //  apply();

        Spark.get("/renovarAcessoAdmin", (req, res) -> {
            if (!isIpNaListaAdmin(req)) {
                //return getMensagemAcessoNegadoAdmin();
            }
            return "<h:form><input > </form>";

        });

        Spark.get("/renovarAcessoGerente", (req, res) -> {
            if (!isIpNaListaGerente(req)) {
                //return getMensagemAcessoNegadoGerente(req);
            }
            return "<h:form><input > </form>";
        });

        Spark.get("/configuracoes", (req, resposta) -> {

            return "ArquivoConfig";
        });

        Spark.put("/configuracoes", (req, resposta) -> {

            return "";
        });

        Spark.get("/gerarQRCoreAdmin", (req, resposta) -> {
            if (!isIpNaListaAdmin(req)) {
                return getMensagemAcessoNegadoAdmin();
            }
            System.out.println(req.ip());
            ByteArrayOutputStream streamByteOutputQrcode
                    = QRCode.from("Olá mundo")
                            .withSize(280, 280)
                            .to(ImageType.JPG)
                            .stream();
            resposta.header("Content-Type", "image/jpeg");
            return streamByteOutputQrcode.toByteArray();
        });

        try {
            Thread.sleep(10000);
        } catch (Throwable t) {

        }
    }
}
