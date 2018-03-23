/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billing_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mohamed
 */
public class BillingServet extends HttpServlet {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;   
    int customer_id;
    double voice_cost;
    BigDecimal voice_cost_big;
    int voice_units_inside;
    int voice_units_inside_total;
    int voice_units_outside;
    double sms_cost;
    BigDecimal sms_cost_big;
    int sms_units_inside;
    int sms_units_inside_total;
    int sms_units_outside;
    double data_cost;
    BigDecimal data_cost_big;
    int data_amount=0;
    double onetimefee_cost;
    int max_voice_fu;
    int max_sms_fu;
    int recuring_cost;
    String profile_name;
    String user_name;
    BigDecimal total=new BigDecimal(0);
    
    
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
        customer_id=Integer.parseInt(request.getParameter("customer_id"));
        
        voice_cost=0;
        voice_units_inside=0;
        voice_units_inside_total=0;
        voice_units_outside=0;
        sms_cost=0;
        sms_units_inside=0;
        sms_units_inside_total=0;
        sms_units_outside=0;
        data_cost=0;
        try {
            //reading the rated CDRs
            pst=conn.prepareStatement("select * from rated_cdr where cid=? and billed=false");
            pst.setInt(1,customer_id);
            rs=pst.executeQuery();
            while(rs.next())
            {
                if(rs.getInt("serviceid")==3)
                {
                    data_cost=data_cost+rs.getDouble("external_charge");
                    data_amount=data_amount+rs.getInt("used_units");
                }else if(rs.getInt("serviceid")==1)
                {
                    Long det_no=rs.getLong("destination_no");
                    String det_no_st=det_no.toString().substring(1,4);
//                    System.out.println(det_no_st);
                    if(det_no_st.equals("012"))
                    {
                        voice_units_inside_total=voice_units_inside_total+rs.getInt("used_units");
                    }else{
                        voice_units_outside=voice_units_outside+rs.getInt("used_units");
                    }
//                    System.out.println(voice_units_outside);
//                    System.out.println(voice_units_inside);
                }else if(rs.getInt("serviceid")==2)
                {
                    Long det_no=rs.getLong("destination_no");
                    String det_no_st=det_no.toString().substring(1,4);
//                    System.out.println(det_no_st);
                    if(det_no_st.equals("012"))
                    {
                        sms_units_inside_total=sms_units_inside_total+rs.getInt("used_units");
                    }else{
                        sms_units_outside=sms_units_outside+rs.getInt("used_units");
                    }
                }
            }
            pst=conn.prepareStatement("select * from customer where customerid=?");
            pst.setInt(1, customer_id);
            rs=pst.executeQuery();
            if(rs.next())
            {
                user_name=rs.getString("name");
               if(voice_units_inside_total > rs.getInt("voice_fu"))
               {
                   voice_units_inside=voice_units_inside_total-rs.getInt("voice_fu");
               }
               if(sms_units_inside_total > rs.getInt("sms_fu"))
               {
                   sms_units_inside=sms_units_inside_total-rs.getInt("sms_fu");
               }
               pst=conn.prepareStatement("select * from rateplan where planid=?");
               pst.setInt(1,rs.getInt("rate_plan_id"));
               rs=pst.executeQuery();
               if(rs.next())
               {
                 
                 profile_name=rs.getString("name");
                 voice_cost=voice_cost+(voice_units_inside+voice_units_outside)*rs.getInt("voice_unit_price");
                 voice_cost_big=new BigDecimal(voice_cost);
                 voice_cost_big=voice_cost_big.divide(new BigDecimal(100.0),2,2);//.divideToIntegralValue(new BigDecimal(100.0));
                 sms_cost=sms_cost+(sms_units_inside+sms_units_outside)*rs.getInt("sms_unit_price");
                 sms_cost_big=new BigDecimal(sms_cost);
                 sms_cost_big=sms_cost_big.divide(new BigDecimal(100.0),2,2);
                 max_voice_fu=rs.getInt("voice_fu");
                 max_sms_fu=rs.getInt("sms_fu");
                 recuring_cost=rs.getInt("recurring");
               } 
               
            }
            data_cost_big=new BigDecimal(data_cost);
            data_cost_big=data_cost_big.divide(new BigDecimal(100.0),2,2);
            //reset the account
//            pst=conn.prepareStatement("update table customer set voice_fu=?,sms_fu=? where customerid=?");
//            pst.setInt(1, max_voice_fu);
//            pst.setInt(2, max_sms_fu);
//            pst.setInt(3, customer_id);
//            pst.executeUpdate();

            //set rated cdr as billed
            pst=conn.prepareStatement("update rated_cdr set billed=true where cid=? ");
            pst.setInt(1,customer_id);
            pst.executeUpdate();
            } catch (SQLException ex) {
               Logger.getLogger(BillingServet.class.getName()).log(Level.SEVERE, null, ex);
            }
        //calculate the cost of one time fee
        onetimefee_cost=0;
        String[] onetimefee_arr=request.getParameterValues("checks");
        if(onetimefee_arr != null)
        {
        for(int i=0;i<onetimefee_arr.length;i++)
        {
            onetimefee_cost=onetimefee_cost+Integer.parseInt(onetimefee_arr[i]);
        }
        }
        
        //writing in file
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text File", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        String filePath = "";
         if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile().getPath();
//            System.out.println("You chose to open this file: "
//                    + filePath);
        }
         BufferedWriter bw=new BufferedWriter(new FileWriter(filePath));
//         String line;
         int total_used_voice_units=voice_units_inside_total+voice_units_outside;
         int total_used_sms_units=sms_units_inside_total+sms_units_outside;
         bw.write("ORANG EGYPT");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("customer name= "+user_name+"\n");
         bw.newLine();
         bw.write("profile name= "+profile_name+"\n");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("total voice units = "+total_used_voice_units+"\n");
         bw.newLine();
         bw.write("voice units for the same mobile network = "+voice_units_inside_total+"\n");
         bw.newLine();
         bw.write("voice units for other mobile networks = "+voice_units_outside+"\n\n");
         bw.newLine();
         bw.write("____________________________________________________________________");
         bw.newLine();
         bw.write("total voice cost = "+voice_cost_big+"\n");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("total sms units = "+total_used_sms_units+"\n");
         bw.newLine();
         bw.write("sms units for the same mobile network = "+sms_units_inside_total+"\n");
         bw.newLine();
         bw.write("sms units for other mobile networks = "+sms_units_outside+"\n\n");
          bw.newLine();
         bw.write("____________________________________________________________________");
         bw.newLine();
         bw.write("total sms cost = "+sms_cost_big+"\n");
         bw.newLine();
         bw.write(" ");
         bw.write(" ");
         bw.newLine();
         bw.write("total data units = "+data_amount+"\n");
          bw.newLine();
         bw.write("____________________________________________________________________");
         bw.newLine();
         bw.write("total data cost = "+data_cost_big+"\n");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("total one time fee cost = "+onetimefee_cost+"\n");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("recuring cost = "+recuring_cost+"\n");
         bw.newLine();
         bw.write("____________________________________________________________________");
         bw.newLine();
         total=total.add(voice_cost_big).add(sms_cost_big).add(data_cost_big).add(new BigDecimal(recuring_cost)).add(new BigDecimal(onetimefee_cost)).multiply(new BigDecimal(1.1));
         
         bw.write("total = "+total.divide(new BigDecimal(1.0),2,2)+"\n");
         bw.newLine();
         bw.write(" ");
         bw.newLine();
         bw.write("*the total price is calculated after adding 10% taxes.");

         
         bw.close();
        
        
        
        
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
