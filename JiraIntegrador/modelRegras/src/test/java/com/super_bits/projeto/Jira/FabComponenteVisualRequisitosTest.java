/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import org.coletivojava.fw.api.objetoNativo.view.componente.ComponenteVisualBase;
import org.junit.Test;

/**
 *
 * @author salvioF
 */
public class FabComponenteVisualRequisitosTest {

    public FabComponenteVisualRequisitosTest() {
    }

    @Test
    public void testValues() {
        ComponenteVisualBase cv = FabComponenteVisualRequisitos.DESCRICAO_ELEMENTO_VINCULADO.getRegistro();
        System.out.println(cv.getId());
    }

    @Test
    public void testValueOf() {
    }

    @Test
    public void testGetFamilia() {
    }

    @Test
    public void testGetComponente() {
    }

    @Test
    public void testGetRegistro() {
    }

}
