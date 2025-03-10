package VehicleAvailability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class VehicleAvailabilityCRUD {
    public static int addVehicleAvailability(VehicleAvailability availability) {
        String query = "INSERT INTO vehicle_availability (vehicleId, stDate, endDate) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, availability.getVehicleId());
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
    
     // ✅ Get All Vehicle Availabilities
    public static List<VehicleAvailability> getVehicleAvailability() {
        List<VehicleAvailability> availabilities = new ArrayList<>();
        String query = "SELECT * FROM vehicle_availability";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                availabilities.add(new VehicleAvailability(
                        rs.getInt("id"),
                        rs.getInt("vehicleId"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availabilities;
    }

// 🔹 Delete Vehicle Availability using vehicleId and stDate
public static int deleteVehicleAvailability(int vehicleId, Date stDate) {
    String query = "DELETE FROM vehicle_availability WHERE vehicleId = ? AND stDate = ?";

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, vehicleId);
        stmt.setDate(2, stDate);

        return stmt.executeUpdate(); // ✅ Returns number of rows deleted

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // ❌ Failure
}

}
