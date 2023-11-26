package ui;

import App.App;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PastOrders {

    public JPanel rootPanel;
    private JTable ordersTable;
    private JScrollPane scrollPane;

    public PastOrders (User user) throws SQLException {
        try (ResultSet orders = App.loadPastOrders(user)){
            DefaultTableModel tableModel = new DefaultTableModel();

            // Create columns based on ResultSet metadata
            tableModel.addColumn("Order Date");
            tableModel.addColumn("Status");
            System.out.println(orders);
            while (orders.next()) {
                Object[] row = new Object[2]; // Three columns: brand, productName, price
                row[0] = orders.getDate("orderDate");
                row[1] = orders.getString("status");
                tableModel.addRow(row);
            }
            ordersTable.setModel(tableModel);
            ordersTable.setDefaultEditor(Object.class, null);
            scrollPane.setViewportView(ordersTable);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
