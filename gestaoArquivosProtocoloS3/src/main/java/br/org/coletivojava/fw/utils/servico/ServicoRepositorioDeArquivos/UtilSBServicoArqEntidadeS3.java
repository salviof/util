/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos;

import br.org.coletivojava.fw.utils.servico.ServicoRepositorioDeArquivos.model.HashsDeArquivoDeEntidade;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author salvio
 */
public class UtilSBServicoArqEntidadeS3 {

    public static String getHSQLPesquisaHashsDeArquivoDeEntidade(String pNomeEntidade, String pCampo, String id) {
        String jpql = " from " + HashsDeArquivoDeEntidade.class.getSimpleName()
                + " where entidade ='" + pNomeEntidade + "'"
                + " and idEntidade=" + id
                + " and atributo='" + pCampo + "'";

        return jpql;

    }

    public static String getHSQLPesquisaHashDoArquivo(String pIdentificadorHashArquivo, String pNomeEntidade, String pCampo, String id) {
        String jpql = " from " + HashsDeArquivoDeEntidade.class.getSimpleName()
                + " where hashCalculado='" + pIdentificadorHashArquivo + "' and entidade ='" + pNomeEntidade
                + "' and atributo='" + pCampo + "'"
                + " and idEntidade=" + id;

        return jpql;

    }

    public static HashsDeArquivoDeEntidade getHashArquivoEntidade(EntityManager em, String pEndidade, String pCampo, String pId) {
        String jpaQl = UtilSBServicoArqEntidadeS3.getHSQLPesquisaHashsDeArquivoDeEntidade(pEndidade, pCampo, pId);

        HashsDeArquivoDeEntidade arquivoHashAnterior = (HashsDeArquivoDeEntidade) UtilSBPersistencia.getRegistroByJPQL(jpaQl,
                HashsDeArquivoDeEntidade.class, em);
        return arquivoHashAnterior;
    }

    public static String getHashDoArquivo(String pNomeEntidade, String pCampo, String id) {
        try {
            String hql = UtilSBServicoArqEntidadeS3.getHSQLPesquisaHashsDeArquivoDeEntidade(pNomeEntidade, pCampo, id);
            String valor = (String) UtilSBPersistencia.getRegistroByJPQL("select hashCalculado " + hql);
            return valor;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro buscando identificador hash registrado de arquivo de entidade", t);
            return null;
        }
    }

    public static String getHashDoArquivo(ItfCampoInstanciado pCampo) {
        return getHashDoArquivo(pCampo.getObjetoDoAtributo().getClass().getSimpleName(), pCampo.getNomeCamponaClasse(), String.valueOf(pCampo.getObjetoDoAtributo().getId()));
    }
}
