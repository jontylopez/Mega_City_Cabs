/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReservationFinalize;

/**
 *
 * @author Janith
 */
public class ReservationFinalize {
    private int id;
    private int resId;
    private double extraKm;
    private double extraHr;
    private double price;
    private String stat; // "Pending" or "Paid"

    // Constructor
    public ReservationFinalize() {}

    public ReservationFinalize(int id, int resId, double extraKm, double extraHr, double price, String stat) {
        this.id = id;
        this.resId = resId;
        this.extraKm = extraKm;
        this.extraHr = extraHr;
        this.price = price;
        this.stat = stat;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public double getExtraKm() {
        return extraKm;
    }

    public void setExtraKm(double extraKm) {
        this.extraKm = extraKm;
    }

    public double getExtraHr() {
        return extraHr;
    }

    public void setExtraHr(double extraHr) {
        this.extraHr = extraHr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
