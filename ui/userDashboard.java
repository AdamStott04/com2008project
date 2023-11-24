package ui;

import App.App;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class userDashboard {
    public JPanel rootPanel;
    private JPanel buttons;
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


        pastOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editAccountDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.userDetails(user);
            }
        });
        staffDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
