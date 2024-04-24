import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection initialize() {

		String URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
		String USER = "plf_training_admin";
		String PASSWORD = "pff123";
		String DRIVER = "org.postgresql.Driver";

		try {
			Class.forName(DRIVER);
			Connection conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
