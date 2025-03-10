package DriverAvailability;

import DateAdapter.SqlDateAdapter;
import java.sql.Date;
import com.google.gson.annotations.JsonAdapter;

public class DriverAvailability {

    private int id;
    private int driverId;

    @JsonAdapter(SqlDateAdapter.class)
    private Date stDate;

    @JsonAdapter(SqlDateAdapter.class)
    private Date endDate;

    public DriverAvailability() {
    }

    public DriverAvailability(int id, int driverId, Date stDate, Date endDate) {
        this.id = id;
        this.driverId = driverId;
        this.stDate = stDate;
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
        return stDate;
    }

    public void setStartDate(Date startDate) {
        this.stDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
