package ui;

import App.App;
import items.*;
import user.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import user.Order;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.util.List;

public class UserPastOrders extends JFrame {

    public JPanel rootPanel;
    private JTable ordersTable;
    private JScrollPane scrollPane;
    private JButton backToDashboardButton;

    public UserPastOrders(User user) throws SQLException {
        ArrayList<Order> orders = Order.loadUserPastOrders(user);
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
        ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = ordersTable.getSelectedRow();
                    if (selectedRow != -1) {
                        displayOrderInformation(selectedRow, orders);
                    }
                }
            }
        });

    }
    private void displayOrderInformation(int rowIndex, List<Order> orders) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        // Retrieve information about the selected item
        System.out.println(rowIndex);
        System.out.println(orders.get(rowIndex).toString());
        Order selectedOrder = orders.get(rowIndex);
        ArrayList<OrderLine> orderLines = Order.loadOrderLines(selectedOrder.getOrderID());
        System.out.println(orderLines);
        int count = 1;
        for (OrderLine line : orderLines) {
            String productCode = line.getProductCode();
            String[] itemDetails = Item.getItemDetails(productCode);
            String brand = itemDetails[0];
            String productName = itemDetails[1];
            String price = itemDetails[2];
            int quantity = line.getQuantity();
            panel.add(new JLabel("<html><b>Item " + count + ":</b></html>"));
            panel.add(new JLabel("Brand: " + brand));
            panel.add(new JLabel("Name : " + productName));
            panel.add(new JLabel("Price: " + price));
            panel.add(new JLabel("Quantity: " + quantity));
            count += 1;
        }
        JOptionPane.showMessageDialog(this, panel, "Order Information", JOptionPane.INFORMATION_MESSAGE);
    }

}
