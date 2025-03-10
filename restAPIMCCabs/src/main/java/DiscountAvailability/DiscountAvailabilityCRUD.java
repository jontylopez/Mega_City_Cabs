/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DiscountAvailability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class DiscountAvailabilityCRUD {

    // 🔹 Add Discount Usage Record
    public static int addDiscountUsage(DiscountAvailability discountUsage) {
        String query = "INSERT INTO discount_availability (userId, dissId, usedAt) VALUES (?, ?, NOW())";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, discountUsage.getUserId());
            stmt.setInt(2, discountUsage.getDiscountId());
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

    // 🔹 Get All Discount Usages
    public static List<DiscountAvailability> getDiscountUsages() {
        List<DiscountAvailability> discountList = new ArrayList<>();
        String query = "SELECT * FROM discount_availability";

        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                discountList.add(new DiscountAvailability(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("dissId"),
                        rs.getTimestamp("usedAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discountList;
    }
// 🔹 Delete Discount Availability using userId and discountId
public static int deleteDiscountAvailability(int userId, int discountId) {
    String query = "DELETE FROM discount_availability WHERE userId = ? AND dissId = ?";

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, userId);
        stmt.setInt(2, discountId);

        return stmt.executeUpdate(); // ✅ Returns the number of rows deleted

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // ❌ Failure
}

}