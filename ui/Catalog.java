package ui;

import database.database;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Catalog extends JFrame {
    public JPanel rootPanel;
    private JScrollPane itemsList;
    private JTable rows;

    public Catalog(ResultSet items) throws SQLException {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();

            // Create columns based on ResultSet metadata
            for (int i = 1; i <= items.getMetaData().getColumnCount(); i++) {
                tableModel.addColumn(items.getMetaData().getColumnName(i));
            }

            // Populate the table model with data
            while (items.next()) {
                Object[] row = new Object[items.getMetaData().getColumnCount()];
                for (int i = 1; i <= items.getMetaData().getColumnCount(); i++) {
                    if (items.getObject(i) == null)
                        row[i - 1] = " ";
                    else
                        row[i - 1] = items.getObject(i);
                }
                tableModel.addRow(row);
            }

            rows.setModel(tableModel);
            itemsList.setViewportView(rows);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }
}


