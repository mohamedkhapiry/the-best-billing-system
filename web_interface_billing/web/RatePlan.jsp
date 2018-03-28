<%-- 
    Document   : RatePlan
    Created on : Mar 27, 2018, 9:36:34 AM
    Author     : Rouen Antar <rouen49@gmail.com>
--%>

<%@page import="java.io.PrintWriter"%>
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
    border: 3px solid #ff7922;
    padding: 10px;
}

span.psw {
    float: right;
    padding-top: 16px;
}
#5{
    margin: auto;
    width: 50%;
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
<form action="RatePlan.jsp" >


  
    <label for="uname"><b>Rate Plan Name: </b></label><br>
    <input type="text" placeholder="Enter Rate Plan Name" name="Name" required><br>

      <label for="uname"><b>Description:</b></label><br>
    <input type="text" placeholder="Enter Description" name="Des" required><br>
    
      <label for="uname"><b>Recurring:</b></label><br>
    <input type="text" placeholder="Enter Recurring in LE" name="Recurring" required><br>
    
      <label for="uname"><b>SMS Free Units:</b></label><br>
    <input type="integer" placeholder="Enter SMS Free Units" name="SMSFU" required><br>
    
      <label for="uname"><b>Voice Free Units:</b></label><br>
    <input type="integer" placeholder="Enter Voice Free Units" name="VoiceFU" required><br>
    
      <label for="uname"><b>SMS Free Units (Others):</b></label><br>
    <input type="integer" placeholder="Enter SMS Free Units (Others)" name="SMSFUO" required><br>
    
    <label for="uname"><b>Voice Free Units(Others):</b></label><br>
    <input type="integer" placeholder="Enter Voice Free Units(Others)" name="VoiceFUO" required><br>
    
    
      <label for="uname"><b>SMS Unit Price:</b></label><br>
    <input type="integer" placeholder="Enter SMS Unit Price in Pt." name="SMSPrice" required><br>
    
      <label for="uname"><b>Voice Unit Price:</b></label><br>
    <input type="integer" placeholder="Enter Voice Unit Price in Pt." name="VoicePrice" required><br>
    
     <button type="submit">Submit</button>
   
 


</form>
 
        <div align='center'> <a  href='MainMenu'><button style="width:80%;" type="submit">Back</button></a></div><br>

</div>
</body>
    
</html>


<%
String name=request.getParameter("Name");
System.out.println("name"+name);
String Des=request.getParameter("Des");
String Recurring=request.getParameter("Recurring");
String SMSFU=request.getParameter("SMSFU");
String VoiceFU=request.getParameter("VoiceFU");
String SMSFUO=request.getParameter("SMSFUO");
String VoiceFUO=request.getParameter("VoiceFUO");
String SMSPrice=request.getParameter("SMSPrice");
String VoicePrice=request.getParameter("VoicePrice");

if(name!=null&&Des!=null&&Recurring!=null&&SMSFU!=null&&VoiceFU!=null&&SMSFUO!=null&&VoiceFUO!=null&&SMSPrice!=null&&VoicePrice!=null)
{
   Connection  conn = (Connection) request.getServletContext().getAttribute("conn");
    
   PreparedStatement st=conn.prepareStatement("insert into rateplan (name,description,recurring,sms_fu,voice_fu,sms_fu_other,voice_fu_other,sms_unit_price,voice_unit_price) values(?,?,?,?,?,?,?,?,?)");
    st.setString(1, name);
    st.setString(2, Des);
    st.setDouble(3, Double.parseDouble(Recurring));
    st.setInt(4, Integer.parseInt(SMSFU));
    st.setInt(5, Integer.parseInt(VoiceFU));
    st.setInt(6, Integer.parseInt(SMSFUO));
    st.setInt(7, Integer.parseInt(VoiceFUO));
    st.setInt(8, Integer.parseInt(SMSPrice));
    st.setInt(9, Integer.parseInt(VoicePrice));
    st.executeUpdate();
    
    

}
%>





