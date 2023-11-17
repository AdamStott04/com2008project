import database.database;
import ui.LoginPage;
import ui.RegistrationPage;


import javax.swing.*;
import java.sql.*;


public class main {
    public static void main(String[] args) throws SQLException {
        login(loadUsers());
        showCatalog(loadItems());
    }

    public static ResultSet loadUsers() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM users";
        ResultSet userSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            userSet = preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return userSet;
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

    public static void login(ResultSet users) throws SQLException {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginPage(users).rootPanel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void registration() {
        JFrame frame = new JFrame("Registration");
        frame.setContentPane(new RegistrationPage().rootPanel);
        frame.setSize(500, 400);
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
}