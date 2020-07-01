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
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreBytes;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.stringSubstituicao.MapaSubstituicao;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.CentralDeArquivosAbstrata;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.FabTipoArquivoConhecido;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.acessoArquivo.FabTipoAcessoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.interfaces.ItfCentralPermissaoArquivo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfSessao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.ItemGenerico;
import com.super_bits.modulosSB.webPaginas.arquivosDoProjeto.CentralDeArquivosWebAppServidorSB;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

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

    private synchronized void inicioSetup() {
        // try {
        //    UtilSBCoreArquivos.listOpenFiles();
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

        if (pExtencaoArquivo.length() > 4) {
            throw new UnsupportedOperationException("a extnção " + pExtencaoArquivo + " não é reconhecida");

        }
        return UtilSBCoreArquivos.getHashDoByteArray(pArquivo) + pExtencaoArquivo;
    }

    protected static String getHSQLPesquisaHashDaEntidade(String pNomeEntidade, String pCampo, String id) {
        String jpql = " from " + HashsDeArquivoDeEntidade.class.getSimpleName()
                + " where entidade ='" + pNomeEntidade
                + "' and atributo='" + pCampo + "'"
                + " and idEntidade=" + id;

        return jpql;

    }

    protected static String getHSQLPesquisaHashDoArquivo(String pIdentificadorHashArquivo, String pNomeEntidade, String pCampo, String id) {
        String jpql = " from " + HashsDeArquivoDeEntidade.class.getSimpleName()
                + " where hashCalculado='" + pIdentificadorHashArquivo + "' and entidade ='" + pNomeEntidade
                + "' and atributo='" + pCampo + "'"
                + " and idEntidade=" + id;

        return jpql;

    }

    @Override
    public boolean salvarArquivo(ItfCampoInstanciado pCampo, byte[] arquivo, String pNomeArquivo) {
        inicioSetup();

        try {

            if (!s3configurado) {
                return centralGenerica.salvarArquivo(pCampo, arquivo, pNomeArquivo);
            }
            SalvarArquivoS3Hash tarefaSalvarNoS3 = new SalvarArquivoS3Hash(configModulo, pCampo.getObjetoDoAtributo().getClass(), pCampo.getObjetoDoAtributo().getId(), pCampo.getNomeCamponaClasse(), arquivo, pNomeArquivo);
            tarefaSalvarNoS3.start();
            if (!tarefaSalvarNoS3.aguardarFinalizacao()) {
                return centralGenerica.salvarArquivo(pCampo, arquivo, pNomeArquivo);
            }

            return tarefaSalvarNoS3.aguardarFinalizacao();
            ///Qualquer problema, salva no HD
        } catch (Throwable ex) {
            return centralGenerica.salvarArquivo(pCampo, arquivo, pNomeArquivo);

        }

    }

    @Override
    public boolean salvarImagemTodosOsFormatos(ItfBeanSimplesSomenteLeitura entidade, InputStream foto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean salvarImagemTamanhoMedio(ItfBeanSimplesSomenteLeitura entidade, InputStream foto) {

        return salvarArquivo(((ItfBeanSimples) entidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_PEQUENA), UtilSBCoreBytes.gerarBytePorInputstream(foto), "imagemLogoPequeno.jpg");
    }

    @Override
    public boolean salvarImagemTamanhoPequeno(ItfBeanSimplesSomenteLeitura entidade, InputStream foto) {
        return salvarArquivo(((ItfBeanSimples) entidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_MEDIA), UtilSBCoreBytes.gerarBytePorInputstream(foto), "imagemLogoMedio.jpg");
    }

    @Override
    public boolean salvarImagemTamanhoGrande(ItfBeanSimplesSomenteLeitura entidade, InputStream foto) {
        return salvarArquivo(((ItfBeanSimples) entidade).getCampoInstanciadoByAnotacao(FabTipoAtributoObjeto.IMG_GRANDE), UtilSBCoreBytes.gerarBytePorInputstream(foto), "imagemLogoGrande.jpg");
    }

    @Override
    public boolean salvarArquivo(ItfBeanSimplesSomenteLeitura entidade, byte[] arquivo, String nomeCampo) {
        return salvarArquivo(((ItfBeanSimples) entidade).getCampoInstanciadoByNomeOuAnotacao(nomeCampo), arquivo, ((ItfBeanSimples) entidade).getCampoInstanciadoByNomeOuAnotacao(nomeCampo).getValor().toString());
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
    public String getEndrLocalArquivoCampoInstanciado(ItfCampoInstanciado pCampo) {
        String caminhoArquivo = getEndrLocalArquivoItem((ItfBeanSimplesSomenteLeitura) pCampo.getObjetoDoAtributo(), (String) pCampo.getValor(), pCampo.getNomeCamponaClasse());
        return caminhoArquivo;
    }

    @Override
    public String getEndrLocalArquivoItem(ItfBeanSimplesSomenteLeitura pItem, String nomeArquivo, String pCampo) {
        inicioSetup();
        if (!s3configurado) {
            return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
        } else {
            if (pItem.getId() == 0) {
                return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
            }
            ItfBeanSimples objeto = (ItfBeanSimples) pItem;
            String nomeImagemRepresentanteEntidade = nomeArquivo;
            ItfCampoInstanciado cp = objeto.getCampoInstanciadoByNomeOuAnotacao(pCampo);

            String nomecampoidentificador = cp.getNomeCamponaClasse();

            String hql = getHSQLPesquisaHashDaEntidade(UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(pItem.getClass().getSimpleName()).getSimpleName(),
                    nomecampoidentificador, String.valueOf(pItem.getId()));
            String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + hql);

            if (valor != null) {

                return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET) + ".s3.amazonaws.com/" + valor;
            } else {

                String arquivoLocal = centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
                File arquivo = new File(arquivoLocal);
                if (arquivo.exists()) {
                    try {

                        new SalvarArquivoS3Hash(configModulo, pItem.getClass(), pItem.getId(), nomecampoidentificador, UtilSBCoreBytes.gerarBytesPorArquivo(arquivo), nomeImagemRepresentanteEntidade).start();

                        return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);
                    } catch (Exception t) {
                        return null;
                    }
                }

                return centralGenerica.getEndrLocalArquivoItem(pItem, nomeArquivo, pCampo);

            }

        }
    }

    @Override
    public String getEndrLocalImagem(ItfBeanSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo, ItfSessao pSessao) {
        inicioSetup();
        if (!s3configurado) {
            return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
        } else {
            if (item.getId() == 0) {
                return centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
            }
            ItfBeanSimples objeto = (ItfBeanSimples) item;
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
            String hql = getHSQLPesquisaHashDaEntidade(UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(item.getClass().getSimpleName()).getSimpleName(),
                    nomeCampoidentificador, String.valueOf(item.getId()));
            String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + hql);

            if (valor != null) {

                return "https://" + configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET) + ".s3.amazonaws.com/" + valor;
            } else {

                String arquivoLocal = centralGenerica.getEndrLocalImagem(item, tipo, pSessao);
                File arquivo = new File(arquivoLocal);
                if (arquivo.exists()) {
                    try {

                        new SalvarArquivoS3Hash(configModulo, item.getClass(), item.getId(), nomeCampoidentificador, UtilSBCoreBytes.gerarBytesPorArquivo(arquivo), nomeImagemRepresentanteEntidade).start();

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
    public String getEndrRemotoRecursosDoObjeto(Class entidade, FabTipoAcessoArquivo tipoAcesso, FabTipoArquivoConhecido pTipoArquivo) {
        inicioSetup();
        return centralGenerica.getEndrRemotoRecursosDoObjeto(entidade, tipoAcesso, pTipoArquivo);

    }

    @Override
    public String getEndrRemotoIMGPadraoPorTipoCampo(FabTipoAtributoObjeto tipo) {
        inicioSetup();
        return centralGenerica.getEndrRemotoIMGPadraoPorTipoCampo(tipo);
    }

    @Override
    public String getEntdrRemotoIMGPadraoPorTipoClasse(Class entidade) {
        inicioSetup();
        return centralGenerica.getEntdrRemotoIMGPadraoPorTipoClasse(entidade);
    }

    @Override
    public String getEndrRemotoImagem(ItfBeanSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo) {
        inicioSetup();
        String campo = tipo.toString();
        ItfCampoInstanciado cpInstanciado = ((ItfBeanSimples) item).getCampoInstanciadoByAnotacao(tipo);
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
            campo = ((ItfBeanSimples) item).getCampoInstanciadoByAnotacao(tipo).getNomeCamponaClasse();

        }

        return getEndrRemotoArquivoItem(item, nomeArquivo, campo, FabTipoAcessoArquivo.VISUALIZAR,
                tipoConhecido);

    }

    @Override
    public String getEndrRemotoArquivoItem(ItfBeanSimplesSomenteLeitura pItem, String nomeArquivo, FabTipoAcessoArquivo pTipoAcesso, FabTipoArquivoConhecido pTipoArquivo) {

        return getEndrRemotoArquivoItem(pItem, nomeArquivo, FabTipoAtributoObjeto.IMG_PEQUENA.toString(), pTipoAcesso, pTipoArquivo);

    }

    @Override
    public String getEndrRemotoArquivoItem(ItfBeanSimplesSomenteLeitura pItem, String nomeArquivo, String categoria, FabTipoAcessoArquivo pTipoAcesso, FabTipoArquivoConhecido pTipoArquivo) {
        try {
            if (pTipoAcesso.equals(FabTipoAcessoArquivo.BAIXAR)) {
                throw new UnsupportedOperationException("Baixar arquivo ainda não é suportado via acesso direto ao bucket");

            }
            String jpql = "select hashCalculado ".concat(getHSQLPesquisaHashDaEntidade(pItem.getClass().getSimpleName(), categoria, String.valueOf(pItem.getId())));
            EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
            try {
                try {
                    String hash = (String) UtilSBPersistencia.getRegistroByJPQL(jpql, em);
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
            } finally {
                UtilSBPersistencia.fecharEM(em);
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
    public String getEndrRemotoRecursosItem(ItfBeanSimples item, String galeria, FabTipoAcessoArquivo pTipoAcesso, FabTipoArquivoConhecido pTipoArquivo) {
        return centralGenerica.getEndrRemotoRecursosItem(item, galeria, pTipoAcesso, pTipoArquivo);

    }

    @Override
    public List<String> getEnterecosLocaisRecursosItem(ItfBeanSimples item, String galeria
    ) {
        return centralGenerica.getEnterecosLocaisRecursosItem(item, galeria);
    }

    @Override
    public List<String> getEnterecosRemotosRecursosItem(ItfBeanSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getEndrsLocaisDeCategoriasItem(ItfBeanSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getNomesPastasCategoriasItem(ItfBeanSimplesSomenteLeitura item
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean baixarArquivo(ItfBeanSimplesSomenteLeitura entidade, InputStream arqivo,
            String pNomeCampoOuCategoria, String pNomeArquivo,
            MapaSubstituicao mapaSubistituicao
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEndrLocalImagem(ItfBeanSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo
    ) {
        return getEndrLocalImagem(item, tipo, SBCore.getServicoSessao().getSessaoAtual());
    }

    @Override
    public void setCentralDePermissao(ItfCentralPermissaoArquivo pPermissao
    ) {
        inicioSetup();
        try {
            centralGenerica.setCentralDePermissao(pPermissao);
            ItemGenerico.class.newInstance().getImgPequena();
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

    @Override
    public String getEndrRemotoArquivoCampoInstanciadoAbrir(ItfCampoInstanciado pCampo
    ) {
        inicioSetup();
        if (!s3configurado) {
            return getEndrRemotoArquivoCampoInstanciadoAbrir(pCampo);
        }
        String prefixo = "https://";
        if (temproxy) {
            prefixo = prefixo.concat(configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_DOMINIO_PROXY_S3));
        } else {
            prefixo = prefixo.concat(configModulo.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET));
        }

        String jpql = getHSQLPesquisaHashDaEntidade(UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(HashsDeArquivoDeEntidade.class.getSimpleName()).getSimpleName(), pCampo.getNomeCamponaClasse(), String.valueOf(pCampo.getObjetoDoAtributo().getId()));
        String hash = (String) UtilSBPersistencia.getRegistroByJPQL(jpql);
        if (hash == null) {
            return super.getEndrRemotoArquivoCampoInstanciadoAbrir(pCampo);
        }
        String url = prefixo + "/" + hash;
        return url;

    }

    @Override
    public String getEndrRemotoArquivoCampoInstanciadoBaixar(ItfCampoInstanciado pCampo
    ) {
        return centralGenerica.getEndrRemotoArquivoCampoInstanciadoBaixar(pCampo);
    }

    @Override
    public boolean isTemImagem(ItfBeanSimplesSomenteLeitura item, FabTipoAtributoObjeto tipo
    ) {
        String jpql = getHSQLPesquisaHashDaEntidade(item.getClass().getSimpleName(), tipo.toString(), String.valueOf(item.getId()));
        String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + jpql);
        if (valor == null) {
            return centralGenerica.isTemImagem(item, tipo); //To change body of generated methods, choose Tools | Templates.
        } else {
            return true;
        }

    }

}