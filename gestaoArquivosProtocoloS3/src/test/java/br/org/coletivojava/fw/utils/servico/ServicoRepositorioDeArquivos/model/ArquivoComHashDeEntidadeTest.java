/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model;

import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.ConfigCoreTestesGestaoDeArquivosProtocoloS3;
import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.ConfigPersistenciaTestesArquivosProtocolos3;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.List;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author sfurbino
 */
public class ArquivoComHashDeEntidadeTest extends TesteJunitSBPersistencia {

    public ArquivoComHashDeEntidadeTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testeQuery() {
        try {

            Query query = getEMTeste().createNativeQuery("select id,nome,arquivo, (select hashCalculado from HashsDeArquivoDeEntidade where idEntidade=ae.id = ae.id and entidade=\"ArquivoAnexado\") FROM ArquivoAnexado ae;", "QrArquivoDeEntidadeComHash");

            List<ArquivoDeEntidadeComHash> arquivos = query.getResultList();

            for (ArquivoDeEntidadeComHash arquivo : arquivos) {

            }

        } catch (Throwable t) {
            lancarErroJUnit(t);
        }

    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreTestesGestaoDeArquivosProtocoloS3(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaTestesArquivosProtocolos3());
    }

}
