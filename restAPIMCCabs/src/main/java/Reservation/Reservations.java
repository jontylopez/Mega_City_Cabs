/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservation;

import java.sql.Date;
import java.sql.Time;

public class Reservations {

    private int id;
    private int userId;
    private int vehicleId;
    private Integer driverId;
    private Date stDate;
    private Date endDate;
    private Time stTime;
    private String stLocation;
    private String stat;
    private String comments;

    public Reservations() {
    }

    public Reservations(int id, int userId, int vehicleId, Integer driverId, Date stDate, Date endDate, Time stTime, String stLocation, String stat, String comments) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.stDate = stDate;
        this.endDate = endDate;
        this.stTime = stTime;
        this.stLocation = stLocation;
        this.stat = stat;
        this.comments = comments;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Date getStDate() {
        return stDate;
    }

    public void setStDate(Date stDate) {
        this.stDate = stDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getStTime() {
        return stTime;
    }

    public void setStTime(Time stTime) {
        this.stTime = stTime;
    }

    public String getStLocation() {
        return stLocation;
    }

    public void setStLocation(String stLocation) {
        this.stLocation = stLocation;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
