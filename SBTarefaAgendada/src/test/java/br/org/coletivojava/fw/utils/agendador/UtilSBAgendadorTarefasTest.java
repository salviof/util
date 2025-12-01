/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.agendador;

import br.org.coletivojava.fw.utils.agendador.testes.projetoDemo.model.ObjetoComPersistenciaTeste;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCDataHora;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.ItensGenericos.basico.UsuarioAnonimo;

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

        AcaoAgendada novaAcao = new AcaoAgendada(
                //FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_ACAO_DE_ENTIDADE_SIMPLES,
                null,
                UtilCRCDataHora.incrementaSegundos(new Date(), 10),
                new UsuarioAnonimo());
        novaAcao.agendar();
        AcaoAgendada novaAcao2 = new AcaoAgendada(
                //FabAcoesDemosntrativoAgendador.ACAO_DEMONSTRACAO_CTR_SEM_PARAMETRO,
                null,
                UtilCRCDataHora.incrementaSegundos(new Date(), 10));
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
        //SBCore.configurar(new ConfigCoreTestesAgendamento(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        //SBPersistencia.configuraJPA(new ConfigPersistenciaDemonstracaoAgenda());
    }

}
