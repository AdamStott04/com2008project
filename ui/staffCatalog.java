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
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class staffCatalog extends JFrame {
    public JPanel rootPanel;
    private JScrollPane scrollPane;
    private JTable itemsTable;
    private JButton backButton;
    private List<Item> allItemsInOrder;
    public staffCatalog(ResultSet items, User user) throws SQLException {
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
                row[2] = items.getDouble("price");

                tableModel.addRow(row);
                if (items.getString("productCode").charAt(0) == 'L'){
                    Locomotive locomotive = new Locomotive(Item.Gauge.valueOf(items.getString("gauge")),
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
                    Track newTrack = new Track(Item.Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(newTrack);
                }
                else if (items.getString("productCode").charAt(0) == 'S') {
                    Carriage carriage = new Carriage(items.getString("era"),
                            Item.Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(carriage);
                }
                else if (items.getString("productCode").charAt(0) == 'P') {
                    TrackPack trackpack = new TrackPack(Item.Gauge.valueOf(items.getString("gauge")),
                            items.getString("brand"), items.getString("productName"),
                            items.getString("productCode"), items.getDouble("price"),
                            items.getInt("stockCount"), items.getString("description"));
                    allItemsInOrder.add(trackpack);
                }
                else if (items.getString("productCode").charAt(0) == 'M'){
                    TrainSet trainset = new TrainSet(Item.Gauge.valueOf(items.getString("gauge")),
                            items.getString("era"), items.getString("brand"),
                            items.getString("productName"), items.getString("productCode"),
                            items.getDouble("price"), items.getInt("stockCount"),
                            items.getString("description"));
                    allItemsInOrder.add(trainset);
                }
            }

            itemsTable.setModel(tableModel);
            itemsTable.setDefaultEditor(Object.class, null);
            scrollPane.setViewportView(itemsTable);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
                    frame.dispose();
                    App.staffDashboard(user);
                }
            });

            itemsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = itemsTable.getSelectedRow();
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

        // Check the type of the selected item and cast it accordingly
        if (selectedItem instanceof Locomotive) {
            Locomotive locomotive = (Locomotive) selectedItem;
            Item.Gauge gauge = locomotive.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = locomotive.getEra();
            panel.add(new JLabel("Era: " + era));
        }
        else if (selectedItem instanceof Track) {
            Track track = (Track) selectedItem;
            Item.Gauge gauge = track.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
        }
        else if (selectedItem instanceof Carriage) {
            Carriage carriage = (Carriage) selectedItem;
            Item.Gauge gauge = carriage.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String era = carriage.getEra();
            panel.add(new JLabel("Era: " + era));
        }
        else if (selectedItem instanceof TrackPack) {
            TrackPack trackPack = (TrackPack) selectedItem;
            Item.Gauge gauge = trackPack.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String description = trackPack.getDescription();
            panel.add(new JLabel("Description: " + description));
        }
        else if (selectedItem instanceof TrainSet) {
            TrainSet trainSet = (TrainSet) selectedItem;
            Item.Gauge gauge = trainSet.getGauge();
            panel.add(new JLabel("Gauge: " + gauge));
            String description = trainSet.getDescription();
            panel.add(new JLabel("Description: " + description));
        }

        panel.add(new JLabel("Current stock level: " + stockCount));


        JLabel newStockLabel = new JLabel("New stock level:");
        panel.add(newStockLabel);

        // Create a JTextField for user entry
        JTextField newStockField = new JTextField();
        panel.add(newStockField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String newStockString = newStockField.getText();
            String productCode = selectedItem.getProductCode();
            try {
                int newStock = Integer.parseInt(newStockString);
                if (newStock > 0) { // stock cant be negative
                    Item.setStock(productCode, newStock);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer for new stock count.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(submitButton);

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

        panel.add(cancelButton);

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

