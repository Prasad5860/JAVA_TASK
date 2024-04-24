
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class log
 */
@WebServlet("/log")
public class log extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public log() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Connection con = DB.initialize();

		try {
			PreparedStatement pt = con.prepareStatement("select pass from Users_207 where username=?");
			pt.setString(1, username);

			ResultSet rs = pt.executeQuery();
			if (rs.next()) {

				String pw = rs.getString(1);
				if (pw.equals(password)) {
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					response.sendRedirect(request.getContextPath() + "/irctc.html");
				} else {
					response.sendRedirect(request.getContextPath() + "/error.html");

				}
			} else {
				response.sendRedirect(request.getContextPath() + "/error.html");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		doGet(request, response);
	}

}
