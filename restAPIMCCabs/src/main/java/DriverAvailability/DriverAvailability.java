package DriverAvailability;

import DateAdapter.SqlDateAdapter;
import java.sql.Date;
import com.google.gson.annotations.JsonAdapter;

public class DriverAvailability {

    private int id;
    private int driverId;

    @JsonAdapter(SqlDateAdapter.class)
    private Date startDate;

    @JsonAdapter(SqlDateAdapter.class)
    private Date endDate;

    public DriverAvailability() {
    }

    public DriverAvailability(int id, int driverId, Date startDate, Date endDate) {
        this.id = id;
        this.driverId = driverId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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
