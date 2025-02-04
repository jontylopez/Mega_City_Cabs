package userDetails;

import DBOperations.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD Operations for UserDetails Table
 * @author Janith
 */
public class UserDetailsCRUD {

    // ✅ Create User Details (INSERT)
    public static boolean createUserDetails(UserDetails userDetails) {
        String query = "INSERT INTO user_details (user_id, name, address, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userDetails.getUserId());
            pstmt.setString(2, userDetails.getName());
            pstmt.setString(3, userDetails.getAddress());
            pstmt.setString(4, userDetails.getPhone());
            pstmt.setString(5, userDetails.getEmail());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Read User Details by User ID (SELECT)
    public static UserDetails getUserDetailsByUserId(int userId) {
        String query = "SELECT * FROM user_details WHERE user_id = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setId(rs.getInt("id"));
                userDetails.setUserId(rs.getInt("user_id"));
                userDetails.setName(rs.getString("name"));
                userDetails.setAddress(rs.getString("address"));
                userDetails.setPhone(rs.getString("phone"));
                userDetails.setEmail(rs.getString("email"));
                return userDetails;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Read All User Details (SELECT)
    public static List<UserDetails> getAllUserDetails() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        String query = "SELECT * FROM user_details";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setId(rs.getInt("id"));
                userDetails.setUserId(rs.getInt("user_id"));
                userDetails.setName(rs.getString("name"));
                userDetails.setAddress(rs.getString("address"));
                userDetails.setPhone(rs.getString("phone"));
                userDetails.setEmail(rs.getString("email"));
                userDetailsList.add(userDetails);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDetailsList;
    }

    // ✅ Update User Details (UPDATE)
    public static boolean updateUserDetails(UserDetails userDetails) {
        String query = "UPDATE user_details SET name = ?, address = ?, phone = ?, email = ? WHERE user_id = ?";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userDetails.getName());
            pstmt.setString(2, userDetails.getAddress());
            pstmt.setString(3, userDetails.getPhone());
            pstmt.setString(4, userDetails.getEmail());
            pstmt.setInt(5, userDetails.getUserId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Delete User Details by User ID (DELETE)
    public static boolean deleteUserDetails(int userId) {
        String query = "DELETE FROM user_details WHERE user_id = ?";
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
