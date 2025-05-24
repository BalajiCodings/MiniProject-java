import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/railway";
    private static final String USER = "root"; // change as needed
    private static final String PASSWORD = "Balaji@2004"; // change as needed

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertPassenger(String name, int age, char preference, String ticketType, int seatNumber) {
        String sql = "INSERT INTO passengers (name, age, preference, ticket_type, seat_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, String.valueOf(preference));
            stmt.setString(4, ticketType);
            stmt.setInt(5, seatNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}