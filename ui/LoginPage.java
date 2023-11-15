package ui;

import database.database;
import user.User;
import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginPage extends JFrame{
    private static ResultSet users;
    public JPanel rootPanel;
    private JLabel loginLabel;
    private JButton loginButton;
    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton registerButton;

    public LoginPage(JFrame frame) throws SQLException {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email_entered = emailTextField.getText();
                String password_entered = Arrays.toString(passwordPasswordField.getPassword());
                try {
                    if (User.validUser(email_entered, password_entered)) {
                        users.close();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                App.registration();
            }
        });

    }

}
