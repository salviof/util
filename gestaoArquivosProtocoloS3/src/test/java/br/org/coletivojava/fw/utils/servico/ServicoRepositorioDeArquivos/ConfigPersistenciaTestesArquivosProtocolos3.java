/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfigPersistenciaPadrao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;

/**
 *
 * @author sfurbino
 */
public class ConfigPersistenciaTestesArquivosProtocolos3 extends ConfigPersistenciaPadrao {

    @Override
    public String bancoPrincipal() {
        return "CJTestesHashArquivoEntidade";
    }

    @Override
    public Class<? extends ItfFabrica>[] fabricasRegistrosIniciais() {
        return new Class[]{};
    }

}
