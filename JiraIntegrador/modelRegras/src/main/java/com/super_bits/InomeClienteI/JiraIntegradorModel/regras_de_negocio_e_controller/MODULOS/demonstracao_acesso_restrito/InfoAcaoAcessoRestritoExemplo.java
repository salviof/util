/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.InomeClienteI.JiraIntegradorModel.regras_de_negocio_e_controller.MODULOS.demonstracao_acesso_restrito;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * Anotação de ação do modulo Acesso Restrito
 *
 *
 * 1- Cada modulo precisa de uma anotação deste tipo 2- é atravez dela que os
 * metodos da camada controller são relacionados com as ações do sistema
 *
 * @author <a href="mailto:salviof@gmail.com">Salvio Furbino</a>
 * @since 16/12/2015
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)

public @interface InfoAcaoAcessoRestritoExemplo {

    public boolean padraoBloqueado() default true;

    /**
     *
     * Ao criar uma anotação InfoAcaoNomeDoModulo é importante:<br>
     * 1 - que o método chame acao <br>
     * 2 - Que o enum implemente ItfFabricaDeAcoes
     *
     * @return A ação vinculada ao Método
     */
    public FabAcaoAcessoRestritoExemplo acao();
}
