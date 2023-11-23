package ui;

import items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import user.User;
import user.BankDetails;



public class Checkout {
    public JPanel rootPanel;
    private JTextField cvv;
    private JTextField cardName;
    private JTextField cardNo;
    private JTextField expiryDate;
    private JLabel displayPrice;
    private JButton checkoutButton;
    private JLabel enterDetailsLabel;

    public Checkout(List<Item> orderItems, User user) {

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
                if (!areBankDetailsFilledIn()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all bank details.");
                } else if (!hasBankDetailsSaved(user) && BankDetails.validBank(Long.parseLong(cardNo.getText()), expiryDate.getText(), Integer.parseInt(cvv.getText())) ) {
                    JOptionPane.showMessageDialog(null, "Processing Order");
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




}

