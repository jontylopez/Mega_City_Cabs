/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DriverAvailability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class DriverAvailabilityCRUD {
    public static int addDriverAvailability(DriverAvailability availability) {
        String query = "INSERT INTO driver_availability (driverId, startDate, endDate) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, availability.getDriverId());
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

    public static int updateDriverAvailability(DriverAvailability availability) {
        String query = "UPDATE driver_availability SET driverId=?, startDate=?, endDate=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, availability.getDriverId());
            stmt.setDate(2, availability.getStartDate());
            stmt.setDate(3, availability.getEndDate());
            stmt.setInt(4, availability.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteDriverAvailability(int id) {
        String query = "DELETE FROM driver_availability WHERE id=?";
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
