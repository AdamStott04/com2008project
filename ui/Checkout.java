package ui;


import items.Item;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;

import App.App;
import items.OrderLine;
import user.Order;

import user.User;
import user.BankDetails;

public class Checkout extends JFrame {
    public JPanel rootPanel;
    private JTextField cvv;
    private JTextField cardName;
    private JTextField cardNo;
    private JTextField expiryDate;
    private JLabel displayPrice;
    private JButton checkoutButton;

    private JButton backButton;

    private JLabel enterDetailsLabel;
    private JLabel cardTypeLabel;
    private JTextField cardType;
    private JButton viewCurrentOrderButton;
    public static ArrayList<BankDetails> bankDetails = new ArrayList<>();

    public Checkout(ArrayList<OrderLine> orderItems, User user) {


        Double total = 0.00;
        for (OrderLine item : orderItems) {
            int quantity = item.getQuantity();
            String[] details = Item.getItemDetails(item.getProductCode());
            Double price = Double.parseDouble(details[2]);
            Double pricePerQuantity = price*quantity;
            total += pricePerQuantity;
        }

        String formattedTotal = String.format("%.2f", total);
        displayPrice.setText("Price: £" + formattedTotal);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all bank details are filled in
                if (!areBankDetailsFilledIn()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all bank details.");
                } else if (!hasBankDetailsSaved(user) && BankDetails.validBank(Long.parseLong(cardNo.getText()), expiryDate.getText(), Integer.parseInt(cvv.getText()), cardType.getText()) ) {
                    BankDetails.addNewBankDetails(Long.parseLong(cardNo.getText()), cardName.getText(), expiryDate.getText(), Integer.parseInt(cvv.getText()), cardType.getText());
                    JOptionPane.showMessageDialog(null, "Processing Order");
                    Order.updateStock(orderItems);
                    Order.addToDb(orderItems, user);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.userDashboard(user);

                } else if (hasBankDetailsSaved(user) && sameDetailsEntered(user)){
                    JOptionPane.showMessageDialog(null, "Processing Order");
                    Order.updateStock(orderItems);
                    Order.addToDb(orderItems, user);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.userDashboard(user);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid bank details.");
                }
            }
        });
        viewCurrentOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderEdit orderEdit = new OrderEdit(Checkout.this, user.getCurrentOrder(), user);
            }
        });
    }







    private boolean areBankDetailsFilledIn() {
        return  !cvv.getText().isEmpty() &&
                !cardName.getText().isEmpty() &&
                !cardNo.getText().isEmpty() &&
                !expiryDate.getText().isEmpty() &&
                !cardType.getText().isEmpty();
    }

    private boolean hasBankDetailsSaved(User user) {
        if (user.getBankDetails() == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean sameDetailsEntered(User user) {
         if (user.getBankDetails().getCardName().equals(cardName.getText()) && user.getBankDetails().getCvv() == Integer.parseInt(cvv.getText()) && user.getBankDetails().getExpiryDate().equals(expiryDate.getText()) && user.getBankDetails().getCardNo() == Long.parseLong(cardNo.getText()) && user.getBankDetails().getCardType().equals(cardType.getText())) {
                return true;
         } else {
                return false;
            }
    }


}

