package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.webPaginas.ConfigGeral.SBWebPaginas;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.B_Pagina;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.MB_PaginaAtual;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.MB_SiteMapa;
import com.super_bits.modulosSB.webPaginas.util.UtilSBWPServletTools;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PaginaAtual extends MB_PaginaAtual {

    @Inject
    private SiteMap siteMapa;

    protected String findNomePaginaAtual() {
        String url = UtilSBWPServletTools.getUrlDigitada();

        if (url.equals(SBWebPaginas.getSiteURL()) || url.equals(SBWebPaginas.getSiteURL() + "/")) {
            return B_Pagina.PAGINAINICIALID;
        }

        String[] camadasURL = url.split("/");

        for (String camada : camadasURL) {
            if (!camada.contains(":") && !camada.contains(".") && !camada.equals("")) {
                if (getSiteMap().isReferenciaAPagina(camada)) {
                    return camada;
                }
            }

        }
        return B_Pagina.PAGINAINICIALID;

    }

    @Produces
    @Named(value = "testee2")
    public String getTeste() {
        return "colé doidin";
    }

    @Override
    protected MB_SiteMapa getSiteMap() {
        return siteMapa;
    }

}
