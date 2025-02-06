/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehicle;

import java.sql.Timestamp;

/**
 *
 * @author Janith
 */
public class Vehicles {

    private int id;
    private String vehiName;
    private String typeOf;
    private String model;
    private String registrationNo;
    private double pricePerDay;
    private String imageUrl;
    private Timestamp createdAt;

    public Vehicles() {
    }

    public Vehicles(int id, String vehiName, String typeOf, String model, String registrationNo, double pricePerDay, String imageUrl, Timestamp createdAt) {
        this.id = id;
        this.vehiName = vehiName;
        this.typeOf = typeOf;
        this.model = model;
        this.registrationNo = registrationNo;
        this.pricePerDay = pricePerDay;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehiName() {
        return vehiName;
    }

    public void setVehiName(String vehiName) {
        this.vehiName = vehiName;
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
