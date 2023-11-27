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

        if (user.getIsStaff() == 0) {
            staffDashboardButton.setVisible(false);
        }

        welcomeLabel.setText("Hi there, " + user.getForename());

        catalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCategories(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        pastOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showPastOrders(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
        staffDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.staffDashboard(user);
            }
        });
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
