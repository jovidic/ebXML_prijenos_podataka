
package org.foi.hr.ebxml.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.OnewayListener;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceRef;
import org.foi.hr.ebxml.data.SOAPOutputData;
import org.foi.hr.ebxml.helper.PrintAndWrite;
import org.foi.hr.ebxml.helper.SOAPHeaderFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

/**
 * JAXM servlet koji poziva metodu web servisa te očekuje ebMS poruku od
 * aplikacije B_jovidic
 *
 * @author jovidic
 */
public class SenderClient extends JAXMServlet implements OnewayListener {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9754/A_jovidic/EbMSWebService.wsdl")
    private EbMSWebService_Service service;

    private static SOAPMessage fullMessage;
    public static SOAPOutputData transData = new SOAPOutputData();
    public static SOAPOutputData sodHeaderSentToB;
    public static SOAPOutputData sodReceivedFromB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Date d = new Date();
        String r = d.toString();
        String ra = SOAPHeaderFactory.giveRandom();

        createAndSend("A-jovidic", "B_jovidic", "123", "USLUGA", "AKCIJA", "MSG Data", "ConversationIDIS456789", r, ra);
        
        SenderClient.sodHeaderSentToB = new SOAPOutputData(ra, "A-jovidic", "B_jovidic", "123", "ConversationIDIS456789", "USLUGA", "AKCIJA", "MSG Data", r);

        response.sendRedirect("/A_jovidic/OutputServlet");
    }

    @Override
    public void onMessage(SOAPMessage message) {

        try {

            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();

            SOAPElement ebHeader = SOAPHeaderFactory.getChild(envelope, header, "MessageHeader");

            SOAPElement ebHeaderTo = SOAPHeaderFactory.getChild(envelope, ebHeader, "To");
            SOAPElement ebHeaderToParty = SOAPHeaderFactory.getChild(envelope, ebHeaderTo, "PartyId");
            String to = ebHeaderToParty.getValue();

            SOAPElement ebHeaderFrom = SOAPHeaderFactory.getChild(envelope, ebHeader, "From");
            SOAPElement ebHeaderFromParty = SOAPHeaderFactory.getChild(envelope, ebHeaderFrom, "PartyId");
            String from = ebHeaderFromParty.getValue();

            SOAPElement ebHeaderCPAId = SOAPHeaderFactory.getChild(envelope, ebHeader, "CPAId");
            String cpaid = ebHeaderCPAId.getValue();

            SOAPElement ebHeaderConversationId = SOAPHeaderFactory.getChild(envelope, ebHeader, "ConversationId");
            String convid = ebHeaderConversationId.getValue();

            SOAPElement ebService = SOAPHeaderFactory.getChild(envelope, ebHeader, "Service");
            String service1 = ebService.getValue();

            SOAPElement ebAction = SOAPHeaderFactory.getChild(envelope, ebHeader, "Action");
            String action = ebAction.getValue();

            SOAPElement ebMsgData = SOAPHeaderFactory.getChild(envelope, ebHeader, "MessageData");
            String msgdata = ebMsgData.getValue();

            SOAPElement ebTimestamp = SOAPHeaderFactory.getChild(envelope, ebHeader, "Timestamp");
            String timestamp = ebTimestamp.getValue();

            SOAPElement msgid = SOAPHeaderFactory.getChild(envelope, ebHeader, "MessageId");
            String msgidstr = msgid.getValue();


            sodReceivedFromB = new SOAPOutputData(msgidstr,from, to, cpaid, convid, service1, action, msgdata, timestamp, "Prijenos");


            PrintAndWrite.printCurrMessage(message, "A je primio poruku od B", "A_prima_povratnu");

            Iterator iterator = message.getAttachments();
            AttachmentPart ap = (AttachmentPart) iterator.next();

            InputStream is = ap.getRawContent();

            Namespace namespace = Namespace.getNamespace("trans", "http://localhost:9754/A_jovidic/files/schemaEbMsATT.xsd");
            SAXBuilder builder = new SAXBuilder();
            Document jdoc = builder.build(is);
            Element root = jdoc.getRootElement();

            List<Element> kids = root.getChildren();
            for (Element e : kids) {

                if (e.getName().equals("From")) {
                    transData.setFromR(e.getValue());

                }
                if (e.getName().equals("To")) {
                    transData.setToR(e.getValue());
                }
                if (e.getName().equals("Cost")) {
                    transData.setCostR(e.getValue());
                }
                if (e.getName().equals("Date")) {
                    transData.setDateR(e.getValue());
                }

            }

        } catch (SOAPException | IOException | JDOMException ex) {
            Logger.getLogger(SenderClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        fullMessage = message;

    }

    public static SOAPOutputData getTransData() {
        return transData;
    }

    public static SOAPOutputData getSodHeaderSentToB() {
        return sodHeaderSentToB;
    }

    public static SOAPOutputData getSodReceivedFromB() {
        return sodReceivedFromB;
    }

    public static SOAPMessage getFullMessage() {
        return fullMessage;
    }
/**
 * Poziv metode Web servisa
 * @param from
 * @param to
 * @param cpaId
 * @param services
 * @param action
 * @param msgData
 * @param conversationId
 * @param date
 * @param id 
 */
    public void createAndSend(java.lang.String from, java.lang.String to, java.lang.String cpaId, java.lang.String services, java.lang.String action, java.lang.String msgData, java.lang.String conversationId, java.lang.String date, java.lang.String id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.hr.ebxml.servlet.EbMSWebService port = service.getEbMSWebServicePort();
        port.createAndSend(from, to, cpaId, services, action, msgData, conversationId, date, id);
    }

}
