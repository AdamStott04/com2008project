package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import App.App;
import items.OrderLine;
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
                    JOptionPane.showMessageDialog(null, "Processing Order");
                } else if (hasBankDetailsSaved(user) && sameDetailsEntered(user) ){
                    JOptionPane.showMessageDialog(null, "Processing Order");
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






}

