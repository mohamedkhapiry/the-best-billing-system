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
    Long msisdn;
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
        request.getRequestDispatcher("header.html").include(request, response);
        conn = (Connection) request.getServletContext().getAttribute("conn");
        if (request.getParameter("msisdn") == null) {
            pt.println("<style>"
                    + "input[type=number], input[type=submit],input[type=integer]{\n"
                    + "    width:100%;\n"
                    + "    padding: 12px 20px;\n"
                    + "    margin: 8px 0;\n"
                    + "    display: inline-block;\n"
                    + "    border: 1px solid #ccc;\n"
                    + "    box-sizing: border-box;\n"
                    + "   \n"
                    + "}"
                    + "button {\n"
                    + "    background-color: #ff7922;\n"
                    + "    color: white;\n"
                    + "    padding: 14px 20px;\n"
                    + "    margin: 8px 0;\n"
                    + "    border: none;\n"
                    + "    cursor: pointer;\n"
                    + "    width:100%;\n"
                    + "}\n"
                    + "\n"
                    + "button:hover {\n"
                    + "    opacity: 0.8;\n"
                    + "}\n"
                    + "\n"
                    + "h2\n"
                    + "{\n"
                    + "   margin: auto; \n"
                    + "}\n"
                    + ".container {\n"
                    + "   \n"
                    + "    margin: auto;\n"
                    + "    width: 60%;\n"
                    + "    border: 3px solid #000000;\n"
                    + "    padding: 10px;\n"
                    + "}\n"
                    + "</style>"
                    + "<div align='center' class='container'>\n"
                    //                    + "insert customer number<br><br>"
                    + "       <form method='get' action='BillingMenuServlet'>\n"
                    //                    + "        <input type='number' name='msisdn' required><br><br>\n"
                    + " <input type=\"number\" placeholder=\"insert customer number\" name=\"msisdn\" required><br>"
                    + "        <button type='submit'>Enter</button>  \n"
                    + "           </form>\n"
                    + "      <a  href='MainMenu'><button  type=\"submit\">Back</button></a><br>"
                    + "        </div> \n"
                    + "");
        } else {
            msisdn = Long.parseLong(request.getParameter("msisdn"));
            
            System.out.println(msisdn);
            try {
                pst = conn.prepareStatement("select * from customer where msisdn=?");
                pst.setLong(1, msisdn);
                rs = pst.executeQuery();
                if (rs.next()) {
                    customer_id = rs.getInt(1);
                    pt.println("<style>"
                    + "input[type=number], input[type=submit],input[type=integer]{\n"
                    + "    width:100%;\n"
                    + "    padding: 12px 20px;\n"
                    + "    margin: 8px 0;\n"
                    + "    display: inline-block;\n"
                    + "    border: 1px solid #ccc;\n"
                    + "    box-sizing: border-box;\n"
                    + "   \n"
                    + "}"
                    + "button {\n"
                    + "    background-color: #ff7922;\n"
                    + "    color: white;\n"
                    + "    padding: 14px 20px;\n"
                    + "    margin: 8px 0;\n"
                    + "    border: none;\n"
                    + "    cursor: pointer;\n"
                    + "    width:100%;\n"
                    + "}\n"
                    + "\n"
                    + "button:hover {\n"
                    + "    opacity: 0.8;\n"
                    + "}\n"
                    + "\n"
                    + "h2\n"
                    + "{\n"
                    + "   margin: auto; \n"
                    + "}\n"
                    + ".container {\n"
                    + "   \n"
                    + "    margin: auto;\n"
                    + "    width: 60%;\n"
                    + "    border: 3px solid #000000;\n"
                    + "    padding: 10px;\n"
                    + "}\n"
                    + "</style>"
                    + "<div  class='container'>"
                    +"one time fee services: <br> \n<form method='get' action='BillingServet'>"
                            );
                    pst = conn.prepareStatement("select * from onetimefee");
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        pt.println("<input type='checkbox' name='checks' value='" + rs.getInt(3) + "'>" + rs.getString(2) + "<br>");
                    }
                    pt.println(
                            "<div align='center' >"
                            + "<input type='hidden' name='customer_id' value='" + customer_id + "'>"
                            + "<br><button type='submit' value='extract the bill'>extract the bill</button>"
                            + "</div></form>"
                               + "      <a  href='MainMenu'><button  type=\"submit\">Back</button></a><br>"     
                                    + "</div>"
                    );
                } else {
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
