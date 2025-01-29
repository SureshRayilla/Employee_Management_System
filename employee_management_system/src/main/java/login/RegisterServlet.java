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

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        String phno = req.getParameter("phno");
        String email = req.getParameter("email");
//        String address = req.getParameter("address");
        String country = req.getParameter("country");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
//        int department = Integer.parseInt(req.getParameter("department"));
       // String defaultIdDepartment = null;
        
        Connection con = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO employee (fullName, password, phno, email, country, state, zip,idDepartment) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_login?user=root&password=root");
            
            ps = con.prepareStatement(query);
            ps.setString(1, fullname);
            ps.setString(2, password);
            ps.setString(3, phno);
            ps.setString(4, email);
//            ps.setString(5, address);
            ps.setString(5, country);
            ps.setString(6, state);
            ps.setString(7, zip);
           
            ps.setNull(8, java.sql.Types.INTEGER);

           
            
            int rowsInserted = ps.executeUpdate();
            
            if (rowsInserted > 0) {
            	RequestDispatcher rd=req.getRequestDispatcher("login.html");
            	rd.forward(req, resp);
            	}
           // 	else {
//            	RequestDispatcher rd=req.getRequestDispatcher("register.html");
//            	rd.include(req, resp);
//            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
