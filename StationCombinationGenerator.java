import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StationCombinationGenerator {

	public static void main(String[] args) {
		String[] stations = { "Vijayawada", "Guntur", "Visakhapatnam", "Rajahmundry", "Kakinada", "Nellore", "Ongole",
				"Hyderabad", "Chennai", "Bangalore", "Tirupathi" };

		List<String> stationList = new ArrayList<>();
		Collections.addAll(stationList, stations);

		List<List<String>> combinations = generateCombinations(stationList);

		try {
			Connection con = DB.initialize();
			Random random = new Random();
			for (List<String> combination : combinations) {
				String station1 = combination.get(0);
				String station2 = combination.get(1);
				int distance = random.nextInt(500) + 100; // Random distance between 100 and 599 kilometers

				// Insert the combination and distance into the table
				String sql = "INSERT INTO station_distances_207 (station_from, station_to, distance) VALUES (?, ?, ?)";
				try (PreparedStatement statement = con.prepareStatement(sql)) {
					statement.setString(1, station1);
					statement.setString(2, station2);
					statement.setInt(3, distance);
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static List<List<String>> generateCombinations(List<String> stations) {
		List<List<String>> combinations = new ArrayList<>();
		for (int i = 0; i < stations.size(); i++) {
			for (int j = i + 1; j < stations.size(); j++) {
				List<String> combination = new ArrayList<>();
				combination.add(stations.get(i));
				combination.add(stations.get(j));
				combinations.add(combination);
			}
		}
		return combinations;
	}
}
