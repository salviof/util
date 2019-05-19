/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;

/**
 *
 * @author salvioF
 */
public enum FabTipoProfissional implements ItfFabrica {

    ANALISTA_BANCO_DE_DADOS,
    ANALISTA_LOGICA_TDD,
    ANALISTA_IMPLEMENTACAO,
    ESPECIALISTA,
    ANALISTA_TELAS,
    DESIGNER,
    ANALISTA_REQUISITOS,
    ANALISTA_ANDROID;

    @Override
    public TipoProfissional getRegistro() {

        TipoProfissional profissional = new TipoProfissional(this);
        profissional.setId(this.ordinal());
        switch (this) {
            case ANALISTA_BANCO_DE_DADOS:
                profissional.setNome("Analista de Banco de Dados");
                profissional.setDescricao(" Responsável por criar a estrutura de armazenamento de informações do sistema, "
                        + "um analista de banco de dados deve ter conhecimento de: Banco de dados Estruturado, SQL ORM JPA ");
                profissional.setValorHoraTecnica(30);
                break;
            case ANALISTA_LOGICA_TDD:
                profissional.setNome("Analista de Lógica TDD");
                profissional.setDescricao(" "
                        + "Um analista de testes TDD deve ter vivencia com programação orientada a objetos"
                        + " habilidade de lógica de algorítimo avançada, sendo recomendavel que tenha muitas horas de prátia de programação já exercida, "
                        + " ele definirá o que deve ser programado, e pensar e definir todos os testes que certifiquem que "
                        + "a progamação foi implementada  com sucesso."
                        + "é desejavel que tenha conhecimento de JPA "
                        + " <a href='https://pt.wikipedia.org/wiki/Test_Driven_Development' target='aprender'> Quero saber tudo sobre TDD (Desenvolvimento Dirigito por Testes). </a> "
                );
                profissional.setValorHoraTecnica(60);
                break;
            case ANALISTA_IMPLEMENTACAO:
                profissional.setNome("Analista Implementação ");
                profissional.setDescricao("Um analista de programação deve ter conhecimento de algorítimos estruturados.");
                profissional.setValorHoraTecnica(20);
                break;
            case ANALISTA_TELAS:
                profissional.setNome("Analista de Telas");
                profissional.setDescricao("Um analista de telas deve ter conhecimento de HTML5, CSS, e Jquery");
                profissional.setValorHoraTecnica(15);
                break;
            case ANALISTA_REQUISITOS:
                profissional.setNome("Analista de Requisitos");
                profissional.setDescricao("Um analista de Requisitos, deve ter profundo conhecimento sobre a regra de negocio do sistema."
                        + "  ele definirá  quais telas e processo deverão existir,embora desejavel, não é nescessário conhecimento em programação");
                profissional.setValorHoraTecnica(150);
                break;
            case ANALISTA_ANDROID:
                profissional.setNome("Analista Android");
                profissional.setDescricao("Uma analista android deve ter conhecimentos de construção de aplicativos, e experiencia em consultas REST");
                profissional.setValorHoraTecnica(100);
                break;
            case DESIGNER:
                profissional.setNome("Designer");
                profissional.setDescricao("Conhecimento em designer Digital, e habilidades com Photophop e mídia digital");
                profissional.setValorHoraTecnica(20);
                break;
            case ESPECIALISTA:
                profissional.setNome("Especialista");
                profissional.setDescricao("Profissional especializado em determinada tecnolgia, normalmente destinado a pesquisa de implantação de novos recursos no sistema");
                profissional.setValorHoraTecnica(150);

                break;
            default:
                throw new AssertionError(this.name());

        }
        return profissional;
    }
}
