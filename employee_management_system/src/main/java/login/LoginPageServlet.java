package login;

import java.io.IOException;
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

public class LoginPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String check = "SELECT email,idDepartment FROM employee WHERE email = ? AND password = ?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
            ps = con.prepareStatement(check);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                int departmentId = rs.getInt("idDepartment");
                HttpSession session = req.getSession();
                session.setAttribute("email", email);
                if (departmentId == 4) {
                    // Admin-->HR
                    RequestDispatcher rd = req.getRequestDispatcher("Adminprofile");
                    rd.forward(req, resp);
                } else {
                	RequestDispatcher rd = req.getRequestDispatcher("profile");
                    rd.forward(req, resp);
                }
            } else {
               RequestDispatcher rd = req.getRequestDispatcher("invalid.html");
                rd.include(req, resp);
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
