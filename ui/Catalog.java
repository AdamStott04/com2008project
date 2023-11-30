package ui;

import items.*;
import items.Item.Gauge;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import user.Order;
import user.Order.Status;
import user.User;
import App.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Catalog extends JFrame {
    public JPanel rootPanel;
    private JScrollPane itemsList;
    private JTable rows;
    private JButton viewCurrentOrderButton;
    private JButton backButton;
    private List<Item> allItemsInOrder;

    public Catalog(ResultSet items, User user) throws SQLException {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();

            // Create columns based on ResultSet metadata
            tableModel.addColumn("Brand");
            tableModel.addColumn("Name");
            tableModel.addColumn("Price");

            allItemsInOrder = new ArrayList<>();

            // Populate the table model with data
            while (items.next()) {
                Object[] row = new Object[3]; // Three columns: brand, productName, price
                row[0] = items.getString("brand");
                row[1] = items.getString("productName");
                row[2] = "£" + items.getDouble("price");



                tableModel.addRow(row);
                if (items.getString("productCode").charAt(0) == 'L'){
                    Locomotive locomotive = new Locomotive(Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description"));
                    allItemsInOrder.add(locomotive);
                }
                else if (items.getString("productCode").charAt(0) == 'C') {
                    Controller controller = new Controller(items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(controller);
                }
                else if (items.getString("productCode").charAt(0) == 'R') {
                    Track newTrack = new Track(Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(newTrack);
                }
                else if (items.getString("productCode").charAt(0) == 'S') {
                    Carriage carriage = new Carriage(items.getString("era"),
                            Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(carriage);
                }
                else if (items.getString("productCode").charAt(0) == 'P') {
                    TrackPack trackpack = new TrackPack(Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(trackpack);
                }
                else if (items.getString("productCode").charAt(0) == 'M'){
                    TrainSet trainset = new TrainSet(Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description"));
                    allItemsInOrder.add(trainset);
                }
            }


            rows.setModel(tableModel);
            rows.setDefaultEditor(Object.class, null);
            itemsList.setViewportView(rows);

            viewCurrentOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    OrderEdit orderEdit = new OrderEdit(Catalog.this, user.getCurrentOrder(), user);
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.showCategories(user);
                }
            });

            rows.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = rows.getSelectedRow();
                        if (selectedRow != -1) {
                            displayItemInformation(selectedRow, allItemsInOrder, user);
                        }
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }



    private void displayItemInformation(int rowIndex, List<Item> allItems, User user) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        // Retrieve information about the selected item
        Item selectedItem = allItems.get(rowIndex);
        String productName = selectedItem.getName();
        String brand = selectedItem.getBrand();
        Double price = selectedItem.getPrice();
        int stockCount = selectedItem.getStockCount();

        panel.add(new JLabel("Item: " + productName));
        panel.add(new JLabel("Brand: " + brand));
        panel.add(new JLabel("Price: £" + price));
        panel.add(new JLabel("Stock: " + stockCount));


        // Check the type of the selected item and cast it accordingly
        if (selectedItem instanceof Locomotive) {
            Locomotive locomotive = (Locomotive) selectedItem;
            Gauge gauge = locomotive.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = locomotive.getEra();
            panel.add(new JLabel("Era: " + era));
        }
        else if (selectedItem instanceof Track) {
            Track track = (Track) selectedItem;
            Gauge gauge = track.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
        }
        else if (selectedItem instanceof Carriage) {
            Carriage carriage = (Carriage) selectedItem;
            Gauge gauge = carriage.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = carriage.getEra();
            panel.add(new JLabel("Era: " + era));
        }
        else if (selectedItem instanceof TrackPack) {
            TrackPack trackPack = (TrackPack) selectedItem;
            Gauge gauge = trackPack.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String description = trackPack.getDescription();
            panel.add(new JLabel("Description: " + description));
        }
        else if (selectedItem instanceof TrainSet) {
            TrainSet trainSet = (TrainSet) selectedItem;
            Gauge gauge = trainSet.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String description = trainSet.getDescription();
            panel.add(new JLabel("Description: " + description));
        }
        // Add buttons for user interaction
        Integer[] quantityOptions = new Integer[stockCount]; //make sure you cant select more than there is stock

        for (int i = 0; i < stockCount; i++) {
            quantityOptions[i] = i + 1;
        }

        JComboBox<Integer> quantityComboBox = new JComboBox<>(quantityOptions);
        quantityComboBox.setSelectedItem(1); //default is 1
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityComboBox);

        JButton addButton = new JButton("Add to Order");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // User chose not to add the item
                JOptionPane.showMessageDialog(null, "Cancelled.");

                // Close the JOptionPane
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        panel.add(addButton);
        panel.add(cancelButton);

        // ActionListener for the Add to Order button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lineID = 1;
                if (stockCount == 0) {
                    JOptionPane.showMessageDialog(null, "Item out of stock!");
                    dispose();
                } else {
                    int selectedQuantity = (Integer) quantityComboBox.getSelectedItem();
                    if (user.getCurrentOrder() != null) {
                        lineID = user.getCurrentOrder().size();
                    }
                    OrderLine newLine = new OrderLine(selectedItem.productCode, selectedQuantity, lineID, 1);
                    user.addToCurrentOrder(newLine);
                    JOptionPane.showMessageDialog(null, "Item added to order!");
                    dispose();
                }

            }
        });

        // ActionListener for the Cancel button

        int result = JOptionPane.showOptionDialog(
                this,
                panel,
                "Item Information",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{},
                null);
    }
}