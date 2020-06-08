/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfigCoreJunitPadraoDesenvolvedorComPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;

/**
 *
 * @author sfurbino
 */
public class ConfigCoreTestesGestaoDeArquivosProtocoloS3 extends ConfigCoreJunitPadraoDesenvolvedorComPersistencia {

    /**
     *
     * O Core do sistema precisa ser configurado para execução da maioria das
     * tarefas, no core classes importante de ambiente de execução são
     * configuradas como:
     *
     * ClasseDeErro CentralDeMensagens CentralVisualizacao CentralEventos
     * ConfigPermissao ControleDeSessao e Fabricas de Ações do sistema
     *
     * Alterando a implementação destas classes o comportamento ao executar um
     * metodo como enviar mensagem ao usuario pode ser alterado, tortando seu
     * código portatil a diversos ambientes, Web, Mobile, Desktop ou IOC
     *
     * As informações cadastrais do sistemas ficam no arquivo SBProjeto.prop na
     * pasta resource
     *
     * @param pConfig Objeto Com informações de configuração do sistema
     */
    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {

        pConfig.setFabricaDeAcoes(new Class[]{});
        setIgnorarConfiguracaoAcoesDoSistema(true);
        setIgnorarConfiguracaoPermissoes(true);
    }

}
