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
import static org.foi.hr.ebxml.servlet.ReceiveQueryRequestServlet.sodHeaderRecived;
import static org.foi.hr.ebxml.servlet.ReceiveQueryRequestServlet.sodHeaderSent;

/**
 * Servlet koji postavlja atribute te prikazuje korisniku komunikaciju s Web
 * servisom kao i poruku poslanu aplikaciji A_jovidic
 *
 * @author jovidic
 */
public class OutputServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            ReceiveQueryRequestServlet.getTempMessage().writeTo(out);
            String strMsg = new String(out.toByteArray());

            ReceiveQueryRequestServlet.getRecivedHeader().writeTo(out2);
            String strMsg1 = new String(out2.toByteArray());

            request.setAttribute("timestamp", sodHeaderRecived.getTimestamp());
            request.setAttribute("from", sodHeaderRecived.getFrom());
            request.setAttribute("to", sodHeaderRecived.getTo());
            request.setAttribute("cpaid", sodHeaderRecived.getCpaId());
            request.setAttribute("convid", sodHeaderRecived.getConversationId());
            request.setAttribute("service", sodHeaderRecived.getService());
            request.setAttribute("action", sodHeaderRecived.getAction());
            request.setAttribute("data", sodHeaderRecived.getMsgData());
            request.setAttribute("id", sodHeaderRecived.getMsgId());

            request.setAttribute("timestamp1", sodHeaderSent.getTimestamp());
            request.setAttribute("from1", sodHeaderSent.getFrom());
            request.setAttribute("to1", sodHeaderSent.getTo());
            request.setAttribute("cpaid1", sodHeaderSent.getCpaId());
            request.setAttribute("convid1", sodHeaderSent.getConversationId());
            request.setAttribute("service1", sodHeaderSent.getService());
            request.setAttribute("action1", sodHeaderSent.getAction());
            request.setAttribute("data1", sodHeaderSent.getMsgData());
            request.setAttribute("description1", sodHeaderSent.getDescription());
            request.setAttribute("id1", sodHeaderSent.getMsgId());

            request.setAttribute("toR", ReceiveQueryRequestServlet.transData.getToR());
            request.setAttribute("fromR", ReceiveQueryRequestServlet.transData.getFromR());
            request.setAttribute("costR", ReceiveQueryRequestServlet.transData.getCostR());
            request.setAttribute("dateR", ReceiveQueryRequestServlet.transData.getDateR());

            request.setAttribute("msg", strMsg);
            request.setAttribute("msg1", strMsg1);
            RequestDispatcher view = request.getRequestDispatcher("/output.jsp");
            view.forward(request, response);
        } catch (SOAPException ex) {
            Logger.getLogger(OutputServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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
