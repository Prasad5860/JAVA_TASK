
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

		List<Station_ob> ls = new ArrayList<>();
		ls = Station_DB.get_stations();
		JSONArray jArray = new JSONArray();
		JSONObject json = new JSONObject();
		for (int i = 0; i < ls.size(); i++) {
			jArray.put(ls.get(i).getStationName());
		}
		json.put("Stations", jArray);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
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
