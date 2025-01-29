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

public class AdminProfile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");

        Connection con = null;
        PreparedStatement adminPs = null;
        PreparedStatement employeesPs = null;
        ResultSet adminRs = null;
        ResultSet employeesRs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");

            // Query for fetching admin details
            String adminQuery = "SELECT e.fullName, e.email, e.phno, e.state, e.zip, e.country, e.empId, " +
                                 "d.DepartmentName, d.DepartmentCode, d.DepartmentLocation, d.LocationCode " +
                                 "FROM employee e " +
                                 "LEFT JOIN department d ON e.idDepartment = d.idDepartment " +
                                 "WHERE e.email = ?";
            adminPs = con.prepareStatement(adminQuery);
            adminPs.setString(1, email);
            adminRs = adminPs.executeQuery();

            // Query for fetching employee details along with empId
            String employeesQuery = "SELECT e.fullName, e.email, e.phno, e.country, e.state, e.zip, e.empId, " +
                                    "d.DepartmentName " +
                                    "FROM employee e " +
                                    "LEFT JOIN department d ON e.idDepartment = d.idDepartment";
            employeesPs = con.prepareStatement(employeesQuery);
            employeesRs = employeesPs.executeQuery();

            PrintWriter out = resp.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Admin Profile</title>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<div class='container my-3'>");
            out.println("<div class='d-flex justify-content-end'>");
            out.println("<a href='logout' class='btn btn-danger'>Logout</a>");
            out.println("</div>");
            out.println("</div>");
            
            out.println("<div class='container my-5'>");
            
            // Display admin profile details
            if (adminRs.next()) {
                out.println("<h2 class='text-center mb-4'>Admin Details</h2>");
                out.println("<table class='table table-bordered'>");
                out.println("<tr><th>Full Name</th><td>" + adminRs.getString("fullName") + "</td></tr>");
                out.println("<tr><th>Email</th><td>" + adminRs.getString("email") + "</td></tr>");
                out.println("<tr><th>Phone Number</th><td>" + adminRs.getString("phno") + "</td></tr>");
                out.println("<tr><th>Country</th><td>" + adminRs.getString("country") + "</td></tr>");
                out.println("<tr><th>State</th><td>" + adminRs.getString("state") + "</td></tr>");
                out.println("<tr><th>Zip Code</th><td>" + adminRs.getString("zip") + "</td></tr>");
                out.println("<tr><th>Employee ID</th><td>" + (adminRs.getString("empId") != null ? adminRs.getString("empId") : "N/A") + "</td></tr>");
                out.println("<tr><th>Department Name</th><td>" + adminRs.getString("DepartmentName") + "</td></tr>");
                out.println("</table>");
            }

            out.println("<h2 class='text-center my-5'>All Employees</h2>");
            out.println("<table class='table table-striped table-hover'>");
            out.println("<thead><tr><th>Employee ID</th><th>Full Name</th><th>Email</th><th>Phone</th><th>Country</th><th>State</th><th>Zip</th><th>Department</th><th>Actions</th></tr></thead>");
            out.println("<tbody>");

            // Display employee details table
            while (employeesRs.next()) {
                out.println("<tr>");
                out.println("<td>" + (employeesRs.getString("empId") != null ? employeesRs.getString("empId") : "N/A") + "</td>");
                out.println("<td>" + employeesRs.getString("fullName") + "</td>");
                out.println("<td>" + employeesRs.getString("email") + "</td>");
                out.println("<td>" + employeesRs.getString("phno") + "</td>");
                out.println("<td>" + employeesRs.getString("country") + "</td>");
                out.println("<td>" + employeesRs.getString("state") + "</td>");
                out.println("<td>" + employeesRs.getString("zip") + "</td>");
                String departmentName = employeesRs.getString("DepartmentName");
                
                if (departmentName != null) {
                    out.println("<td>" + departmentName + "</td>");
                } else {
                    out.println("<td class='alert alert-warning bg-danger'>Not Assigned</td>");
                }

                out.println("<td>");
                out.println("<form action='admindeleteProfile' method='POST'>");
                out.println("<input type='hidden' name='email' value=" + employeesRs.getString("email") + ">");
                out.println("<a href='admineditProfile?email=" + employeesRs.getString("email") + "' class='btn btn-warning btn-sm'>Edit</a>");
                out.println("<button type='submit' class='btn btn-danger btn-sm'>Delete</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (adminRs != null) adminRs.close();
                if (employeesRs != null) employeesRs.close();
                if (adminPs != null) adminPs.close();
                if (employeesPs != null) employeesPs.close();
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
 * java.sql.SQLException; import javax.servlet.RequestDispatcher; import
 * javax.servlet.ServletException; import javax.servlet.http.HttpServlet; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse; import
 * javax.servlet.http.HttpSession;
 * 
 * public class AdminProfile extends HttpServlet {
 * 
 * @Override protected void doPost(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException { HttpSession session =
 * req.getSession(); String email = (String) session.getAttribute("email");
 * 
 * Connection con = null; PreparedStatement adminPs = null; PreparedStatement
 * employeesPs = null; ResultSet adminRs = null; ResultSet employeesRs = null;
 * 
 * try { Class.forName("com.mysql.cj.jdbc.Driver"); con =
 * DriverManager.getConnection(
 * "jdbc:mysql://localhost:3306/project_login?user=root&password=root"); String
 * adminQuery =
 * "SELECT e.fullName, e.email, e.phno, e.state, e.zip, e.country, " +
 * "d.DepartmentName, d.DepartmentCode, d.DepartmentLocation, d.LocationCode " +
 * "FROM employee e " +
 * "LEFT JOIN department d ON e.idDepartment = d.idDepartment " +
 * "WHERE e.email = ?"; adminPs = con.prepareStatement(adminQuery);
 * adminPs.setString(1, email); adminRs = adminPs.executeQuery();
 * 
 * String employeesQuery =
 * "SELECT e.fullName, e.email, e.phno, e.country, e.state, e.zip, e.idDepartment, "
 * + "d.DepartmentName " + "FROM employee e " +
 * "LEFT JOIN department d ON e.idDepartment = d.idDepartment"; employeesPs =
 * con.prepareStatement(employeesQuery); employeesRs =
 * employeesPs.executeQuery();
 * 
 * PrintWriter out = resp.getWriter();
 * 
 * out.println("<!DOCTYPE html>"); out.println("<html lang='en'>");
 * out.println("<head>"); out.println("<meta charset='UTF-8'>"); out.
 * println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
 * ); out.println("<title>Admin Profile</title>"); out.
 * println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>"
 * ); out.println("</head>"); out.println("<body>");
 * 
 * out.println("<div class='container my-3'>");
 * out.println("<div class='d-flex justify-content-end'>");
 * out.println("<a href='logout' class='btn btn-danger'>Logout</a>");
 * out.println("</div>"); out.println("</div>");
 * 
 * out.println("<div class='container my-5'>"); if (adminRs.next()) {
 * out.println("<h2 class='text-center mb-4'>Admin Details</h2>");
 * out.println("<table class='table table-bordered '>");
 * out.println("<tr><th>Full Name</th><td>" + adminRs.getString("fullName") +
 * "</td></tr>"); out.println("<tr><th>Email</th><td>" +
 * adminRs.getString("email") + "</td></tr>");
 * out.println("<tr><th>Phone Number</th><td>" + adminRs.getString("phno") +
 * "</td></tr>"); out.println("<tr><th>Country</th><td>" +
 * adminRs.getString("country") + "</td></tr>");
 * out.println("<tr><th>State</th><td>" + adminRs.getString("state") +
 * "</td></tr>"); out.println("<tr><th>Zip Code</th><td>" +
 * adminRs.getString("zip") + "</td></tr>");
 * out.println("<tr><th>Department Name</th><td>" +
 * adminRs.getString("DepartmentName") + "</td></tr>");
 * 
 * out.println("</table>"); }
 * out.println("<h2 class='text-center my-5'>All Employees</h2>");
 * out.println("<table class='table table-striped table-hover'>"); out.
 * println("<thead><tr><th>Full Name</th><th>Email</th><th>Phone</th><th>Country</th><th>State</th><th>Zip</th><th>Department</th><th>Actions</th></tr></thead>"
 * ); out.println("<tbody>"); while (employeesRs.next()) { out.println("<tr>");
 * out.println("<td>" + employeesRs.getString("fullName") + "</td>");
 * out.println("<td>" + employeesRs.getString("email") + "</td>");
 * out.println("<td>" + employeesRs.getString("phno") + "</td>");
 * out.println("<td>" + employeesRs.getString("country") + "</td>");
 * out.println("<td>" + employeesRs.getString("state") + "</td>");
 * out.println("<td>" + employeesRs.getString("zip") + "</td>"); String
 * departmentName = employeesRs.getString("DepartmentName"); if (departmentName
 * != null) { out.println("<td>" + departmentName + "</td>"); } else {
 * out.println("<td class='alert alert-warning bg-danger'>Not Assigned</td>"); }
 * 
 * out.println("<td>");
 * 
 * out.println("<form action='admindeleteProfile' method='POST'>");
 * out.println("<input type='hidden' name='email' value=" +
 * employeesRs.getString("email") + ">");
 * out.println("<a href='admineditProfile?email=" +
 * employeesRs.getString("email") +
 * "' class='btn btn-warning btn-sm'>Edit</a>"); out.
 * println("<button type='submit' class='btn btn-danger btn-sm'>Delete</button>"
 * ); out.println("</form>");
 * 
 * out.println("</td>"); out.println("</tr>"); } out.println("</tbody>");
 * out.println("</table>"); out.println("</div>"); out.println("</body>");
 * out.println("</html>"); } catch (ClassNotFoundException | SQLException e) {
 * e.printStackTrace(); } finally { try { if (adminRs != null) adminRs.close();
 * if (employeesRs != null) employeesRs.close(); if (adminPs != null)
 * adminPs.close(); if (employeesPs != null) employeesPs.close(); if (con !=
 * null) con.close(); } catch (SQLException e) { e.printStackTrace(); } } } }
 */