package user;

import java.sql.*;
import java.util.ArrayList;

import database.database;

import javax.xml.transform.Result;

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

    public static ArrayList<User> users = new ArrayList<>();

    public static void createUser(int id, String forename, String surname, String email, String password, int isStaff, int isManager) {
        User newUser = new User(id, forename, surname, email, password, isStaff, isManager);
        users.add(newUser);
    }

    public static boolean validUser(String email, String password) throws SQLException {
        for (User user : User.users) {
            if (user.email.equals(email) && user.password.equals(hashPassword(password))) {
                return true; // Found a matching user
            }
        }
        return false; // No matching user found
    }

}
