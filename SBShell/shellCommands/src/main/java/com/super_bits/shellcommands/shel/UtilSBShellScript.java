/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.shellcommands.shel;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringConversores;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;

import com.super_bits.shellcommands.model.Comando;
import com.super_bits.shellcommands.model.RespostaCMD;
import com.super_bits.shellcommands.model.TIPOCMD;
import java.io.File;
import java.util.Map;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class UtilSBShellScript {

    private static RespostaCMD defineResposta(Comando pComando, RespostaCMD pRespostaComSaida) {
        pRespostaComSaida.setResultado(RespostaCMD.RESULTADOCMD.ALERTA);
        switch (pComando.getTipoRespostaEsperada()) {

            case MACH: {
                if (pComando.getRegexResultadoEsperado() == null) {
                    pRespostaComSaida.setResultado(RespostaCMD.RESULTADOCMD.OK);
                    return pRespostaComSaida;
                }
                if (pRespostaComSaida.getRetorno().contains(pComando.getRegexResultadoEsperado())) {

                    pRespostaComSaida.setResultado(RespostaCMD.RESULTADOCMD.OK);
                    return pRespostaComSaida;
                }

                if (pRespostaComSaida.getRetorno().matches(pComando.getRegexResultadoEsperado())) {
                    pRespostaComSaida.setResultado(RespostaCMD.RESULTADOCMD.OK);
                    return pRespostaComSaida;
                }
                break;
            }
            case SEMRESPOSTA: {

                if (pRespostaComSaida.getRetorno().length() <= 1) {
                    pRespostaComSaida.setResultado(RespostaCMD.RESULTADOCMD.OK);
                    return pRespostaComSaida;
                } else {

                }
            }

        }
        return pRespostaComSaida;

    }

    private static String[] buildComandoExecucao(Comando pComando) {

        if (pComando.getTipoExecucao() == Comando.TIPO_EXECUCAO.DIRETO) {
            return pComando.getComando().split(" ");
        }
        if (pComando.getTipoExecucao() == Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX) {
            boolean conseguiuCriarArquivo = true;
            String diretorioScriptExecucao = "/home/superBits/temp/";

            String caminhoScriptExecucao = diretorioScriptExecucao + pComando.getNomeArquivoScript();
            pComando.setDiretorioExecucao(diretorioScriptExecucao);

            UtilSBCoreArquivoTexto.limparArquivoTexto(caminhoScriptExecucao);
            int i = 1;
            for (String linha : pComando.getScript()) {

                for (String pr : pComando.getParametros().keySet()) {
                    linha = linha.replace(pr, (String) pComando.getParametros().get(pr));
                    i++;
                }

                if (!UtilSBCoreArquivoTexto.printLnNoArquivo(linha, caminhoScriptExecucao)) {
                    conseguiuCriarArquivo = false;
                }

            }
            Comando permicaoExecutar = TIPOCMD.LNXPERMICAO_EXECUTAR.getComando();
            permicaoExecutar.configParametro("arquivoExecucao", caminhoScriptExecucao);

            if (!(permicaoExecutar.executarComando().getResultado() == RespostaCMD.RESULTADOCMD.OK)) {
                conseguiuCriarArquivo = false;
            }

            if (conseguiuCriarArquivo) {
                String[] resposta = {pComando.getComando()};
                return resposta;
            } else {
                return null;
            }
        }
        throw new UnsupportedOperationException("Tipo de execução não suportada");

    }

    public static RespostaCMD executaComando(Comando pComando) {

        RespostaCMD resposta = new RespostaCMD();
        resposta.setResultado(RespostaCMD.RESULTADOCMD.OK);
        try {

            String[] comandoComParametros = buildComandoExecucao(pComando);

            // Sun's ProcessBuilder and Process example
            ProcessBuilder pb = new ProcessBuilder(comandoComParametros);
            Map<String, String> env = pb.environment();

            if (pComando.getDiretorioExecucao() != null) {
                pb.directory(new File(pComando.getDiretorioExecucao()));
            }

            System.out.println("Executando comando: " + pComando.getComando());

            Process p = pb.start();

            resposta.setRetornoPadrao(UtilSBCoreStringConversores.getStringByInputStream(p.getErrorStream()));
            resposta.setRetornoErro(UtilSBCoreStringConversores.getStringByInputStream(p.getInputStream()));
            resposta = defineResposta(pComando, resposta);
        } catch (Throwable e) {

          SBCore.RelatarErro(FabErro.SOLICITAR_REPARO,"falha na execucao do comando", e);
            resposta.setResultado(RespostaCMD.RESULTADOCMD.FALHOU);
        }
        System.out.println("STATUS EXECUCAO:" + pComando.getTipoComando());
        System.out.println("-" + pComando.getComando());
        System.out.println("=" + resposta.getResultado());
        if (!(resposta.getResultado() == RespostaCMD.RESULTADOCMD.OK)) {
            System.out.println("ATENÇÃO");
            System.out.println(resposta.getComando());
        } else {

        }

        return resposta;

    }

}
