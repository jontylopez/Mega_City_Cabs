package Driver;

import DateAdapter.SqlDateAdapter;
import java.sql.Date;
import com.google.gson.annotations.JsonAdapter;

public class Drivers {

    private int id;
    private String dName;
    private String dAddress;
    private String dTel;
    private String dLNum;

    @JsonAdapter(SqlDateAdapter.class) 
    private Date dLExpDate;

    private String stat;

    public Drivers() {
    }

    public Drivers(int id, String dName, String dAddress, String dTel, String dLNum, Date dLExpDate, String stat) {
        this.id = id;
        this.dName = dName;
        this.dAddress = dAddress;
        this.dTel = dTel;
        this.dLNum = dLNum;
        this.dLExpDate = dLExpDate;
        this.stat = stat;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDName() {
        return dName;
    }

    public void setDName(String dName) {
        this.dName = dName;
    }

    public String getDAddress() {
        return dAddress;
    }

    public void setDAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getDTel() {
        return dTel;
    }

    public void setDTel(String dTel) {
        this.dTel = dTel;
    }

    public String getDLNum() {
        return dLNum;
    }

    public void setDLNum(String dLNum) {
        this.dLNum = dLNum;
    }

    public Date getDLExpDate() {
        return dLExpDate;
    }

    public void setDLExpDate(Date dLExpDate) {
        this.dLExpDate = dLExpDate;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
