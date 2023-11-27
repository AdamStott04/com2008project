package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import items.Carriage;
import items.Item;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.sql.*;
import App.*;

import items.OrderLine;
import user.User;

public class OrderEdit extends JDialog {
    private JTable orderItemsTable;
    private DefaultTableModel tableModel;
    private JButton removeButton;
    public List<OrderLine> orderItems;


    public OrderEdit(JFrame parent, ArrayList<OrderLine> orderItems, User user) {
        super(parent, "Edit Order", true);

        this.orderItems = orderItems;

        tableModel = new DefaultTableModel();
        orderItemsTable = new JTable(tableModel);

        // Add columns to the table
        tableModel.addColumn("Brand");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Remove");

        // Populate the table with order items

        for (OrderLine item : orderItems) {
            String[] details = App.getItemDetails(item.getProductCode());
            String brand = details[0];
            String name = details[1];
            Double price = Double.parseDouble(details[2]);
            Object[] row = new Object[]{brand, name, price, "Remove"};
            tableModel.addRow(row);
        }

        // Add a button column with a custom cell renderer
        orderItemsTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        orderItemsTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));




        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(topPanel, BorderLayout.NORTH);
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (orderItems.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please add at least one item to your basket");
                } else{
                    JFrame frame = new JFrame("Checkout");
                    frame.setContentPane(new ui.Checkout(orderItems, user).rootPanel);
                    frame.setSize(500, 300);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            }
        });

        checkoutButton.setPreferredSize(new Dimension(160, 60));

        topPanel.add(checkoutButton);

        // Set up the layout
        JScrollPane scrollPane = new JScrollPane(orderItemsTable);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Remove");
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
                int selectedRow = orderItemsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelIndex = orderItemsTable.convertRowIndexToModel(selectedRow);

                    // Remove the item from the orderItems list
                    orderItems.remove(modelIndex);

                    // Remove the row from the table model
                    tableModel.removeRow(modelIndex);

                    // Notify the table that the data has changed
                    fireTableDataChanged();
                }
            });
        }

        private void fireTableDataChanged() {
            // Notify the table that the data has changed
            orderItemsTable.tableChanged(new javax.swing.event.TableModelEvent(tableModel));
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Remove");
            return button;
        }






    }
}