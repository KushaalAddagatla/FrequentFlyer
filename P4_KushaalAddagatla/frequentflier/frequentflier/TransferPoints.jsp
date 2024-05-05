<%@page import = "java.sql.*"%>

<%
   
        String spid = request.getParameter("spid");
        String dpid = request.getParameter("dpid");
        String npoints = request.getParameter("npoints");

        try{
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        Connection conn = DriverManager.getConnection(url,"sashraf9","irirsess");        
    
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =stmt.executeQuery("Select Total_Points from Point_Accounts where passid="+spid);
        String output ="";
        while(rs.next()){
            output += rs.getObject(1);
        }
        
        if(Integer.parseInt(output.toString())  >= Integer.parseInt(npoints))
        {
            stmt.executeUpdate("UPDATE POINT_ACCOUNTS SET TOTAL_POINTS = TOTAL_POINTS-"+npoints+" WHERE PASSID="+spid);
            stmt.executeUpdate("UPDATE POINT_ACCOUNTS SET TOTAL_POINTS = TOTAL_POINTS+"+npoints+" WHERE PASSID="+dpid);
            conn.commit();
            out.print("Points transfer successful");
        }
        else{
            out.print("Does not have enough Points to transfer");
        }
        conn.close();
    }
    catch(SQLException e){
        out.print(e);
    }
%>
