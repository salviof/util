/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model.HashsDeArquivoDeEntidade;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.FabTipoEmpacotamento;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCBytes;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringNomeArquivosEDiretorios;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringSlugs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.CentralDeArquivosAbstrata;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.FabTipoArquivoConhecido;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilCRCArquivos;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.acessoArquivo.FabTipoAcessoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.interfaces.ItfCentralPermissaoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ComoEntidadeGenerica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoEntidadeSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.modulos.objetos.entidade.basico.ComoSessao;

import com.super_bits.modulosSB.webPaginas.arquivosDoProjeto.CentralDeArquivosWebAppServidorSB;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class ServicoDeArquivosWebAppS3 extends CentralDeArquivosAbstrata {

    private CentralDeArquivosWebAppServidorSB centralGenerica = new CentralDeArquivosWebAppServidorSB();
    private boolean s3configurado;
    private boolean temproxy;
    private ConfigModulo configModulo;
    private String dominioProxy;
    private boolean servicoConfigurado = false;
    protected static final List<String> ultimosArquivosSalvos = Collections.synchronizedList(new ArrayList<String>());
    private ItfCentralPermissaoArquivo centralPermissao;

    public ServicoDeArquivosWebAppS3() {
        super(FabTipoEmpacotamento.SITE_WAR,
                TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO.MODULO_PREFIXO_SLUG_DO_MB_DE_GESTAO
        );
        // Porque não iniciar o setup aqui?
        // essa classe é inicializada antes do SBCore ser configurado, portando o acesso aos arquivos de ConfigModulo ainda não estão disponível
        // Esse bloqueio acaba sendo uma boa arquitetura forçando estratégia de lasymode para subir de serviço com inicialização mais complexas..
        //

    }

    public String getEnderecoLocalAlternativo(ComoEntidadeSimplesSomenteLeitura entidade, String pCategoria, String pNome) {
        if (entidade.getId() == null || entidade.getId() == null) {
            return super.getEndrLocalArquivoTemporario(pCategoria, entidade.getClass().getSimpleName(), pNome);
        } else {
            return super.getEndrLocalArquivoItem(entidade, pNome, pCategoria);
        }
    }

    @Override
    public boolean salvarArquivo(ComoEntidadeSimplesSomenteLeitura entidade, byte[] arqivo, String pCategoria, String pNome) {
        try {

            if (!s3configurado || !conectadoComS3 || entidade.getId() == null) {
                return centralGenerica.salvarArquivo(entidade, arqivo, pCategoria, pNome);
            }
            SalvarArquivoS3Hash tarefaSalvarNoS3 = new SalvarArquivoS3Hash(configModulo, UtilCRCReflexaoObjeto.getClassExtraindoProxy(entidade.getClass().getSimpleName()),
                    entidade.getId(), pCategoria, arqivo, pNome);
            tarefaSalvarNoS3.start();
            boolean salvouComSucessoS3 = tarefaSalvarNoS3.aguardarFinalizacao();

            if (salvouComSucessoS3) {
                boolean arquivarnomeobjetoNaentidade = true;
                if (pCategoria != null) {
                    if (pCategoria.equals(FabTipoAtributoObjeto.IMG_PEQUENA.toString())) {
                        if (((ComoEntidadeSimples) entidade).getCampoReflexaoByAnotacao(FabTipoAtributoObjeto.IMG_PEQUENA) == null) {
                            arquivarnomeobjetoNaentidade = false;
                        }
                    }
                }
                if (arquivarnomeobjetoNaentidade) {
                    ((ComoEntidadeSimples) entidade).getCampoInstanciadoByNomeOuAnotacao(pCategoria).setValor(UtilCRCStringSlugs.gerarSlugSimples(pNome));
                }
                //Apagando arquivo local caso sucesso
                String caminhoArquivo;
                if (entidade.getId() == null) {
                    caminhoArquivo = getEndrLocalArquivoTemporario(pCategoria, entidade.getClass().getSimpleName(), pNome);
                } else {
                    caminhoArquivo = getEndrLocalArquivoItem(entidade, pNome, pCategoria);
                }
                try {
                    File arquivotemp = new File(caminhoArquivo);
                    if (arquivotemp.exists()) {
                        arquivotemp.delete();
                    }
                } catch (Throwable t) {

                }
                return true;
            } else {
                System.out.println("Falha Salvando no S3, tentando salvar em arquivo local");
                return centralGenerica.salvarArquivo(entidade, arqivo, pCategoria, pNome);
            }

         ///Qualquer problema, salva no HD
        } catch (Throwable ex) {
            return centralGenerica.salvarArquivo(entidade, arqivo, pCategoria, pNome);

        }

    }

    @Override
    public boolean salvarArquivo(ItfCampoInstanciado pCampo, byte[] arquivo, String pNomeArquivo) {
        inicioSetup();
        return salvarArquivo((ComoEntidadeSimplesSomenteLeitura) pCampo.getObjetoDoAtributo(), arquivo, pCampo.getNomeCamponaClasse(), pNomeArquivo);

    }

    private boolean conectadoComS3;

    private synchronized void inicioSetup() {
        // try {
        //    UtilCRCArquivos.listOpenFiles();
        // } catch (IOException ex) {
        //     Logger.getLogger(ServicoDeArquivosWebAppS3.class.getName()).log(Level.SEVERE, null, ex);
        // }
        if (!servicoConfigurado) {
            try {

                ConfigModulo configTemp = null;
                try {
                    configTemp = new ConfigModulo(FabConfigArquivoDeEntidadeS3.class, SBCore.getClasseLoaderAplicacao());
                } catch (IOException ex) {

                }
                configModulo = configTemp;

                if (configModulo == null) {
                    temproxy = false;
                    s3configurado = false;
                    dominioProxy = null;
                } else {
                    String chavePrivada = configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_SECRETA);
                    String dominioProxyTemp = configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_DOMINIO_PROXY_S3);
                    if (chavePrivada.contains(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_SECRETA.getValorPadrao())) {
                        conectadoComS3 = false;
                    } else {
                        conectadoComS3 = true;
                    }
                    s3configurado = !(chavePrivada != null && chavePrivada.contains("???"));
                    temproxy = !(dominioProxyTemp != null && dominioProxyTemp.contains("???"));
                    if (temproxy) {
                        dominioProxy = dominioProxyTemp;
                    } else {
                        dominioProxy = null;
                    }
                }
            } finally {
                servicoConfigurado = true;
            }
        }
    }

    public static String getIdentificadorArquivo(byte[] pArquivo, String pExtencaoArquivo) {

        if (pArquivo == null) {
            throw new UnsupportedOperationException("Os bytes não foram enviados para geração do hash");
        }
        if (pExtencaoArquivo == null || pExtencaoArquivo.isEmpty()) {
            throw new UnsupportedOperationException("a extenção é obriatória " + pExtencaoArquivo);
        }
        if (pExtencaoArquivo.length() > 4) {
            throw new UnsupportedOperationException("a extnção " + pExtencaoArquivo + " não é reconhecida");

        }

        return UtilCRCArquivos.getHashDoByteArray(pArquivo) + "." + pExtencaoArquivo;
    }

    @Override
    public boolean salvarImagemTodosOsFormatos(ComoEntidadeSimplesSomenteLeitura entidade, InputStream foto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean salvarImagemTamanhoMedio(ComoEntidadeSimplesSomenteLeitura pEntidade, InputStream foto) {

        String categoria = FabTipoAtributoObjeto.IMG_MEDIA.toString();
        if (pEntidade instanceof ComoEntidadeSimples) {
            if (!((ComoEntidadeSimples) pEntidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_MEDIA).isCampoNaoInstanciado()) {
                categoria = ((ComoEntidadeSimples) pEntidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_MEDIA).getNomeCamponaClasse();
            }
        }

        return salvarArquivo(pEntidade, UtilCRCBytes.gerarBytePorInputstream(foto), categoria, "imagemLogoMedio.jpg");
    }

    @Override
    public boolean salvarImagemTamanhoPequeno(ComoEntidadeSimplesSomenteLeitura pEntidade, InputStream foto) {
        String categoria = FabTipoAtributoObjeto.IMG_PEQUENA.toString();
        if (pEntidade instanceof ComoEntidadeSimples) {
            if (((ComoEntidadeSimples) pEntidade).isTemCampoAnotado(FabTipoAtributoObjeto.IMG_PEQUENA)) {
                categoria = ((ComoEntidadeSimples) pEntidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_PEQUENA).getNomeCamponaClasse();
            }
        }
        return salvarArquivo(pEntidade, UtilCRCBytes.gerarBytePorInputstream(foto), categoria, "imagemLogoPequena.jpg");
    }

    @Override
    public boolean salvarImagemTamanhoGrande(ComoEntidadeSimplesSomenteLeitura pEntidade, InputStream foto) {

        String categoria = FabTipoAtributoObjeto.IMG_MEDIA.toString();
        if (pEntidade instanceof ComoEntidadeSimples) {
            if (!((ComoEntidadeSimples) pEntidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_GRANDE).isCampoNaoInstanciado()) {
                categoria = ((ComoEntidadeSimples) pEntidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_GRANDE).getNomeCamponaClasse();
            }
        }
        return salvarArquivo(pEntidade, UtilCRCBytes.gerarBytePorInputstream(foto), categoria, "imagemLogoGrande.jpg");
    }

    @Override
    public boolean salvarArquivo(ComoEntidadeSimplesSomenteLeitura entidade, byte[] arquivo, String nomeCampo) {
        return salvarArquivo(((ComoEntidadeSimples) entidade).getCampoInstanciadoByNomeOuAnotacao(nomeCampo), arquivo, ((ComoEntidadeSimples) entidade).getCampoInstanciadoByNomeOuAnotacao(nomeCampo).getValor().toString());
    }

    @Override
    public String getEntrLocalArquivosFormulario() {
        return centralGenerica.getEntrLocalArquivosFormulario();

    }

    @Override
    public String getEndrLocalResources() {
        return centralGenerica.getEndrLocalResources();
    }

    @Override
    public String getEndrRemotoResources() {
        return centralGenerica.getEndrRemotoResources();
    }

    @Override
    public String getEndrLocalResourcesObjeto() {
        return centralGenerica.getEndrLocalResourcesObjeto();
    }

    @Override
    @Deprecated
    public String getEndrRemotoResourcesObjeto() {
        return null;
    }

    @Override
    public String getEndrLocalImagens() {
        return centralGenerica.getEndrLocalImagens();
    }

    @Override
    public String getEndrRemotoImagens() {
        return centralGenerica.getEndrRemotoImagens();

    }

    @Override
    public String getNomeRemotoPastaImagem() {
        return centralGenerica.getNomeRemotoPastaImagem();
    }

    @Override
    public String getNomeLocalPastaImagem() {
        return centralGenerica.getNomeLocalPastaImagem();
    }

    @Override
    public String getEndrLocalRecursosDoObjeto(Class entidade, String pGaleria) {
        return centralGenerica.getEndrLocalRecursosDoObjeto(entidade, pGaleria);
    }

    @Override
    public String getEndrLocalArquivoItem(ComoEntidadeSimplesSomenteLeitura pItem, String nomeArquivo, String pCampo) {
        inicioSetup();
        if (!s3configurado) {
            return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
        } else {
            if (pItem.getId() == null) {
                return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
            }
            ComoEntidadeSimples objeto = (ComoEntidadeSimples) pItem;
            String nomeImagemRepresentanteEntidade = nomeArquivo;
            ItfCampoInstanciado cp = objeto.getCampoInstanciadoByNomeOuAnotacao(pCampo);

            String hash = (String) UtilSBServicoArqEntidadeS3.getHashDoArquivo(cp);
            if (conectadoComS3) {
                if (hash != null) {
                    removerItemTemporario(pItem, pCampo, nomeArquivo, hash);
                    return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET) + ".s3.amazonaws.com/" + hash;
                } else {

                    String arquivoLocal = centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
                    File arquivo = new File(arquivoLocal);
                    if (arquivo.exists()) {
                        try {

                            new SalvarArquivoS3Hash(configModulo, pItem.getClass(), pItem.getId(), cp.getNomeCamponaClasse(), UtilCRCBytes.gerarBytesPorArquivo(arquivo), nomeImagemRepresentanteEntidade).start();

                            return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
                        } catch (Exception t) {
                            return null;
                        }
                    }

                    return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);

                }

            } else {
                return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
            }
        }
    }

    @Override
    public String getEndrLocalImagem(ComoEntidadeSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo,
            ComoSessao pSessao
    ) {
        inicioSetup();
        if (!s3configurado || item.getId() == null) {
            return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
        } else {
            if (item.getId() == null) {
                return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
            }
            ComoEntidadeSimples objeto = (ComoEntidadeSimples) item;
            String nomeImagemRepresentanteEntidade = tipo.toString() + ".jpg";
            switch (tipo) {

                case IMG_PEQUENA:

                case IMG_MEDIA:

                case IMG_GRANDE:
                    break;
                default:
                    tipo = FabTipoAtributoObjeto.IMG_PEQUENA;

            }
            ItfCampoInstanciado cp = objeto.getCampoInstanciadoByAnotacao(tipo);
            String nomeCampoidentificador = null;
            if (cp == null) {
                nomeCampoidentificador = FabTipoAtributoObjeto.IMG_PEQUENA.toString();
            } else {
                String nomecampoidentificador = cp.getNomeCamponaClasse();
            }

            String hashArquivoArmazenado = UtilSBServicoArqEntidadeS3.getHashDoArquivo(item.getClass().getSimpleName(),
                    nomeCampoidentificador, String.valueOf(item.getId()));
            String arquivoLocal = centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
            if (hashArquivoArmazenado != null) {
                removerArquivoImagemTemporario(item, tipo, pSessao, hashArquivoArmazenado);
                return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET) + ".s3.amazonaws.com/" + hashArquivoArmazenado;
            } else {

                File arquivo = new File(arquivoLocal);
                if (arquivo.exists()) {
                    try {
                        if (s3configurado) {
                            SalvarArquivoS3Hash trheadSalvarArquivoS3 = new SalvarArquivoS3Hash(configModulo, item.getClass(), item.getId(), nomeCampoidentificador, UtilCRCBytes.gerarBytesPorArquivo(arquivo), nomeImagemRepresentanteEntidade);
                            trheadSalvarArquivoS3.start();

                        }
                        return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
                    } catch (Exception t) {
                        return null;
                    }
                }

                return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);

            }

        }

    }

    @Override
    @Deprecated
    public String getEndrRemotoRecursosDoObjeto(Class entidade, FabTipoAcessoArquivo tipoAcesso,
            FabTipoArquivoConhecido pTipoArquivo
    ) {
        inicioSetup();
        return centralGenerica.getEndrRemotoRecursosDoObjeto(entidade, tipoAcesso, pTipoArquivo);

    }

    @Override
    public String getEndrRemotoIMGPadraoPorTipoCampo(FabTipoAtributoObjeto tipo
    ) {
        inicioSetup();
        return centralGenerica.getEndrRemotoIMGPadraoPorTipoCampo(tipo);
    }

    @Override
    public String getEntdrRemotoIMGPadraoPorTipoClasse(Class entidade
    ) {
        inicioSetup();
        return centralGenerica.getEntdrRemotoIMGPadraoPorTipoClasse(entidade);
    }

    private void removerItemTemporario(ComoEntidadeSimplesSomenteLeitura pItem, String pCampo, String pNomeArquivo, String pHash) {

        if (pHash != null) {
            if (pItem.getId() != null && pItem.getId() > 0) {
                new Thread() {
                    @Override
                    public void run() {
                        String arquivoLocal = centralGenerica.getEndrLocalArquivoItem(pItem, pNomeArquivo, pCampo);
                        File arquivoLocalTemporario = new File(arquivoLocal);
                        if (arquivoLocalTemporario.exists()) {
                            arquivoLocalTemporario.delete();
                        }
                    }

                }.start();
            }
        }
    }

    private void removerArquivoImagemTemporario(ComoEntidadeSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo, ComoSessao pSessao, String pHash) {
        String arquivoLocal = centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
        if (pHash != null) {
            if (item.getId() != null && item.getId() > 0.) {
                new Thread() {
                    @Override
                    public void run() {

                        File arquivoLocalTemporario = new File(arquivoLocal);
                        if (arquivoLocalTemporario.exists()) {
                            arquivoLocalTemporario.delete();
                        }
                    }

                }.start();
            }
        }
    }

    @Override
    public String getEndrRemotoImagem(ComoEntidadeSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo
    ) {
        inicioSetup();

        String campo = tipo.toString();
        ItfCampoInstanciado cpInstanciado = ((ComoEntidadeSimples) item).getCampoInstanciadoByAnotacao(tipo);
        if (item.getId() != null && item.getId() > 0) {
            String campoCategoria = tipo.name();
            if (cpInstanciado != null) {
                campoCategoria = cpInstanciado.getNomeCamponaClasse();
            }
            String hashArquivoArmazenado = UtilSBServicoArqEntidadeS3.getHashDoArquivo(item.getClass().getSimpleName(),
                    campoCategoria, String.valueOf(item.getId()));
            if (hashArquivoArmazenado != null && s3configurado && item.getId() != null) {

                removerArquivoImagemTemporario(item, tipo, null, hashArquivoArmazenado);
            }
        }

        String nomeArquivo;
        FabTipoArquivoConhecido tipoConhecido = FabTipoArquivoConhecido.IMAGEM_WEB;

        switch (tipo) {
            case IMG_GRANDE:
                tipoConhecido = FabTipoArquivoConhecido.IMAGE_REPRESENTATIVA_ENTIDADE_GRANDE;
                break;
            case IMG_PEQUENA:
                tipoConhecido = FabTipoArquivoConhecido.IMAGE_REPRESENTATIVA_ENTIDADE_PEQUENO;
                break;
            case IMG_MEDIA:
                tipoConhecido = FabTipoArquivoConhecido.IMAGE_REPRESENTATIVA_ENTIDADE_MEDIO;
                break;

        }
        switch (tipo) {
            case IMG_GRANDE:
            case IMG_PEQUENA:
            case IMG_MEDIA:
                nomeArquivo = tipo.toString() + ".jpg";
                break;
            default: {
                if (cpInstanciado == null) {
                    nomeArquivo = tipo.toString() + ".jpg";
                } else {
                    nomeArquivo = cpInstanciado.getValor().toString();
                }

            }

        }
        if (cpInstanciado != null) {

            campo = ((ComoEntidadeSimples) item).getCampoInstanciadoByAnotacao(tipo).getNomeCamponaClasse();

        }

        return getEndrRemotoArquivoItem(item, nomeArquivo, campo, FabTipoAcessoArquivo.VISUALIZAR,
                tipoConhecido);

    }

    @Override
    public String getEndrRemotoArquivoItem(ComoEntidadeSimplesSomenteLeitura pItem, String nomeArquivo,
            FabTipoAcessoArquivo pTipoAcesso, FabTipoArquivoConhecido pTipoArquivo
    ) {

        return getEndrRemotoArquivoItem(pItem, nomeArquivo, FabTipoAtributoObjeto.IMG_PEQUENA.toString(), pTipoAcesso, pTipoArquivo);

    }

    @Override
    public String getEndrRemotoArquivoItem(ComoEntidadeSimplesSomenteLeitura pItem, String nomeArquivo,
            String categoria, FabTipoAcessoArquivo pTipoAcesso,
            FabTipoArquivoConhecido pTipoArquivo
    ) {
        try {
            if (pTipoAcesso.equals(FabTipoAcessoArquivo.BAIXAR)) {
                throw new UnsupportedOperationException("Baixar arquivo ainda não é suportado via acesso direto ao bucket");

            }

            if (conectadoComS3) {

                try {
                    String hash = UtilSBServicoArqEntidadeS3.getHashDoArquivo(pItem.getClass().getSimpleName(), categoria, String.valueOf(pItem.getId()));

                    if (hash == null) {
                        throw new UnsupportedOperationException("Baixar arquivo ainda não é suportado via acesso direto ao bucket");
                    }

                    if (temproxy) {
                        return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_DOMINIO_PROXY_S3) + "/" + hash;
                    } else {
                        return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET) + ".s3.amazonaws.com/" + hash;
                    }
                } catch (Throwable t) {
                    throw new UnsupportedOperationException("Baixar arquivo ainda não é suportado via acesso direto ao bucket");
                }

            } else {
                throw new UnsupportedOperationException("s3 desconectado");
            }
        } catch (Throwable t) {
            switch (pTipoArquivo) {

                case IMAGE_REPRESENTATIVA_ENTIDADE_PEQUENO:
                    return centralGenerica.getEndrRemotoImagem(pItem, FabTipoAtributoObjeto.IMG_PEQUENA);
                case IMAGE_REPRESENTATIVA_ENTIDADE_MEDIO:
                    return centralGenerica.getEndrRemotoImagem(pItem, FabTipoAtributoObjeto.IMG_MEDIA);
                case IMAGE_REPRESENTATIVA_ENTIDADE_GRANDE:
                    return centralGenerica.getEndrRemotoImagem(pItem, FabTipoAtributoObjeto.IMG_GRANDE);

                default:
                    return centralGenerica.getEndrRemotoArquivoItem(pItem, nomeArquivo, categoria, pTipoAcesso, pTipoArquivo);

            }
        }

    }

    @Override
    @Deprecated
    public String getEndrRemotoRecursosItem(ComoEntidadeSimples item, String galeria,
            FabTipoAcessoArquivo pTipoAcesso, FabTipoArquivoConhecido pTipoArquivo
    ) {
        return centralGenerica.getEndrRemotoRecursosItem(item, galeria, pTipoAcesso, pTipoArquivo);

    }

    @Override
    public List<String> getEnterecosLocaisRecursosItem(ComoEntidadeSimples item, String galeria
    ) {
        return centralGenerica.getEnterecosLocaisRecursosItem(item, galeria);
    }

    @Override
    public List<String> getEnterecosRemotosRecursosItem(ComoEntidadeSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getEndrsLocaisDeCategoriasItem(ComoEntidadeSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getNomesPastasCategoriasItem(ComoEntidadeSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean baixarArquivo(ComoEntidadeSimplesSomenteLeitura entidade, InputStream arqivo,
            String pNomeCampoOuCategoria, String pNomeArquivo,
            MapaSubstituicao mapaSubistituicao
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEndrLocalImagem(ComoEntidadeSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo
    ) {
        return getEndrLocalImagem(item, tipo, SBCore.getServicoSessao().getSessaoAtual());
    }

    @Override
    public void setCentralDePermissao(ItfCentralPermissaoArquivo pPermissao
    ) {
        inicioSetup();
        try {
            centralGenerica.setCentralDePermissao(pPermissao);
            ComoEntidadeGenerica.class.newInstance().getImgPequena();
        } catch (InstantiationException ex) {
            Logger.getLogger(ServicoDeArquivosWebAppS3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServicoDeArquivosWebAppS3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ItfCentralPermissaoArquivo getCentralPermissao() {
        return centralGenerica.getCentralPermissao();
    }

    @Override
    public String getEndrLocalTemporarioArquivoCampoInstanciado(ItfCampoInstanciado pCampo
    ) {
        return centralGenerica.getEndrLocalTemporarioArquivoCampoInstanciado(pCampo);
    }

    @Override
    public String getEndrRemotoBaixarArquivoPastaTemporaria(String pNomeArquivo
    ) {
        return centralGenerica.getEndrRemotoBaixarArquivoPastaTemporaria(pNomeArquivo);
    }

    @Override
    public String getEndrRemotoAbrirArquivoPastaTemporaria(String pNomeArquivo
    ) {
        return centralGenerica.getEndrRemotoAbrirArquivoPastaTemporaria(pNomeArquivo);
    }

    private String obterNomeDoArquivoPeloHash(ItfCampoInstanciado pCampo) {

        EntityManager em = UtilSBPersistencia.getNovoEMIniciandoTransacao();
        try {
            final String pEntidade = UtilCRCReflexaoObjeto.getClassExtraindoProxy(pCampo.getObjetoDoAtributo().getClass().getSimpleName()).getSimpleName();
            final String pIdEntidade = String.valueOf(pCampo.getObjetoDoAtributo().getId());
            final String pNomeCampo = pCampo.getNomeCamponaClasse();
            //final String pHashDoArquivo;

            String hqlHashDeCampoInstanciado = UtilSBServicoArqEntidadeS3.getHSQLPesquisaHashsDeArquivoDeEntidade(pEntidade, pNomeCampo, pIdEntidade);

            Query consulta = em.createQuery(hqlHashDeCampoInstanciado);
            consulta.setMaxResults(1);
            HashsDeArquivoDeEntidade hash = (HashsDeArquivoDeEntidade) consulta.getSingleResult();

            if (hash != null) {
                if (UtilCRCStringValidador.isNuloOuEmbranco(pCampo.getValor())) {
                    if (!UtilCRCStringValidador.isNuloOuEmbranco(hash.getNome())) {
                        String extencao = UtilCRCStringNomeArquivosEDiretorios.getExtencaoNomeArquivoSemPonto(hash.getNome());

                        ComoEntidadeSimples beanSimples = UtilSBPersistencia.loadEntidade(pCampo.getObjetoDoAtributo(), em);
                        beanSimples.getCPinst(pCampo.getNomeCamponaClasse()).setValor("arquivo_sem_nome" + extencao);
                        UtilSBPersistencia.mergeRegistro(beanSimples, em);
                    }
                }
                if (UtilCRCStringValidador.isNuloOuEmbranco(hash.getNome())) {

                    return hash.getNome();

                }
            }
            if (pCampo.getValor() == null) {
                return null;
            }
            return pCampo.getValor().toString();
        } finally {
            UtilSBPersistencia.finzalizaTransacaoEFechaEM(em);
        }

    }

    @Override
    public String getEndrRemotoArquivoCampoInstanciadoAbrir(ItfCampoInstanciado pCampo
    ) {
        inicioSetup();
        if (!s3configurado) {
            return centralGenerica.getEndrRemotoArquivoCampoInstanciadoAbrir(pCampo);
        }
        String urlAbrir = centralGenerica.getEndrRemotoArquivoCampoInstanciadoAbrir(pCampo);

        if (urlAbrir == null) {
            try {
                if (pCampo == null) {
                    throw new UnsupportedOperationException("Enviado campo instanciado nulo para obter URL");
                }
                String nomeArquivo = obterNomeDoArquivoPeloHash(pCampo);
                FabTipoArquivoConhecido tipo = FabTipoArquivoConhecido.getTipoArquivoByNomeArquivo(nomeArquivo);
                String urlInicio = centralGenerica.getEndrRemotoRecursosItem(pCampo.getObjetoDoAtributo(), urlAbrir, FabTipoAcessoArquivo.VISUALIZAR, tipo);
                return urlInicio;
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo Endereço remoto de campo instanciado", t);
                return null;
            }
        }

        return urlAbrir;

    }

    @Override
    public String getEndrRemotoArquivoCampoInstanciadoBaixar(ItfCampoInstanciado pCampo
    ) {
        inicioSetup();
        if (!s3configurado) {
            return centralGenerica.getEndrRemotoArquivoCampoInstanciadoBaixar(pCampo);
        }
        String urlBaixar = centralGenerica.getEndrRemotoArquivoCampoInstanciadoBaixar(pCampo);

        if (urlBaixar == null) {
            try {
                if (pCampo == null) {
                    throw new UnsupportedOperationException("Enviado campo instanciado nulo para obter URL");
                }
                String nomeArquivo = obterNomeDoArquivoPeloHash(pCampo);
                FabTipoArquivoConhecido tipo = FabTipoArquivoConhecido.getTipoArquivoByNomeArquivo(nomeArquivo);
                String urlInicio = centralGenerica.getEndrRemotoRecursosItem(pCampo.getObjetoDoAtributo(), urlBaixar, FabTipoAcessoArquivo.BAIXAR, tipo);
                return urlInicio;
            } catch (Throwable t) {
                SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo Endereço remoto de campo instanciado", t);
                return null;
            }
        }

        return urlBaixar;
    }

    @Override
    public boolean isTemImagem(ComoEntidadeSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo
    ) {
        String jpql = UtilSBServicoArqEntidadeS3.getHSQLPesquisaHashsDeArquivoDeEntidade(item.getClass().getSimpleName(), tipo.toString(), String.valueOf(item.getId()));
        String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + jpql);
        if (valor == null) {
            return centralGenerica.isTemImagem(item, tipo); //To change body of generated methods, choose Tools | Templates.
        } else {
            return true;
        }

    }

    @Override
    public boolean isExisteArquivo(ItfCampoInstanciado pCampo) {
        return super.isExisteArquivo(pCampo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEndrLocalArquivoCampoInstanciado(ItfCampoInstanciado pCampo) {
        String caminhoArquivo = getEndrLocalArquivoItem((ComoEntidadeSimplesSomenteLeitura) pCampo.getObjetoDoAtributo(), (String) pCampo.getValor(), pCampo.getNomeCamponaClasse());
        return caminhoArquivo;
    }

    @Override
    public boolean excluirArquivo(ItfCampoInstanciado pCampo) {
        try {
            String hql = UtilSBServicoArqEntidadeS3.getHSQLPesquisaHashsDeArquivoDeEntidade(UtilCRCReflexaoObjeto.getClassExtraindoProxy(pCampo.getObjetoDoAtributo().getClass().getSimpleName()).getSimpleName(),
                    pCampo.getNomeCamponaClasse(), String.valueOf(pCampo.getObjetoDoAtributo().getId()));
            String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + hql);
            if (ultimosArquivosSalvos.contains(valor)) {
                ServicoDeArquivosWebAppS3.ultimosArquivosSalvos.remove(valor);
            }
            if (valor == null) {
                return centralGenerica.excluirArquivo(pCampo);
            } else {
                String hqlExlusao = "delete from " + HashsDeArquivoDeEntidade.class.getSimpleName() + " where "
                        + " entidade ='" + UtilCRCReflexaoObjeto.getClassExtraindoProxy(pCampo.getObjetoDoAtributo().getClass().getSimpleName()).getSimpleName() + "'"
                        + " and idEntidade=" + pCampo.getObjetoDoAtributo().getId()
                        + " and atributo='" + pCampo.getNomeCamponaClasse() + "'";

                return UtilSBPersistencia.executaSQL(hqlExlusao);

            }
        } catch (Throwable t) {
            return false;
        }

    }

    @Override
    public String getHashArquivoDeEntidadeRegistrado(ItfCampoInstanciado pCampo) {
        return UtilSBServicoArqEntidadeS3.getHashDoArquivo(pCampo);

    }

}
