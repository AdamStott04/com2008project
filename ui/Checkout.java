package ui;


import database.database;
import items.Item;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;
import database.database;

import App.App;
import items.OrderLine;
import user.Order;

import user.User;
import user.BankDetails;
import java.sql.*;
import database.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Checkout {
    public JPanel rootPanel;
    private JTextField cvv;
    private JTextField cardName;
    private JTextField cardNo;
    private JTextField expiryDate;
    private JLabel displayPrice;
    private JButton checkoutButton;
    private JLabel enterDetailsLabel;
    private JLabel cardTypeLabel;
    private JTextField cardType;
    public static ArrayList<BankDetails> bankDetails = new ArrayList<>();

    public Checkout(ArrayList<OrderLine> orderItems, User user) {

        Double total = 0.00;
        for (OrderLine item : orderItems) {
            String[] details = App.getItemDetails(item.getProductCode());
            Double price = Double.parseDouble(details[2]);
            total += price;
        }

        String formattedTotal = String.format("%.2f", total);
        displayPrice.setText("Price: £" + formattedTotal);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all bank details are filled in
                if (!areBankDetailsFilledIn()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all bank details.");
                } else if (!hasBankDetailsSaved(user) && BankDetails.validBank(Long.parseLong(cardNo.getText()), expiryDate.getText(), Integer.parseInt(cvv.getText())) ) {
                    addNewBankDetails(Long.parseLong(cardNo.getText()), cardName.getText(), expiryDate.getText(), Integer.parseInt(cvv.getText()));
                    JOptionPane.showMessageDialog(null, "Processing Order");
                    addNewBankDetails(Long.parseLong(cardNo.getText()), cardName.getText(), expiryDate.getText(), Integer.parseInt(cvv.getText()), cardType.getText());
                    Order.addToDb(orderItems, user);
                } else if (hasBankDetailsSaved(user) && sameDetailsEntered(user) ){
                    // add order to the database
                    JOptionPane.showMessageDialog(null, "Processing Order");
                    Order.addToDb(orderItems, user);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid bank details.");
                }
            }
        });
    }



    private boolean areBankDetailsFilledIn() {
        return  !cvv.getText().isEmpty() &&
                !cardName.getText().isEmpty() &&
                !cardNo.getText().isEmpty() &&
                !expiryDate.getText().isEmpty();
    }

    private boolean hasBankDetailsSaved(User user) {
        if (user.getBankDetails() == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean sameDetailsEntered(User user) {
         if (user.getBankDetails().getCardName() == cardName.getText() && user.getBankDetails().getCvv() == Integer.parseInt(cvv.getText()) && user.getBankDetails().getExpiryDate() == expiryDate.getText() && user.getBankDetails().getCardNo() == Long.parseLong(cardNo.getText())      ) {
                return true;
         } else {
                return false;
            }
    }

    public static void addNewBankDetails(long cardNo, String cardName, String expiryDate, int cvv, String cardType) {
        int ID = 0;
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO bankDetails (cardNo, cardName, expiryDate, cvv, cardType) VALUES (?, ?, ?, ?, ?);")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);
            preparedStatement.setString(5, cardType);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BankDetails bank = new BankDetails(ID, cardNo, cardName, expiryDate, cvv, cardType);
        bankDetails.add(bank);
    }

    public static void addNewOrder(long cardNo, String cardName, String expiryDate, int cvv) {
        int ID = 0;
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO bankDetails (cardNo, cardName, expiryDate, cvv) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BankDetails bank = new BankDetails(ID, cardNo, cardName, expiryDate, cvv);
        bankDetails.add(bank);
    }

}

