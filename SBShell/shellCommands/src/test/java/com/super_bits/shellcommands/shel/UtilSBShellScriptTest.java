/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.shel;

import com.super_bits.shellcommands.model.RespostaCMD;
import com.super_bits.shellcommands.model.TIPOCMD;
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

        RespostaCMD resp = UtilSBShellScript.executaComando(TIPOCMD.LNXPING.getComando());
        System.out.println("Tipo de resposta" + resp.getResultado());
        System.out.println("Resposta Retornou=" + resp.getRetorno());

    }

}
