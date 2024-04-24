
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Stations
 */
@WebServlet("/Stations")
public class Stations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection conn = null;
	static Statement st = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Stations() {
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

		try {
			conn = (Connection) DB.initialize();
			st = conn.createStatement();

			String qry = "select station_name from stations_207";

			ResultSet rs = st.executeQuery(qry);

			JSONArray jArray = new JSONArray();

			while (rs.next()) {
				jArray.put(rs.getString(1));
			}

			JSONObject json = new JSONObject();

			json.put("Stations", jArray);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());

		} catch (Exception e) {

			System.out.println(e.getMessage());
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
