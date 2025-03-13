package Ratings;

import java.math.BigDecimal;

public class Ratings {

    private int id;
    private int userId;
    private BigDecimal tripRating;
    private BigDecimal vehicleRating;
    private BigDecimal driverRating;
    private String comment;

    public Ratings() {
    }

    public Ratings(int id, int userId, BigDecimal tripRating,
                   BigDecimal vehicleRating, BigDecimal driverRating, String comment) {
        this.id = id;
        this.userId = userId;
        this.tripRating = tripRating;
        this.vehicleRating = vehicleRating;
        this.driverRating = driverRating;
        this.comment = comment;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTripRating() {
        return tripRating;
    }

    public void setTripRating(BigDecimal tripRating) {
        this.tripRating = tripRating;
    }

    public BigDecimal getVehicleRating() {
        return vehicleRating;
    }

    public void setVehicleRating(BigDecimal vehicleRating) {
        this.vehicleRating = vehicleRating;
    }

    public BigDecimal getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(BigDecimal driverRating) {
        this.driverRating = driverRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // ✅ Compute Overall Rating (Backend Calculation)
    public BigDecimal getOverallRating() {
        return tripRating.add(vehicleRating).add(driverRating)
                .divide(BigDecimal.valueOf(3), 1, BigDecimal.ROUND_HALF_UP);
    }
}
