package ui;

import javax.swing.*;

import items.Item;
import user.User;
import App.App;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadTrainsets(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        trackPacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadTrackPacks(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        locomotivesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadLocomotives(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadCarriages(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadTrack(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        controllersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                try {
                    App.showStaffCatalog(Item.loadControllers(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
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
