/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.controller;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.model.ObjetoComPersistenciaTeste;
import com.super_bits.modulosSB.Persistencia.dao.ControllerAbstratoSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;

/**
 *
 * @author desenvolvedor
 */
public class ModuloAgendadorDemo extends ControllerAbstratoSBPersistencia {

    @InfoAcaoAgendaDemonstrativo(acao = FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_SIMPLES)
    public static ItfRespostaAcaoDoSistema acaoControlerSimples(ObjetoComPersistenciaTeste p) {
        ItfRespostaAcaoDoSistema resp = getNovaResposta(ObjetoComPersistenciaTeste.class);
        resp.addAviso("Ação executada com sucesso p" + p.getId());
        resp.dispararMensagens();
        return resp;
    }

    @InfoAcaoAgendaDemonstrativo(acao = FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_SEM_PARAMETRO)
    public static ItfRespostaAcaoDoSistema acaoControlerSemParametro() {
        ItfRespostaAcaoDoSistema resp = getNovaResposta(ObjetoComPersistenciaTeste.class);
        resp.addAviso("Ação executada com sucesso");
        resp.dispararMensagens();
        return resp;
    }

    @InfoAcaoAgendaDemonstrativo(acao = FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_COM_ATRIBUTOS)
    public static ItfRespostaAcaoDoSistema acaoControlerComAtributosSimples() {
        ItfRespostaAcaoDoSistema resp = getNovaResposta(ObjetoComPersistenciaTeste.class);
        resp.addAviso("Ação executada com sucesso");
        resp.dispararMensagens();
        return resp;
    }
}
