package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteProfile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("email");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
			String query = "DELETE from employee WHERE email=?";
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				session.invalidate();
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.forward(req, resp);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("profile");
				rd.forward(req, resp);

			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}

////

//package login;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class DeleteProfile extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		HttpSession session = req.getSession();
//		String email = (String) session.getAttribute("email");
//		Connection con = null;
//		PreparedStatement ps = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
//			String query = "DELETE from employee WHERE email=?";
//			ps = con.prepareStatement(query);
//			ps.setString(1, email);
//			int rowsUpdated = ps.executeUpdate();
//			if (rowsUpdated > 0) {
//				session.invalidate();
//				RequestDispatcher rd = req.getRequestDispatcher("login.html");
//				rd.forward(req, resp);
//			} else {
//				RequestDispatcher rd = req.getRequestDispatcher("profile");
//				rd.forward(req, resp);
//
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null)
//					ps.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//}
