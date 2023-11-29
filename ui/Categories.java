package ui;

import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;

import items.Item;
import user.User;

public class Categories extends JFrame {
    private JButton rollingStockButton;
    private JButton controllersButton;
    private JButton locomotivesButton;
    private JButton trackButton;
    private JButton trackPacksButton;
    private JButton trainsetsButton;

    public JPanel rootPanel;
    private JButton backButton;
    private JButton viewCurrentOrderButton;

    public Categories(User user) {
        viewCurrentOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                OrderEdit orderEdit = new OrderEdit(Categories.this, user.getCurrentOrder(), user);
            }
        });
        locomotivesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadLocomotives(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackPacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadTrackPacks(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        controllersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadControllers(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trainsetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadTrainsets(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadCarriages(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCatalog(Item.loadTrack(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.userDashboard(user);
            }
        });
    }
}