package javaServlets;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/authWithDB")
public class AuthLoginServletJDBC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("in init of auth with db");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingdb", "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

		super.destroy();
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// extract params
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		System.out.println(uid+" : "+pwd);

		if (uid.length() > 0 && pwd.length() > 0) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = con.prepareStatement("SELECT u_id, password FROM users WHERE u_id=? AND password=?");
				ps.setString(1, uid);
				ps.setString(2, pwd);
				rs = ps.executeQuery();

				PrintWriter out = response.getWriter();

				if (rs.next()) {
					out.println("Login in successful: " + rs.getString(1) + " : " + rs.getString(2));
				} else {
					out.println("Login Failed");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {
					if (rs != null) {
						rs.close();
					}
					if (ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

		} else {
			response.sendRedirect("/FirstJavaWebApp/login.html");
		}
	}

}
