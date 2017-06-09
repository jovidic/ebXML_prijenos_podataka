
package org.foi.hr.ebxml.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.foi.hr.ebxml.helper.PrintAndWrite;
import org.foi.hr.ebxml.helper.SOAPHeaderFactory;

/**
 * Web servis koji prima parametre potrebne za kreiranje zaglavlja SOAP poruke,
 * nakon kreiranja otvara vezu prema aplikaciji B_jovidic te šalje SOAP
 * zaglavlje
 *
 * @author jovidic
 */
@WebService(serviceName = "EbMSWebService")
public class EbMSWebService {

    private SOAPConnectionFactory conFac;
    private SOAPConnection con;
    private int from;
    private int to;
    private String cost;
    private SOAPMessage poruka;

    @WebMethod(operationName = "createAndSend")
    public void createAndSend(@WebParam(name = "from") String from, @WebParam(name = "to") String to, @WebParam(name = "cpaId") String cpaId, @WebParam(name = "services") String services, @WebParam(name = "action") String action, @WebParam(name = "msgData") String msgData, @WebParam(name = "conversationId") String conversationId, @WebParam(name = "date") String date, @WebParam(name = "id") String id) {

        try {
            this.conFac = SOAPConnectionFactory.newInstance();
            con = conFac.createConnection();

            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage msg = mf.createMessage();
            SOAPPart sp = msg.getSOAPPart();
            SOAPEnvelope envelope = sp.getEnvelope();
            SOAPHeaderFactory.ebSetup(envelope, from, to, cpaId, conversationId, services, action, msgData, date, id);

            PrintAndWrite.printCurrMessage(msg, "A je poslao Header", "A_salje_header");

            URLEndpoint endPoint = new URLEndpoint("http://localhost:8080/B_jovidic/ReceiveQueryRequestServlet");

            con.call(msg, endPoint);
        } catch (SOAPException | UnsupportedOperationException | IOException ex) {
            Logger.getLogger(EbMSWebService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
