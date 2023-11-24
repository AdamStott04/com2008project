package ui;

import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import user.User;

public class Categories {
    private JButton rollingStockButton;
    private JButton controllersButton;
    private JButton locomotivesButton;
    private JButton trackButton;
    private JButton trackPacksButton;
    private JButton trainsetsButton;
    private String itemButtonPressed;

    public Categories(User user) {
        locomotivesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "locomotive";
                    catalog.displayLocomotiveCatalog();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackPacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "trackPack";
                    App.showCatalog(App.loadItems(), user, itemButtonPressed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        controllersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "controller";
                    App.showCatalog(App.loadItems(), user, itemButtonPressed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trainsetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "trainset";
                    App.showCatalog(App.loadItems(), user, itemButtonPressed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "rollingStock";
                    App.showCatalog(App.loadItems(), user, itemButtonPressed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    itemButtonPressed = "trackButton";
                    App.showCatalog(App.loadItems(), user, itemButtonPressed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
