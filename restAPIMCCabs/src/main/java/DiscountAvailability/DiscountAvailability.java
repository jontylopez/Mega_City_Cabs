/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DiscountAvailability;

import java.sql.Timestamp;

public class DiscountAvailability {
    private int id;
    private int userId;
    private int discountId;
    private Timestamp usedAt;

    public DiscountAvailability() {}

    public DiscountAvailability(int id, int userId, int discountId, Timestamp usedAt) {
        this.id = id;
        this.userId = userId;
        this.discountId = discountId;
        this.usedAt = usedAt;
    }

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

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }
}
