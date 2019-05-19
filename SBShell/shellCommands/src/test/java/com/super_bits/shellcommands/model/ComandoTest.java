/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.model;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Before;

/**
 *
 * @author sfurbino
 */
public class ComandoTest {

    public ComandoTest() {
    }

    @Before
    public void setUp() {
        //SBCore.configurar(new ConfigCoreDeveloper());
    }

    private void printResposta(RespostaCMD resp) {
        System.out.println("REsultado=" + resp.getResultado());
        System.out.println("retorno=" + resp.getRetorno());
        System.out.println("erro=" + resp.getRetornoErro());
        System.out.println("texto=" + resp.getRetornoPadrao());
    }

    /**
     * Test of getTipoComando method, of class Comando.
     */
    public void testaComando() {
        //teste ping
        Comando ping = TIPOCMD.LNXPING.getComando();
        ping.configParametro("ip", "192.168.0.2");
        RespostaCMD resp = ping.executarComando();
        printResposta(resp);
        //teste copiarPasta
        Comando copiarPasta = TIPOCMD.LNXDIR_COPIAR_PASTA.getComando();
        copiarPasta.configParametro("arq1", "/home/superBits/projetos/testes");
        copiarPasta.configParametro("arq2", "/home/superBits/projetos/funciona");
        resp = copiarPasta.executarComando();
        printResposta(resp);

        Comando substituirPalavra = TIPOCMD.LNXSUBSTITUIR_PALAVRA_EM_ARQUIVOS.getComando();
        //substituirPalavra.configParametro("arq", "/home/superBits/projetos/superbits/SBShell/shellCommands/src/test/resources/teste/textoTeste");
        substituirPalavra.configParametro("textoAntigo", "PALAVRATESTE");
        substituirPalavra.configParametro("novoTexto", "PALAVRATESTE1");
        resp = substituirPalavra.executarComando();

        printResposta(resp);

    }

    public void testaSubistring() {
        //     Comando teste = new Comando();
        //   teste.setComando("ls");
        // teste.setDiretorioExecucao("/home/superBits/projetos/Super_Bits/source/SBProject/");
        RespostaCMD resp = new RespostaCMD();

        printResposta(resp);
        Comando replace = TIPOCMD.LNXSUBSTITUIR_PALAVRA_EM_ARQUIVOS.getComando();
        System.out.println("parametros=" + replace.getParametros().keySet().toArray());
        replace.setDiretorioExecucao("/home/superBits/projetos/Super_Bits/source/SBProject/");
        replace.configParametro("textoAntigo", "teste");
        replace.configParametro("novoTexto", "teste agora vai saporra");
        replace.configParametro("pastaRecursiva", "/home/superBits/projetos/Super_Bits/source/SBProject");
        resp = replace.executarComando();
        printResposta(resp);

    }

    public void testaAddSVN() {
        Comando addArquivoSVN = TIPOCMD.LNXSVN_ADD_ARQUIVO_REPOSITORIO.getComando();

        addArquivoSVN.configParametro("arquivo", "");
    }

}
