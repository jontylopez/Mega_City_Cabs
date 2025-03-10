/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Discount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;
import java.util.UUID;

/**
 *
 * @author Janith
 */
public class DiscountsCRUD {

    public static int addDiscount(Discounts discount) {
        String query = "INSERT INTO discounts (diskId, percentage, startDate, endDate, dStatus) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            String uniqueDiskId = UUID.randomUUID().toString().replace("-", "").substring(0, 10); // Generate 10-character unique ID
            stmt.setString(1, uniqueDiskId);
            stmt.setBigDecimal(2, discount.getPercentage());
            stmt.setDate(3, discount.getStartDate());
            stmt.setDate(4, discount.getEndDate());
            stmt.setString(5, discount.getDStatus());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ❌ Failure
    }

    // 🔹 Get Active Discounts
    public static List<Discounts> getActiveDiscounts() {
        List<Discounts> discounts = new ArrayList<>();
        String query = "SELECT * FROM discounts WHERE dStatus = 'Active' AND CURDATE() BETWEEN startDate AND endDate";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                discounts.add(new Discounts(
                        rs.getInt("id"),
                        rs.getString("diskId"),
                        rs.getBigDecimal("percentage"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("dStatus")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }
    public static List<Discounts> getExpiredDiscounts() {
    List<Discounts> discounts = new ArrayList<>();
    String query = "SELECT * FROM discounts WHERE dStatus = 'Inactive' OR endDate < CURDATE()";
    
    try (Connection conn = ConnectionHelper.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
         
        while (rs.next()) {
            discounts.add(new Discounts(
                rs.getInt("id"),
                rs.getString("diskId"),
                rs.getBigDecimal("percentage"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getString("dStatus")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return discounts;
}


    // 🔹 Update Existing Discount
    public static int updateDiscount(Discounts discount) {
        String query = "UPDATE discounts SET percentage=?, startDate=?, endDate=?, dStatus=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, discount.getPercentage());
            stmt.setDate(2, discount.getStartDate());
            stmt.setDate(3, discount.getEndDate());
            stmt.setString(4, discount.getDStatus());
            stmt.setInt(5, discount.getId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
public static Discounts getDiscountById(int id) {
        String query = "SELECT * FROM discounts WHERE id = ?";
        
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Discounts(
                    rs.getInt("id"),
                    rs.getString("diskId"),
                    rs.getBigDecimal("percentage"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getString("dStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // ❌ No discount found
    }
    // 🔹 Delete a Discount
    public static int deleteDiscount(int id) {
        String query = "DELETE FROM discounts WHERE id=?";
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
