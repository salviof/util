/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.InomeClienteI.InomeProjetoI.configAppp;

import com.super_bits.config.ConfigPersistenciaIntegrador;
import com.super_bits.config.ConfiguradorJiraIntegradorModel;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * É recomendável que seu projeto tenha uma classse basica de testes, onde os
 * testes genericos posssam ser criados, e o setup do teste possa ser
 * universalizado
 *
 *
 * O Sistema oferece 3 classes básicas para teste.
 *
 * Uma é TesteJunitPersistencia, que possui um entityManager único do tipo
 * Estático para ser chamado por todos os métodos @Test
 *
 * O Outro é o TesteJunit, que obriga criar um método para configurar o
 * ambiente, e possui o metodo lancar exececao, para exibição de relatório de
 * erro mantendo a compatibilidade com o Junit
 *
 * e A TesteAcoes, para testar ações do Sistema
 *
 * Utilize este exemplo como parametro
 *
 * @author sfurbino
 */
public class TesteProjetoExemplo extends TesteJunitSBPersistencia {

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfiguradorJiraIntegradorModel(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaIntegrador());
    }

    @Test
    public void teste() {

        try {
            System.out.println("Veja um exeplo de teste");
        } catch (Throwable t) {
            lancarErroJUnit(t);
        }

    }

}
