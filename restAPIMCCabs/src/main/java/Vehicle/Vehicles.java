/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehicle;

import java.sql.Date;

public class Vehicles {

    private int id;
    private int catId;
    private String vehicleNo;
    private Date regExpDate;
    private String stat;

    public Vehicles() {
    }

    public Vehicles(int id, int catId, String vehicleNo, Date regExpDate, String stat) {
        this.id = id;
        this.catId = catId;
        this.vehicleNo = vehicleNo;
        this.regExpDate = regExpDate;
        this.stat = stat;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Date getRegExpDate() {
        return regExpDate;
    }

    public void setRegExpDate(Date regExpDate) {
        this.regExpDate = regExpDate;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
