package user;

import java.sql.*;

import database.database;

import javax.xml.transform.Result;

import static helper.passwordHash.hashPassword;

public class User {

    private String id;
    private String forename;
    private String surname;
    private String email;
    private String password;

    private Address address;

    private BankDetails bankDetails;

    public User(String id, String forename, String surname, String email, String password) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public static boolean validUser(String email, String password, ResultSet results) throws SQLException {

        while (results.next()) {
            String dbEmail = results.getString("email");
            String dbPassword = results.getString("password");

            if (dbEmail.equals(email) && dbPassword.equals(hashPassword(password))) {
                return true;
            }
        }
        return false;
    }

}
