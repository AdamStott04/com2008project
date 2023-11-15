package App;

import database.database;
import ui.LoginPage;
import ui.RegistrationPage;
import user.User;


import javax.swing.*;
import java.sql.*;

import static user.User.createUser;


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
        ResultSet userSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            userSet = preparedStatement.executeQuery();
            while (userSet.next()) {
                int id = userSet.getInt("userID");
                String forename = userSet.getString("forename");
                String surname = userSet.getString("surname");
                String email = userSet.getString("email");
                String password = userSet.getString("password");
                int isStaff = userSet.getInt("isStaff");
                int isManager = userSet.getInt("isManager");
                createUser(id,forename,surname,email,password,isStaff,isManager);

            }
            preparedStatement.close();
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
}
