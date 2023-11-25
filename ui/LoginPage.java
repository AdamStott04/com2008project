package ui;

import database.database;
import items.Item;
import user.User;
import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends JFrame {
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
                char[] password = passwordPasswordField.getPassword();
                String password_entered = new String(password);
                try {
                    User user = User.validUser(email_entered, password_entered);
                    if (user != null) {
                        frame.dispose();
                        App.userDashboard(user);
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
