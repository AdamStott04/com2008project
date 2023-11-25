package ui;

import App.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;

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

    public Categories(User user) {
        locomotivesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadLocomotives(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackPacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadTrackPacks(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        controllersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadControllers(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trainsetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadTrainsets(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadCarriages(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    App.showCatalog(App.loadTrack(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.userDashboard(user);
            }
        });
    }
}