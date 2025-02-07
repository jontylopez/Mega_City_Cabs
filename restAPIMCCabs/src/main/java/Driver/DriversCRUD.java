/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class DriversCRUD {
    public static int addDriver(Drivers driver) {
        String query = "INSERT INTO drivers (dName, dAddress, dTel, dLNum, dLExpDate, stat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, driver.getDName());
            stmt.setString(2, driver.getDAddress());
            stmt.setString(3, driver.getDTel());
            stmt.setString(4, driver.getDLNum());
            stmt.setDate(5, driver.getDLExpDate());
            stmt.setString(6, driver.getStat());
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

    public static List<Drivers> getDrivers() {
        List<Drivers> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                drivers.add(new Drivers(
                        rs.getInt("id"),
                        rs.getString("dName"),
                        rs.getString("dAddress"),
                        rs.getString("dTel"),
                        rs.getString("dLNum"),
                        rs.getDate("dLExpDate"),
                        rs.getString("stat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public static int updateDriver(Drivers driver) {
        String query = "UPDATE drivers SET dName=?, dAddress=?, dTel=?, dLNum=?, dLExpDate=?, stat=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, driver.getDName());
            stmt.setString(2, driver.getDAddress());
            stmt.setString(3, driver.getDTel());
            stmt.setString(4, driver.getDLNum());
            stmt.setDate(5, driver.getDLExpDate());
            stmt.setString(6, driver.getStat());
            stmt.setInt(7, driver.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteDriver(int id) {
        String query = "DELETE FROM drivers WHERE id=?";
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
