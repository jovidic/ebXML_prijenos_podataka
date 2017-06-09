/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.hr.ebxml.helper;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.*;
import org.foi.hr.ebxml.data.SOAPOutputData;

/**
 * Klasa koja sadrži pomoćne metode za pretragu i kreiranje SOAP zaglavlja
 * poruke
 *
 * @author jovidic
 */
public class SOAPHeaderFactory {

    public static SOAPOutputData sod;
    public static final String EB = "eb";
    public static final String URI_EB = "http://www.ebxml.org/project_teams/transport/messageHeader.xsd";
    public static final String XLINK = "xlink";
    public static final String URI_XLINK = "http://www.w3.org/1999/xlink";

    public static void ebSetup(SOAPEnvelope envelope,
            String from,
            String to,
            String cpaID,
            String conversationID,
            String service,
            String action,
            String msgData,
            String timestamp,
            String id
    ) {
        try {
            SOAPHeader header = envelope.getHeader();
            SOAPHeaderElement ebHeader = header.addHeaderElement(envelope.createName("MessageHeader", EB, URI_EB));
            ebHeader.setMustUnderstand(true);
            ebHeader.addAttribute(envelope.createName("version"), "1.0");
            SOAPElement ebFrom = createSOAPElem(ebHeader, "From", envelope);
            SOAPElement ebPartyFrom = createSOAPElem(ebFrom, "PartyId", envelope);
            ebPartyFrom.addTextNode(from);
            SOAPElement ebTo = createSOAPElem(ebHeader, "To", envelope);
            SOAPElement ebPartyTo = createSOAPElem(ebTo, "PartyId", envelope);
            ebPartyTo.addTextNode(to);
            SOAPElement ebCPAId = createSOAPElem(ebHeader, "CPAId", envelope);
            ebCPAId.addTextNode(cpaID);
            SOAPElement ebConv = createSOAPElem(ebHeader, "ConversationId", envelope);
            ebConv.addTextNode(conversationID);
            SOAPElement ebService = createSOAPElem(ebHeader, "Service", envelope);
            ebService.addTextNode(service);
            SOAPElement ebAction = createSOAPElem(ebHeader, "Action", envelope);
            ebAction.addTextNode(action);
            SOAPElement ebMesData = createSOAPElem(ebHeader, "MessageData", envelope);
            ebMesData.addTextNode(msgData);
            SOAPElement ebMesId = createSOAPElem(ebHeader, "MessageId", envelope);
            ebMesId.addTextNode(id);
            SOAPElement ebMesTime = createSOAPElem(ebHeader, "Timestamp", envelope);

            ebMesTime.addTextNode(timestamp);

            sod = new SOAPOutputData(id, from, to, cpaID, conversationID, service, action, msgData, timestamp);

        } catch (Exception e) {
        }
    }

    /**
     * Pomoćna metoda za dohvaćanje SOAP elementa unutar poruke
     *
     * @param envelope
     * @param element
     * @param child
     * @return
     */
    public static SOAPElement getChild(SOAPEnvelope envelope, SOAPElement element, String child) {
        try {
            Iterator iter = element.getChildElements(envelope.createName(child, EB, URI_EB));
            if (iter.hasNext()) {
                SOAPElement e = (SOAPElement) iter.next();
                return e;
            } else {
                return null;
            }
        } catch (SOAPException ex) {
            Logger.getLogger(SOAPHeaderFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Pomoćna metoda za kreiranje SOAP elementa unutar zaglavlja
     *
     * @param ebHeader
     * @param name
     * @param envelope
     * @return
     */
    private static SOAPElement createSOAPElem(SOAPHeaderElement ebHeader, String name, SOAPEnvelope envelope) {
        try {
            SOAPElement sp = ebHeader.addChildElement(envelope.createName(name, EB, URI_EB));
            return sp;
        } catch (SOAPException ex) {
            Logger.getLogger(SOAPHeaderFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Pomoćna metoda za kreiranje SOAP elementa unutar drugog elementa
     *
     * @param ebHeader
     * @param name
     * @param envelope
     * @return
     */
    private static SOAPElement createSOAPElem(SOAPElement ebHeader, String name, SOAPEnvelope envelope) {
        try {
            SOAPElement sp = ebHeader.addChildElement(envelope.createName(name, EB, URI_EB));
            return sp;
        } catch (SOAPException ex) {
            Logger.getLogger(SOAPHeaderFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Metoda koja vraća u obliku Stringa pseudo-nasumični broj
     *
     * @return
     */
    public static String giveRandom() {
        Random r = new Random();
        Integer num = r.nextInt(10000);

        String t = num.toString();

        return t;

    }
}
