package VehicleAvailability;

import DateAdapter.SqlDateAdapter;
import java.sql.Date;
import com.google.gson.annotations.JsonAdapter;

public class VehicleAvailability {

    private int id;
    private int vehicleId;

    @JsonAdapter(SqlDateAdapter.class)
    private Date startDate;

    @JsonAdapter(SqlDateAdapter.class)
    private Date endDate;

    public VehicleAvailability() {
    }

    public VehicleAvailability(int id, int vehicleId, Date startDate, Date endDate) {
        this.id = id;
        this.vehicleId = vehicleId;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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
