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

public class UpdateByAdmin extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String fullName = req.getParameter("fullName");
		String phno = req.getParameter("phno");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		int department = Integer.parseInt(req.getParameter("department"));
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
			String query = "UPDATE employee SET fullName=?, phno=?, state=?, zip=?, idDepartment=? WHERE email=?";
			ps = con.prepareStatement(query);
			ps.setString(1, fullName);
			ps.setString(2, phno);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setInt(5, department);
			ps.setString(6, email);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				RequestDispatcher rd = req.getRequestDispatcher("Adminprofile");
				rd.forward(req, resp);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("Adminprofile");
				rd.forward(req, resp);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
