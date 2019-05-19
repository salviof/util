/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coletivoJava.TesteComunicacaoVip;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfApiErpSuperBits;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERP;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author desenvolvedor
 */
public class GeradorERPImplementacaoTeste extends GeradorClasseGenerico {

    public GeradorERPImplementacaoTeste(ItfApiErpSuperBits pFabrica) {
        super(UtilSBCoreReflexaoAPIERP.getNomeClasseAnotacaoImplementacaoPadrao(pFabrica), UtilSBCoreReflexaoAPIERP.getNomeClasseAnotacaoImplementacao(pFabrica));
        getCodigoJava().addAnnotation(Qualifier.class);
        getCodigoJava().addImport(pFabrica.getInterface());
        getCodigoJava().addAnnotation(InfoReferenciaEntidade.class).setLiteralValue("tipoObjeto ", pFabrica.getInterface().getSimpleName() + ".class");
        getCodigoJava().addAnnotation(Documented.class);
        getCodigoJava().addAnnotation(Retention.class).setEnumValue(RetentionPolicy.RUNTIME);
        getCodigoJava().addAnnotation(Target.class).setEnumValue(ElementType.TYPE);
        if (!SBCore.getGrupoProjeto().equals("erpColetivoJava")) {
            throw new UnsupportedOperationException("Esta geração deve estar organizada no grupo erpColetivoJava");
        }
    }

}
