/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;

public class ReservationsCRUD {
    public static int addReservation(Reservations reservation) {
        String query = "INSERT INTO reservations (userId, vehicleId, driverId, stDate, endDate, stTime, stLocation, stat, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getVehicleId());
            stmt.setObject(3, reservation.getDriverId(), Types.INTEGER);
            stmt.setDate(4, reservation.getStDate());
            stmt.setDate(5, reservation.getEndDate());
            stmt.setTime(6, reservation.getStTime());
            stmt.setString(7, reservation.getStLocation());
            stmt.setString(8, reservation.getStat());
            stmt.setString(9, reservation.getComments());
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

    public static List<Reservations> getReservations() {
        List<Reservations> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new Reservations(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("vehicleId"),
                        rs.getObject("driverId") != null ? rs.getInt("driverId") : null,
                        rs.getDate("stDate"),
                        rs.getDate("endDate"),
                        rs.getTime("stTime"),
                        rs.getString("stLocation"),
                        rs.getString("stat"),
                        rs.getString("comments")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    
    // ✅ Fetch All Reservations for a User
public List<Reservations> getReservationsByUserId(int userId) {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE userId = ?";
        
        try (Connection conn = ConnectionHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Reservations reservation = new Reservations();
                reservation.setId(rs.getInt("id"));
                reservation.setUserId(rs.getInt("userId"));
                reservation.setVehicleId(rs.getInt("vehicleId"));
                reservation.setDriverId(rs.getInt("driverId"));
                reservation.setStDate(rs.getDate("stDate"));
                reservation.setEndDate(rs.getDate("endDate"));
                reservation.setStTime(rs.getTime("stTime"));
                reservation.setStLocation(rs.getString("stLocation"));
                reservation.setStat(rs.getString("stat"));
                reservation.setComments(rs.getString("comments"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public static int updateReservation(Reservations reservation) {
        String query = "UPDATE reservations SET userId=?, vehicleId=?, driverId=?, stDate=?, endDate=?, stTime=?, stLocation=?, stat=?, comments=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getVehicleId());
            stmt.setObject(3, reservation.getDriverId(), Types.INTEGER);
            stmt.setDate(4, reservation.getStDate());
            stmt.setDate(5, reservation.getEndDate());
            stmt.setTime(6, reservation.getStTime());
            stmt.setString(7, reservation.getStLocation());
            stmt.setString(8, reservation.getStat());
            stmt.setString(9, reservation.getComments());
            stmt.setInt(10, reservation.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE id=?";
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

