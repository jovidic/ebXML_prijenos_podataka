/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.hr.ebxml.data;

/**
 * Klasa koja opisuje model podataka SOAP poruke potreban za prikaz korisniku
 *
 * @author jovidic
 */
public class SOAPOutputData {

    private String from;
    private String to;
    private String cpaId;
    private String conversationId;
    private String service;
    private String action;
    private String msgData;
    private String timestamp;
    private String description;
    private String msgId;

    private String toR;
    private String fromR;
    private String costR;
    private String dateR;

    public SOAPOutputData(String id, String from, String to, String cpaId, String conversationId, String service, String action, String msgData, String timestamp) {
        this.msgId = id;
        this.from = from;
        this.to = to;
        this.cpaId = cpaId;
        this.conversationId = conversationId;
        this.service = service;
        this.action = action;
        this.msgData = msgData;
        this.timestamp = timestamp;
    }

    public SOAPOutputData(String id, String from, String to, String cpaId, String conversationId, String service, String action, String msgData, String timestamp, String description) {
        this.msgId = id;
        this.from = from;
        this.to = to;
        this.cpaId = cpaId;
        this.conversationId = conversationId;
        this.service = service;
        this.action = action;
        this.msgData = msgData;
        this.timestamp = timestamp;
        this.description = description;
    }

    public SOAPOutputData() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCpaId() {
        return cpaId;
    }

    public void setCpaId(String cpaId) {
        this.cpaId = cpaId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMsgData() {
        return msgData;
    }

    public void setMsgData(String msgData) {
        this.msgData = msgData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getToR() {
        return toR;
    }

    public void setToR(String toR) {
        this.toR = toR;
    }

    public String getFromR() {
        return fromR;
    }

    public void setFromR(String fromR) {
        this.fromR = fromR;
    }

    public String getCostR() {
        return costR;
    }

    public void setCostR(String costR) {
        this.costR = costR;
    }

    public String getDateR() {
        return dateR;
    }

    public void setDateR(String dateR) {
        this.dateR = dateR;
    }

}
