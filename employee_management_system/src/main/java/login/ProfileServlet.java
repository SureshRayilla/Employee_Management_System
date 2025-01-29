package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        session.setAttribute("email", email);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
            String query = "SELECT e.fullName, e.email, e.password, e.phno, e.state, e.zip, e.country, e.empId, " +
                           "d.DepartmentName, d.DepartmentCode, d.DepartmentLocation, d.LocationCode " +
                           "FROM employee e " +
                           "JOIN department d ON e.idDepartment = d.idDepartment " +
                           "WHERE e.email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            PrintWriter out = resp.getWriter();

            if (rs.next()) {
            	out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Profile Page</title>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
                out.println("</head>");
                out.println("<body>");
                
                out.println("<div class='container my-5'>");
                out.println("<h2 class='text-center mb-4'>Profile Details</h2>");
                

                out.println("<table class='table table-bordered'>");
                out.println("<tr><th>Full Name</th><td>" + rs.getString("fullName") + "</td></tr>");
                out.println("<tr><th>Email</th><td>" + rs.getString("email") + "</td></tr>");
                out.println("<tr><th>Phone Number</th><td>" + rs.getString("phno") + "</td></tr>");
                out.println("<tr><th>Country</th><td>" + rs.getString("country") + "</td></tr>");
                out.println("<tr><th>State</th><td>" + rs.getString("state") + "</td></tr>");
                out.println("<tr><th>Zip Code</th><td>" + rs.getString("zip") + "</td></tr>");
                
                // Add empId row
                out.println("<tr><th>Employee ID</th><td>" + 
                             (rs.getString("empId") != null ? rs.getString("empId") : "N/A") + 
                             "</td></tr>");

                out.println("<tr><th>Department Name</th><td>" + rs.getString("DepartmentName") + "</td></tr>");
                out.println("<tr><th>Department Code</th><td>" + rs.getString("DepartmentCode") + "</td></tr>");
                out.println("<tr><th>Department Location</th><td>" + rs.getString("DepartmentLocation") + "</td></tr>");
                out.println("<tr><th>Location Code</th><td>" + rs.getString("LocationCode") + "</td></tr>");
                out.println("</table>");

                
                out.println("<div class='d-flex justify-content-start mt-3'>");
                out.println("<a href='editProfile' class='btn btn-warning me-3'>Edit</a>");
                out.println("<a href='deleteProfile' class='btn btn-danger me-3'>Delete</a>");
                out.println("<a href='logout' class='btn btn-secondary'>Logout</a>");
                out.println("</div>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
            	RequestDispatcher rd=req.getRequestDispatcher("login.html");
                rd.forward(req, resp);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


/*
 * package login;
 * 
 * import java.io.IOException; import java.io.PrintWriter; import
 * java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.PreparedStatement; import java.sql.ResultSet; import
 * java.sql.SQLException;
 * 
 * import javax.servlet.RequestDispatcher; import
 * javax.servlet.ServletException; import javax.servlet.http.HttpServlet; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse; import
 * javax.servlet.http.HttpSession;
 * 
 * public class ProfileServlet extends HttpServlet {
 * 
 * @Override protected void doPost(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException { HttpSession session =
 * req.getSession(); String email = (String) session.getAttribute("email");
 * session.setAttribute("email", email); Connection con = null;
 * PreparedStatement ps = null; ResultSet rs = null;
 * 
 * try { Class.forName("com.mysql.cj.jdbc.Driver"); con =
 * DriverManager.getConnection(
 * "jdbc:mysql://localhost:3306/project_login?user=root&password=root"); String
 * query =
 * "SELECT e.fullName, e.email, e.password, e.phno, e.state, e.zip, e.country, "
 * + "d.DepartmentName, d.DepartmentCode, d.DepartmentLocation, d.LocationCode "
 * + "FROM employee e " +
 * "JOIN department d ON e.idDepartment = d.idDepartment " +
 * "WHERE e.email = ?"; ps = con.prepareStatement(query); ps.setString(1,
 * email); rs = ps.executeQuery();
 * 
 * PrintWriter out = resp.getWriter();
 * 
 * if (rs.next()) { out.println("<!DOCTYPE html>");
 * out.println("<html lang='en'>"); out.println("<head>");
 * out.println("<meta charset='UTF-8'>"); out.
 * println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
 * ); out.println("<title>Profile Page</title>"); out.
 * println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>"
 * ); out.println("</head>"); out.println("<body>");
 * 
 * out.println("<div class='container my-5'>");
 * out.println("<h2 class='text-center mb-4'>Profile Details</h2>");
 * 
 * 
 * out.println("<table class='table table-bordered'>");
 * out.println("<tr><th>Full Name</th><td>" + rs.getString("fullName") +
 * "</td></tr>"); out.println("<tr><th>Email</th><td>" + rs.getString("email") +
 * "</td></tr>"); out.println("<tr><th>Phone Number</th><td>" +
 * rs.getString("phno") + "</td></tr>"); out.println("<tr><th>Country</th><td>"
 * + rs.getString("country") + "</td></tr>");
 * out.println("<tr><th>State</th><td>" + rs.getString("state") + "</td></tr>");
 * out.println("<tr><th>Zip Code</th><td>" + rs.getString("zip") +
 * "</td></tr>");
 * 
 * out.println("<tr><th>Department Name</th><td>" +
 * rs.getString("DepartmentName") + "</td></tr>");
 * out.println("<tr><th>Department Code</th><td>" +
 * rs.getString("DepartmentCode") + "</td></tr>");
 * out.println("<tr><th>Department Location</th><td>" +
 * rs.getString("DepartmentLocation") + "</td></tr>");
 * out.println("<tr><th>Location Code</th><td>" + rs.getString("LocationCode") +
 * "</td></tr>"); out.println("</table>");
 * 
 * 
 * out.println("<div class='d-flex justify-content-start mt-3'>");
 * out.println("<a href='editProfile' class='btn btn-warning me-3'>Edit</a>");
 * out.println("<a href='deleteProfile' class='btn btn-danger me-3'>Delete</a>"
 * ); out.println("<a href='logout' class='btn btn-secondary'>Logout</a>");
 * out.println("</div>");
 * 
 * out.println("</div>"); out.println("</body>"); out.println("</html>"); } else
 * { RequestDispatcher rd=req.getRequestDispatcher("login.html");
 * rd.forward(req, resp); }
 * 
 * } catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
 * finally { try { if (rs != null) rs.close(); if (ps != null) ps.close(); if
 * (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
 * } } }
 */