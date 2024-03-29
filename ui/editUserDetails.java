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
    private JTextField cityField;
    private JTextField cardNoField;
    private JTextField cardNameField;
    private JTextField expiryDateField;
    private JButton backToDashboardButton;
    private JButton saveButton;
    private JLabel CVVLabel;
    private JTextField cvvField;
    private JTextField cardTypeField;


    public editUserDetails(User user, JFrame frame) {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cardNameField.getText().isEmpty() || cardTypeField.getText().isEmpty() || cardNoField.getText().isEmpty() || cvvField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You haven't filled in all of the bank details" +
                            " Please re-enter valid bank details!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int userId = user.getId();
                    Integer bankID = user.getBankID();
                    String new_forename = forenameField.getText();
                    String new_surname = surnameField.getText();
                    String new_email = emailField.getText();
                    String new_password = passwordField.getText();
                    int new_houseNo = Integer.parseInt(houseNoField.getText());
                    String new_street = streetNameField.getText();
                    String new_postcode = postcodeField.getText();
                    String new_country = cityField.getText();
                    long new_cardNo = (long) Double.parseDouble(cardNoField.getText());
                    String new_cardName = cardNameField.getText();
                    String new_expiry = expiryDateField.getText();
                    int new_cvv = Integer.parseInt(cvvField.getText());
                    String new_cardType = cardTypeField.getText();
                    if (!(BankDetails.validBank(new_cardNo, new_expiry, new_cvv, new_cardType))) {
                        JOptionPane.showMessageDialog(null, "The bank details you have entered are not valid" +
                                " Please re-enter valid bank details!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (Address.validAddress(new_houseNo, new_postcode) == null) {
                            Address.createAddress(new_houseNo, new_postcode, new_street, new_country);
                            user.setAddress(Address.validAddress(new_houseNo, new_postcode));
                        } else if (Address.validAddress(user.getHouseNo(), user.getPostcode()).getCity() != new_country || Address.validAddress(user.getHouseNo(), user.getPostcode()).getStreetName() != new_street) {
                            Address.updateAddress(user.getHouseNo(), user.getPostcode(), new_street, new_country);
                            user.setAddress(Address.validAddress(new_houseNo, new_postcode));
                        }
                        if (bankID == 0) {
                            BankDetails.addNewBankDetails(new_cardNo, new_cardName, new_expiry, new_cvv, new_cardType);
                            bankID = BankDetails.findBankID(new_cardNo, new_cardName, new_expiry, new_cvv);
                            user.setBankDetails(BankDetails.bankExists(bankID));
                        } else {
                            try {
                                BankDetails.updateBankDetails(bankID, new_cardNo, new_cardName, new_expiry, new_cvv, new_cardType);
                                user.setBankDetails(BankDetails.bankExists(bankID));
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
                        updateQuery.append("bankID = ?");
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
                                preparedStatement.setInt(7, bankID);
                                preparedStatement.setInt(8, userId);
                            } else {
                                preparedStatement.setInt(4, new_houseNo);
                                preparedStatement.setString(5, new_postcode);
                                preparedStatement.setInt(6, bankID);
                                preparedStatement.setInt(7, userId);

                            }
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            con.close();

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            BankDetails.updateBankDetails(bankID, new_cardNo, new_cardName, new_expiry, new_cvv, new_cardType);
                            user.setBankDetails(BankDetails.bankExists(bankID));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        user.setEmail(new_email);
                        user.setSurname(new_surname);
                        user.setForename(new_forename);
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
            cityField.setText(address.getCity());
        }
        BankDetails bankDetails = user.getBankDetails();
        if (!(bankDetails == null)) {
            cardNoField.setText(String.valueOf(bankDetails.getCardNo()));
            cardNameField.setText(bankDetails.getCardName());
            expiryDateField.setText(bankDetails.getExpiryDate());
            cvvField.setText(String.valueOf(bankDetails.getCvv()));
            cardTypeField.setText(bankDetails.getCardType());
        }
    }
}