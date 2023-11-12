package ui;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Catalog {
    private JPanel itemList;

    public static void getAllItems(Connection con) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM items";
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("<=================== GET ALL ITEMS ====================>");
            while (resultSet.next()) {
                // Print each item's information in the specified format
                System.out.println("{" +
                        "productCode='" + resultSet.getString("productCode") + "'" +
                        ", brand='" + resultSet.getString("brand") + "'" +
                        ", productName='" + resultSet.getString("productName") + "'" +
                        ", price='" + resultSet.getFloat("price") + "'" +
                        ", stockCount='" + resultSet.getInt("stockCount") + "'" +
                        ", description='" + resultSet.getString("price") + "'" +
                        ", era='" + resultSet.getString("era") + "'" +
                        ", gauge='" + resultSet.getString("gauge") + "'" +
                        "}");
            }
            System.out.println("<======================================================>");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }
}
