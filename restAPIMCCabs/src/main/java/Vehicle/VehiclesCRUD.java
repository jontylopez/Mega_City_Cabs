/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class VehiclesCRUD {
    public static int addVehicle(Vehicles vehicle) {
        String query = "INSERT INTO vehicles (vehiName, typeOf, model, registration_no, price_per_day, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehicle.getVehiName());
            stmt.setString(2, vehicle.getTypeOf());
            stmt.setString(3, vehicle.getModel());
            stmt.setString(4, vehicle.getRegistrationNo());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setString(6, vehicle.getImageUrl());
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

    public static List<Vehicles> getVehicles() {
        List<Vehicles> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vehicles.add(new Vehicles(
                        rs.getInt("id"),
                        rs.getString("vehiName"),
                        rs.getString("typeOf"),
                        rs.getString("model"),
                        rs.getString("registration_no"),
                        rs.getDouble("price_per_day"),
                        rs.getString("image_url"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static int updateVehicle(Vehicles vehicle) {
        String query = "UPDATE vehicles SET vehiName=?, typeOf=?, model=?, registration_no=?, price_per_day=?, image_url=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVehiName());
            stmt.setString(2, vehicle.getTypeOf());
            stmt.setString(3, vehicle.getModel());
            stmt.setString(4, vehicle.getRegistrationNo());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setString(6, vehicle.getImageUrl());
            stmt.setInt(7, vehicle.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteVehicle(int id) {
        String query = "DELETE FROM vehicles WHERE id=?";
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

