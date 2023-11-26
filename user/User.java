package user;

import java.sql.*;
import java.util.ArrayList;
import items.OrderLine;
import static helper.passwordHash.hashPassword;

public class User {

    private Integer bankID;
    private int id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private int houseNo;
    private String postcode;
    private int isStaff;
    private int isManager;
    private ArrayList<OrderLine> currentOrder = new ArrayList<>();

    private Address address;

    private BankDetails bankDetails;

    public User(int id, String forename, String surname, String email, String password, int houseNo, String postcode, int isStaff, int isManager, Integer bankID) throws SQLException {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.houseNo = houseNo;
        this.postcode = postcode;
        this.isManager = isManager;
        this.isStaff = isStaff;
        this.bankID = bankID;
        this.address = Address.validAddress(houseNo, postcode);
        this.bankDetails = BankDetails.bankExists(bankID);
    }

    public int getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String value) {
        this.forename = value;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String value) {
        this.surname = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = hashPassword(value);
    }

    public int getHouseNo() { return houseNo;}

    public void setHouseNo(int value) { this.houseNo = value;}

    public String getPostcode() { return postcode;}

    public void setPostcode(String value) { this.postcode = value;}

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int value) {
        if (value == 0 || value == 1) {
            this.isManager = value;
        }
    }

    public int getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(int value) {
        if (value == 0 || value == 1) {
            this.isStaff = value;
        }
    }

    public Integer getBankID() { return bankID; }

    public void setBankID(Integer value) { this.bankID = value;}

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }


    public static ArrayList<User> users = new ArrayList<>();

    public static void createUser(int id, String forename, String surname, String email, String password, int houseNo, String postcode, int isStaff, int isManager, int bankID) throws SQLException {
        User newUser = new User(id, forename, surname, email, password, houseNo, postcode, isStaff, isManager, bankID);
        users.add(newUser);
    }

    public static User validUser(String email, String password) throws SQLException {
        for (User user : User.users) {
            if (user.email.equals(email) && user.password.equals(hashPassword(password))) {
                return user; // Found a matching user
            }
        }
        return null; // No matching user found
    }

    public static boolean uniqueEmail(String email) {
        for (User user : User.users) {
            if (user.email.equals(email)) {
                return false; // Found a matching user
            }
        }
        return true; // No matching user found
    }

    public ArrayList<OrderLine> getCurrentOrder() {
        return currentOrder;
    }

    public void addToCurrentOrder(OrderLine newLine) {
        currentOrder.add(newLine);
    }

}
