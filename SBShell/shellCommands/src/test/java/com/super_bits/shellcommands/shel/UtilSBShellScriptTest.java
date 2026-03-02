/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.shel;

import com.super_bits.shellcommands.model.Comando;
import com.super_bits.shellcommands.model.RespostaCMD;
import com.super_bits.shellcommands.model.TIPOCMD;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sfurbino
 */
public class UtilSBShellScriptTest {

    public UtilSBShellScriptTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of executaComando method, of class UtilSBShellScript.
     */
    @Test
    public void testExecutaComando() {

        Comando comando = TIPOCMD.LNXPING.getComando();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ip", "127.0.0.1");
        comando.setParametros(parametros);
        RespostaCMD resp = UtilSBShellScript.executaComando(comando);
        System.out.println("Tipo de resposta" + resp.getResultado());
        System.out.println("Resposta Retornou=" + resp.getRetorno());

    }

}
