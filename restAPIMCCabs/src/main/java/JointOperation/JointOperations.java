package JointOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class JointOperations {

    /**
     * ✅ Get Available Vehicles for a Given Category and Date Range
     */
    public static List<Integer> getAvailableVehicles(int categoryId, Date startDate, Date endDate) {
        List<Integer> availableVehicles = new ArrayList<>();
        String query = "SELECT v.id FROM vehicles v " +
                       "WHERE v.catId = ? AND v.id NOT IN (" +
                       "    SELECT va.vehicleId FROM vehicle_availability va " +
                       "    WHERE (va.startDate <= ? AND va.endDate >= ?)" +
                       ")";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, categoryId);
            stmt.setDate(2, endDate);
            stmt.setDate(3, startDate);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                availableVehicles.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableVehicles;
    }

    /**
     * ✅ Get Available Drivers for a Given Date Range
     */
    public static List<Integer> getAvailableDrivers(Date startDate, Date endDate) {
        List<Integer> availableDrivers = new ArrayList<>();
        String query = "SELECT d.id FROM drivers d " +
                       "WHERE d.id NOT IN (" +
                       "    SELECT da.driverId FROM driver_availability da " +
                       "    WHERE (da.startDate <= ? AND da.endDate >= ?)" +
                       ")";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, endDate);
            stmt.setDate(2, startDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                availableDrivers.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDrivers;
    }

    /**
     * ✅ Create a Reservation (Assigning Available Vehicle & Driver)
     */
    public static int createReservation(int userId, int categoryId, Date startDate, Date endDate, Time startTime, String startLocation) {
        int reservationId = -1;
        List<Integer> availableVehicles = getAvailableVehicles(categoryId, startDate, endDate);
        List<Integer> availableDrivers = getAvailableDrivers(startDate, endDate);

        if (availableVehicles.isEmpty()) {
            System.out.println("❌ No available vehicles for the selected dates.");
            return -1;
        }

        int vehicleId = availableVehicles.get(0); // Pick first available vehicle
        Integer driverId = availableDrivers.isEmpty() ? null : availableDrivers.get(0); // Pick first available driver (if available)

        String insertReservation = "INSERT INTO reservations (userId, vehicleId, driverId, stDate, endDate, stTime, stLocation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertVehicleAvailability = "INSERT INTO vehicle_availability (vehicleId, startDate, endDate) VALUES (?, ?, ?)";
        String insertDriverAvailability = "INSERT INTO driver_availability (driverId, startDate, endDate) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionHelper.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            try (PreparedStatement stmt = conn.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, vehicleId);
                if (driverId != null) {
                    stmt.setInt(3, driverId);
                } else {
                    stmt.setNull(3, Types.INTEGER);
                }
                stmt.setDate(4, startDate);
                stmt.setDate(5, endDate);
                stmt.setTime(6, startTime);
                stmt.setString(7, startLocation);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    reservationId = rs.getInt(1);
                }
            }

            // Block vehicle for the selected date range
            try (PreparedStatement stmt = conn.prepareStatement(insertVehicleAvailability)) {
                stmt.setInt(1, vehicleId);
                stmt.setDate(2, startDate);
                stmt.setDate(3, endDate);
                stmt.executeUpdate();
            }

            // Block driver for the selected date range (if assigned)
            if (driverId != null) {
                try (PreparedStatement stmt = conn.prepareStatement(insertDriverAvailability)) {
                    stmt.setInt(1, driverId);
                    stmt.setDate(2, startDate);
                    stmt.setDate(3, endDate);
                    stmt.executeUpdate();
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationId;
    }
}
