/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requisito;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author desenvolvedor
 */
@XmlRootElement()
public class RespWs extends RespostaWebService {

    @Expose()
    private String retornoJson;
    @Expose
    private Resultado resultadoWS = Resultado.SUCESSO;

    @Deprecated
    public RespWs() {
        super();
    }

    @Override
    public ItfResposta setRetorno(Object pObjetoResultante) {
        super.setRetorno(pObjetoResultante); //To change body of generated methods, choose Tools | Templates.
        retornoJson = new Gson().toJson(pObjetoResultante);
        return this;
    }

    @Override
    public String getRetornoJson() {
        return retornoJson;
    }

    @Override
    public void setRetornoJson(String retornoJson) {
        this.retornoJson = retornoJson;
    }

    @Override
    public void setResultadoWS(Resultado resultadoWS) {
        this.resultadoWS = resultadoWS;

    }

}
