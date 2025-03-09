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

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("✅ Driver added successfully. ID: " + rs.getInt(1));
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("🚨 SQL Error in addDriver: " + e.getMessage());
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
            System.out.println("✅ Drivers fetched successfully. Count: " + drivers.size());
        } catch (SQLException e) {
            System.err.println("🚨 SQL Error in getDrivers: " + e.getMessage());
            e.printStackTrace();
        }
        return drivers;
    }
    
    // 🔹 Retrieve Driver by ID
public static Drivers getDriverById(int id) {
    String query = "SELECT * FROM drivers WHERE id=?";
    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Drivers(
                    rs.getInt("id"),
                    rs.getString("dName"),
                    rs.getString("dAddress"),
                    rs.getString("dTel"),
                    rs.getString("dLNum"),
                    rs.getDate("dLExpDate"),
                    rs.getString("stat")
            );
        }
    } catch (SQLException e) {
        System.err.println("🚨 SQL Error in getDriverById: " + e.getMessage());
        e.printStackTrace();
    }
    return null; // Return null if no driver is found
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

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Driver updated successfully. ID: " + driver.getId());
            }
            return rowsUpdated;
        } catch (SQLException e) {
            System.err.println("🚨 SQL Error in updateDriver: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteDriver(int id) {
        String query = "DELETE FROM drivers WHERE id=?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Driver deleted successfully. ID: " + id);
            }
            return rowsDeleted;
        } catch (SQLException e) {
            System.err.println("🚨 SQL Error in deleteDriver: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}
