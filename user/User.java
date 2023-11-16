package user;

import java.sql.*;
import java.util.ArrayList;

import static helper.passwordHash.hashPassword;

public class User {

    private int id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private int isStaff;
    private int isManager;

    private Address address;

    private BankDetails bankDetails;

    public User(int id, String forename, String surname, String email, String password, int isStaff, int isManager) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
        this.isStaff = isStaff;
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
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
        this.password = value;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int value) {
        this.isManager = value;
    }

    public int getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(int value) {
        this.isStaff = value;
    }


    public static ArrayList<User> users = new ArrayList<>();

    public static void createUser(int id, String forename, String surname, String email, String password, int isStaff, int isManager) {
        User newUser = new User(id, forename, surname, email, password, isStaff, isManager);
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

}
