package ui;

import App.App;
import user.Address;
import user.BankDetails;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class userDashboard {
    public JPanel rootPanel;
    private JPanel buttons;
    private JButton catalogButton;
    private JButton editAccountDetailsButton;
    private JButton logoutButton;
    private JButton pastOrdersButton;
    private JButton staffDashboardButton;
    private JLabel welcomeLabel;

    public userDashboard(User user, JFrame frame) {

        // Hide staff dashboard button if user is not staff
        if (user.getIsStaff() == 0) {
            staffDashboardButton.setVisible(false);
        }

        // Creates a welcome message using user's forename
        welcomeLabel.setText("Hi there, " + user.getForename());

        // If catalog button is pressed, show catalog
        catalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.showCategories(user);
            }
        });
        // If past orders button is pressed, show past orders
        pastOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showUserPastOrders(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // If edit account details button is pressed, show edit account details page
        editAccountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Address.addresses.clear();
                User.users.clear();
                BankDetails.bankDetails.clear();
                App.loadFromDb();
                frame.dispose();
                App.userDetails(user);

            }
        });
        // If staff dashboard button is pressed, show staff dashboard
        staffDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.staffDashboard(user);
            }
        });
        // If logout button is pressed, logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.login();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
