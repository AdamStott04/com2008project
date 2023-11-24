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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                App.userDashboard(user);
            }
        });
    }
}
