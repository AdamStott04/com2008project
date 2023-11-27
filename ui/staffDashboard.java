package ui;

import javax.swing.*;
import user.User;
import App.App;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class staffDashboard {
    public JPanel rootPanel;
    private JButton trainSetsButton;
    private JButton trackPacksButton;
    private JButton locomotivesButton;
    private JButton rollingStockButton;
    private JButton trackButton;
    private JButton controllersButton;
    private JButton managerViewButton;
    private JButton pendingOrdersButton;
    private JButton fulfilledOrdersButton;
    private JButton backButton;

    public staffDashboard(User user, JFrame frame) {
        if (user.getIsManager() == 0) {
            managerViewButton.setVisible(false);
        }

        trainSetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        trackPacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        locomotivesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        controllersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.managerDashboard(user);
            }
        });

        pendingOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        fulfilledOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.userDashboard(user);
            }
        });
    }
}
