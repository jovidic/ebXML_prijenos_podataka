
package org.foi.hr.ebxml.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;
import static org.foi.hr.ebxml.servlet.SenderClient.sodHeaderSentToB;
import static org.foi.hr.ebxml.servlet.SenderClient.sodReceivedFromB;
import static org.foi.hr.ebxml.servlet.SenderClient.transData;

/**
 * Servlet koji postavlja atribute te prikazuje korisniku komunikaciju s Web
 * servisom kao i poruku primljenu od strane aplikacije B_jovidic
 * @author jovidic
 */
public class OutputServlet extends HttpServlet {

       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
            response.setContentType("text/html;charset=UTF-8");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
           try {
               SenderClient.getFullMessage().writeTo(out);
           } catch (SOAPException ex) {
               Logger.getLogger(OutputServlet.class.getName()).log(Level.SEVERE, null, ex);
           }
            String strMsg = new String(out.toByteArray());

            //ReceiveQueryRequestServlet.getRecivedHeader().writeTo(out2);
            //String strMsg1 = new String(out2.toByteArray());
            String strMsg1 = " klfdsjkdasdsadasdsadjsf ";

            request.setAttribute("timestamp", sodHeaderSentToB.getTimestamp());
            request.setAttribute("from", sodHeaderSentToB.getFrom());
            request.setAttribute("to", sodHeaderSentToB.getTo());
            request.setAttribute("cpaid", sodHeaderSentToB.getCpaId());
            request.setAttribute("convid", sodHeaderSentToB.getConversationId());
            request.setAttribute("service", sodHeaderSentToB.getService());
            request.setAttribute("action", sodHeaderSentToB.getAction());
            request.setAttribute("data", sodHeaderSentToB.getMsgData());
            request.setAttribute("id", sodHeaderSentToB.getMsgId());

            request.setAttribute("timestamp1", sodReceivedFromB.getTimestamp());
            request.setAttribute("from1", sodReceivedFromB.getFrom());
            request.setAttribute("to1", sodReceivedFromB.getTo());
            request.setAttribute("cpaid1", sodReceivedFromB.getCpaId());
            request.setAttribute("convid1", sodReceivedFromB.getConversationId());
            request.setAttribute("service1", sodReceivedFromB.getService());
            request.setAttribute("action1", sodReceivedFromB.getAction());
            request.setAttribute("data1", sodReceivedFromB.getMsgData());
            request.setAttribute("description1", sodReceivedFromB.getDescription());
            request.setAttribute("id1", sodReceivedFromB.getMsgId());
            
            request.setAttribute("toR", transData.getToR());
            request.setAttribute("fromR", transData.getFromR());
            request.setAttribute("costR", transData.getCostR());
            request.setAttribute("dateR", transData.getDateR());

            request.setAttribute("msg", strMsg);
            //request.setAttribute("msg1", strMsg1);
            RequestDispatcher view = request.getRequestDispatcher("/output.jsp");
            view.forward(request, response);


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
