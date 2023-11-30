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
    private JButton confirmedOrdersButton;
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
                    App.showStaffCatalog(Item.loadTrainsets(), user, "train set");
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
                    App.showStaffCatalog(Item.loadTrackPacks(), user, "track pack");
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
                    App.showStaffCatalog(Item.loadLocomotives(), user, "locomotive");
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
                    App.showStaffCatalog(Item.loadCarriages(), user, "rolling stock");
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
                    App.showStaffCatalog(Item.loadTrack(), user, "track");
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
                    App.showStaffCatalog(Item.loadControllers(), user, "controller");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.managerDashboard(user);
            }
        });

        confirmedOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose();
                    App.showAllPastOrders(user, "Confirmed");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        fulfilledOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose();
                    App.showAllPastOrders(user, "Fulfilled");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
