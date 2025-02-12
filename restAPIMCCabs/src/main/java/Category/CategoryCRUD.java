package Category;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class CategoryCRUD {

    // 🔹 Add a New Category
    public static int addCategory(Category category) {
        String query = "INSERT INTO category (catName, maxPsngr, perDayValue, maxKmPerDay, milePkg1, milePkg2, waitingPerHr, extraKm, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getCatName());
            stmt.setInt(2, category.getMaxPsngr());
            stmt.setBigDecimal(3, category.getPerDayValue());
            stmt.setInt(4, category.getMaxKmPerDay());
            stmt.setBigDecimal(5, category.getMilePkg1());
            stmt.setBigDecimal(6, category.getMilePkg2());
            stmt.setBigDecimal(7, category.getWaitingPerHr());
            stmt.setBigDecimal(8, category.getExtraKm());
            stmt.setString(9, category.getActive());

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

    // 🔹 Retrieve All Categories
    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("catName"),
                        rs.getInt("maxPsngr"),
                        rs.getBigDecimal("perDayValue"),
                        rs.getInt("maxKmPerDay"),
                        rs.getBigDecimal("milePkg1"),
                        rs.getBigDecimal("milePkg2"),
                        rs.getBigDecimal("waitingPerHr"),
                        rs.getBigDecimal("extraKm"),
                        rs.getString("active")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // 🔹 Update a Category
    public static int updateCategory(Category category) {
        String query = "UPDATE category SET catName=?, maxPsngr=?, perDayValue=?, maxKmPerDay=?, milePkg1=?, milePkg2=?, waitingPerHr=?, extraKm=?, active=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.getCatName());
            stmt.setInt(2, category.getMaxPsngr());
            stmt.setBigDecimal(3, category.getPerDayValue());
            stmt.setInt(4, category.getMaxKmPerDay());
            stmt.setBigDecimal(5, category.getMilePkg1());
            stmt.setBigDecimal(6, category.getMilePkg2());
            stmt.setBigDecimal(7, category.getWaitingPerHr());
            stmt.setBigDecimal(8, category.getExtraKm());
            stmt.setString(9, category.getActive());
            stmt.setInt(10, category.getId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔹 Delete a Category
    public static int deleteCategory(int id) {
        String query = "DELETE FROM category WHERE id=?";
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
