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

public class EditByAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");

            // Query employee data
            String query = "SELECT idemployee, fullName, phno, state, zip, idDepartment, empId FROM employee WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            PrintWriter out = resp.getWriter();

            if (rs.next()) {
                String idemployee = rs.getString("idemployee");
                String fullName = rs.getString("fullName");
                String empId = rs.getString("empId");

                if (empId == null || empId.trim().isEmpty()) {
                    String firstThreeLetters = fullName.length() >= 3 ? fullName.substring(0, 3).toUpperCase() : fullName.toUpperCase();
                    empId = idemployee + firstThreeLetters; 
                    String updateQuery = "UPDATE employee SET empId = ? WHERE email = ?";
                    PreparedStatement updatePs = con.prepareStatement(updateQuery);
                    updatePs.setString(1, empId);
                    updatePs.setString(2, email);
                    updatePs.executeUpdate();
                    updatePs.close();
                }

                // Render edit form
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Edit Employee Profile</title>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container mt-5'>");
                out.println("<h2>Edit Employee Profile</h2>");
                out.println("<form action='updateByAdmin' method='post'>");

                // Pass necessary fields
                out.println("<input type='hidden' name='email' value='" + email + "'>");
                out.println("<input type='hidden' name='empId' value='" + empId + "'>");

                out.println("<div class='mb-3'>");
                out.println("<label for='fullName'>Full Name</label>");
                out.println("<input type='text' class='form-control' id='fullName' name='fullName' value='" + rs.getString("fullName") + "' required>");
                out.println("</div>");
                out.println("<div class='mb-3'>");
                out.println("<label for='phno'>Phone Number</label>");
                out.println("<input type='text' class='form-control' id='phno' name='phno' value='" + rs.getString("phno") + "' required>");
                out.println("</div>");
                out.println("<div class='mb-3'>");
                out.println("<label for='state'>State</label>");
                out.println("<input type='text' class='form-control' id='state' name='state' value='" + rs.getString("state") + "' required>");
                out.println("</div>");
                out.println("<div class='mb-3'>");
                out.println("<label for='zip'>Zip Code</label>");
                out.println("<input type='text' class='form-control' id='zip' name='zip' value='" + rs.getString("zip") + "' required>");
                out.println("</div>");
                out.println("<div class='mb-3'>");
                out.println("<label for='department'>Department</label>");
                out.println("<select class='form-control' id='department' name='department' required>");
                out.println("<option value='1' " + (rs.getInt("idDepartment") == 1 ? "selected" : "") + ">Information Technology</option>");
                out.println("<option value='2' " + (rs.getInt("idDepartment") == 2 ? "selected" : "") + ">Finance</option>");
                out.println("<option value='3' " + (rs.getInt("idDepartment") == 3 ? "selected" : "") + ">Research and Development</option>");
                out.println("<option value='4' " + (rs.getInt("idDepartment") == 4 ? "selected" : "") + ">Human Resources</option>");
                out.println("</select>");
                out.println("</div>");

                out.println("<div class='mb-3'>");
                out.println("<label>Employee ID:</label>");
                out.println("<input type='text' class='form-control' value='" + empId + "' disabled>");
                out.println("</div>");

                out.println("<button type='submit' class='btn btn-primary'>Save Changes</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("Adminprofile");
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


//package login;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class EditByAdmin extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email");
//        
//
//        Connection con = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
//
//            String query = "SELECT fullName, phno, state, zip, idDepartment FROM employee WHERE email = ?";
//            ps = con.prepareStatement(query);
//            ps.setString(1, email);
//            rs = ps.executeQuery();
//
//            PrintWriter out = resp.getWriter();
//            
//
//            if (rs.next()) {
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Edit Employee Profile</title>");
//                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<div class='container mt-5'>");
//                out.println("<h2>Edit Employee Profile</h2>");
//                out.println("<form action='updateByAdmin' method='post'>");
//                out.println("<input type='hidden' name='email' value='" + email + "'>");
//                out.println("<div class='mb-3'>");
//                out.println("<label for='fullName'>Full Name</label>");
//                out.println("<input type='text' class='form-control' id='fullName' name='fullName' value='" + rs.getString("fullName") + "' required>");
//                out.println("</div>");
//                out.println("<div class='mb-3'>");
//                out.println("<label for='phno'>Phone Number</label>");
//                out.println("<input type='text' class='form-control' id='phno' name='phno' value='" + rs.getString("phno") + "' required>");
//                out.println("</div>");
//                out.println("<div class='mb-3'>");
//                out.println("<label for='state'>State</label>");
//                out.println("<input type='text' class='form-control' id='state' name='state' value='" + rs.getString("state") + "' required>");
//                out.println("</div>");
//                out.println("<div class='mb-3'>");
//                out.println("<label for='zip'>Zip Code</label>");
//                out.println("<input type='text' class='form-control' id='zip' name='zip' value='" + rs.getString("zip") + "' required>");
//                out.println("</div>");
//                out.println("<div class='mb-3'>");
//                out.println("<label for='department'>Department</label>");
//                out.println("<select class='form-control' id='department' name='department' required>");
//
//                out.println("<option value='1' " + (rs.getInt("idDepartment") == 1 ? "selected" : "") + ">Information Technology</option>");
//                out.println("<option value='2' " + (rs.getInt("idDepartment") == 2 ? "selected" : "") + ">Finance</option>");
//                out.println("<option value='3' " + (rs.getInt("idDepartment") == 3 ? "selected" : "") + ">Research and Development</option>");
//                out.println("<option value='4' " + (rs.getInt("idDepartment") == 4 ? "selected" : "") + ">Human Resources</option>");
//                out.println("</select>");
//
//                out.println("</div>");
//                out.println("<button type='submit' class='btn btn-primary'>Save Changes</button>");
//                out.println("</form>");
//                out.println("</div>");
//                out.println("</body>");
//                out.println("</html>");
//            } else {
//                RequestDispatcher rd = req.getRequestDispatcher("Adminprofile");
//                rd.forward(req, resp);
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
