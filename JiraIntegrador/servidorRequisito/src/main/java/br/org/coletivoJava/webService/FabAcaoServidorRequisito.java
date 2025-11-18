/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService;

import com.super_bits.modulos.SBAcessosModel.controller.FabModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.controller.InfoModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.fabricas.ComoFabricaDeAcoesPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisito;

/**
 *
 * @author desenvolvedor
 */
@InfoModulosSistemaSB(modulo = FabModulosSistemaSB.PAGINAS_DO_SISTEMA)
public enum FabAcaoServidorRequisito implements ComoFabricaDeAcoesPersistencia {
    @InfoTipoAcaoGestaoEntidade(nomeAcao = "Gestão de Comentarios WS", entidade = ComentarioRequisito.class)
    COMENTARIO_MB,
    COMENTARIO_CTR_CADASTRAR_COMENTARIO,
    COMENTARIO_FRM_LISTAR_COMENTARIOS_DA_ACAO,
    COMENTARIO_FRM_LISTAR_COMENTARIOS_DA_ENTIDADE;

}
