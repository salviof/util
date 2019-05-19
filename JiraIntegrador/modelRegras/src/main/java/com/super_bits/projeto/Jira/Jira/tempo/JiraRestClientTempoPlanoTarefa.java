/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira.tempo;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.SessionRestClient;
import com.atlassian.jira.rest.client.api.domain.Session;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.json.BasicProjectsJsonParser;
import com.atlassian.jira.rest.client.internal.json.SessionJsonParser;
import com.atlassian.util.concurrent.Promise;

import com.super_bits.modulosSB.SBCore.UtilGeral.UTILSBCoreDesktopApp;

import java.net.URI;
import java.util.Iterator;
import javax.ws.rs.core.UriBuilder;
import org.coletivojava.fw.api.objetoNativo.mensagem.MensagemUsuario;

/**
 *
 * @author salvioF
 */
public class JiraRestClientTempoPlanoTarefa extends AbstractAsynchronousRestClient implements SessionRestClient {

    private final SessionJsonParser sessionJsonParser = new SessionJsonParser();
    private final URI serverUri;
    private final HttpClient cliente;
    private static final String JSON_CONTENT_TYPE = "application/json";
    private final BasicProjectsJsonParser basicProjectsJsonParser = new BasicProjectsJsonParser();

    public JiraRestClientTempoPlanoTarefa(final URI serverUri, final HttpClient client) {
        super(client);
        cliente = client;
        this.serverUri = serverUri;
    }

    @Override
    public Promise<Session> getCurrentSession() throws RestClientException {
        return getAndParse(UriBuilder.fromUri(serverUri).path("rest/tempo-planning/1/allocation").build(), sessionJsonParser);
    }

    public void listarPLanosDeTrabalho() {

        try {
            URI urlAcesso = UriBuilder.fromUri(serverUri).path("rest/tempo-planning/1/allocation").build();
            Promise<Iterable<PlanTimeObjeto>> teste = getAndParse(urlAcesso, new PlanTimeJsonParcer());
            Iterable<PlanTimeObjeto> lista = teste.claim();

            for (Iterator iterator = lista.iterator(); lista.iterator().hasNext();) {
                Object next = iterator.next();

            }

        } catch (Throwable t) {
            UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemUsuario(t.getMessage()));
            UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemUsuario(t.getCause().getMessage()));
            UTILSBCoreDesktopApp.showMessageStopProcess(new MensagemUsuario(t.getLocalizedMessage()));
        }

    }

    public void incluirPLanosDeTrabalho() {

    }

}
