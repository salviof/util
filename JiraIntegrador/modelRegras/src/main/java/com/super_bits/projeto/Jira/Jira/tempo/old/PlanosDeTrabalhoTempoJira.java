/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package com.super_bits.projeto.Jira.Jira.tempo.old;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 *
 * [{"id":79,{ "assignee":{"key":"salvio","type":"user"}, "planItem":{
 * "key":"SK-200", "id":27101, "type":"ISSUE", "name":"SK-200",
 * "summary":"testettttt", "description":"teste", "iconName":"Task",
 * "iconUrl":"/secure/viewavatar?size=xsmall&avatarId=11518&avatarType=issuetype",
 * "projectKey":"SK", "projectColor":"#EC8E00",
 * "avatarUrls":{"16x16":"/secure/projectavatar?size=xsmall&pid=11001&avatarId=12700","24x24":"/secure/projectavatar?size=small&pid=11001&avatarId=12700","32x32":"/secure/projectavatar?size=medium&pid=11001&avatarId=12700","48x48":"/secure/projectavatar?pid=11001&avatarId=12700"},
 * "planItemUrl":"browse/SK-200"}, "scope":{"id":11001,"type":"project"},
 * "commitment":25.0,"start":"2016-06-06", "end":"2016-06-06",
 * "description":"Teste 2 horas Trabalho", "seconds":7200,
 * created":"2016-06-06", createdBy":"salvio", "updated":"2016-06-06",
 * "updatedBy":"salvio", "recurrence":{"rule":"NEVER"}},
 * {"id":77,"assignee":{"key":"salvio","type":"user"}":
 *
 *
 * @author desenvolvedor
 */
public class PlanosDeTrabalhoTempoJira {

    private String id;
    private Assignee assignee;
    private List<planItem> planItem;

    private class Assignee {

        private String key = "SALVIO";
        private String type = "USER";
    }

    private class Scopo {

        private String id;
        private String type = "project";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    private class planItem {

        String key;
        String id;
        String type;
        String name;
        String sumary;
        String description;
        String iconName;
        String iconUrl;
        String projectColor;
        String planItemURL;
        Scopo scope;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSumary() {
            return sumary;
        }

        public void setSumary(String sumary) {
            this.sumary = sumary;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconName() {
            return iconName;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getProjectColor() {
            return projectColor;
        }

        public void setProjectColor(String projectColor) {
            this.projectColor = projectColor;
        }

        public String getPlanItemURL() {
            return planItemURL;
        }

        public void setPlanItemURL(String planItemURL) {
            this.planItemURL = planItemURL;
        }

        public Scopo getScope() {
            return scope;
        }

        public void setScope(Scopo scope) {
            this.scope = scope;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public List<planItem> getPlanItem() {
        return planItem;
    }

    public void setPlanItem(List<planItem> planItem) {
        this.planItem = planItem;
    }

}
