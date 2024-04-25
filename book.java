
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class book
 */
@WebServlet("/book")
public class book extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public book() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Parse JSON data
		String fromStation = request.getParameter("fromStation");
		String toStation = request.getParameter("toStation");
		String date = request.getParameter("date");
		String Train = request.getParameter("Train");
		String Gclass = request.getParameter("Gclass");
		String[] passengerNames = request.getParameterValues("passengerName");
		String[] passengerAges = request.getParameterValues("passengerAge");
		String[] passengerGenders = request.getParameterValues("passengerGender");

		double[] Fare_prices = Fare_cal.cal_prices(fromStation, toStation, Gclass, passengerAges);

		double tot_price = 0.0;

		for (int i = 0; i < Fare_prices.length; i++) {
			tot_price += Fare_prices[i];
		}

		List<Passenger_Data> l = new ArrayList<>();

		l = Passenger_DB.push(fromStation, toStation, date, Train, Gclass, passengerNames, passengerAges,
				passengerGenders);

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Ticket Details</h1>");
		out.println("<h3>PNRNumber:" + l.get(0).getTicketNumber() + "</h3>");
		out.println("<p>From Station: " + l.get(0).getFromStation() + "</p>");
		out.println("<p>To Station: " + l.get(0).getToStation() + "</p>");
		out.println("<p>Date: " + l.get(0).getDate() + "</p>");
		out.println("<p>Train_NO: " + l.get(0).getTrainNumber() + "</p>");
		out.println("<p>Train_Name: " + l.get(0).getTrainName() + "</p>");
		out.println("<p>Class: " + l.get(0).getTicketClass() + "</p>");
		out.println("<h2>Passenger Details</h2>");
		for (int i = 0; i < l.size(); i++) {
			out.println("<p><b>PassengerName:</b>      " + l.get(i).getPassengerName() + "  <b>Age:</b>      "
					+ l.get(i).getPassengerAge() + "  <b>Gender: </b>" + l.get(i).getPassengerGender()
					+ "     <b>Ticket_fare:</b>" + l.get(i).getFarePrice());
		}

		out.println("<h4>Total Ticket Cost:   " + tot_price + "</h4>");
		out.println("</body>");
		out.println("</html>");

	}
}
