package org.foi.hr.ebxml.servlet;

import javax.xml.messaging.*;
import javax.xml.soap.*;
import javax.servlet.*;
import org.jdom2.*;
import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.hr.ebxml.data.SOAPOutputData;
import org.foi.hr.ebxml.helper.PrintAndWrite;
import org.foi.hr.ebxml.helper.SOAPHeaderFactory;
import static org.foi.hr.ebxml.helper.SOAPHeaderFactory.EB;
import static org.foi.hr.ebxml.helper.SOAPHeaderFactory.URI_EB;
import static org.foi.hr.ebxml.helper.SOAPHeaderFactory.URI_XLINK;
import static org.foi.hr.ebxml.helper.SOAPHeaderFactory.XLINK;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * JAXM servlet koji očekuje poruku web servisa aplikacije A s zaglavljem, te na
 * temelju zaglavlja kreira kompletnu ebMS poruku po specifikacijama i šalje je
 * u servlet aplikacije A
 *
 * @author jovidic
 */
public class ReceiveQueryRequestServlet extends JAXMServlet implements OnewayListener {

    private SOAPConnectionFactory conFac;
    private SOAPConnection con;
    private static SOAPMessage finalToSend;
    private static SOAPMessage recivedHeader;
    public static SOAPOutputData transData = new SOAPOutputData();
    public static SOAPOutputData sodHeaderRecived;
    public static SOAPOutputData sodHeaderSent;
    /*
     @Override
     public void init(ServletConfig servletConfig) throws ServletException {
     super.init(servletConfig);
     }*/

    /**
     * Metoda koja primljeni zahtjev preusmjeruje na servlet za prikaz korisniku
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        response.sendRedirect("/B_jovidic/OutputServlet");

    }

    /**
     * Metoda koja na temelju ulazne SOAP poruke kreira novo zaglavlje i
     * privitak, kao i sami čin slanja poruke aplikaciji A_jovidic
     *
     * @param message
     */
    @Override
    public void onMessage(SOAPMessage message) {
        try {
            recivedHeader = message;

            this.conFac = SOAPConnectionFactory.newInstance();
            con = conFac.createConnection();

            PrintAndWrite.printCurrMessage(message, "B je primio header", "B_prima");

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
            String service = ebService.getValue();

            SOAPElement ebAction = SOAPHeaderFactory.getChild(envelope, ebHeader, "Action");
            String action = ebAction.getValue();

            SOAPElement ebMsgData = SOAPHeaderFactory.getChild(envelope, ebHeader, "MessageData");
            String msgdata = ebMsgData.getValue();

            SOAPElement ebTimestamp = SOAPHeaderFactory.getChild(envelope, ebHeader, "Timestamp");
            String timestamp = ebTimestamp.getValue();

            MessageFactory mf = MessageFactory.newInstance();
            finalToSend = mf.createMessage();
            SOAPPart sp = finalToSend.getSOAPPart();
            String id = giveRandom();
            sodHeaderRecived = new SOAPOutputData(id, from, to, cpaid, convid, service, action, msgdata, timestamp);

            SOAPEnvelope newenvelope = sp.getEnvelope();

            String time = new Date().toString();
            SOAPHeaderFactory.ebSetup(newenvelope, to, from, cpaid, convid, "opisUsluge", "opisAkcije", "porukaPrimljena", time, id);

            SOAPBody body = newenvelope.getBody();
            SOAPElement manifest = body.addBodyElement(newenvelope.createName("Manifest", EB, URI_EB));
            SOAPElement reference = manifest.addChildElement(newenvelope.createName("Reference", EB, URI_EB));
            reference.addAttribute(newenvelope.createName("href", XLINK, URI_XLINK), "prijenos@josipu.com");
            reference.addAttribute(newenvelope.createName("type", XLINK, URI_XLINK), "referenca");
            SOAPElement description = reference.addChildElement(newenvelope.createName("Description", EB, URI_EB));
            description.addTextNode("Prijenos");

            sodHeaderSent = new SOAPOutputData(id, to, from, cpaid, convid, "opisUsluge", "opisAkcije", "porukaPrimljena", time, "Prijenos");

            Namespace namespace = Namespace.getNamespace("trans", "http://localhost:8080/B_jovidic/files/schemaEbMsATT.xsd");

            Document doc = createEbMSPayloadMsg(namespace);

            StringWriter sw = new StringWriter();

            XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
            outputter.output(doc, sw);

            AttachmentPart ap = finalToSend.createAttachmentPart(sw.toString(), "text/plain; charset=ISO-8859-1");
            ap.setContentType("text/plain; charset=ISO-8859-1");
            ap.setContentId("potvrda@josip.com");
            finalToSend.addAttachmentPart(ap);

            PrintAndWrite.printCurrMessage(finalToSend, "B je poslao poruku", "B_salje");

            URLEndpoint endPoint = new URLEndpoint("http://localhost:9754/A_jovidic/SenderClient");
            con.call(finalToSend, endPoint);

        } catch (SOAPException | UnsupportedOperationException | IOException ex) {
            Logger.getLogger(ReceiveQueryRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda daje pseudo-nasumični broj u obliku String objekta
     *
     * @return
     */
    public static String giveRandom() {
        Random r = new Random();
        Integer num = r.nextInt(10000);

        String t = num.toString();

        return t;

    }

    /**
     * Pomoćna metoda koja na temelju zadanog namespacea kreira privitak SOAP
     * poruci
     *
     * @param namespace
     * @return
     */
    public Document createEbMSPayloadMsg(Namespace namespace) {
        String r = "";
        Element transfer = new Element("Transfer", namespace);

        Element fromElement = new Element("From", namespace);
        r = giveRandom();
        fromElement.addContent(r);
        transfer.addContent(fromElement);
        transData.setFromR(r);

        Element toElement = new Element("To", namespace);
        r = giveRandom();
        toElement.addContent(r);
        transfer.addContent(toElement);
        transData.setToR(r);

        Element costElement = new Element("Cost", namespace);
        r = giveRandom();
        costElement.addContent(r + " kn");
        transfer.addContent(costElement);
        transData.setCostR(r);

        Date date = new Date();
        Element dateElement = new Element("Date", namespace);
        dateElement.addContent(date.toString());
        transfer.addContent(dateElement);
        transData.setDateR(date.toString());

        Document doc = new Document(transfer);

        return doc;

    }

    public static SOAPMessage getTempMessage() {
        return finalToSend;
    }

    public static SOAPMessage getRecivedHeader() {
        return recivedHeader;
    }

    public static SOAPOutputData getTransData() {
        return transData;
    }

}
