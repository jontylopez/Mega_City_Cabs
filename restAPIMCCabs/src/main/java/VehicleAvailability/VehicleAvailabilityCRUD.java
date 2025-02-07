/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
            stmt.setDate(2, availability.getStartDate());
            stmt.setDate(3, availability.getEndDate());
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

    public static int updateVehicleAvailability(VehicleAvailability availability) {
        String query = "UPDATE vehicle_availability SET vehicleId=?, startDate=?, endDate=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, availability.getVehicleId());
            stmt.setDate(2, availability.getStartDate());
            stmt.setDate(3, availability.getEndDate());
            stmt.setInt(4, availability.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteVehicleAvailability(int id) {
        String query = "DELETE FROM vehicle_availability WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}