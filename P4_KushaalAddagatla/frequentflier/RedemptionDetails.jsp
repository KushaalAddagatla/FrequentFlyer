<%@page import = "java.sql.*"%>

<%
    String aid = request.getParameter("awardid");
    String passid = request.getParameter("pid");
    
    try{
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url,"sashraf9","irirsess");
    
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select a.a_description, a.points_required, to_char(cast(rh.redemption_date as date),'DD-MON-YY'), ex.center_name from awards a, "
    + "redemption_history rh, exchgcenters ex where a.award_id = rh.award_id and rh.center_id = ex.center_id and rh.passid = " + passid + " and a.award_id =" + aid);

    String output ="";
    while(rs.next()){
        output += rs.getObject(1)+","+rs.getObject(2)+","+rs.getObject(3)+","+rs.getObject(4)+"#";
    }
    conn.close();
    out.print(output);
    }
    catch(SQLException e){
        out.print(e);
    }    

%>
