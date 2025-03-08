/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Discount;

import DateAdapter.SqlDateAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import com.google.gson.annotations.JsonAdapter;

public class Discounts {

    private int id;
    private String diskId;
    private BigDecimal percentage;

    @JsonAdapter(SqlDateAdapter.class) 
    private Date startDate;

    @JsonAdapter(SqlDateAdapter.class) 
    private Date endDate;

    private String dStatus;

    public Discounts() {
    }

    public Discounts(int id, String diskId, BigDecimal percentage, Date startDate, Date endDate, String dStatus) {
        this.id = id;
        this.diskId = diskId;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dStatus = dStatus;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public String getDStatus() {
        return dStatus;
    }

    public void setDStatus(String dStatus) {
        this.dStatus = dStatus;
    }
}
