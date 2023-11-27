package items;

import database.database;

import java.sql.*;
import java.util.ArrayList;

public class Item {
    public enum Gauge {OO, TT, N}

    public String brand;
    public String productName;
    public String productCode;
    public double price;
    public int stockCount;
    public String description;

    public Item(String brand, String productName, String productCode, double price, int stockCount, String description) {
        this.brand = brand;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockCount = stockCount;
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStockCount() { return stockCount; }

    public String getProductCode() { return productCode; }

    public static String[] getItemDetails(String productCode) {
        ResultSet resultItem = null;
        String[] details = new String[3];
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "SELECT * FROM items WHERE productCode = ?"
             )) {
            preparedStatement.setString(1, productCode);
            resultItem = preparedStatement.executeQuery();
            if (resultItem.next()) {
                details[0] = resultItem.getString("brand");
                details[1] = resultItem.getString("productName");
                details[2] = "" + resultItem.getDouble("price");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return details;
    }
    public static ResultSet loadTrainsets() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'M%';";
        ResultSet trainsetSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            trainsetSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return trainsetSet;
    }
    public static ResultSet loadTrackPacks() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'P%';";
        ResultSet trackPackSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            trackPackSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return trackPackSet;
    }
    public static ResultSet loadCarriages() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'S%';";
        ResultSet carriageSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            carriageSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return carriageSet;
    }
    public static ResultSet loadTrack() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'R%';";
        ResultSet trackSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            trackSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return trackSet;
    }
    public static ResultSet loadControllers() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'C%';";
        ResultSet controllerSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            controllerSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return controllerSet;
    }
    public static ResultSet loadLocomotives() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT * FROM items WHERE productCode LIKE 'L%';";
        ResultSet locomotiveSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);

            locomotiveSet = preparedStatement.executeQuery();;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return locomotiveSet;
    }
    public static void setStock (String productCode, int newStock) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "UPDATE items SET stockCount = ? WHERE productCode = ?;")) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, productCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
