package ui;

import App.App;
import items.Item;
import items.OrderLine;
import user.Order;
import user.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffPastOrders extends JFrame {
    public JPanel rootPanel;
    private JScrollPane scrollPane;
    private JTable ordersTable;
    private JButton backToDashboardButton;
    private JTable topOrderTable;
    private JScrollPane topOrderScroll;
    private JButton fulfillOrderButton;
    private JButton deleteOrderButton;
    private JLabel othersLabel;
    private User user;
    private String status;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableModel topOrderModel = new DefaultTableModel();
    private ArrayList<Order> orders;

    public StaffPastOrders(User user, String status) throws SQLException {
        this.user = user;
        this.status = status;
        orders = Order.loadAllPastOrders(status);

        // Create columns based on ResultSet metadata
        tableModel.addColumn("Order ID");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Status");
        topOrderModel.addColumn("Order ID");
        topOrderModel.addColumn("Order Date");
        topOrderModel.addColumn("Status");

        if (status.equals("Confirmed")) {
            int counter = 0;
            for (Order order : orders) {
                Object[] row = new Object[4];
                row[0] = order.getOrderID();
                row[1] = order.getOrderDate();
                row[2] = order.getStatus();
                if (counter == 0) {
                    topOrderModel.addRow(row);
                }
                else {
                    tableModel.addRow(row);
                }
                counter++;
            }
        } else {
            //Hide top order elements
            topOrderTable.setVisible(false);
            topOrderScroll.setVisible(false);
            fulfillOrderButton.setVisible(false);
            deleteOrderButton.setVisible(false);
            othersLabel.setVisible(false);

            for (Order order : orders) {
                Object[] row = new Object[3];
                row[0] = order.getOrderID();
                row[1] = order.getOrderDate();
                row[2] = order.getStatus();
                tableModel.addRow(row);
            }
        }

        topOrderTable.setModel(topOrderModel);
        topOrderTable.setDefaultEditor(Object.class, null);
        topOrderScroll.setViewportView(topOrderTable);

        ordersTable.setModel(tableModel);
        ordersTable.setDefaultEditor(Object.class, null);
        scrollPane.setViewportView(ordersTable);

        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                frame.dispose();
                App.staffDashboard(user);
            }
        });

        fulfillOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if there are orders available.
                if (orders.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No orders to fulfill.");
                    return;
                }

                //Get first and second orders
                Order topOrder = orders.get(0);
                Order sndOrder = null;
                //Check if there is more than one order confirmed.
                if (orders.size() >= 2) {
                    sndOrder = orders.get(1);
                }

                //Attempt to fulfill the top order
                if(Order.fulfill(topOrder)) {
                    // Remove the top order
                    topOrderModel.removeRow(0);
                    orders.remove(0);
                    // Notify the top order table that the data has changed
                    topOrderModel.fireTableRowsDeleted(0, 0);

                    //Get new top order - if needed
                    if (sndOrder != null) {
                        tableModel.removeRow(0);
                        Object[] row = new Object[3];
                        row[0] = sndOrder.getOrderID();
                        row[1] = sndOrder.getOrderDate();
                        row[2] = sndOrder.getStatus();
                        topOrderModel.addRow(row);
                        topOrderModel.fireTableRowsInserted(0, 0);
                        tableModel.fireTableRowsDeleted(0, 0);
                    }
                }
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (orders.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No orders to delete.");
                    return;
                }

                // Remove the top order
                topOrderModel.removeRow(0);
                Order topOrder = orders.get(0);
                Order sndOrder = orders.get(1);
                orders.remove(0);

                // Notify the top order table that the data has changed
                topOrderModel.fireTableRowsDeleted(0, 0);
                Order.delete(topOrder);

                //Get new top order
                tableModel.removeRow(0);
                Object[] row = new Object[3];
                row[0] = sndOrder.getOrderID();
                row[1] = sndOrder.getOrderDate();
                row[2] = sndOrder.getStatus();
                topOrderModel.addRow(row);
                topOrderModel.fireTableRowsInserted(0, 0);
                tableModel.fireTableRowsDeleted(0, 0);
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

        topOrderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = topOrderTable.getSelectedRow();
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

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Fulfill");
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                int clickedRow = ordersTable.convertRowIndexToModel(ordersTable.getEditingRow());
                if (clickedRow != -1) {
                    ordersTable.getCellEditor().stopCellEditing();
                    // Remove the row from the table model
                    tableModel.removeRow(clickedRow);
                    Order order = orders.get(clickedRow);
                    orders.remove(clickedRow);

                    System.out.println(order);
                    // Notify the table that the data has changed
                    tableModel.fireTableRowsDeleted(clickedRow, clickedRow);
                    Order.fulfill(order);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Fulfilling");
            return button;
        }
    }
}