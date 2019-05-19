/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira.tempo;

import com.atlassian.jira.rest.client.internal.json.JsonArrayParser;
import java.util.ArrayList;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author desenvolvedor
 */
public class PlanTimeJsonParcer implements JsonArrayParser<Iterable<PlanTimeObjeto>> {

    @Override
    public Iterable<PlanTimeObjeto> parse(JSONArray json) throws JSONException {
        ArrayList<PlanTimeObjeto> res = new ArrayList<PlanTimeObjeto>(json.length());

        //final DateTime created = JsonParseUtil.parseDateTime(json, "created");
        //final BasicUser author = json.has("author") ? JsonParseUtil.parseBasicUser(json.getJSONObject("author")) : null;
        //final Collection<ChangelogItem> items = JsonParseUtil.parseJsonArray(json.getJSONArray("items"), changelogItemJsonParser);
        return res;
    }

}
