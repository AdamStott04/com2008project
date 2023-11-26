package ui;

import database.database;
import App.App;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static helper.passwordHash.hashPassword;

public class RegistrationPage extends JFrame {
    public JPanel rootPanel;
    private JTextField forenameTextField;
    private JTextField surnameTextField;
    private JTextField emailTextField;
    private JTextField passwordTextField;
    private JTextField houseNumberField;
    private JTextField streetNameField;
    private JTextField postcodeField;
    private JTextField countryField;
    private JButton registerButton;
    private JButton backToLoginButton;

    public RegistrationPage(JFrame frame) {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    con = DriverManager.getConnection(database.url, database.username, database.password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                String forename = forenameTextField.getText();
                String surname = surnameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                String houseNumber = houseNumberField.getText();
                String streetName = streetNameField.getText();
                String postcode = postcodeField.getText();
                String country = countryField.getText();
                if (forename.isEmpty() | surname.isEmpty() | email.isEmpty() | password.isEmpty() | streetName.isEmpty()
                        | postcode.isEmpty() | country.isEmpty() | houseNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You have left 1 or more text fields empty." +
                            " Please fill them in before pressing register!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "The email you have entered is not in the correct format!" +
                            " Please re-enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!areAllDigits(houseNumber)) {
                    JOptionPane.showMessageDialog(null, "The house number you have entered is not a valid integer." +
                            " Please re-enter a valid house number!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!(User.uniqueEmail(email))) {
                    JOptionPane.showMessageDialog(null, "The email you have entered already exists" +
                            " Please re-enter a unique email!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create address instance in db first, so it can be used in user table
                    String address_sql = "INSERT INTO addresses (houseNo, postcode, street, country) VALUES (?, ?, ?, ?)";

                    PreparedStatement preparedStatement = null;
                    try {
                        preparedStatement = con.prepareStatement(address_sql);
                        preparedStatement.setInt(1, Integer.parseInt(houseNumber));
                        preparedStatement.setString(2, postcode);
                        preparedStatement.setString(3, streetName);
                        preparedStatement.setString(4, country);

                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    String sql = "INSERT INTO users (forename, surname, email, password, houseNo, postcode, isStaff, isManager, bankID) VALUES (?, ?, ?, ?, ?, ?, 0, 0, NULL)";
                    String hashed_password = hashPassword(password);

                    PreparedStatement preparedStatement2 = null;
                    try {
                        preparedStatement2 = con.prepareStatement(sql);
                        preparedStatement2.setString(1, forename);
                        preparedStatement2.setString(2, surname);
                        preparedStatement2.setString(3, email);
                        preparedStatement2.setString(4, hashed_password);
                        preparedStatement2.setInt(5, Integer.parseInt(houseNumber));
                        preparedStatement2.setString(6, postcode);

                        preparedStatement2.executeUpdate();
                        preparedStatement2.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "You successfully created an account!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        User.reloadUserArray();
                        frame.dispose();
                        App.login();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                try {
                    App.login();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private static boolean areAllDigits(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; // If any character is not a digit, return false
            }
        }
        return true; // All characters are digits
    }
}
