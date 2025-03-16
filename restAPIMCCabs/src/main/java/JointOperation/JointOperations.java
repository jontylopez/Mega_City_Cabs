package JointOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DBConnection.ConnectionHelper;
import Discount.Discounts;

public class JointOperations {

    /**
     * ✅ Get Available Vehicles for a Given Category and Date Range
     */
    public static List<Integer> getAvailableVehicles(int categoryId, Date startDate, Date endDate) {
        List<Integer> availableVehicles = new ArrayList<>();
        String query = "SELECT v.id FROM vehicles v " +
                       "WHERE v.catId = ? AND v.id NOT IN (" +
                       "    SELECT va.vehicleId FROM vehicle_availability va " +
                       "    WHERE (va.stDate <= ? AND va.endDate >= ?)" +
                       ")";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, categoryId);
            stmt.setDate(2, endDate);
            stmt.setDate(3, startDate);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                availableVehicles.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableVehicles;
    }

    /**
     * ✅ Get Available Drivers for a Given Date Range
     */
    public static List<Integer> getAvailableDrivers(Date startDate, Date endDate) {
        List<Integer> availableDrivers = new ArrayList<>();
        String query = "SELECT d.id FROM drivers d " +
                       "WHERE d.id NOT IN (" +
                       "    SELECT da.driverId FROM driver_availability da " +
                       "    WHERE (da.stDate <= ? AND da.endDate >= ?)" +
                       ")";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, endDate);
            stmt.setDate(2, startDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                availableDrivers.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDrivers;
    }

    /**
 * ✅ Get Available Discounts for a User
 */
public static List<Discounts> getAvailableDiscounts(int userId) {
    List<Discounts> availableDiscounts = new ArrayList<>();

    String query = "SELECT d.* FROM discounts d " +
                   "LEFT JOIN discount_availability da ON d.id = da.dissId AND da.userId = ? " +
                   "WHERE d.endDate >= CURDATE() AND da.dissId IS NULL AND d.dStatus = 'Active'";

    try (Connection conn = ConnectionHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            availableDiscounts.add(new Discounts(
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

    return availableDiscounts;
}

   public static int createReservation(int userId, int categoryId, Date stDate, Date endDate, Time startTime, String startLocation, Integer dissId, Double finalPrice) {
    int reservationId = -1;
    List<Integer> availableVehicles = getAvailableVehicles(categoryId, stDate, endDate);
    List<Integer> availableDrivers = getAvailableDrivers(stDate, endDate);
    
    // ✅ Get Available Discounts & Extract IDs
    List<Discounts> discountList = getAvailableDiscounts(userId);
    List<Integer> availableDiscountIds = new ArrayList<>();
    for (Discounts discount : discountList) {
        availableDiscountIds.add(discount.getId());
    }

    if (availableVehicles.isEmpty()) {
        System.out.println("❌ No available vehicles for the selected dates.");
        return -1;
    }

    // ✅ Check if the user-selected discount is valid
    if (dissId != null && !availableDiscountIds.contains(dissId)) {
        System.out.println("❌ Selected discount is not valid or has already expired.");
        return -1;
    }

    int vehicleId = availableVehicles.get(0); // Pick first available vehicle
    Integer driverId = availableDrivers.isEmpty() ? null : availableDrivers.get(0); // Pick first available driver (if available)

    String insertReservation = "INSERT INTO reservations (userId, vehicleId, driverId, dissId, finalPrice, stDate, endDate, stTime, stLocation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String insertVehicleAvailability = "INSERT INTO vehicle_availability (vehicleId, stDate, endDate) VALUES (?, ?, ?)";
    String insertDriverAvailability = "INSERT INTO driver_availability (driverId, stDate, endDate) VALUES (?, ?, ?)";
    String insertDiscountUsage = "INSERT INTO discount_availability (userId, dissId) VALUES (?, ?)";

    try (Connection conn = ConnectionHelper.getConnection()) {
        conn.setAutoCommit(false); // Begin transaction

        try (PreparedStatement stmt = conn.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, vehicleId);
            if (driverId != null) {
                stmt.setInt(3, driverId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            if (dissId != null) {
                stmt.setInt(4, dissId);
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            if (finalPrice != null) {
                stmt.setDouble(5, finalPrice);
            } else {
                stmt.setNull(5, Types.DECIMAL);
            }
            stmt.setDate(6, stDate);
            stmt.setDate(7, endDate);
            stmt.setTime(8, startTime);
            stmt.setString(9, startLocation);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reservationId = rs.getInt(1);
            }
        }

        // ✅ Block vehicle for the selected date range
        try (PreparedStatement stmt = conn.prepareStatement(insertVehicleAvailability)) {
            stmt.setInt(1, vehicleId);
            stmt.setDate(2, stDate);
            stmt.setDate(3, endDate);
            stmt.executeUpdate();
        }

        // ✅ Block driver for the selected date range (if assigned)
        if (driverId != null) {
            try (PreparedStatement stmt = conn.prepareStatement(insertDriverAvailability)) {
                stmt.setInt(1, driverId);
                stmt.setDate(2, stDate);
                stmt.setDate(3, endDate);
                stmt.executeUpdate();
            }
        }

        // ✅ Mark Discount as Used (if applied)
        if (dissId != null) {
            try (PreparedStatement stmt = conn.prepareStatement(insertDiscountUsage)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, dissId);
                stmt.executeUpdate();
            }
        }

        conn.commit(); // ✅ Commit transaction
        System.out.println("✅ Reservation created successfully! ID: " + reservationId);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reservationId;
}
    /**
     * ✅ Get Pending Payments for a User
     */
    public static List<PendingPaymentDetails> getPendingPaymentsForUser(int userId) {
        List<PendingPaymentDetails> pendingPayments = new ArrayList<>();
        String query = "SELECT rp.id AS paymentId, rp.resId, rp.price, r.stDate, r.endDate " +
                       "FROM reservation_finalize rp " +
                       "JOIN reservations r ON rp.resId = r.id " +
                       "WHERE r.userId = ? AND rp.stat = 'Pending'";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int paymentId = rs.getInt("paymentId");
                int resId = rs.getInt("resId");
                double price = rs.getDouble("price");
                Date startDate = rs.getDate("stDate");
                Date endDate = rs.getDate("endDate");

                // Create PendingPaymentDetails object
                PendingPaymentDetails payment = new PendingPaymentDetails(paymentId, resId, price, startDate, endDate);
                pendingPayments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingPayments;
    }
    /**
     * Pending Payment Details Model
     */
    public static class PendingPaymentDetails {
        private int paymentId;
        private int resId;
        private double price;
        private Date startDate;
        private Date endDate;

        public PendingPaymentDetails(int paymentId, int resId, double price, Date startDate, Date endDate) {
            this.paymentId = paymentId;
            this.resId = resId;
            this.price = price;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        // Getters and Setters
        public int getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(int paymentId) {
            this.paymentId = paymentId;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
