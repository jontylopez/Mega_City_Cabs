package VehicleAvailability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class VehicleAvailabilityCRUD {
    public static int addVehicleAvailability(VehicleAvailability availability) {
        String query = "INSERT INTO vehicle_availability (vehicleId, startDate, endDate) VALUES (?, ?, ?)";
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
}
