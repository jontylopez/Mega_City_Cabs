package DriverAvailability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class DriverAvailabilityCRUD {
    public static int addDriverAvailability(DriverAvailability availability) {
        String query = "INSERT INTO driver_availability (driverId, stDate, endDate) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, availability.getDriverId());
            stmt.setDate(2, new java.sql.Date(availability.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(availability.getEndDate().getTime()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<DriverAvailability> getDriverAvailability() {
        List<DriverAvailability> availabilities = new ArrayList<>();
        String query = "SELECT * FROM driver_availability";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                availabilities.add(new DriverAvailability(
                        rs.getInt("id"),
                        rs.getInt("driverId"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availabilities;
    }
    
// 🔹 Delete Driver Availability using driverId and stDate
public static int deleteDriverAvailability(int driverId, Date startDate) {
    String query = "DELETE FROM driver_availability WHERE driverId = ? AND stDate = ?";

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, driverId);
        stmt.setDate(2, new java.sql.Date(startDate.getTime()));

        return stmt.executeUpdate(); // ✅ Returns the number of rows deleted

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // ❌ Failure
}

}
