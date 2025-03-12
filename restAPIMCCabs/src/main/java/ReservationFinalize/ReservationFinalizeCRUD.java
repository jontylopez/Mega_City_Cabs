/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReservationFinalize;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;
/**
 *
 * @author Janith
 */
public class ReservationFinalizeCRUD {
     // 🔹 Add Reservation Finalization Record
    public static int addReservationFinalize(ReservationFinalize finalize) {
        String query = "INSERT INTO reservation_finalize (resId, extraKm, extraHr, price, stat) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, finalize.getResId());
            stmt.setDouble(2, finalize.getExtraKm());
            stmt.setDouble(3, finalize.getExtraHr());
            stmt.setDouble(4, finalize.getPrice());
            stmt.setString(5, finalize.getStat());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // ✅ Return generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ❌ Failure
    }

    // 🔹 Get Reservation Finalization by ID
    public static ReservationFinalize getReservationFinalizeById(int id) {
        String query = "SELECT * FROM reservation_finalize WHERE id = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ReservationFinalize(
                        rs.getInt("id"),
                        rs.getInt("resId"),
                        rs.getDouble("extraKm"),
                        rs.getDouble("extraHr"),
                        rs.getDouble("price"),
                        rs.getString("stat")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ❌ Not found
    }

    // 🔹 Get All Finalized Reservations
    public static List<ReservationFinalize> getAllReservationFinalizations() {
        List<ReservationFinalize> finalizeList = new ArrayList<>();
        String query = "SELECT * FROM reservation_finalize";

        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                finalizeList.add(new ReservationFinalize(
                        rs.getInt("id"),
                        rs.getInt("resId"),
                        rs.getDouble("extraKm"),
                        rs.getDouble("extraHr"),
                        rs.getDouble("price"),
                        rs.getString("stat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return finalizeList;
    }

    // 🔹 Update Reservation Finalization
    public static int updateReservationFinalize(ReservationFinalize finalize) {
        String query = "UPDATE reservation_finalize SET resId = ?, extraKm = ?, extraHr = ?, price = ?, stat = ? WHERE id = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, finalize.getResId());
            stmt.setDouble(2, finalize.getExtraKm());
            stmt.setDouble(3, finalize.getExtraHr());
            stmt.setDouble(4, finalize.getPrice());
            stmt.setString(5, finalize.getStat());
            stmt.setInt(6, finalize.getId());

            return stmt.executeUpdate(); // ✅ Returns number of rows updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ❌ Failure
    }

    // 🔹 Delete Reservation Finalization
    public static int deleteReservationFinalize(int id) {
        String query = "DELETE FROM reservation_finalize WHERE id = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate(); // ✅ Returns the number of rows deleted

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ❌ Failure
    }

    // 🔹 Update Only Status (Pending → Paid)
    public static int updateReservationStatus(int id, String newStatus) {
        String query = "UPDATE reservation_finalize SET stat = ? WHERE id = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, id);

            return stmt.executeUpdate(); // ✅ Returns number of rows updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // ❌ Failure
    }
}
