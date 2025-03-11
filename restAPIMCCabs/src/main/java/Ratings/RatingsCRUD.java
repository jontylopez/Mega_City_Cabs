package Ratings;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class RatingsCRUD {

    public static int addRating(Ratings rating) {
        String query = "INSERT INTO ratings (userId, reservationId, tripRating, vehicleRating, driverRating, comment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rating.getUserId());
            stmt.setInt(2, rating.getReservationId());
            stmt.setBigDecimal(3, rating.getTripRating());
            stmt.setBigDecimal(4, rating.getVehicleRating());
            stmt.setBigDecimal(5, rating.getDriverRating());
            stmt.setString(6, rating.getComment());
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

    public static List<Ratings> getRatings() {
        List<Ratings> ratings = new ArrayList<>();
        String query = "SELECT * FROM ratings";  // 🔹 Removed `overalRating` from SQL, using Java instead
        try (Connection conn = ConnectionHelper.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ratings rating = new Ratings(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("reservationId"),
                        rs.getBigDecimal("tripRating"),
                        rs.getBigDecimal("vehicleRating"),
                        rs.getBigDecimal("driverRating"),
                        rs.getString("comment")
                );
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    public static int updateRating(Ratings rating) {
        String query = "UPDATE ratings SET tripRating=?, vehicleRating=?, driverRating=?, comment=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBigDecimal(1, rating.getTripRating());
            stmt.setBigDecimal(2, rating.getVehicleRating());
            stmt.setBigDecimal(3, rating.getDriverRating());
            stmt.setString(4, rating.getComment());
            stmt.setInt(5, rating.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteRating(int id) {
        String query = "DELETE FROM ratings WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 🔹 Get Rating by ID
    public static Ratings getRatingById(int id) {
        String query = "SELECT * FROM ratings WHERE id = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ratings(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("reservationId"),
                        rs.getBigDecimal("tripRating"),
                        rs.getBigDecimal("vehicleRating"),
                        rs.getBigDecimal("driverRating"),
                        rs.getString("comment")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
