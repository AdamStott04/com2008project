package ui;

import database.database;
import user.Address;
import user.BankDetails;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class editUserDetails {
    public JPanel rootPanel;
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField houseNoField;
    private JTextField streetNameField;
    private JTextField postcodeField;
    private JTextField countryField;
    private JTextField cardNoField;
    private JTextField cardNameField;
    private JTextField expiryDateField;
    private JButton backToDashboardButton;
    private JButton saveButton;
    private JLabel CVVLabel;
    private JTextField cvvField;


    public editUserDetails(User user) {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = user.getId();
                String new_forename = forenameField.getText();
                String new_surname = surnameField.getText();
                String new_email = emailField.getText();
                String new_password = passwordField.getText();
                int new_houseNo = Integer.parseInt(houseNoField.getText());
                String new_street = streetNameField.getText();
                String new_postcode = postcodeField.getText();
                String new_country = countryField.getText();
                int new_cardNo = Integer.parseInt(cardNoField.getText());
                String new_cardName = cardNameField.getText();
                String new_expiry = expiryDateField.getText();
                int new_cvv = Integer.parseInt(cvvField.getText());
                if (!(BankDetails.validBank(new_cardNo, new_expiry, new_cvv))) {
                    JOptionPane.showMessageDialog(null, "The bank details you have entered are not valid" +
                            " Please re-enter valid bank details!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    Connection con = null;
                    try {
                        con = DriverManager.getConnection(database.url, database.username, database.password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    StringBuilder updateQuery = new StringBuilder("UPDATE users SET ");
                    updateQuery.append("forename = ?, ");
                    updateQuery.append("surname = ?, ");
                    updateQuery.append("email = ?, ");
                    if (!(passwordField.getText().isEmpty())) {
                        updateQuery.append("password = ?, ");
                    }
                    updateQuery.append("houseNo = ?, ");
                    updateQuery.append("postcode = ?");
                    updateQuery.append(" WHERE userID = ?;");

                }
            }
        });
        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        forenameField.setText(user.getForename());
        surnameField.setText(user.getSurname());
        emailField.setText(user.getEmail());
        Address address = user.getAddress();
        if (!(address == null)) {
            houseNoField.setText(String.valueOf(address.getHouseNo()));
            streetNameField.setText(address.getStreetName());
            postcodeField.setText(address.getPostcode());
            countryField.setText(address.getCountry());
        }
        BankDetails bankDetails = user.getBankDetails();
        if (!(bankDetails == null)) {
            cardNoField.setText(String.valueOf(bankDetails.getCardNo()));
            cardNameField.setText(bankDetails.getCardName());
            expiryDateField.setText(bankDetails.getExpiryDate());
            cvvField.setText(String.valueOf(bankDetails.getCvv()));
        }
    }
}
