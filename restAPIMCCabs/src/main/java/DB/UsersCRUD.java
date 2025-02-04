/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import DBConnection.ConnectionHelper;

/**
 * Users CRUD Operations
 */
public class UsersCRUD {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ Add User (INSERT)
   public int addUser(Users user) {
    if (user == null) {
        System.out.println("🚨 ERROR: User object is null");
        return -1;
    }

    // 🚨 Check if required fields are missing
    if (user.getUsername() == null || user.getpWord() == null || user.getfullName() == null) {
        System.out.println("🚨 ERROR: Missing required fields!");
        return -1;
    }

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (username, pWord, uRole, fullName, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, user.getUsername());
        stmt.setString(2, encoder.encode(user.getpWord())); // Hash password
        stmt.setString(3, user.getuRole());
        stmt.setString(4, user.getfullName());
        stmt.setString(5, user.getAddress());
        stmt.setString(6, user.getPhone());
        stmt.setString(7, user.getEmail());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("✅ User Created Successfully! ID: " + generatedKeys.getInt(1));
                return generatedKeys.getInt(1);
            }
        }
    } catch (SQLException e) {
        System.out.println("🚨 ERROR: Database Insert Failed - " + e.getMessage());
        e.printStackTrace();
    }
    return -1;
}

    // ✅ Get All Users
    public List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                users.add(new Users(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pWord"),
                        rs.getString("uRole"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // ✅ Get Single User by ID
    public Users getUser(int userId) {
        Users user = null;
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Users(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pWord"),
                        rs.getString("uRole"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // ✅ Update User
    public int updateUser(Users user) {
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET username = ?, pWord = ?, uRole = ?, fullName = ?, address = ?, phone = ?, email = ? WHERE id = ?")) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, encoder.encode(user.getpWord())); // Hash password
            stmt.setString(3, user.getuRole());
            stmt.setString(4, user.getfullName());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getEmail());
            stmt.setInt(8, user.getId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Delete User
    public int deleteUser(int userId) {
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

  // ✅ Validate User (Check Email & Password)
public Users isValidUser(String email, String password) {
    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT id, pWord, uRole FROM users WHERE email = ?")) {

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
    return null; // Return null if user is invalid
}


    // ✅ Main Method to Test
    public static void main(String[] args) {
        UsersCRUD usersCRUD = new UsersCRUD();

        Users user = usersCRUD.getUser(1); // Fetch user with ID 1

        if (user != null) {
            System.out.println("✅ User Found: " + user.getUsername() + ", Email: " + user.getEmail());
        } else {
            System.out.println("❌ No user found with ID 1.");
        }
        
    }
}
