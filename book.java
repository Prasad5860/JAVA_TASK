
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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

		Random random = new Random();
		int ticketNumber = 100000 + random.nextInt(900000);

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

		int no = Integer.parseInt(Train);

		Connection con = DB.initialize();

		for (int i = 0; i < passengerNames.length; i++) {

			try {
				PreparedStatement stmt = con
						.prepareStatement("insert into Passsengers_207 values(?,?,?,?,?,?,?,?,?,?,?)");
				PreparedStatement pt = con.prepareStatement("select train_name from Trains_207 where train_no=?");
				pt.setInt(1, no);
				ResultSet rs = pt.executeQuery();
				rs.next();
				stmt.setInt(1, ticketNumber);
				stmt.setString(2, fromStation);
				stmt.setString(3, toStation);
				stmt.setString(4, date);
				stmt.setString(5, Train);
				stmt.setString(6, rs.getString("train_name"));
				stmt.setString(7, Gclass);
				stmt.setString(8, passengerNames[i]);
				stmt.setString(9, passengerAges[i]);
				stmt.setString(10, passengerGenders[i]);
				stmt.setDouble(11, Fare_prices[i]);

				int j = stmt.executeUpdate();

				System.out.println("Records Insterted" + j);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("error occured");
				e.printStackTrace();
			}
		}

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Ticket Details</h1>");
		out.println("<h3>PNRNumber:" + ticketNumber + "</h3>");
		out.println("<p>From Station: " + fromStation + "</p>");
		out.println("<p>To Station: " + toStation + "</p>");
		out.println("<p>Date: " + date + "</p>");
		out.println("<p>Train_NO: " + Train + "</p>");
		out.println("<p>Class: " + Gclass + "</p>");
		out.println("<h2>Passenger Details</h2>");
		for (int i = 0; i < passengerNames.length; i++) {
			out.println("<p><b>PassengerName:</b>      " + passengerNames[i] + "  <b>Age:</b>      " + passengerAges[i]
					+ "  <b>Gender: </b>" + passengerGenders[i] + "     <b>Ticket_fare:</b>" + Fare_prices[i]);
		}

		out.println("<h4>Total Ticket Cost:   " + tot_price + "</h4>");
		out.println("</body>");
		out.println("</html>");

	}
}
