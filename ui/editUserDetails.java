package ui;

import database.database;
import user.Address;
import user.BankDetails;
import user.User;
import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static helper.passwordHash.hashPassword;

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
    private JLabel cardTypeLabel;
    private JTextField cardTypeField;


    public editUserDetails(User user, JFrame frame) {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = user.getId();
                int bankID = user.getBankID();
                String new_forename = forenameField.getText();
                String new_surname = surnameField.getText();
                String new_email = emailField.getText();
                String new_password = passwordField.getText();
                int new_houseNo = Integer.parseInt(houseNoField.getText());
                String new_street = streetNameField.getText();
                String new_postcode = postcodeField.getText();
                String new_country = countryField.getText();
                long new_cardNo = (long) Double.parseDouble(cardNoField.getText());
                String new_cardName = cardNameField.getText();
                String new_expiry = expiryDateField.getText();
                int new_cvv = Integer.parseInt(cvvField.getText());
                String new_cardType = cardTypeField.getText();
                System.out.println(String.valueOf(new_cardNo).length());
                if (!(BankDetails.validBank(new_cardNo, new_expiry, new_cvv))) {
                    JOptionPane.showMessageDialog(null, "The bank details you have entered are not valid" +
                            " Please re-enter valid bank details!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (Address.validAddress(new_houseNo, new_postcode) == null) {
                        try {
                            Address.createAddress(new_houseNo, new_postcode, new_street, new_country);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            Address.updateAddress(user.getHouseNo(), user.getPostcode(), new_houseNo, new_street, new_postcode, new_country);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
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
                    updateQuery.append("postcode = ?, ");
                    if (user.getBankDetails() == null) {
                        updateQuery.append("bankID = ?");
                        try {
                            BankDetails.addNewBankDetails(new_cardNo, new_cardName, new_expiry, new_cvv, new_cardType);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        updateQuery.setLength(updateQuery.length() - 2);
                    }
                    updateQuery.append(" WHERE userID = ?;");
                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateQuery.toString());
                        preparedStatement.setString(1, new_forename);
                        preparedStatement.setString(2, new_surname);
                        preparedStatement.setString(3, new_email);
                        if (!(passwordField.getText().isEmpty())) {
                            preparedStatement.setString(4, hashPassword(new_password));
                            preparedStatement.setInt(5, new_houseNo);
                            preparedStatement.setString(6, new_postcode);
                            if (user.getBankDetails() == null) {
                                int new_bankID = BankDetails.findBankID(new_cardNo, new_cardName, new_expiry, new_cvv);
                                preparedStatement.setInt(7, new_bankID);
                                preparedStatement.setInt(8, userId);
                            } else {
                                preparedStatement.setInt(7, userId);
                            }
                        } else {
                            preparedStatement.setInt(4, new_houseNo);
                            preparedStatement.setString(5, new_postcode);
                            if (user.getBankDetails() == null) {
                                int new_bankID = BankDetails.findBankID(new_cardNo, new_cardName, new_expiry, new_cvv);
                                preparedStatement.setInt(6, new_bankID);
                                preparedStatement.setInt(7, userId);
                            } else {
                                preparedStatement.setInt(6, userId);
                            }

                        }
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        con.close();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        BankDetails.updateBankDetails(bankID, new_cardNo, new_cardName, new_expiry, new_cvv);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.userDashboard(user);

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
