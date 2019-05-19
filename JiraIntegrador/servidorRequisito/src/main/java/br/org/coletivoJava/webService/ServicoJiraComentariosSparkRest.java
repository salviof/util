/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.webService;

import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisito;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisitoEnvioNovo;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ItfServiceComentariosJira;
import com.super_bits.modulosSB.SBCore.modulos.requisito.Requisito;
import com.super_bits.projeto.Jira.FabConfigModuloJiraIntegrador;

import com.super_bits.projeto.Jira.Jira.MapaTarefasProjeto;
import com.super_bits.projeto.Jira.Jira.TarefaSuperBits;
import com.super_bits.projeto.Jira.ProjetoJiraSuperBits;

/**
 *
 * @author desenvolvedor
 */
public class ServicoJiraComentariosSparkRest implements ItfServiceComentariosJira {

    private static final ConfigModulo CONFIGURACOES = SBCore.getConfigModulo(FabConfigModuloJiraIntegrador.class);

    @Override
    public RespostaWebService adicionarComentario(ComentarioRequisitoEnvioNovo coment) {

        ProjetoJiraSuperBits testeOFicialProjeto = new ProjetoJiraSuperBits(CONFIGURACOES);
        ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(coment.getAcaoDoSistema());
        TarefaSuperBits tr = MapaTarefasProjeto.getTarefaDaAcao(acao);
        testeOFicialProjeto.adicionarComentario(tr, coment.getComentario());

        return (RespostaWebService) new RespostaWebService(ComentarioRequisito.class, FabAcaoServidorRequisito.COMENTARIO_CTR_CADASTRAR_COMENTARIO.getRegistro()).setRetorno(coment);
    }

    @Override
    public ItfResposta obterRequisitoDeAcaoDoProjeto(String acaoNomeUnico) {
        ProjetoJiraSuperBits testeOFicialProjeto = new ProjetoJiraSuperBits(CONFIGURACOES);
        ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(acaoNomeUnico);
        TarefaSuperBits tr = MapaTarefasProjeto.getTarefaDaAcao(acao);
        Issue tarefa = testeOFicialProjeto.getInssueJiraByTarefa(tr);
        Requisito req = new Requisito();
        req.setNome(tarefa.getSummary());

        req.setUrlRequisito(CONFIGURACOES.getPropriedade(FabConfigModuloJiraIntegrador.SERVIDOR) + "/browse/" + tarefa.getKey());
        req.setDescricao(tarefa.getDescription());
        req.setStatusDescricao(tarefa.getStatus().getDescription());
        for (Comment coment : tarefa.getComments()) {
            ComentarioRequisito comentario
                    = new ComentarioRequisito(coment.getBody(), coment.getAuthor().getDisplayName());
            req.getComentarios().add(comentario);
        }
        RespostaWebService resp = new RespostaWebService(Requisito.class, FabAcaoServidorRequisito.COMENTARIO_CTR_CADASTRAR_COMENTARIO.getRegistro());
        resp.setRetorno(req);
        return resp;
    }

    @Override
    public ItfResposta obterTabelaDeAcaoDoProjeto(String pNomeClasse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
