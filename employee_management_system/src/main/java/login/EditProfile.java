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

public class EditProfile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("email");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
	         String query = "SELECT fullName, phno, state, zip, idDepartment FROM employee WHERE email = ?";
	            ps = con.prepareStatement(query);
	            ps.setString(1, email);
	            rs = ps.executeQuery();
	           
	           PrintWriter out = resp.getWriter();

	            if (rs.next()) {
	                out.println("<!DOCTYPE html>");
	                out.println("<html>");
	                out.println("<head>");
	                out.println("<title>Edit Profile</title>");
	                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
	                out.println("</head>");
	                out.println("<body>");
	                out.println("<div class='container mt-5'>");
	                out.println("<h2>Edit Profile</h2>");
	                out.println("<form action='updateProfile' method='post'>");
	                out.println("<div class='mb-3'>");
	                out.println("<label for='fullName'>Full Name</label>");
	                out.println("<input type='text' class='form-control' id='fullName' name='fullName' value='" + rs.getString("fullName") + "' required>");
	                out.println("</div>");
	                out.println("<div class='mb-3'>");
	                out.println("<label for='phno'>Phone Number</label>");
	                out.println("<input type='text' class='form-control' id='phno' name='phno' value='" + rs.getString("phno") + "' required>");
	                out.println("</div>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='address'>Address</label>");
//	                out.println("<input type='text' class='form-control' id='address' name='address' value='" + rs.getString("address") + "' required>");
//	                out.println("</div>");
	                out.println("<div class='mb-3'>");
	                out.println("<label for='state'>State</label>");
	                out.println("<input type='text' class='form-control' id='state' name='state' value='" + rs.getString("state") + "' required>");
	                out.println("</div>");
	                out.println("<div class='mb-3'>");
	                out.println("<label for='zip'>Zip Code</label>");
	                out.println("<input type='text' class='form-control' id='zip' name='zip' value='" + rs.getString("zip") + "' required>");
	                out.println("</div>");
	                out.println("<div class='mb-3'>");
	                //out.println("<label for='department'>Department</label>");
	                //
//	                <label for="department" class="form-label">Department</label>
//                    out.println("<select class='form-control' id='department' name='department' required>"
//                        +"<option value='1'>Information Technology</option>"
//      change1                  +"<option value='2'>Finance</option>"
//                        +"<option value='3'>Research and Development</option>"
//                        +"<option value='4'>Human Resources</option>"
//                    +"</select>");
                    //
	               // out.println("<input type='text' class='form-control' id='department' name='department' value='" + rs.getString("idDepartment") + "' required>");
	               // out.println("</div>");
	                out.println("<button type='submit' class='btn btn-primary'>Save Changes</button>");
	                out.println("</form>");
	                out.println("</div>");
	                out.println("</body>");
	                out.println("</html>");
	            } else {
	            	RequestDispatcher rd= req.getRequestDispatcher("profile");
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

///
//
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
//import javax.servlet.http.HttpSession;
//
//public class EditProfile extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession();
//		String email = (String) session.getAttribute("email");
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		 try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//	         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
//	         String query = "SELECT fullName, phno, state, zip, idDepartment FROM employee WHERE email = ?";
//	            ps = con.prepareStatement(query);
//	            ps.setString(1, email);
//	            rs = ps.executeQuery();
//	           
//	           PrintWriter out = resp.getWriter();
//
//	            if (rs.next()) {
//	                out.println("<!DOCTYPE html>");
//	                out.println("<html>");
//	                out.println("<head>");
//	                out.println("<title>Edit Profile</title>");
//	                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
//	                out.println("</head>");
//	                out.println("<body>");
//	                out.println("<div class='container mt-5'>");
//	                out.println("<h2>Edit Profile</h2>");
//	                out.println("<form action='updateProfile' method='post'>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='fullName'>Full Name</label>");
//	                out.println("<input type='text' class='form-control' id='fullName' name='fullName' value='" + rs.getString("fullName") + "' required>");
//	                out.println("</div>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='phno'>Phone Number</label>");
//	                out.println("<input type='text' class='form-control' id='phno' name='phno' value='" + rs.getString("phno") + "' required>");
//	                out.println("</div>");
////	                out.println("<div class='mb-3'>");
////	                out.println("<label for='address'>Address</label>");
////	                out.println("<input type='text' class='form-control' id='address' name='address' value='" + rs.getString("address") + "' required>");
////	                out.println("</div>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='state'>State</label>");
//	                out.println("<input type='text' class='form-control' id='state' name='state' value='" + rs.getString("state") + "' required>");
//	                out.println("</div>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='zip'>Zip Code</label>");
//	                out.println("<input type='text' class='form-control' id='zip' name='zip' value='" + rs.getString("zip") + "' required>");
//	                out.println("</div>");
//	                out.println("<div class='mb-3'>");
//	                out.println("<label for='department'>Department</label>");
//	                //
////	                <label for="department" class="form-label">Department</label>
//                    out.println("<select class='form-control' id='department' name='department' required>"
//                        +"<option value='1'>Information Technology</option>"
//                        +"<option value='2'>Finance</option>"
//                        +"<option value='3'>Research and Development</option>"
//                        +"<option value='4'>Human Resources</option>"
//                    +"</select>");
//                    //
//	               // out.println("<input type='text' class='form-control' id='department' name='department' value='" + rs.getString("idDepartment") + "' required>");
//	                out.println("</div>");
//	                out.println("<button type='submit' class='btn btn-primary'>Save Changes</button>");
//	                out.println("</form>");
//	                out.println("</div>");
//	                out.println("</body>");
//	                out.println("</html>");
//	            } else {
//	            	RequestDispatcher rd= req.getRequestDispatcher("profile");
//	            	rd.forward(req, resp);
//	            }
//	        } catch (ClassNotFoundException | SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (rs != null) rs.close();
//	                if (ps != null) ps.close();
//	                if (con != null) con.close();
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//		} 
//         
//	
//}
//
