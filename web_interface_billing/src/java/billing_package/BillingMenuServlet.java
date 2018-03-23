/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billing_package;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mohamed
 */
public class BillingMenuServlet extends HttpServlet {


    Connection conn;
    PreparedStatement pst;
    ResultSet rs;   
    int msisdn;
    int customer_id;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pt = response.getWriter();
        conn = (Connection) request.getServletContext().getAttribute("conn");
        if (request.getParameter("msisdn") == null) {
            pt.println("<div align='center'>\n"
                    + "       <form method='get' action='BillingMenuServlet'>\n"
                    + "        <input type='number' name='msisdn' required><br><br>\n"
                    + "        <input type='submit' value='enter'>  \n"
                    + "           </form>\n"
                    + "        </div> \n"
                    + "");
        } else {
            msisdn = Integer.parseInt(request.getParameter("msisdn"));

        try {
            pst=conn.prepareStatement("select * from customer where msisdn=?");
            pst.setInt(1, msisdn);
            rs=pst.executeQuery();
            if(rs.next())
            {
                customer_id=rs.getInt(1);
                pt.println("one time fee services: <br> \n<form method='get' action='BillingServet'>");
                pst=conn.prepareStatement("select * from onetimefee");
                rs=pst.executeQuery();
                while(rs.next())
                {
                    pt.println("<input type='checkbox' name='checks' value='"+rs.getInt(3)+"'>"+rs.getString(2)+"<br>");
                }
                pt.println(
                            "<div align='center'>"
                          + "<input type='hidden' name='customer_id' value='"+customer_id+"'>"
                          + "<br><input type='submit' value='extract the bill'>"
                          + "</div></form>"
                           );
            }else{
                pt.println("<div align='center'>"
                        + "The MSISDN isn't avilable<br><br>"
                        + "<a href='BillingMenuServlet'>Back</a>"
                        + "</div>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillingMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
