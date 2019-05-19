/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.model;


import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringListas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sfurbino
 */
public class SvnStatusArquivosRepositorio {

    public List<String> excluidos;
    public List<String> modificados;
    public List<String> adicionados;

    public SvnStatusArquivosRepositorio(String diretorio) {
        excluidos = new ArrayList<>();
        modificados = new ArrayList<>();
        adicionados = new ArrayList<>();

        Comando comandoSvn = TIPOCMD.LNXSVN_LISTARSTATUS.getComando();
        comandoSvn.configParametro("pastaSvnStatus", diretorio);
        RespostaCMD resposta = comandoSvn.executarComando();
        System.out.println(resposta.getComando());
        List<String> status = UtilSBCoreStringListas.getlistadeLinhas(resposta.getRetorno());

        for (String ln : status) {

            if (ln.length() > 1) {

                String tipoAlteracao = String.valueOf(ln.charAt(0));
                String arquivo = String.valueOf(ln.subSequence(2, ln.length()));
                arquivo = arquivo.replaceAll(" ", "");
                System.out.println("tipoAlteracao=" + tipoAlteracao);
                System.out.println("Arquivo" + arquivo);
                System.out.println();

                switch (tipoAlteracao) {
                    case "!":
                        excluidos.add(arquivo);
                        break;
                    case "?":
                    case "A":
                        if (arquivo.endsWith("SBComp")) {
                            break;
                        }
                        if (arquivo.contains("target")) {
                            break;
                        }

                        adicionados.add(arquivo);
                        break;
                    case "M":
                        modificados.add(arquivo);
                }

            }

        }

    }

    public List<String> getExcluidos() {
        return excluidos;
    }

    public void setExcluidos(List<String> excluidos) {
        this.excluidos = excluidos;
    }

    public List<String> getModificados() {
        return modificados;
    }

    public void setModificados(List<String> modificados) {
        this.modificados = modificados;
    }

    public List<String> getAdicionados() {
        return adicionados;
    }

    public void setAdicionados(List<String> adicionados) {
        this.adicionados = adicionados;
    }

    private String separarPorEspaco(List<String> lista) {
        return UtilSBCoreStringListas.getStringDaListaComSeparador(lista, " ");
    }

    public String getExcluidosSeparadosPorEspaco() {
        return separarPorEspaco(excluidos);
    }

    public String getModificadosSeparadosPorEspaco() {
        return separarPorEspaco(modificados);
    }

    public String getAdicionadosSeparadosPorEspaco() {
        return separarPorEspaco(adicionados);
    }

}
