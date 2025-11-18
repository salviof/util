/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.superBits.utilitario.editorArquivos.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.EntidadeSimples;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.superBits.utilitario.editorArquivos.ConfiguradorCoreEditor;

/**
 *
 * @author salvio
 */
public class UtilSBEditorPlanilhaTest {

    public UtilSBEditorPlanilhaTest() {
    }

    @InfoObjetoSB(tags = "Item teste", plural = "Itens testes")
    class Itemteste extends EntidadeSimples {

        private String nome;
        private String teste;

        public Itemteste(String pNome, String pTeste) {
            nome = pNome;
            teste = pTeste;
        }

        @Override
        public String getNome() {
            return nome;
        }

        public String getTeste() {
            return teste;
        }

    }

    /**
     * Test of gerarPlanilha method, of class UtilSBEditorPlanilha.
     */
    @Test
    public void testGerarPlanilha() {
        SBCore.configurar(new ConfiguradorCoreEditor(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        MapaObjetosProjetoAtual.adcionarObjeto(Itemteste.class);
        System.out.println("gerarPlanilha");
        String pCaminhoPlanilha = "/home/salvio/temp/teste.xls";
        String pNomeAba = "Planilha teste";
        List<Itemteste> pItens = new ArrayList<>();
        boolean expResult = true;
        pItens.add(new Itemteste("Eita", "o2222pa"));
        pItens.add(new Itemteste("EitaUpoa", "opwwwwwa"));
        pItens.add(new Itemteste("Eitaasddddd", "opaaaaaaaa"));

        boolean result = UtilSBEditorPlanilha.gerarPlanilha(pCaminhoPlanilha, pNomeAba, (List) pItens);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

}
