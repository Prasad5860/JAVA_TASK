import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Fare_cal {

	public static double get_distance(String f, String t) {

		Connection con = DB.initialize();
		try {
			PreparedStatement pt = con.prepareStatement(
					"select distance from station_distances_207 where station_from=? and station_to=?");

			pt.setString(1, f);
			pt.setString(2, t);
			ResultSet rs = pt.executeQuery();

			if (rs.next()) {
				double res = rs.getInt(1);
				return res;
			} else {
				pt = con.prepareStatement(
						"select distance from station_distances_207 where station_from=? and station_to=?");
				pt.setString(1, t);
				pt.setString(2, f);
				rs = pt.executeQuery();
				if (rs.next()) {
					double res = rs.getInt(1);
					return res;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		return 0;
	}

	public static double[] cal_prices(String from, String to, String classs, String[] passengerAges) {

		double dist = get_distance(from, to);

		double dist_prc = dist / 2.0;

		int c;
		if (classs == "Two_tier AC") {
			c = 7;
		} else if (classs == "Three_tier AC") {
			c = 4;
		} else if (classs == "secondClass") {
			c = 2;
		} else {
			c = 1;
		}
		double[] prices = new double[passengerAges.length];
		for (int i = 0; i < passengerAges.length; i++) {
			if (Integer.parseInt(passengerAges[i]) < 8) {
				dist_prc = dist_prc / 2.0;
			} else if (Integer.parseInt(passengerAges[i]) > 65) {
				dist_prc = dist_prc / 3.0;
			}
			prices[i] = dist_prc * c;
		}
		return prices;
	}

}
