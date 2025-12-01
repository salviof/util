/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.transportecomunicacao;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCEmail;
import com.super_bits.modulosSB.SBCore.modulos.email.ConfigEmailServersProjeto;
import testesFW.TesteJUnitBasicoSemPersistencia;

/**
 *
 * @author desenvolvedor
 */
public class TestesComunicacaoVip extends TesteJUnitBasicoSemPersistencia {

    @Override
    protected void configAmbienteDesevolvimento() {
        super.configAmbienteDesevolvimento(); //To change body of generated methods, choose Tools | Templates.
        UtilCRCEmail.configurar(new ConfigEmailServersProjeto("mail.casanovadigital.com.br", "contato@superkompras.com.br", "superkompras@123"));
    }

}
