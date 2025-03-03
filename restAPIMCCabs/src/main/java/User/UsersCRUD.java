/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsersCRUD {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static int addUser(Users user) {
        String query = "INSERT INTO users (username, pWord, uRole, fullName, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, encoder.encode(user.getpWord()));
            stmt.setString(3, user.getuRole());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getEmail());         
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

    public static List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new Users(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pWord"),
                        rs.getString("uRole"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static int updateUser(Users user) {
        String query = "UPDATE users SET username=?, pWord=?, uRole=?, fullName=?, address=?, phone=?, email=? WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, encoder.encode(user.getpWord()));
            stmt.setString(3, user.getuRole());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getEmail());

            stmt.setInt(9, user.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteUser(int id) {
        String query = "DELETE FROM users WHERE id=?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Users isValidUser(String email, String password) {
        String query = "SELECT id, pWord, uRole FROM users WHERE email = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("pWord");
                if (encoder.matches(password, storedHashedPassword)) {
                    return new Users(rs.getInt("id"), "", "", rs.getString("uRole"), "", "", "", email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
