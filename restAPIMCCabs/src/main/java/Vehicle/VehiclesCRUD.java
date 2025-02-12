package Vehicle;

import DBConnection.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            return rs.getInt(1); // ✅ Return new vehicle ID
        }
    } catch (SQLException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            return -2; // ✅ Special return code for duplicate vehicleNo
        }
        e.printStackTrace();
    }
    return -1; // ✅ General error code
}



    // 🔹 FETCH ALL VEHICLES
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


   // 🔹 UPDATE VEHICLE
public static int updateVehicle(Vehicles vehicle) {
    String query = "UPDATE vehicles SET catId=?, vehicleNo=?, regExpDate=?, stat=? WHERE id=?";
    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, vehicle.getCatId());
        stmt.setString(2, vehicle.getVehicleNo());

        // ✅ Convert String to SQL Date
        stmt.setDate(3, vehicle.getRegExpDate() != null ? new java.sql.Date(vehicle.getRegExpDate().getTime()) : null);

        stmt.setString(4, vehicle.getStat());
        stmt.setInt(5, vehicle.getId());

        return stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

    // 🔹 DELETE VEHICLE
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
