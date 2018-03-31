<%-- 
    Document   : AddCustomer
    Created on : Mar 27, 2018, 11:59:39 AM
    Author     : Rouen Antar <rouen49@gmail.com>
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
     
        <style>
body {font-family: Arial, Helvetica, sans-serif}
form {
    /*border: 3px solid #f1f1f1;*/  
    margin: auto;
    width: 80%;
    /*border: 3px solid #73AD21;*/
    padding: 10px;}

input[type=text], input[type=submit],input[type=integer]{
    width:100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
   
}

button {
    background-color: #ff7922;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width:100%;
}

button:hover {
    opacity: 0.8;
}

h2
{
   margin: auto; 
}
.container {
   
    margin: auto;
    width: 60%;
    border: 3px solid #000000;
    padding: 10px;
}

span.psw {
    float: right;
    padding-top: 16px;
}
#5{
    margin: auto;
    width: 60%;
    border: 3px solid #ff7922;
    padding: 10px;    
}


</style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
   
    
    
    <body>
<%@include file= "header.html"%>
<div id="5" class="container">
<form action="AddCustomer.jsp" >
  
    <label for="uname"><b>Name: </b></label><br>
    <input type="text" placeholder="Enter Customer Name" name="Name" required><br>

      <label for="uname"><b>Phone Number:</b></label><br>
    <input type="text" placeholder="Enter Phone Number" name="msisdn" required><br
        <label for="uname"><b>Recuring cost:</b></label><br>
    <input type="text" placeholder="Recurring Cost in LE" name="recurring" required><br>
          <label for="uname"><b>Chosse RatePlan</b></label><br><br>

    <select name='RatePlans' >
    <%
        Connection  conn = (Connection) request.getServletContext().getAttribute("conn");
        Statement stat= conn.createStatement();
        ResultSet rs= stat.executeQuery("select name from rateplan");
        
        
       while(rs.next())
       {   
           //338466
        out.println("<option value="+rs.getString(1)+ " >"+rs.getString(1)+"</option>");
       
       }
        %>
    </select>

    
      
    
     
    <button type="submit">Submit</button>
   


</form>
    <div align='center'> <a  href='MainMenu'><button style="width:80%;" type="submit">Back</button></a></div><br>
</div>
</body>
    
</html>


<%
String name=request.getParameter("Name");
String recurring=request.getParameter("recurring");
String msisdn=request.getParameter("msisdn");
String RatePlans=request.getParameter("RatePlans");
System.out.println("RatePlans"+RatePlans);
int Rateid=0;

if(name!=null&&msisdn!=null&&RatePlans!=null&&recurring!=null)
{
  
    Statement stat1= conn.createStatement();
     ResultSet rs1= stat1.executeQuery("select name,planid from rateplan");
        
       while(rs1.next())
       {
           if(RatePlans.equals(rs1.getString(1)))
           {
               Rateid=rs1.getInt(2);
           }
       }
  
       
   PreparedStatement st=conn.prepareStatement("insert into customer (name,msisdn,rate_plan_id,end_date,recurring) values(?,?,?,?,?)");
    st.setString(1, name);
    st.setLong(2,Long.parseLong(msisdn));
    st.setInt(3, );
    st.setDate(4,Date.valueOf(LocalDate.now()));
    st.setInt(5, Integer.parseInt(recurring));
     st.executeUpdate();

    
    

}
%>