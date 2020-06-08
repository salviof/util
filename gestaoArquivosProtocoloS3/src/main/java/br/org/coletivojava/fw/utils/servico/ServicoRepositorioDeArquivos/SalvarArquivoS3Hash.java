/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model.HashsDeArquivoDeEntidade;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreBytes;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringNomeArquivosEDiretorios;
import java.util.logging.Level;
import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.ServicoDeArquivosWebAppS3.*;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class SalvarArquivoS3Hash extends Thread {

    private boolean trabalhoRealizado;
    private boolean trabalhoConcluidoComsucesso;

    private final byte[] arquivo;
    private final String nomeArquivo;
    private final String campoReferencia;
    private final ConfigModulo configModuloArquivosEnts3;
    private final String nomeClasseEntidade;
    private final int idEntidade;
    private static final List<String> ultimosArquivosSalvos = new ArrayList<String>();

    private enum acaoControleHashDeArquivo {
        CONSULTAR, ATUALIZAR
    }

    public SalvarArquivoS3Hash(ConfigModulo pConfigModuloArquivosEnts3, Class pClasseEntidade, int pIdEntidade, String pCampoReferencia, byte[] pArquivo, String pNomeArquivo) {

        arquivo = pArquivo;
        configModuloArquivosEnts3 = pConfigModuloArquivosEnts3;
        campoReferencia = pCampoReferencia;
        nomeArquivo = pNomeArquivo;
        nomeClasseEntidade = UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(pClasseEntidade.getSimpleName()).getSimpleName();
        idEntidade = pIdEntidade;

    }

    public static synchronized boolean salvarArquivoS3(byte[] arquivo, String pNomeArquivo, String pChavePublica, String pChavePrivada, String bucket, String phashIdentificadorArquivo) {
        try {

            if (ultimosArquivosSalvos.contains(phashIdentificadorArquivo)) {
                return true;
            }
            if (ultimosArquivosSalvos.size() > 10) {
                ultimosArquivosSalvos.remove(0);
            }
            ultimosArquivosSalvos.add(phashIdentificadorArquivo);

            String extencao = UtilSBCoreStringNomeArquivosEDiretorios.getExtencaoNomeArquivoSemPonto(pNomeArquivo);
            AWSCredentials credentials = new BasicAWSCredentials(pChavePublica, pChavePrivada);
            AmazonS3 s3client = new AmazonS3Client(credentials);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("extencaoArquivo", extencao);
            metadata.addUserMetadata("nomeArquivo", pNomeArquivo);
            //metadata.setUserMetadata(userMetadata);
            PutObjectResult putResult = s3client.putObject(new PutObjectRequest(bucket,
                    phashIdentificadorArquivo,
                    UtilSBCoreBytes.geraInputStreamByByteArray(arquivo), metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro salvando arquivo no s3", t);
            return false;
        }

    }

    public static synchronized HashsDeArquivoDeEntidade getControleDeArquivosDeEntidade(String pEntid, String pCampo, String pID, acaoControleHashDeArquivo pTIpoAcao, HashsDeArquivoDeEntidade pHashAtualizado) {

        switch (pTIpoAcao) {
            case CONSULTAR:
                String jpaQl = ServicoDeArquivosWebAppS3.getHSQLPesquisaHashDaEntidade(pEntid, pCampo, pID);
                EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();

                HashsDeArquivoDeEntidade arquivoHashAnterior = (HashsDeArquivoDeEntidade) UtilSBPersistencia.getRegistroByJPQL(jpaQl,
                        HashsDeArquivoDeEntidade.class, em);
                UtilSBPersistencia.fecharEM(em);
                return arquivoHashAnterior;

            case ATUALIZAR:
                HashsDeArquivoDeEntidade hashatualizado;

                EntityManager emAtualizacao = UtilSBPersistencia.getEntyManagerPadraoNovo();
                UtilSBPersistencia.iniciarTransacao(emAtualizacao);
                try {
                    if (pHashAtualizado.getId() == 0) {
                        hashatualizado = UtilSBPersistencia.mergeRegistro(pHashAtualizado, emAtualizacao);
                    } else {
                        hashatualizado = UtilSBPersistencia.loadEntidade(pHashAtualizado, emAtualizacao);
                        hashatualizado.setHashCalculado(pHashAtualizado.getHashCalculado());
                        hashatualizado.setNome(pHashAtualizado.getNome());
                        hashatualizado = UtilSBPersistencia.mergeRegistro(emAtualizacao);

                    }
                } finally {
                    UtilSBPersistencia.finzalizaTransacaoEFechaEM(emAtualizacao);
                }
                return hashatualizado;

            default:
                throw new AssertionError(pTIpoAcao.name());

        }

    }

    @Override
    public void run() {

        try {

            try {
                String extencaoDoArquivp = UtilSBCoreStringNomeArquivosEDiretorios.getExtencaoNomeArquivo(nomeArquivo).replace(".", "");
                String identificadorHAshArquivo = ServicoDeArquivosWebAppS3.getIdentificadorArquivo(arquivo, extencaoDoArquivp);
                HashsDeArquivoDeEntidade arquivoHashAnterior = getControleDeArquivosDeEntidade(nomeClasseEntidade, campoReferencia, String.valueOf(idEntidade), acaoControleHashDeArquivo.CONSULTAR, null);
                if (arquivoHashAnterior != null && arquivoHashAnterior.getHashCalculado().equals(identificadorHAshArquivo)) {
                    trabalhoConcluidoComsucesso = true;
                } else {

                    try {
                        if (!salvarArquivoS3(arquivo, nomeArquivo, configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_PUBLICA), configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_SECRETA),
                                configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET), identificadorHAshArquivo)) {
                            throw new UnsupportedOperationException("Falha salvando arquivo no serviço S3");
                        }
                        if (arquivoHashAnterior == null) {

                            HashsDeArquivoDeEntidade novoarquivoHas = new HashsDeArquivoDeEntidade();
                            novoarquivoHas.setEntidade(nomeClasseEntidade);
                            novoarquivoHas.setAtributo(campoReferencia);
                            novoarquivoHas.setIdEntidade(idEntidade);
                            novoarquivoHas.setHashCalculado(identificadorHAshArquivo);

                            if (getControleDeArquivosDeEntidade(nomeArquivo, nomeArquivo, nomeArquivo, acaoControleHashDeArquivo.ATUALIZAR, novoarquivoHas) != null) {
                                trabalhoConcluidoComsucesso = true;
                            }
                        } else {

                            // Apenas atualiza o nome do aruqivo
                            arquivoHashAnterior.setNome(nomeArquivo);
                            arquivoHashAnterior.setHashCalculado(identificadorHAshArquivo);

                            if (getControleDeArquivosDeEntidade(nomeArquivo, nomeArquivo, nomeArquivo, acaoControleHashDeArquivo.ATUALIZAR, arquivoHashAnterior) != null) {
                                trabalhoConcluidoComsucesso = true;
                            }

                        }

                    } catch (Throwable t) {
                        SBCore.RelatarErroAoUsuario(FabErro.SOLICITAR_REPARO, "Erro atualizando arquivo no S3", t);
                        trabalhoConcluidoComsucesso = false;
                    }

                }

            } catch (Throwable t) {
                trabalhoConcluidoComsucesso = false;
            }

        } finally {
            trabalhoRealizado = true;

        }

    }

    public boolean aguardarFinalizacao() {

        while (!trabalhoRealizado) {
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SalvarArquivoS3Hash.class.getName()).log(Level.SEVERE, null, ex);
                trabalhoConcluidoComsucesso = false;
                trabalhoRealizado = true;
            }
        }

        return trabalhoConcluidoComsucesso;

    }

}
