package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checkout {
    public JPanel rootPanel;
    private JTextField bankID;
    private JTextField cvv;
    private JTextField cardName;
    private JTextField cardNo;
    private JTextField expiryDate;
    private JButton checkoutButton;

    public Checkout() {
        bankID = new JTextField();
        cvv = new JTextField();
        cardName = new JTextField();
        cardNo = new JTextField();
        expiryDate = new JTextField();

        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayout(7, 2));

        rootPanel.add(new JLabel("Bank ID:"));
        rootPanel.add(bankID);
        rootPanel.add(new JLabel("CVV:"));
        rootPanel.add(cvv);
        rootPanel.add(new JLabel("Card Name:"));
        rootPanel.add(cardName);
        rootPanel.add(new JLabel("Card Number:"));
        rootPanel.add(cardNo);
        rootPanel.add(new JLabel("Expiry Date:"));
        rootPanel.add(expiryDate);

        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all bank details are filled in
                if (areBankDetailsFilledIn()) {
                    JOptionPane.showMessageDialog(null, "Checkout Complete!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all bank details.");
                }
            }
        });
        rootPanel.add(checkoutButton);
    }

    private boolean areBankDetailsFilledIn() {
        // Check if all bank details are filled in (add your logic here)
        return !bankID.getText().isEmpty() &&
                !cvv.getText().isEmpty() &&
                !cardName.getText().isEmpty() &&
                !cardNo.getText().isEmpty() &&
                !expiryDate.getText().isEmpty();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkout");
        frame.setContentPane(new Checkout().rootPanel);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

