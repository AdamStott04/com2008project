package App;

import database.database;
import ui.LoginPage;
import ui.RegistrationPage;
import ui.editUserDetails;
import user.User;


import javax.swing.*;
import java.sql.*;

import static user.User.createUser;
import static user.Address.createAddress;
import static user.BankDetails.createBankDetails;


public class App {
    public static void main(String[] args) throws SQLException {
        loadFromDb();
        login();

    }

    public static void loadFromDb() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM users";
        String sql2 = "SELECT * FROM addresses";
        String sql3 = "SELECT * FROM bankDetails";
        ResultSet userSet = null;
        ResultSet addressSet = null;
        ResultSet bankDetailsSet = null;

        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement2 = con.prepareStatement(sql2);
            preparedStatement3 = con.prepareStatement(sql3);

            userSet = preparedStatement.executeQuery();
            addressSet = preparedStatement2.executeQuery();
            bankDetailsSet = preparedStatement3.executeQuery();
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
                createUser(id, forename, surname, email, password, houseNo, postcode, isStaff, isManager, bankID);

            }
            while (addressSet.next()) {
                int houseNo = addressSet.getInt("houseNo");
                String postcode = addressSet.getString("postcode");
                String street = addressSet.getString("street");
                String country = addressSet.getString("country");
                createAddress(houseNo, street, postcode, country);
            }
            while (bankDetailsSet.next()) {
                int cardNum = bankDetailsSet.getInt("cardNo");
                String cardName = bankDetailsSet.getString("cardName");
                String expiryDate = bankDetailsSet.getString("expiryDate");
                int bankID = bankDetailsSet.getInt("bankID");
                int cvv = bankDetailsSet.getInt("cvv");
                createBankDetails(bankID, cardNum, cardName, expiryDate, cvv);
            }
            preparedStatement.close();
            preparedStatement2.close();
            preparedStatement3.close();
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void login() throws SQLException {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginPage(frame).rootPanel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void registration() {
        JFrame frame = new JFrame("Registration");
        frame.setContentPane(new RegistrationPage(frame).rootPanel);
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void userDetails(User user) {
        JFrame frame = new JFrame("Edit User Details");
        frame.setContentPane(new editUserDetails(user).rootPanel);
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
