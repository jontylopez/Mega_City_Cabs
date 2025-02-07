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
        String query = "INSERT INTO vehicles (catId, vehicleNo, regExpDate, stat) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, vehicle.getCatId());
            stmt.setString(2, vehicle.getVehicleNo());
            stmt.setDate(3, vehicle.getRegExpDate());
            stmt.setString(4, vehicle.getStat());
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
                        rs.getInt("catId"),
                        rs.getString("vehicleNo"),
                        rs.getDate("regExpDate"),
                        rs.getString("stat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static int updateVehicle(Vehicles vehicle) {
        String query = "UPDATE vehicles SET catId=?, vehicleNo=?, regExpDate=?, stat=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicle.getCatId());
            stmt.setString(2, vehicle.getVehicleNo());
            stmt.setDate(3, vehicle.getRegExpDate());
            stmt.setString(4, vehicle.getStat());
            stmt.setInt(5, vehicle.getId());
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