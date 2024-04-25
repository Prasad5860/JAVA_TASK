import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Station_DB {
	public static List<Station_ob> get_stations() {
		try {
			List<Station_ob> ls = new ArrayList<>();
			Connection conn;
			Statement st;
			conn = (Connection) DB.initialize();
			st = conn.createStatement();

			String qry = "select station_id,station_name from Stations_207";

			ResultSet rs = st.executeQuery(qry);

			while (rs.next()) {
				Station_ob s = new Station_ob();
				s.setStationId(rs.getInt(1));
				s.setStationName(rs.getString(2));
				ls.add(s);
			}
			return ls;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
