/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

/**
 *
 * @author Janith
 */
public class Users {
     private int id;
    private String username;
    private String pWord;
    private String uRole;
    private String fullName;
    private String address;
    private String phone;
    private String email;

    public Users() {
    }

    public Users(int id, String username, String password, String uRole, String name, String address, String phone, String email) {
        this.id = id;
        this.username = username;
        this.pWord = password;
        this.uRole = uRole;
        this.fullName = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // ✅ Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getpWord() {
        return pWord;
    }

    public void setpWordord(String pWord) {
        this.pWord = pWord;
    }

    public String getuRole() {
        return uRole;
    }

    public void setuRole(String uRole) {
        this.uRole = uRole;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
