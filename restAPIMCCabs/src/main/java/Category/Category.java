package Category;

import java.math.BigDecimal;

/**
 * Category Model Class
 *
 * @author Janith
 */
public class Category {

    private int id;
    private String catName;
    private int maxPsngr;
    private BigDecimal perDayValue;
    private int maxKmPerDay;
    private BigDecimal milePkg1;
    private BigDecimal milePkg2;
    private BigDecimal waitingPerHr;
    private BigDecimal extraKm;
    private String active;

    public Category() {
    }

    public Category(int id, String catName, int maxPsngr, BigDecimal perDayValue, int maxKmPerDay,
            BigDecimal milePkg1, BigDecimal milePkg2, BigDecimal waitingPerHr, BigDecimal extraKm,
            String active) {
        this.id = id;
        this.catName = catName;
        this.maxPsngr = maxPsngr;
        this.perDayValue = perDayValue;
        this.maxKmPerDay = maxKmPerDay;
        this.milePkg1 = milePkg1;
        this.milePkg2 = milePkg2;
        this.waitingPerHr = waitingPerHr;
        this.extraKm = extraKm;
        this.active = active;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getMaxPsngr() {
        return maxPsngr;
    }

    public void setMaxPsngr(int maxPsngr) {
        this.maxPsngr = maxPsngr;
    }

    public BigDecimal getPerDayValue() {
        return perDayValue;
    }

    public void setPerDayValue(BigDecimal perDayValue) {
        this.perDayValue = perDayValue;
    }

    public int getMaxKmPerDay() {
        return maxKmPerDay;
    }

    public void setMaxKmPerDay(int maxKmPerDay) {
        this.maxKmPerDay = maxKmPerDay;
    }

    public BigDecimal getMilePkg1() {
        return milePkg1;
    }

    public void setMilePkg1(BigDecimal milePkg1) {
        this.milePkg1 = milePkg1;
    }

    public BigDecimal getMilePkg2() {
        return milePkg2;
    }

    public void setMilePkg2(BigDecimal milePkg2) {
        this.milePkg2 = milePkg2;
    }

    public BigDecimal getWaitingPerHr() {
        return waitingPerHr;
    }

    public void setWaitingPerHr(BigDecimal waitingPerHr) {
        this.waitingPerHr = waitingPerHr;
    }

    public BigDecimal getExtraKm() {
        return extraKm;
    }

    public void setExtraKm(BigDecimal extraKm) {
        this.extraKm = extraKm;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
