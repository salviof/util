/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model.HashsDeArquivoDeEntidade;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCBytes;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.FabTipoArquivoConhecido;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.acessoArquivo.FabTipoAcessoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.interfaces.ItfCentralPermissaoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimplesSomenteLeitura;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import testesFW.TesteJunitSBPersistencia;

/**
 *
 * @author sfurbino
 */
public class ServicoDeArquivosWebAppS3Test extends TesteJunitSBPersistencia {

    @After
    public void tearDown() {
    }

    /**
     * Test of salvarArquivo method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarArquivo_3args_1() {
        System.out.println("salvarArquivo");
        ItfCampoInstanciado pCampo = null;
        byte[] arquivo = null;
        String pNomeArquivo = "";
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarArquivo(pCampo, arquivo, pNomeArquivo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntrLocalArquivosFormulario method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEntrLocalArquivosFormulario() {
        System.out.println("getEntrLocalArquivosFormulario");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEntrLocalArquivosFormulario();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalResources method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalResources() {
        System.out.println("getEndrLocalResources");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrLocalResources();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoResources method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoResources() {
        System.out.println("getEndrRemotoResources");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoResources();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalResourcesObjeto method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalResourcesObjeto() {
        System.out.println("getEndrLocalResourcesObjeto");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrLocalResourcesObjeto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoResourcesObjeto method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoResourcesObjeto() {
        System.out.println("getEndrRemotoResourcesObjeto");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoResourcesObjeto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalImagens method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalImagens() {
        System.out.println("getEndrLocalImagens");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrLocalImagens();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoImagens method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoImagens() {
        System.out.println("getEndrRemotoImagens");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoImagens();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomeRemotoPastaImagem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetNomeRemotoPastaImagem() {
        System.out.println("getNomeRemotoPastaImagem");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getNomeRemotoPastaImagem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomeLocalPastaImagem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetNomeLocalPastaImagem() {
        System.out.println("getNomeLocalPastaImagem");
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getNomeLocalPastaImagem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalRecursosDoObjeto method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalRecursosDoObjeto() {
        System.out.println("getEndrLocalRecursosDoObjeto");
        Class entidade = null;
        String pGaleria = "";
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrLocalRecursosDoObjeto(entidade, pGaleria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoRecursosDoObjeto method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoRecursosDoObjeto() {
        System.out.println("getEndrRemotoRecursosDoObjeto");
        Class entidade = null;
        FabTipoAcessoArquivo tipoAcesso = null;
        FabTipoArquivoConhecido pTipoArquivo = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoRecursosDoObjeto(entidade, tipoAcesso, pTipoArquivo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoIMGPadraoPorTipoCampo method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoIMGPadraoPorTipoCampo() {
        System.out.println("getEndrRemotoIMGPadraoPorTipoCampo");
        FabTipoAtributoObjeto tipo = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoIMGPadraoPorTipoCampo(tipo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntdrRemotoIMGPadraoPorTipoClasse method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEntdrRemotoIMGPadraoPorTipoClasse() {
        System.out.println("getEntdrRemotoIMGPadraoPorTipoClasse");
        Class entidade = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEntdrRemotoIMGPadraoPorTipoClasse(entidade);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoImagem method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoImagem() {
        System.out.println("getEndrRemotoImagem");
        ComoEntidadeSimplesSomenteLeitura item = null;
        FabTipoAtributoObjeto tipo = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoImagem(item, tipo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoRecursosItem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoRecursosItem() {
        System.out.println("getEndrRemotoRecursosItem");
        ComoEntidadeSimples item = null;
        String galeria = "";
        FabTipoAcessoArquivo pTipoAcesso = null;
        FabTipoArquivoConhecido pTipoArquivo = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoRecursosItem(item, galeria, pTipoAcesso, pTipoArquivo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnterecosLocaisRecursosItem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEnterecosLocaisRecursosItem() {
        System.out.println("getEnterecosLocaisRecursosItem");
        ComoEntidadeSimples item = null;
        String galeria = "";
        ServicoDeArquivosWebAppS3 instance = null;
        List<String> expResult = null;
        List<String> result = instance.getEnterecosLocaisRecursosItem(item, galeria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnterecosRemotosRecursosItem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEnterecosRemotosRecursosItem() {
        System.out.println("getEnterecosRemotosRecursosItem");
        ComoEntidadeSimplesSomenteLeitura item = null;
        ServicoDeArquivosWebAppS3 instance = null;
        List<String> expResult = null;
        List<String> result = instance.getEnterecosRemotosRecursosItem(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrsLocaisDeCategoriasItem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrsLocaisDeCategoriasItem() {
        System.out.println("getEndrsLocaisDeCategoriasItem");
        ComoEntidadeSimplesSomenteLeitura item = null;
        ServicoDeArquivosWebAppS3 instance = null;
        List<String> expResult = null;
        List<String> result = instance.getEndrsLocaisDeCategoriasItem(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomesPastasCategoriasItem method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetNomesPastasCategoriasItem() {
        System.out.println("getNomesPastasCategoriasItem");
        ComoEntidadeSimplesSomenteLeitura item = null;
        ServicoDeArquivosWebAppS3 instance = null;
        List<String> expResult = null;
        List<String> result = instance.getNomesPastasCategoriasItem(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarImagemTodosOsFormatos method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarImagemTodosOsFormatos() {
        System.out.println("salvarImagemTodosOsFormatos");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        InputStream foto = null;
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarImagemTodosOsFormatos(entidade, foto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarImagemTamanhoMedio method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarImagemTamanhoMedio() {
        System.out.println("salvarImagemTamanhoMedio");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        InputStream foto = null;
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarImagemTamanhoMedio(entidade, foto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarImagemTamanhoPequeno method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarImagemTamanhoPequeno() {
        System.out.println("salvarImagemTamanhoPequeno");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        InputStream foto = null;
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarImagemTamanhoPequeno(entidade, foto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarImagemTamanhoGrande method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarImagemTamanhoGrande() {
        System.out.println("salvarImagemTamanhoGrande");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        InputStream foto = null;
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarImagemTamanhoGrande(entidade, foto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarArquivo method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSalvarArquivo_3args_2() {
        System.out.println("salvarArquivo");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        byte[] arquivo = null;
        String nomeCampo = "";
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.salvarArquivo(entidade, arquivo, nomeCampo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of baixarArquivo method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testBaixarArquivo() {
        System.out.println("baixarArquivo");
        ComoEntidadeSimplesSomenteLeitura entidade = null;
        InputStream arqivo = null;
        String pNomeCampoOuCategoria = "";
        String pNomeArquivo = "";
        MapaSubstituicao mapaSubistituicao = null;
        ServicoDeArquivosWebAppS3 instance = null;
        boolean expResult = false;
        boolean result = instance.baixarArquivo(entidade, arqivo, pNomeCampoOuCategoria, pNomeArquivo, mapaSubistituicao);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMethodSalvarArquivo() {
        ServicoDeArquivosWebAppS3 servico = new ServicoDeArquivosWebAppS3();
        HashsDeArquivoDeEntidade hash = new HashsDeArquivoDeEntidade();
        ItfCampoInstanciado campoInstanciado = hash.getCPinst("hashCalculado");
        BufferedInputStream arquivo = UTilSBCoreInputs.getStreamBuffredByURL("https://www.4devs.com.br/4devs_gerador_imagem.php?acao=gerar_imagem&txt_largura=320&txt_altura=240&extensao=png&fundo_r=0.049800067292380845&fundo_g=0.1256103515625&fundo_b=0.029025927186012268&texto_r=0.9302978515625&texto_g=0.8945259004831314&texto_b=0.8945259004831314&texto=Apenas%20um%20teste%20simples&tamanho_fonte=10");
        byte[] imagem1 = UtilCRCBytes.gerarBytePorInputstream(arquivo);

        servico.salvarArquivo(campoInstanciado, imagem1, "teste.jpg");
        servico.salvarArquivo(campoInstanciado, imagem1, "testeasd.jpg");

    }

    /**
     * Test of getEndrLocalImagem method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalImagem_3args() {
        ServicoDeArquivosWebAppS3 servico = new ServicoDeArquivosWebAppS3();
        HashsDeArquivoDeEntidade entidadeTeste = new HashsDeArquivoDeEntidade();
        entidadeTeste.setId(1l);
        String result = servico.getEndrLocalImagem(entidadeTeste, FabTipoAtributoObjeto.IMG_MEDIA, SBCore.getServicoSessao().getSessaoAtual());

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalImagem method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalImagem_ComoEntidadeSimplesSomenteLeitura_FabTipoAtributoObjeto() {

    }

    /**
     * Test of setCentralDePermissao method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testSetCentralDePermissao() {
        System.out.println("setCentralDePermissao");
        ItfCentralPermissaoArquivo pPermissao = null;
        ServicoDeArquivosWebAppS3 instance = null;
        instance.setCentralDePermissao(pPermissao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCentralPermissao method, of class ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetCentralPermissao() {
        System.out.println("getCentralPermissao");
        ServicoDeArquivosWebAppS3 instance = null;
        ItfCentralPermissaoArquivo expResult = null;
        ItfCentralPermissaoArquivo result = instance.getCentralPermissao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrLocalTemporarioArquivoCampoInstanciado method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrLocalTemporarioArquivoCampoInstanciado() {
        System.out.println("getEndrLocalTemporarioArquivoCampoInstanciado");
        ItfCampoInstanciado pCampo = null;
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrLocalTemporarioArquivoCampoInstanciado(pCampo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoBaixarArquivoPastaTemporaria method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoBaixarArquivoPastaTemporaria() {
        System.out.println("getEndrRemotoBaixarArquivoPastaTemporaria");
        String pNomeArquivo = "";
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoBaixarArquivoPastaTemporaria(pNomeArquivo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndrRemotoAbrirArquivoPastaTemporaria method, of class
     * ServicoDeArquivosWebAppS3.
     */
    @Test
    public void testGetEndrRemotoAbrirArquivoPastaTemporaria() {
        System.out.println("getEndrRemotoAbrirArquivoPastaTemporaria");
        String pNomeArquivo = "";
        ServicoDeArquivosWebAppS3 instance = null;
        String expResult = "";
        String result = instance.getEndrRemotoAbrirArquivoPastaTemporaria(pNomeArquivo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Override
    protected void configAmbienteDesevolvimento() {
        SBCore.configurar(new ConfigCoreTestesGestaoDeArquivosProtocoloS3(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaTestesArquivosProtocolos3());
    }

}
