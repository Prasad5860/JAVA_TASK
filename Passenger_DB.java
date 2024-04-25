import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Passenger_DB {

	public static List<Passenger_Data> push(String fromStation, String toStation, String date, String Train,
			String Gclass, String[] passengerNames, String[] passengerAges, String[] passengerGenders) {
		double[] Fare_prices = Fare_cal.cal_prices(fromStation, toStation, Gclass, passengerAges);
		Random random = new Random();
		int ticketNumber = 100000 + random.nextInt(900000);
		int no = Integer.parseInt(Train);

		Connection con = DB.initialize();
		List<Passenger_Data> ps = new ArrayList<>();
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

				Passenger_Data pd = new Passenger_Data();
				pd.setTicketNumber(ticketNumber);
				pd.setFromStation(fromStation);
				pd.setToStation(toStation);
				pd.setDate(date);
				pd.setTrainNumber(Train);
				pd.setTrainName(rs.getString("train_name"));
				pd.setTicketClass(Gclass);
				pd.setPassengerName(passengerNames[i]);
				pd.setPassengerAge(passengerAges[i]);
				pd.setPassengerGender(passengerGenders[i]);
				pd.setFarePrice(Fare_prices[i]);

				ps.add(pd);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("error occured");
				e.printStackTrace();
			}
		}

		return ps;

	}
}
