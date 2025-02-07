/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JoinOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DBConnection.ConnectionHelper;
/**
 *
 * @author Janith
 */
public class JointOperations {
        public List<Map<String, Object>> getAvailableVehiclesByCategory(int catId, Date givenDate) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String query = "SELECT v.id, v.vehicleNo " +
                       "FROM vehicles v " +
                       "LEFT JOIN vehicle_availability va ON v.id = va.vehicleId " +
                       "LEFT JOIN reservations r ON v.id = r.vehicleId " +
                       "WHERE v.catId = ? " +
                       "AND ( " +
                       "    (va.startDate IS NULL OR va.endDate IS NULL OR (? NOT BETWEEN va.startDate AND va.endDate)) " +
                       "    AND " +
                       "    (r.stDate IS NULL OR r.endDate IS NULL OR (? NOT BETWEEN r.stDate AND r.endDate)) " +
                       ");";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, catId);
            stmt.setDate(2, givenDate);
            stmt.setDate(3, givenDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("vehicleId", rs.getInt("id"));
                data.put("vehicleNo", rs.getString("vehicleNo"));
                resultList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
        
        public Map<String, Object> getAvailableDriver(Date givenDate) {
    Map<String, Object> driverData = new HashMap<>();
    String query = "SELECT d.id, d.dName " +
                   "FROM drivers d " +
                   "LEFT JOIN driver_availability da ON d.id = da.driverId " +
                   "LEFT JOIN reservations r ON d.id = r.driverId " +
                   "WHERE ( " +
                   "    (da.startDate IS NULL OR da.endDate IS NULL OR (? NOT BETWEEN da.startDate AND da.endDate)) " +
                   "    AND " +
                   "    (r.stDate IS NULL OR r.endDate IS NULL OR (? NOT BETWEEN r.stDate AND r.endDate)) " +
                   ") " +
                   "AND d.stat = 'Active' " +
                   "LIMIT 1;";

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setDate(1, givenDate);
        stmt.setDate(2, givenDate);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            driverData.put("driverId", rs.getInt("id"));
            driverData.put("driverName", rs.getString("dName"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return driverData;
}

}
