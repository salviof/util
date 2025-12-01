/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Priority;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringFiltros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringSlugs;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ComoAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.fabricas.FabTipoAcaoSistemaGenerica;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;

import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.projeto.Jira.Jira.TarefaJira;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author desenvolvedor
 */
public class UtilCRCJira {

    private static ConfigModulo configuracao = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);

    public static enum TIPO_GRUPO_TAREFA {
        TELA_GESTAO_ENTIDADE,
        MODULO_CONTROLLER,
        MODELAGEM_TABELA,
        ACAO_BANCO_AMBIENTE_E_ADEQUACAO,
        REQUISITO_PERSONALIZADAO
    }

    public static TIPOS_DE_TAREFA_JIRA[] getTiposTarefaPorTipoAcao(FabTipoAcaoSistemaGenerica pTipoAcaoDoSistemaGenerica) {
        switch (pTipoAcaoDoSistemaGenerica) {
            case FORMULARIO_NOVO_REGISTRO:

            case FORMULARIO_EDITAR:

            case FORMULARIO_VISUALIZAR:

            case FORMULARIO_LISTAR:

            case FORMULARIO_MODAL:

            case SELECAO_DE_ACAO:

                return new TIPOS_DE_TAREFA_JIRA[]{
                    TIPOS_DE_TAREFA_JIRA.ACAO_CRIAR_FORMULARIO};

            case FORMULARIO_PERSONALIZADO:

                return new TIPOS_DE_TAREFA_JIRA[]{
                    TIPOS_DE_TAREFA_JIRA.ACAO_CRIAR_FORMULARIO_COMPLEXO};

            case CONTROLLER_SALVAR_EDICAO:

                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_SALVAR_NOVO:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_SALVAR_MODO_MERGE:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_ATIVAR_DESATIVAR:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_ATIVAR:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_REMOVER:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_DESATIVAR:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER};

            case CONTROLLER_PERSONALIZADO:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_CONTROLLER_COMPLEXO,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO};

            case GERENCIAR_DOMINIO:
                return new TIPOS_DE_TAREFA_JIRA[]{TIPOS_DE_TAREFA_JIRA.ACAO_TESTE_MANAGED_BEAN,
                    TIPOS_DE_TAREFA_JIRA.ACAO_IMPLEMENTACAO_MANAGED_BEAN};

            default:
                throw new UnsupportedOperationException("O tipo de ação Jira não foi definido para:" + pTipoAcaoDoSistemaGenerica + " ");
        }

    }

    public static TIPOS_DE_TAREFA_JIRA[] getTiposTarefaPorEntidade(Class pClasseEntidade) {
        EstruturaDeEntidade est;
        try {
            est = MapaObjetosProjetoAtual.getEstruturaObjeto(pClasseEntidade.getSimpleName());
        } catch (Throwable t) {
            MapaObjetosProjetoAtual.adcionarObjeto(pClasseEntidade);
            est = MapaObjetosProjetoAtual.getEstruturaObjeto(pClasseEntidade.getSimpleName());
        }

        TIPOS_DE_TAREFA_JIRA[] tiposPossiveis = new TIPOS_DE_TAREFA_JIRA[]{
            TIPOS_DE_TAREFA_JIRA.ACAO_TESTES_ENTIDADE_CALCULO,
            TIPOS_DE_TAREFA_JIRA.ACAO_TESTES_ENTIDADE_LISTAS,
            TIPOS_DE_TAREFA_JIRA.ACAO_ENTIDADE_CRIAR_LISTAS,
            TIPOS_DE_TAREFA_JIRA.ACAO_ENTIDADE_CRIAR_CALCULOS,
            TIPOS_DE_TAREFA_JIRA.ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO};
        List<TIPOS_DE_TAREFA_JIRA> listaOficial = new ArrayList();

        for (TIPOS_DE_TAREFA_JIRA tipo : tiposPossiveis) {
            boolean adicionar = true;
            switch (tipo) {

                case ACAO_TESTES_ENTIDADE_CALCULO:
                    if (est.getCalculos().isEmpty()) {
                        adicionar = false;
                    }
                    break;
                case ACAO_TESTES_ENTIDADE_LISTAS:
                    if (est.getListas().isEmpty()) {
                        adicionar = false;
                    }
                    break;
                case ACAO_ENTIDADE_CRIAR_LISTAS:
                    if (est.getListas().isEmpty()) {
                        adicionar = false;
                    } else {
                        adicionar = true;
                    }
                    break;
                case ACAO_ENTIDADE_CRIAR_CALCULOS:
                    if (est.getCalculos().isEmpty()) {
                        adicionar = false;
                    } else {
                        adicionar = true;
                    }
                    break;

                case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                    break;

                default:
                    throw new AssertionError(tipo.name());

            }
            if (adicionar) {
                listaOficial.add(tipo);
            }

        }
        TIPOS_DE_TAREFA_JIRA[] arrayREsp = new TIPOS_DE_TAREFA_JIRA[listaOficial.size()];
        listaOficial.toArray(arrayREsp);
        return arrayREsp;

    }

    public static enum TIPOS_DE_TAREFA_JIRA {

        ACAO_TESTE_MANAGED_BEAN,
        ACAO_IMPLEMENTACAO_MANAGED_BEAN,
        ACAO_CRIAR_FORMULARIO,
        ACAO_CRIAR_FORMULARIO_COMPLEXO,
        ACAO_IMPLEMENTAR_CONTROLLER,
        ACAO_TESTE_CONTROLLER,
        ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO,
        ACAO_TESTE_CONTROLLER_COMPLEXO,
        ACAO_TESTES_AMBIENTE_DE_DADOS,
        ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS,
        ACAO_BANCO_TESTES_TIPOS,
        ACAO_BANCO_IMPLEMENTACAO_TIPOS,
        ACAO_TESTES_ENTIDADE_CALCULO,
        ACAO_TESTES_ENTIDADE_LISTAS,
        ACAO_ENTIDADE_CRIAR_CALCULOS,
        ACAO_ENTIDADE_CRIAR_LISTAS,
        ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO,
        REQUISITO_PERSONALIZADO_PESQUISA,
        REQUISITO_PERSONALIZADO_IMPLEMENTACAO,
        REQUISITO_PERSONALIZADO_TESTES;

        public FabTipoProfissional getTipoProfissional() {
            switch (this) {
                case ACAO_TESTE_MANAGED_BEAN:
                case ACAO_TESTE_CONTROLLER:
                case ACAO_TESTE_CONTROLLER_COMPLEXO:
                case ACAO_TESTES_ENTIDADE_CALCULO:
                case ACAO_TESTES_ENTIDADE_LISTAS:
                    return FabTipoProfissional.ANALISTA_LOGICA_TDD;
                case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                case ACAO_CRIAR_FORMULARIO:
                case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                case ACAO_IMPLEMENTAR_CONTROLLER:
                case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                case ACAO_ENTIDADE_CRIAR_CALCULOS:
                case ACAO_ENTIDADE_CRIAR_LISTAS:
                    return FabTipoProfissional.ANALISTA_IMPLEMENTACAO;
                case ACAO_TESTES_AMBIENTE_DE_DADOS:
                case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
                case ACAO_BANCO_TESTES_TIPOS:
                case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                    return FabTipoProfissional.ANALISTA_BANCO_DE_DADOS;
                case REQUISITO_PERSONALIZADO_PESQUISA:
                case REQUISITO_PERSONALIZADO_IMPLEMENTACAO:
                case REQUISITO_PERSONALIZADO_TESTES:
                    return FabTipoProfissional.ESPECIALISTA;

                default:
                    throw new AssertionError(this.name());

            }

        }

        public TarefaJira getTarefaInssueJira() {
            TarefaJira tarefa = new TarefaJira();
            switch (this) {

                case ACAO_TESTE_MANAGED_BEAN:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                    tarefa.setTempoEsperado("6h");
                    break;
                case ACAO_TESTES_AMBIENTE_DE_DADOS:
                    tarefa.setTempoEsperado("6h");
                    break;
                case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
                    tarefa.setTempoEsperado("16h");
                    break;
                case ACAO_BANCO_TESTES_TIPOS:
                    tarefa.setTempoEsperado("6h");
                    break;
                case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                    tarefa.setTempoEsperado("6h");
                    break;
                case ACAO_TESTES_ENTIDADE_CALCULO:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_TESTES_ENTIDADE_LISTAS:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_IMPLEMENTAR_CONTROLLER:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_TESTE_CONTROLLER:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_CRIAR_FORMULARIO:
                    tarefa.setTempoEsperado("1h");
                    break;
                case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                    tarefa.setTempoEsperado("8h");
                    break;
                case ACAO_ENTIDADE_CRIAR_CALCULOS:
                    tarefa.setTempoEsperado("3h");
                    break;
                case ACAO_ENTIDADE_CRIAR_LISTAS:
                    tarefa.setTempoEsperado("3h");
                    break;
                case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                    tarefa.setTempoEsperado("12h");
                    break;
                case ACAO_TESTE_CONTROLLER_COMPLEXO:
                    tarefa.setTempoEsperado("4h");
                    break;
                case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                    tarefa.setTempoEsperado("3h");
                    break;
                case REQUISITO_PERSONALIZADO_PESQUISA:
                    tarefa.setTempoEsperado("10h");
                    break;
                case REQUISITO_PERSONALIZADO_IMPLEMENTACAO:
                    tarefa.setTempoEsperado("10h");
                    break;
                case REQUISITO_PERSONALIZADO_TESTES:
                    tarefa.setTempoEsperado("20h");
                    break;
                default:
                    throw new AssertionError(this.name());
            }

            return tarefa;
        }

        public String getSigla() {
            switch (this) {
                case ACAO_TESTE_MANAGED_BEAN:
                    return "TMB";
                case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                    return "IMB";
                case ACAO_CRIAR_FORMULARIO:
                    return "FNOVO";
                case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                    return "FNCX";
                case ACAO_IMPLEMENTAR_CONTROLLER:
                    return "IC";
                case ACAO_TESTE_CONTROLLER:
                    return "TC";
                case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                    return "ICCX";
                case ACAO_TESTE_CONTROLLER_COMPLEXO:
                    return "TCCX";
                case ACAO_TESTES_AMBIENTE_DE_DADOS:
                    return "TADB";
                case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
                    return "IADB";
                case ACAO_BANCO_TESTES_TIPOS:
                    return "TTADB";
                case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                    return "IADB";
                case ACAO_TESTES_ENTIDADE_CALCULO:
                    return "TEC";
                case ACAO_TESTES_ENTIDADE_LISTAS:
                    return "TELIST";
                case ACAO_ENTIDADE_CRIAR_CALCULOS:
                    return "IEC";
                case ACAO_ENTIDADE_CRIAR_LISTAS:
                    return "IEL";
                case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                    return "VCR";
                case REQUISITO_PERSONALIZADO_PESQUISA:
                    return "RPP";
                case REQUISITO_PERSONALIZADO_IMPLEMENTACAO:
                    return "RPI";
                case REQUISITO_PERSONALIZADO_TESTES:
                    return "RPT";
                default:
                    throw new AssertionError(this.name());

            }
        }

        public TIPO_GRUPO_TAREFA getGrupoTarefaInssueJira() {
            switch (this) {
                case ACAO_TESTE_MANAGED_BEAN:
                case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                case ACAO_CRIAR_FORMULARIO:
                case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                    return TIPO_GRUPO_TAREFA.TELA_GESTAO_ENTIDADE;

                case ACAO_TESTES_AMBIENTE_DE_DADOS:
                case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                case ACAO_BANCO_TESTES_TIPOS:
                case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
                    return TIPO_GRUPO_TAREFA.ACAO_BANCO_AMBIENTE_E_ADEQUACAO;

                case ACAO_TESTES_ENTIDADE_CALCULO:
                case ACAO_TESTES_ENTIDADE_LISTAS:
                case ACAO_ENTIDADE_CRIAR_CALCULOS:
                case ACAO_ENTIDADE_CRIAR_LISTAS:
                case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                    return TIPO_GRUPO_TAREFA.MODELAGEM_TABELA;

                case ACAO_IMPLEMENTAR_CONTROLLER:
                case ACAO_TESTE_CONTROLLER:
                case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                case ACAO_TESTE_CONTROLLER_COMPLEXO:

                    return TIPO_GRUPO_TAREFA.MODULO_CONTROLLER;
                case REQUISITO_PERSONALIZADO_PESQUISA:

                case REQUISITO_PERSONALIZADO_IMPLEMENTACAO:

                case REQUISITO_PERSONALIZADO_TESTES:
                    return TIPO_GRUPO_TAREFA.REQUISITO_PERSONALIZADAO;

                default:
                    throw new AssertionError(this.name());

            }
        }

    }

    public static ConexaoJiraProjetoSB criarConexaoJira(String pUsuario, String pSenha, String pProjeto) {

        ConfigModulo modulo = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);
        String url = modulo.getPropriedade(FabConfigModuloJiraIntegrador.SERVIDOR);
        if (pProjeto == null) {
            pProjeto = modulo.getPropriedade(FabConfigModuloJiraIntegrador.PROJETO);
        }
        if (!url.startsWith("https")) {
            throw new UnsupportedOperationException("A URL Jira precisa ser uma conexão segura com https");
        }
        try {

            URI jiraServerUrl = new URI(url);
            final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUrl, pUsuario, pSenha);

            Iterator<BasicProject> projetos = restClient.getProjectClient().getAllProjects().claim().iterator();
            while (projetos.hasNext()) {
                BasicProject projeto = projetos.next();
                if (projeto.getName().equals(pProjeto)) {
                    return new ConexaoJiraProjetoSB(restClient, pProjeto);
                }
            }

            throw new UnsupportedClassVersionError("Projeto " + pProjeto + " não foi encontrado em" + url);

        } catch (URISyntaxException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Impossível conectar com o servidor Jira", ex);
            return null;
        }

    }

    private static BasicProject getProjetoPrincipal(JiraRestClient conexao) {
        return getProjetoPrincipal(conexao, null);
    }

    private static BasicProject getProjetoPrincipal(JiraRestClient conexao, String pNomeProjeto) {
        List<BasicProject> lista = Lists.newArrayList(conexao.getProjectClient().getAllProjects().claim());
        if (pNomeProjeto == null) {
            pNomeProjeto = SBCore.getGrupoProjeto();
        }
        if (lista.size() > 1) {
            String textoProjetos = "";

            for (BasicProject proj : lista) {
                textoProjetos += proj.getKey();
                String nomeProjeto = UtilCRCStringSlugs.gerarSlugCaixaAlta(pNomeProjeto);
                String nomeProjetoRemoto = UtilCRCStringSlugs.gerarSlugCaixaAlta(proj.getName());
                System.out.println(nomeProjeto);
                if (nomeProjeto != null && proj.getName() != null && nomeProjetoRemoto.equals(nomeProjeto)) {

                    return proj;
                }
                if (proj.getKey().contains("SK")) {
                    return proj;
                }
            }
            throw new UnsupportedOperationException("Foram encontrados mais de um projeto vinculado a este usuário, impossívcel determinar o projeto " + textoProjetos);
        }
        return lista.get(0);
    }

    private static IssueType getTipoIssueTarefaPrincipal(JiraRestClient conexao) {

        List<IssueType> tiposAcoes = Lists.newArrayList(conexao.getMetadataClient().getIssueTypes().claim().iterator());
        for (IssueType tipoAcao : tiposAcoes) {
            if (!tipoAcao.isSubtask()) {
                if (tipoAcao.getName().startsWith("Task")
                        || tipoAcao.getName().startsWith("Tarefa")) {
                    return tipoAcao;
                }
            }

        }
        throw new UnsupportedOperationException("Issue do tipo Task não encontrada");

    }

    private static BasicPriority getPrioridadeMaxiama(JiraRestClient conexao) {

        List<Priority> prioridades = Lists.newArrayList(conexao.getMetadataClient().getPriorities().claim().iterator());
        for (Priority prioridae : prioridades) {
            if (prioridae.getDescription().contains("Major")
                    || prioridae.getDescription().contains("Serious")) {
                return prioridae;
            }
        }

        throw new UnsupportedOperationException("Alta Prioridade não encontrada");

    }

    public static User getUsuarioPorNome(JiraRestClient conexao, String username) {
        try {
            List<User> usuarios = Lists.newArrayList(conexao.getUserClient().getUser(username).claim());
            if (usuarios.size() > 0) {
                return usuarios.get(0);
            }
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, username, t);
        }

        throw new UnsupportedOperationException("O usuário" + username + " não foi encontrado");

    }

    private static IssueType getTipoIssueTarefaSecundaria(JiraRestClient conexao) {

        List<IssueType> tiposAcoes = Lists.newArrayList(conexao.getMetadataClient().getIssueTypes().claim().iterator());
        for (IssueType tipoAcao : tiposAcoes) {
            if (tipoAcao.isSubtask()) {
                if (tipoAcao.getName().startsWith("Sub-Task")
                        || tipoAcao.getName().startsWith("Sub-task")
                        || tipoAcao.getName().startsWith("Sub-Tarefa")) {
                    return tipoAcao;
                }
            }

        }
        throw new UnsupportedOperationException("Issue do tipo Task não encontrada");

    }

    public static TarefaJira buildTarefaPrincipal(ConexaoJiraProjetoSB conexao, TarefaJira pTarefa) {
        return pTarefa.getTarefaPrincipal();

    }

    public static String getIdTarefaPeloLabel(ConexaoJiraProjetoSB conexao, TarefaJira pTarefa) {
        Issue tarefa = getIssuePeloLabel(conexao, pTarefa);
        if (tarefa != null) {
            return tarefa.getId().toString();
        } else {
            SBCore.getCentralDeEventos().registrarLogDeEvento(FabMensagens.ALERTA, "nenhuma tarefa vinculada foi encontrada no servidor gira para: " + pTarefa.getNomeUnicoTarefa());
            return null;
        }

    }

    public static Issue getIssuePeloLabel(ConexaoJiraProjetoSB conexao, TarefaJira pTarefa) {

        String referencia = pTarefa.getReferencia();

        Promise<SearchResult> pesquisa = conexao.getSearchClient().searchJql("labels = \"" + referencia + "\"  and project = \"" + conexao.getNomeProjeto() + "\"");
        SearchResult resp = pesquisa.claim();
        List<Issue> resplist = Lists.newArrayList(resp.getIssues().iterator());
        if (resplist.size() == 1) {
            return resplist.get(0);
        } else if (resplist.size() > 1) {
            SBCore.getCentralDeEventos().registrarLogDeEvento(FabMensagens.ALERTA, "Foram encontrados " + resplist.size() + " com o label " + referencia + " o método retornará apenas o primeiro registro");
            return resplist.get(0);
        }
        return null;
    }

    public static String getIdSubTarefaPeloLabel(JiraRestClient conexao, TarefaJira pTarefa) {
        IssueType subTarefa = getTipoIssueTarefaSecundaria(conexao);
        Promise<SearchResult> pesquisa = conexao.getSearchClient().searchJql("labels = \"" + pTarefa.getReferencia() + "\" and issueType =\"" + subTarefa.getName() + "\"");
        SearchResult resp = pesquisa.claim();
        List<Issue> resplist = Lists.newArrayList(resp.getIssues().iterator());
        if (resplist.size() > 0) {
            return resplist.get(0).getId().toString();
        }
        return null;
    }

    public static boolean apagarTarefasDaCao(ConexaoJiraProjetoSB conexao,
            TarefaJira pTarefa, boolean pApagarSubTarefas) {
        try {

            if (pTarefa == null) {
                throw new UnsupportedOperationException("Evocou metodo criar tarefas com a tarefa nula");
            }
            if (conexao == null) {
                throw new UnsupportedOperationException("Evocou metodo criar tarefas com a conexão nula");
            }

            TarefaJira tarefaPrincipal = buildTarefaPrincipal(conexao, pTarefa);

            if (tarefaPrincipal == null) {
                throw new UnsupportedOperationException("Impossível deterinar a tarefa principal para" + pTarefa.getReferencia());
            }

            Issue tarefaEncontrada = getIssuePeloLabel(conexao, pTarefa);
            if (tarefaEncontrada == null) {
                return false;
            } else {
                conexao.getIssueClient().deleteIssue(tarefaEncontrada.getKey(), pApagarSubTarefas).claim();

            }
            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro excluindo tarefa" + pTarefa.getNomeTarefa(), t);
            return false;
        }
    }

    public static List<Issue> listarTodasTarefasDoProjeto(JiraRestClient conexao) {

        BasicProject proj = getProjetoPrincipal(conexao);
        Promise<SearchResult> pesquisa = conexao.getSearchClient().searchJql("project =  " + proj.getKey());
        SearchResult resp = pesquisa.claim();
        return Lists.newArrayList(resp.getIssues().iterator());

    }

    public static boolean comentario_adicionar(JiraRestClient conexao, Issue pTarefa, String pComentario) {
        try {
            Comment comentario = Comment.valueOf(pComentario);

            conexao.getIssueClient().addComment(pTarefa.getCommentsUri(), comentario).claim();
            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro adicionando Comentario", t);
            return false;
        }
    }

    public static boolean criarTarefafasDaAcao(ConexaoJiraProjetoSB conexao, TarefaJira pTarefa, User pUsuarioResponsavel) {

        try {

            if (pTarefa == null) {
                throw new UnsupportedOperationException("Evocou metodo criar tarefas com a tarefa nula");
            }
            if (conexao == null) {
                throw new UnsupportedOperationException("Evocou metodo criar tarefas com a conexão nula");
            }

            BasicProject projeto = getProjetoPrincipal(conexao);
            IssueType tipoTarefaSecundaria = getTipoIssueTarefaSecundaria(conexao);
            IssueType tipoTarefaPrincipal = getTipoIssueTarefaPrincipal(conexao);
            BasicPriority prioridadeAlta = getPrioridadeMaxiama(conexao);

            TarefaJira tarefaPrincipal = buildTarefaPrincipal(conexao, pTarefa);
            String codigoTarefaPrincipal = tarefaPrincipal.getCodigoTarefa();
            System.out.println("Codigo Tarefa principal->" + codigoTarefaPrincipal);
            if (tarefaPrincipal == null) {
                throw new UnsupportedOperationException("Impossível deterinar a tarefa principal para" + pTarefa.getReferencia());
            }
            String idTarefaPrincipal = getIdTarefaPeloLabel(conexao, tarefaPrincipal);

            // verificando se tarefa principal existe
            BasicIssue tarefaPrincipalCriada = null;
            IssueInput issuePrincipal = null;
            if (idTarefaPrincipal == null) {
                issuePrincipal = tarefaPrincipal.getIssueGrupoAcoes(projeto, tipoTarefaPrincipal, prioridadeAlta, pUsuarioResponsavel);
            } else {
                issuePrincipal = tarefaPrincipal.getIssueGrupoAcoes(projeto, tipoTarefaPrincipal, prioridadeAlta, pUsuarioResponsavel);
            }
            //caso não exista
            if (idTarefaPrincipal == null) {
                tarefaPrincipalCriada = conexao.getIssueClient().createIssue(issuePrincipal).claim();
                //caso exista
            } else {
                conexao.getIssueClient().updateIssue(idTarefaPrincipal, issuePrincipal);

                tarefaPrincipalCriada = conexao.getIssueClient().getIssue(idTarefaPrincipal).claim();
            }

            //Obtendo ID da tarefa
            String idTarefa = getIdSubTarefaPeloLabel(conexao, pTarefa);
            IssueInput issueTarefa = pTarefa.getIssue(projeto, tipoTarefaSecundaria, prioridadeAlta, tarefaPrincipalCriada, pUsuarioResponsavel);
            String codigoTarefa = pTarefa.getCodigoTarefa();
            System.out.println("Codigo  principal->" + codigoTarefa);
            System.out.println(pTarefa.getNomeTarefa());

            if (idTarefa == null) {
                BasicIssue tarefaCriada = conexao.getIssueClient().createIssue(issueTarefa).claim();
            } else {
                conexao.getIssueClient().updateIssue(idTarefa, issueTarefa);
            }
            return true;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro cadastrando tarefa" + pTarefa.getNomeTarefa(), t);
            return false;
        }

    }

    public static TarefaJira getTarefaJiraEntidade(TIPOS_DE_TAREFA_JIRA pTipoTarefaJira, Class pEntidade) {
        TarefaJira tarefa = pTipoTarefaJira.getTarefaInssueJira();
        String nomeEntidade = UtilCRCReflexaoObjeto.getNomeDoObjetoPorAnotacaoInfoClasse(pEntidade);
        tarefa.setTabelaVinculada(pEntidade);
        tarefa.setTipoTarefa(pTipoTarefaJira);

        switch (pTipoTarefaJira) {
            case ACAO_TESTE_MANAGED_BEAN:

            case ACAO_IMPLEMENTACAO_MANAGED_BEAN:

            case ACAO_CRIAR_FORMULARIO:

            case ACAO_CRIAR_FORMULARIO_COMPLEXO:

            case ACAO_IMPLEMENTAR_CONTROLLER:

            case ACAO_TESTE_CONTROLLER:

            case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:

            case ACAO_TESTE_CONTROLLER_COMPLEXO:

            case ACAO_TESTES_AMBIENTE_DE_DADOS:

            case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:

            case ACAO_BANCO_TESTES_TIPOS:

            case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
                throw new UnsupportedOperationException("Algo está errado, Esta tarefa é referente a ação do sistema, mas o tipo esta setado como" + pTipoTarefaJira.name());

            case ACAO_TESTES_ENTIDADE_CALCULO:
                tarefa.setNomeTarefa("Testes de Calculo para " + nomeEntidade);
                tarefa.setDescricaoTarefa("A partir de um ambiente de banco de dados pré configurado, um assert para cada Calculo deve ser gerado, conferindo o valor esperado");
                break;
            case ACAO_TESTES_ENTIDADE_LISTAS:
                tarefa.setNomeTarefa("Testes de Lista para " + nomeEntidade);
                tarefa.setDescricaoTarefa("A partir de um ambiente de banco de dados pré configurado, um assert para cada lista deve ser gerado, conferindo o valor esperado");
                break;
            case ACAO_ENTIDADE_CRIAR_CALCULOS:
                tarefa.setNomeTarefa("Implementação de Calculos para " + nomeEntidade);
                tarefa.setDescricaoTarefa("Os calculos implementam campos somente leitura que retornam um valor especifico encontrado no banco de dados a partir do parametro id da entidade " + nomeEntidade);
                break;
            case ACAO_ENTIDADE_CRIAR_LISTAS:
                tarefa.setNomeTarefa("Implementação de listas para " + nomeEntidade);
                tarefa.setDescricaoTarefa("Implementa lista de registros que podem ser obtidos a partir do id da entidade" + nomeEntidade);
                break;
            case ACAO_ENTIDADE_VALIDAR_CAMPOS_REQUISITO:
                tarefa.setNomeTarefa("Validação de todas as configuranções de label, notNull, infocampo, e anotações  da entidade " + nomeEntidade);
                tarefa.setDescricaoTarefa("Criação de assert para todos os campos da entidade [" + nomeEntidade + "] ");

                break;
            default:
                throw new AssertionError(pTipoTarefaJira.name());

        }
        return tarefa;

    }

    public static String getSlugFacilAcao(ComoAcaoDoSistema pAcao) {
        try {
            String slugModulo = UtilCRCStringFiltros.getPrimeirasXLetrasDaString(pAcao.getModulo().getNome(), 5);
            String slugNomeAcao = pAcao.getEnumAcaoDoSistema().toString();
            return slugModulo + " " + slugNomeAcao;
        } catch (Throwable t) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro obtendo Slug facil da ação " + pAcao, t);
            return null;
        }
    }

    public static TarefaJira getTarefaJiraAcaoDoSistema(TIPOS_DE_TAREFA_JIRA pTipoTarefaJira, ComoAcaoDoSistema pAcao) {
        TarefaJira tarefa = pTipoTarefaJira.getTarefaInssueJira();
        tarefa.setAcaoVinculada(pAcao);
        tarefa.setTipoTarefa(pTipoTarefaJira);
        switch (pTipoTarefaJira) {
            case ACAO_TESTE_MANAGED_BEAN:

                pAcao.getComoGestaoEntidade().getClasseRelacionada();
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Criar Teste Managed Bean para " + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Simular todo o Fluxo nescessário para utilização do XHTML em" + pAcao.getNomeAcao());

                break;
            case ACAO_IMPLEMENTACAO_MANAGED_BEAN:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Implementar Managed Bean para " + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Implementar o código atendendo os requisitos do teste de fluxo criado pelo desenvolvedor TDD");
                break;
            case ACAO_CRIAR_FORMULARIO:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Criar formulario para " + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Criar o XHTML para " + pAcao.getDescricao() + " <br> não esqueça da responsividade,"
                        + " e da criatividade do usuário que clicará em tudo e ficará triste com erros, deixe o usuário ser feliz!");
                break;
            case ACAO_CRIAR_FORMULARIO_COMPLEXO:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Criar formulario Complexo para " + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Criar o XHTML Complexo para pagina descrita como: " + pAcao.getDescricao() + " <br> não esqueça da responsividade,"
                        + " e da criatividade do usuário que clicará em tudo e ficará triste com erros, deixe o usuário ser feliz!"
                        + " <br> Atenção! por se um formulário complexo, é recomendável que você altere o tempo previsto, lembre-se conseguir prever a data de entrega está atrelado a honra dos programadore super-bits ");
                break;
            case ACAO_TESTES_AMBIENTE_DE_DADOS:
            case ACAO_BANCO_IMPLEMENTACAO_AMBIENTE_DE_DADOS:
            case ACAO_BANCO_TESTES_TIPOS:
            case ACAO_BANCO_IMPLEMENTACAO_TIPOS:
            case ACAO_TESTES_ENTIDADE_CALCULO:
            case ACAO_TESTES_ENTIDADE_LISTAS:
            case ACAO_ENTIDADE_CRIAR_CALCULOS:
            case ACAO_ENTIDADE_CRIAR_LISTAS:
                throw new UnsupportedOperationException(" Algo está errado, Esta tarefa é referente a ação do sistema, mas o tipo esta setado como" + pTipoTarefaJira.name());
            case ACAO_IMPLEMENTAR_CONTROLLER:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Implementação de código para " + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Implementar codigo e complementos do teste de acordo com a especificaçao do analista TDD");
                break;
            case ACAO_TESTE_CONTROLLER:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + "Teste TDD para" + pAcao.getNomeAcao());
                tarefa.setDescricaoTarefa("Cria os testes pensando na metodologia TDD, para ação descrita como " + pAcao.getNomeAcao() + " um bom teste deve testar a evocação do métod em todas as opções possíveis, verificando  via comandos de assert o resultado esperado, um bom teste considera não só as opções disponíveis para o usuaŕio, "
                        + "como também aqueles possíveis de serem cometidas por programadores e clientes de api");
            case ACAO_IMPLEMENTAR_CONTROLLER_COMPLEXO:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Implemetação código para " + pAcao.getNome());
                tarefa.setDescricaoTarefa("A implementação do código da ação descrita como " + pAcao.getDescricao() + ",dica: F6 para testar, control F6 para debugar, e um atalho pode ser criado para executar o método de teste focado antes iniciar a implementação leia o fluxo do teste, o teste vai te dar uma visão ainda mais clara sobre o que este método faz, além disso se você der uma re-lida na documentação pode evitar re-trabalho e tristeza");
                break;
            case ACAO_TESTE_CONTROLLER_COMPLEXO:
                tarefa.setNomeTarefa(getSlugFacilAcao(pAcao) + " Criar testes TDD para " + pAcao.getNome());
                tarefa.setDescricaoTarefa("O bom desenvolvimento do TDD para controller complexo deve ser realizado com muito cuidado, pois todas as possibilidades devem ser pensadas, nesta etapa é normal detectar a nescessidade de criar novos campos e implementações nos objtos esse trabalho precisa ser realizado pelo TDD, qualquer desuido aqui poderá gerar grandes retrabalhos e perda de tempo na implementação, por ser uma ação  complexa é recomendavel alterar o tempo previsto... (lembrando que a nossa honra está em jogo quando falamos de tempo previsto ");
                break;
            default:
                throw new AssertionError("A regra de negocio para O tipo de tarefa:" + pTipoTarefaJira.name() + "não foi definda");

        }
        return tarefa;

    }

    public static TarefaJira getTarefaJiraDoBancoDeDados(TIPOS_DE_TAREFA_JIRA pTipoTarefaJira, Object pEntidade) {
        TarefaJira tarefa = pTipoTarefaJira.getTarefaInssueJira();
        return tarefa;
    }

    public static void limparPorJiraQuery(JiraRestClient pConexao, String pJiraPQL) {

        boolean fim = false;

        while (!fim) {
            SearchResult tarefas = pConexao.getSearchClient().searchJql("project = SK AND" + pJiraPQL).claim();
            for (Issue issue : tarefas.getIssues()) {
                pConexao.getIssueClient().deleteIssue(issue.getKey(), true).claim();

            }
            if (tarefas.getTotal() == 0) {
                fim = true;
            }
        }

    }

}
