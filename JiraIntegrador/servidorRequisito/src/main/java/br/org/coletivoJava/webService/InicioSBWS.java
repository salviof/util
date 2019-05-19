/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;

/**
 *
 * @author desenvolvedor
 */
public class InicioSBWS {

    final static ServicoJiraComentariosSparkRest SERVICO_COMENTARIO = new ServicoJiraComentariosSparkRest();

    public static void main(String[] args) {

        SBCore.configurar(new ConfiguradorCoreWsRequisitos(), SBCore.ESTADO_APP.PRODUCAO);
        SBWSServidorRequisito.iniciaServidor();
    }

}
