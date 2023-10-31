package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import database.database;

public class login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                String email = emailField.getText();
                char[] password = passwordField.getPassword();

                try {

                    Connection con = DriverManager.getConnection(database.url,database.username,database.password);
                    // SQL query to check username and password
                    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, Arrays.toString(password));

                    ResultSet result = statement.executeQuery();

                    // Check if there's a result
                    if (result.next()) {
                        // User authentication is successful
                        con.close();
                        System.out.println("Signed in successfully!");
                    } else {
                        // User authentication failed
                        con.close();
                        System.out.println("Failed to sign in");
                    }
                } catch (Exception e) {
                    // Handle any exceptions (e.g., SQLException)
                    e.printStackTrace();
                }




                System.out.println("Username: " + email);
                System.out.println("Password: " + new String(password));

                // Clear the fields after printing
                emailField.setText("");
                passwordField.setText("");
            }
        });

        frame.setVisible(true);
    }
}
