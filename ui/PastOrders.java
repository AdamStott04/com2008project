package ui;

import App.App;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import user.Order;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PastOrders extends JFrame {

    public JPanel rootPanel;
    private JTable ordersTable;
    private JScrollPane scrollPane;
    private JButton backToDashboardButton;

    public PastOrders (User user) throws SQLException {
        ArrayList<Order> orders = App.loadPastOrders(user);
        DefaultTableModel tableModel = new DefaultTableModel();

        // Create columns based on ResultSet metadata
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Status");
        for (Order order : orders) {
            Object[] row = new Object[2]; // Three columns: brand, productName, price
            row[0] = order.getOrderDate();
            row[1] = order.getStatus();
            tableModel.addRow(row);
        }
        ordersTable.setModel(tableModel);
        ordersTable.setDefaultEditor(Object.class, null);
        scrollPane.setViewportView(ordersTable);

        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.userDashboard(user);
            }
        });
    }

}
