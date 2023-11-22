package ui;

import items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import user.BankDetails;
import user.BankDetails.*;

public class Checkout {
    public JPanel rootPanel;
    private JTextField bankID;
    private JTextField cvv;
    private JTextField cardName;
    private JTextField cardNo;
    private JTextField expiryDate;
    private JLabel displayPrice;
    private JButton checkoutButton;
    private JLabel enterDetailsLabel;
    private JLabel bankIDLabel;


    public Checkout(List<Item> orderItems) {

        Double total = 0.00;
        for (int i = 0; i < orderItems.size(); i++) {
            total += orderItems.get(i).getPrice();
        }

        String formattedTotal = String.format("%.2f", total);
        displayPrice.setText("Price: £" + formattedTotal);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all bank details are filled in
                if (areBankDetailsFilledIn()) {
                    JOptionPane.showMessageDialog(null, "Bank details correct, processing order");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all bank details.");
                }
            }
        });
    }

    private boolean areBankDetailsFilledIn() {
        return !bankID.getText().isEmpty() &&
                !cvv.getText().isEmpty() &&
                !cardName.getText().isEmpty() &&
                !cardNo.getText().isEmpty() &&
                !expiryDate.getText().isEmpty();
    }


}

