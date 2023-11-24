package ui;

import items.*;
import items.Item.Gauge;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import user.Order;
import user.Order.Status;
import user.User;
import helper.ItemCreator;

public class Catalog extends JFrame {
    public JPanel rootPanel;
    private JScrollPane itemsList;
    private JTable rows;
    private JButton viewCurrentOrderButton;
    private List<Item> allItemsInOrder;
    private List<Item> currentOrderItems = new ArrayList<>();
    private Order currentOrder = new Order(1, Status.Pending, null, null);
    private List<Item> locomotiveCatalog = new ArrayList<>();
    private List<Item> controllerCatalog = new ArrayList<>();
    private List<Item> trackCatalog = new ArrayList<>();
    private List<Item> carriageCatalog = new ArrayList<>();
    private List<Item> trackPackCatalog = new ArrayList<>();
    private List<Item> trainSetCatalog = new ArrayList<>();

    public Catalog(ResultSet items, User user, String itemButtonPressed) throws SQLException {
        try {
            allItemsInOrder = new ArrayList<>();

            // Populate the table model with data
            while (items.next()) {
                Item item = ItemCreator.createItem(items.getString("productCode"), items);

                // Add the item to the appropriate catalog based on the type
                if (item != null) {
                    switch (items.getString("productCode").charAt(0)) {
                        case 'L':
                            locomotiveCatalog.add(item);
                            break;
                        case 'C':
                            controllerCatalog.add(item);
                            break;
                        case 'R':
                            trackCatalog.add(item);
                            break;
                        case 'S':
                            carriageCatalog.add(item);
                            break;
                        case 'P':
                            trackPackCatalog.add(item);
                            break;
                        case 'M':
                            trainSetCatalog.add(item);
                            break;
                    }

                }
            }
            Catalog catalog = new Catalog(items, user, "locomotive");
            viewCurrentOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OrderEdit orderEdit = new OrderEdit(Catalog.this, currentOrderItems, user);
                }
            });

            rows.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = rows.getSelectedRow();
                        if (selectedRow != -1) {
                            displayItemInformation(selectedRow, allItemsInOrder);
                        }
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    private void displayCatalog(List<Item> catalog) {
        DefaultTableModel tableModel = new DefaultTableModel();

        // Create columns based on ResultSet metadata
        tableModel.addColumn("Brand");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");

        // Populate the table model with items from the selected catalog
        for (Item item : catalog) {
            Object[] row = new Object[3];
            row[0] = item.getBrand();
            row[1] = item.getName();
            row[2] = item.getPrice();
            tableModel.addRow(row);
        }

        rows.setModel(tableModel);
        rows.setDefaultEditor(Object.class, null);
        itemsList.setViewportView(rows);
    }

    public void displayLocomotiveCatalog() {
        displayCatalog(locomotiveCatalog);
    }
    public void displayControllerCatalog() {
        displayCatalog(controllerCatalog);
    }
    public void displayRollingStockCatalog() {
        displayCatalog(carriageCatalog);
    }
    public void displayTrackCatalog() {
        displayCatalog(trackCatalog);
    }
    public void displayTrackPackCatalog() {
        displayCatalog(trackPackCatalog);
    }
    public void displayTrainsetCatalog() {
        displayCatalog(trainSetCatalog);
    }

    private void displayItemInformation(int rowIndex, List<Item> allItems) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        // Retrieve information about the selected item
        System.out.println(rowIndex);
        System.out.println(allItems.get(rowIndex));
        Item selectedItem = allItems.get(rowIndex);
        String productName = selectedItem.getName();
        String brand = selectedItem.getBrand();
        Double price = selectedItem.getPrice();

        panel.add(new JLabel("Item: " + productName));
        panel.add(new JLabel("Brand: " + brand));
        panel.add(new JLabel("Price: £" + price));


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

        // ActionListener for the Add to Bag button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrderItems.add(selectedItem);
                System.out.println(currentOrderItems);
                JOptionPane.showMessageDialog(null, "Item added to order!");
            }
        });

        // ActionListener for the Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // User chose not to add the item
                JOptionPane.showMessageDialog(null, "Cancelled.");
            }
        });

        // Show the option pane with the custom panel
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


