package javaServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingdb", "root", "root");
		} catch (ClassNotFoundException e) {
			System.out.print("Class Not Found : ");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.print("Mysql Driver Not found");
		}
	}

	public void destroy() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("Error while closing con");
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");

		// assuming we have received all data
		// create statement -- setString -- execute Query -- store result set
		// on success redirect user to welcome page
		// else redirect to user to register page

		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO users (u_id, password, fname, mname, lname, email, contact) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {

			ps = con.prepareStatement(query);
			ps.setString(1, uid);
			ps.setString(2, pwd);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, email);
			ps.setString(7, contact);
			int updatedRows = ps.executeUpdate();

			if (updatedRows > 0) {
				System.out.println("Recieved result set");
				response.getWriter().append("Registered Successful");
			} else {
				response.getWriter().append("Registered Failed");
			}

		} catch (SQLException e) {
			System.out.println("Error while writing to DB");
			e.printStackTrace();
		}

	}

}
