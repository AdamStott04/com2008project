package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

import database.database;
import user.User;

public class LoginPage {
    public JPanel rootPanel;
    private JLabel loginLabel;
    private JButton loginButton;
    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton registerButton;

    public LoginPage(ResultSet users) throws SQLException {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    con = DriverManager.getConnection(database.url, database.username, database.password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                String email_entered = emailTextField.getText();
                String password_entered = Arrays.toString(passwordPasswordField.getPassword());
                try {
                    if (User.validUser(email_entered, password_entered, users)) {

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
