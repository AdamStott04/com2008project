package App;

import database.database;
import ui.LoginPage;
import ui.RegistrationPage;
import ui.editUserDetails;
import user.Address;
import user.BankDetails;
import user.User;


import javax.swing.*;
import java.sql.*;

import static user.User.createUser;
import static user.Address.createAddress;
import static user.BankDetails.createBankDetails;


public class App {
    public static void main(String[] args) throws SQLException {
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
            while (addressSet.next()) {
                int houseNo = addressSet.getInt("houseNo");
                String postcode = addressSet.getString("postcode");
                String street = addressSet.getString("street");
                String country = addressSet.getString("country");
                Address.addresses.add(new Address(houseNo, street, postcode, country));
            }
            while (bankDetailsSet.next()) {
                long cardNum = bankDetailsSet.getLong("cardNo");
                String cardName = bankDetailsSet.getString("cardName");
                String expiryDate = bankDetailsSet.getString("expiryDate");
                int bankID = bankDetailsSet.getInt("bankID");
                int cvv = bankDetailsSet.getInt("cvv");
                BankDetails.bankDetails.add(new BankDetails(bankID, cardNum, cardName, expiryDate, cvv));
            }
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
            preparedStatement.close();
            preparedStatement2.close();
            preparedStatement3.close();
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ResultSet loadItems() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items";
        ResultSet itemSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            itemSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return itemSet;
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
        frame.setContentPane(new editUserDetails(user, frame).rootPanel);
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void showCatalog(ResultSet items) throws SQLException {
        JFrame frame = new JFrame("Catalog");
        frame.setContentPane(new ui.Catalog(items).rootPanel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void userDashboard(User user) {
        JFrame frame = new JFrame("Dashboard");
        frame.setContentPane(new ui.userDashboard(user, frame).rootPanel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}
