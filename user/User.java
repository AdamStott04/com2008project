package user;

import java.sql.*;
import java.util.ArrayList;

import database.database;
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

    //Constructor for creating a new user
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

    // Getters and setters for user attributes
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

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int value) {
        this.houseNo = value;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String value) {
        this.postcode = value;
    }

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

    public Integer getBankID() {
        return bankID;
    }

    public void setBankID(Integer value) {
        this.bankID = value;
    }

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


    // Array of all users
    public static ArrayList<User> users = new ArrayList<>();

    // Method for creating a new user
    public static void createUser(int id, String forename, String surname, String email, String password, int houseNo, String postcode, int isStaff, int isManager, int bankID) throws SQLException {
        User newUser = new User(id, forename, surname, email, password, houseNo, postcode, isStaff, isManager, bankID);
        users.add(newUser);
    }

    // Checks if a user exists with the given email and password and returns the user if found
    public static User validUser(String email, String password) {
        for (User user : User.users) {
            if (user.email.equals(email) && user.password.equals(hashPassword(password))) {
                return user; // Found a matching user
            }
        }
        return null; // No matching user found
    }

    // Clears the user array and reloads it from the database. Used after registering a user to ensure the user array is up-to-date.
    public static void reloadUserArray() throws SQLException {
        users.clear();
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        String sql = "SELECT * FROM users";
        ResultSet userSet = null;
        PreparedStatement preparedStatement = null;
        preparedStatement = con.prepareStatement(sql);
        userSet = preparedStatement.executeQuery();
        while (userSet.next()) {
            int id = userSet.getInt("userID");
            String forename = userSet.getString("forename");
            String surname = userSet.getString("surname");
            String email = userSet.getString("email");
            String password = userSet.getString("password");
            int houseNo = userSet.getInt("houseNo");
            String postcode = userSet.getString("postcode");
            int isStaff = userSet.getInt("isStaff");
            int isManager = userSet.getInt("isManager");
            int bankID = userSet.getInt("bankID");
            User.createUser(id, forename, surname, email, password, houseNo, postcode, isStaff, isManager, bankID);
        }
    }

    // Checks if a user exists with the given email and returns true if not found
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

    public static String getEmail(int userID) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM users WHERE userID = " + userID + ";";
        ResultSet user = null;
        String userEmail = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            user = preparedStatement.executeQuery();
            ;
            while (user.next()) {
                userEmail = user.getString("email");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return userEmail;
    }


}
