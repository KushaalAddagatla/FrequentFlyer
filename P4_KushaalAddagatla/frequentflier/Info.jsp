<%@page import = "java.sql.*"%>

<%
    String passid = request.getParameter("pid");
    
    try{   
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url,"sashraf9","irirsess");    

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("select p.pname, pa.total_points  from Passengers p, Point_Accounts pa Where p.passid = pa.passid and p.passid= " + passid);

    String output ="";
    while(rs.next()){
    output += rs.getObject(1)+","+rs.getObject(2)+ "#";
    }
    conn.close();
    out.print(output);
    }
    catch(SQLException e){
        out.print(e);
    }   

%>
