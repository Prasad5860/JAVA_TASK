
import java.io.IOException;
import java.io.PrintWriter;
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

		List<Train_ob> ts = new ArrayList<>();

		ts = Trains_DB.get_Trains(from, to);
		JSONArray jArray = new JSONArray();
		for (int i = 0; i < ts.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("Train_no", ts.get(i).getNumber());
			json.put("Train_name", ts.get(i).getTrainName());
			jArray.put(json);
		}

		PrintWriter out = response.getWriter();
		out.println(jArray.toString());

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
