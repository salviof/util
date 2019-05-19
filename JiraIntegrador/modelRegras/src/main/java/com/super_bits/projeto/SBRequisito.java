/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvioF
 */
public class SBRequisito {

    private static boolean configurado = false;
    private static Class<? extends ItfRequisitoService> classeRequisito;

    public static void configurar(Class<? extends ItfRequisitoService> pClasseRequisito) {
        classeRequisito = pClasseRequisito;
        configurado = true;
    }

    public static ItfRequisitoService getRequisitoServices() {

        try {
            return classeRequisito.newInstance();
        } catch (Throwable ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando classe de serviço de requisitos", ex);
        }
        return null;
    }

}
