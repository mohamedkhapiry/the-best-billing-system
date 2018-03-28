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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mohamed
 */
public class MainMenu extends HttpServlet {

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
        request.getRequestDispatcher("header.html").include(request, response);
        pt.println(
                "<div align='center'>"
               + "<table align='center' width='60%'>"
               + "<tr>"
                        + "<td align='center'><a href='RatePlan.jsp'> <img style='height: 50px' src='rate.png' ><br></a>add rateplan</td>"
                        + "<td align='center'><a href='AddCustomer.jsp'> <img style='height: 50px' src='customer.png'><br></a>add customer</td>"
                        + "<td align='center'><a href='BillingMenuServlet'> <img style='height: 50px' src='bill.png'><br></a>Extract bill</td>"
               +"</tr>"
                        + "</div>"
//        "<li><a  class=\"link\" href='RatePlan.jsp'><input  class=\"click\" type=\"submit\" value='Adding rate plan'/></a></li><br>\n" +
//        "<li><a  class=\"link\" href='AddCustomer.jsp'> <input class=\"click\" type=\"submit\" value='Add customer'/></a></li><br>\n" +
//        "<li><a  class=\"link\" href='BillingMenuServlet'><input  class=\"click\" type=\"submit\" value='Extract bill'/></a></li><br>\n"
        );
  
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
