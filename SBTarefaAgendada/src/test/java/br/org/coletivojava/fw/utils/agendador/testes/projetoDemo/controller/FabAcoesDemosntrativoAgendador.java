/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.controller;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.model.ObjetoComPersistenciaTeste;
import com.super_bits.modulos.SBAcessosModel.controller.FabModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.controller.InfoModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.fabricas.ItfFabricaDeAcoesPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;

/**
 *
 * @author desenvolvedor
 */
@InfoModulosSistemaSB(modulo = FabModulosSistemaSB.PAGINAS_DO_SISTEMA)
public enum FabAcoesDemosntrativoAgendador implements ItfFabricaDeAcoesPersistencia {

    @InfoTipoAcaoGestaoEntidade(entidade = ObjetoComPersistenciaTeste.class)
    ACAO_DEMONSTRACAO_MB_ACOES_TESTAVEIS,
    ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_SIMPLES,
    ACAO_DEMONSTRACAO_CTR_SEM_PARAMETRO,
    ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_COM_ATRIBUTOS;

}
