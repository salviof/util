/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.config.ConfigCoreTestesAgendamento;
import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.config.ConfigPersistenciaDemonstracaoAgenda;
import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.controller.FabAcoesDemosntrativoAgendador;
import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.model.ObjetoComPersistenciaTeste;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;

import java.util.Date;
import org.junit.Test;
import testesFW.TesteJunit;

/**
 *
 * @author desenvolvedor
 */
public class UtilSBAgendadorTarefasTest extends TesteJunit {

    @Test
    public void testAgendarTarefa() {
        ObjetoComPersistenciaTeste obj = new ObjetoComPersistenciaTeste();
        obj.setId(666);
        obj.setNome("Apenas Teste");
        UtilSBPersistencia.mergeRegistro(obj);
        AcaoAgendada novaAcao = new AcaoAgendada(
                FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_SIMPLES,
                UtilSBCoreDataHora.incrementaSegundos(new Date(), 10),
                obj);
        novaAcao.agendar();
        AcaoAgendada novaAcao2 = new AcaoAgendada(
                FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_SEM_PARAMETRO,
                UtilSBCoreDataHora.incrementaSegundos(new Date(), 10));
        novaAcao2.agendar();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (Throwable t) {

            }
        }

    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreTestesAgendamento(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaDemonstracaoAgenda());
    }

}
