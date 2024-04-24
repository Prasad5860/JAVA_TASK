
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Train
 */
@WebServlet("/Train")
public class Train extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		response.setContentType("application/json");

		String from = request.getParameter("from");
		String to = request.getParameter("to");

		System.out.println(from + " ========>" + to);

		if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid from/to parameters");
			return;
		}

		try {
			Connection con = DB.initialize();
			PreparedStatement ps = con.prepareStatement("WITH from_station AS (\r\n"
					+ "    SELECT train_no, train_index FROM train_schedule_207 WHERE station_name = ? \r\n" + "),\r\n"
					+ "to_station AS (\r\n"
					+ "    SELECT train_no, train_index FROM train_schedule_207 WHERE station_name = ? \r\n" + ")\r\n"
					+ "SELECT DISTINCT t1.train_no, td.train_name\r\n" + "FROM from_station t1\r\n"
					+ "INNER JOIN to_station t2 ON t1.train_no = t2.train_no\r\n"
					+ "INNER JOIN trains_207 td ON td.train_no = t1.train_no\r\n"
					+ "WHERE t1.train_index < t2.train_index;");
			ps.setString(1, from);
			ps.setString(2, to);
			ResultSet rs = ps.executeQuery();
			JSONArray jArray = new JSONArray();

			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("Train_no", rs.getInt(1));
				json.put("Train_name", rs.getString(2));
				jArray.put(json);

				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));

			}

			PrintWriter out = response.getWriter();
			out.println(jArray.toString());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
