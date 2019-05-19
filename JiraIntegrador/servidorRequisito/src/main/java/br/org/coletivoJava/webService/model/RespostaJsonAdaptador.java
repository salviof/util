/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;
import java.io.IOException;

/**
 *
 * @author desenvolvedor
 */
public class RespostaJsonAdaptador extends TypeAdapter<RespostaWebService> {

    private RespostaWebService resp;

    @Override
    public void write(JsonWriter writer, RespostaWebService t) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RespostaWebService read(JsonReader reader) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
