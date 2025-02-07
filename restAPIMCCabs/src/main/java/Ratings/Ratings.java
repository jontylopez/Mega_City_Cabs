/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ratings;

import java.math.BigDecimal;

public class Ratings {

    private int id;
    private int userId;
    private int reservationId;
    private BigDecimal tripRating;
    private BigDecimal vehicleRating;
    private BigDecimal driverRating;
    private BigDecimal overalRating;
    private String comment;

    public Ratings() {
    }

    public Ratings(int id, int userId, int reservationId, BigDecimal tripRating, BigDecimal vehicleRating, BigDecimal driverRating, BigDecimal overalRating, String comment) {
        this.id = id;
        this.userId = userId;
        this.reservationId = reservationId;
        this.tripRating = tripRating;
        this.vehicleRating = vehicleRating;
        this.driverRating = driverRating;
        this.overalRating = overalRating;
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

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public BigDecimal getOveralRating() {
        return overalRating;
    }

    public void setOveralRating(BigDecimal overalRating) {
        this.overalRating = overalRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
