/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;


import DBOperations.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 *
 * @author Janith
 */
public class UsersCRUD {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
     // Create User (INSERT)
    public static int createUserAndGetId(Users user) {
    String query = "INSERT INTO users (username, password, uRole) VALUES (?, ?, ?)";
    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());  // Store hashed password
        pstmt.setString(3, user.getuRole());

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return the generated userId
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // If insertion fails, return -1
}

    public static boolean createUser(Users user) {
        String query = "INSERT INTO users (username, password, uRole) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());  // Store hashed password
            pstmt.setString(3, user.getuRole());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Validate Login (Check Username & Password)
    public static Users validateUser(String username, String password) {
        String query = "SELECT id, password, uRole FROM users WHERE username = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (encoder.matches(password, storedHashedPassword)) {  // Check if password matches
                    Users user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setuRole(rs.getString("uRole"));
                    return user;  // Return user ID and role
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if login fails
    }

    // Read User by ID (SELECT)
    public static Users getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setuRole(rs.getString("uRole"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read All Users (SELECT)
    public static List<Users> getAllUsers() {
    List<Users> userList = new ArrayList<>();
    String query = "SELECT * FROM users";
    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        System.out.println("🔍 Running Query: " + query); // Debugging line

        while (rs.next()) {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setuRole(rs.getString("uRole"));
            userList.add(user);
        }

        System.out.println("✅ Users retrieved: " + userList.size()); // Debugging line

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return userList;
}
    // Update User (UPDATE)
    public static boolean updateUser(Users user) {
        String query = "UPDATE users SET username = ?, password = ?, uRole = ? WHERE id = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());  // Store hashed password
            pstmt.setString(3, user.getuRole());
            pstmt.setInt(4, user.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete User (DELETE)
    public static boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
