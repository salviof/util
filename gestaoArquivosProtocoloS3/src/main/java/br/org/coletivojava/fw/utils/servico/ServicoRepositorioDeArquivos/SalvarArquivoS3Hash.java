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
import java.io.ByteArrayInputStream;
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

    public boolean salvarArquivoS3(byte[] arquivo, String pNomeArquivo, String pChavePublica, String pChavePrivada, String bucket, String phashIdentificadorArquivo) {
        try {

            if (ServicoDeArquivosWebAppS3.ultimosArquivosSalvos.contains(phashIdentificadorArquivo)) {
                return true;
            }
            if (ServicoDeArquivosWebAppS3.ultimosArquivosSalvos.size() > 10) {
                ServicoDeArquivosWebAppS3.ultimosArquivosSalvos.remove(0);
            }
            ServicoDeArquivosWebAppS3.ultimosArquivosSalvos.add(phashIdentificadorArquivo);

            String extencao = UtilSBCoreStringNomeArquivosEDiretorios.getExtencaoNomeArquivoSemPonto(pNomeArquivo);
            AWSCredentials credentials = new BasicAWSCredentials(pChavePublica, pChavePrivada);
            AmazonS3 s3client = new AmazonS3Client(credentials);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("extencaoArquivo", extencao);
            metadata.addUserMetadata("nomeArquivo", pNomeArquivo);
            //metadata.setUserMetadata(userMetadata);
            PutObjectResult putResult = s3client.putObject(new PutObjectRequest(bucket,
                    phashIdentificadorArquivo,
                    new ByteArrayInputStream(arquivo), metadata).
                    withCannedAcl(CannedAccessControlList.PublicRead));

            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro salvando arquivo [" + pNomeArquivo + "] no s3", t);
            return false;
        }

    }

    public synchronized HashsDeArquivoDeEntidade getControleDeArquivosDeEntidade(String pEntid, String pCampo, String pID, acaoControleHashDeArquivo pTIpoAcao, HashsDeArquivoDeEntidade pHashAtualizado) {

        switch (pTIpoAcao) {
            case CONSULTAR:
                String jpaQl = ServicoDeArquivosWebAppS3.getHSQLPesquisaHashsDeArquivoDeEntidade(pEntid, pCampo, pID);
                EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();

                HashsDeArquivoDeEntidade arquivoHashAnterior = (HashsDeArquivoDeEntidade) UtilSBPersistencia.getRegistroByJPQL(jpaQl,
                        HashsDeArquivoDeEntidade.class, em);
                UtilSBPersistencia.fecharEM(em);
                return arquivoHashAnterior;

            case ATUALIZAR:
                HashsDeArquivoDeEntidade hashatualizado;
                String novoHash = pHashAtualizado.getHashCalculado();

                EntityManager emAtualizacao = UtilSBPersistencia.getEntyManagerPadraoNovo();
                UtilSBPersistencia.iniciarTransacao(emAtualizacao);
                try {
                    if (pHashAtualizado.getId() == 0) {
                        System.out.println("registrando novo hash");
                        hashatualizado = UtilSBPersistencia.mergeRegistro(pHashAtualizado, emAtualizacao);
                        System.out.println("criado" + hashatualizado.getId() + " com hash " + hashatualizado.getHashCalculado());
                    } else {

                        hashatualizado = UtilSBPersistencia.loadEntidade(pHashAtualizado, emAtualizacao);
                        hashatualizado.setHashCalculado(novoHash);
                        System.out.println("update" + hashatualizado.getId() + " com hash " + hashatualizado.getHashCalculado());
                        //     hashatualizado.setNome(pHashAtualizado.getNome());
                        hashatualizado = UtilSBPersistencia.mergeRegistro(hashatualizado);

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
            String extencaoDoArquivp = UtilSBCoreStringNomeArquivosEDiretorios.getExtencaoNomeArquivo(nomeArquivo).replace(".", "");
            String identificadorHAshArquivo = ServicoDeArquivosWebAppS3.getIdentificadorArquivo(arquivo, extencaoDoArquivp);
            HashsDeArquivoDeEntidade arquivoHashAnterior = getControleDeArquivosDeEntidade(nomeClasseEntidade, campoReferencia, String.valueOf(idEntidade), acaoControleHashDeArquivo.CONSULTAR, null);
            if (arquivoHashAnterior != null && arquivoHashAnterior.getHashCalculado().equals(identificadorHAshArquivo)) {
                trabalhoConcluidoComsucesso = true;
                trabalhoRealizado = true;
                return;
            } else {

                try {
                    if (!salvarArquivoS3(arquivo, nomeArquivo, configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_PUBLICA), configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_CHAVE_SECRETA),
                            configModuloArquivosEnts3.getPropriedade(FabConfigArquivoDeEntidadeS3.ARQUIVOS_ENTIDADE_S3_BUCKET), identificadorHAshArquivo)) {
                        throw new UnsupportedOperationException("Falha salvando arquivo no serviço S3");
                    }
                    if (arquivoHashAnterior == null) {
                        System.out.println("criando HashDeEntidade do arquivo atualizado");
                        HashsDeArquivoDeEntidade novoarquivoHas = new HashsDeArquivoDeEntidade();
                        novoarquivoHas.setEntidade(nomeClasseEntidade);
                        novoarquivoHas.setAtributo(campoReferencia);
                        novoarquivoHas.setIdEntidade(idEntidade);
                        novoarquivoHas.setHashCalculado(identificadorHAshArquivo);

                        if (getControleDeArquivosDeEntidade(nomeClasseEntidade, campoReferencia, String.valueOf(idEntidade), acaoControleHashDeArquivo.ATUALIZAR, novoarquivoHas) != null) {
                            trabalhoConcluidoComsucesso = true;
                            trabalhoRealizado = true;
                            return;
                        }
                    } else {

                        // Apenas atualiza o  hash do arquivo
                        System.out.println("Atualizando HashDeEntidade do arquivo atualizado");
                        arquivoHashAnterior.setHashCalculado(identificadorHAshArquivo);

                        if (getControleDeArquivosDeEntidade(campoReferencia, campoReferencia, String.valueOf(idEntidade), acaoControleHashDeArquivo.ATUALIZAR, arquivoHashAnterior) != null) {
                            trabalhoConcluidoComsucesso = true;
                            trabalhoRealizado = true;

                        } else {
                            throw new UnsupportedOperationException("Falha Atualizando " + HashsDeArquivoDeEntidade.class.getSimpleName() + " ");
                        }

                    }

                } catch (Throwable t) {
                    SBCore.RelatarErroAoUsuario(FabErro.SOLICITAR_REPARO, "Erro atualizando arquivo no S3", t);
                    trabalhoConcluidoComsucesso = false;
                    trabalhoRealizado = true;

                }

            }

        } catch (Throwable t) {
            trabalhoConcluidoComsucesso = false;
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
