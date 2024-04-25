import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Trains_DB {

	public static List<Train_ob> get_Trains(String from, String to) {

		try {
			List<Train_ob> ls = new ArrayList<>();

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

			while (rs.next()) {
				Train_ob t = new Train_ob();
				t.setNumber(rs.getInt(1));
				t.setTrainName(rs.getString(2));
				ls.add(t);
			}

			return ls;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
