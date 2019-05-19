/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.projeto.Jira.grupoDeTarefas;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.projeto.Jira.TipoGrupoTarefa;

/**
 *
 * @author salvioF
 */
public enum FabTipoGrupoTarefa implements ItfFabrica {

    MODULO, GESTAO, TABELA, PERSONALIZADA;

    @Override
    public TipoGrupoTarefa getRegistro() {

        TipoGrupoTarefa novoGrupo = new TipoGrupoTarefa();

        novoGrupo.setId(this.ordinal());
        switch (this) {
            case MODULO:
                novoGrupo.setNome("Modulo do Sistema");
                novoGrupo.setIcone("fa  fa-codepen");
                novoGrupo.setDescricao("Um modulo do sistema, compreende em várias telas de gestão do sistema, "
                        + "normalmente correspondente a um departamento  ou tipo de Atividade específica");
                break;
            case GESTAO:
                novoGrupo.setNome("Gestão de Ações");
                novoGrupo.setIcone("fa fa-list-alt");
                novoGrupo.setDescricao("Um gestão de ação, corresponde a telas com a função de gerir uma informação específica, uma area de gestão pode conter diversas subtelas e ações dependendo da complexidade do sistema");
                break;
            case TABELA:
                novoGrupo.setNome("Construção de 'Tabela' de Informação");
                novoGrupo.setDescricao("Em um sistema, os dados são armazenados como em tabelas de excel, "
                        + "porém a informação entre as diversas tabelas se cruzam e podem receber tratamentos específicos, além dos campos da tabela, são configurados filtros, validações processadas nome para campos, icones, etc..");
                novoGrupo.setIcone("fa fa-table");
                break;
            case PERSONALIZADA:
                novoGrupo.setNome("Tecnologias e outras tarefas");
                novoGrupo.setIcone("fa fa-rocket");
                novoGrupo.setDescricao("Uma nova tecnologia, envolve pesquisa, teste, e implementação, o tempo nescessário para este tipo de atividade é o mais incerto, e os profissionais envolvidos precisam ser muito qualificados, para não haver desperdício de recursos");
                break;
            default:
                throw new AssertionError(this.name());

        }
        return novoGrupo;

    }

}
