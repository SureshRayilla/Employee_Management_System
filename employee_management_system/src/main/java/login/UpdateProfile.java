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

public class UpdateProfile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("email");

		String fullName = req.getParameter("fullName");
		String phno = req.getParameter("phno");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		// change 2 int department = Integer.parseInt(req.getParameter("department"));
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
			//chnage 3
			String query = "UPDATE employee SET fullName=?, phno=?, state=?, zip=? WHERE email=?";
			ps = con.prepareStatement(query);
			ps.setString(1, fullName);
			ps.setString(2, phno);
//	            ps.setString(3, address);
			ps.setString(3, state);
			ps.setString(4, zip);
//	chnage 4&down5		ps.setInt(5, department);
			ps.setString(5, email);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				RequestDispatcher rd = req.getRequestDispatcher("profile");
				rd.forward(req, resp);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("profile");
				rd.forward(req, resp);

			}
		} catch (ClassNotFoundException | SQLException e) {
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
