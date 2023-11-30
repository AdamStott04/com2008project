package items;

import database.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLine {

    private String productCode;
    private int quantity;
    private int lineID;
    private int orderID;


    public OrderLine(String productCode, int quantity, int lineID, int orderID) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.lineID = lineID;
        this.orderID = orderID;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return "OrderLine{" +
                "productCode='" + productCode + '\'' +
                ", quantity=" + quantity + '\'' +
                ", lineID=" + lineID + '\'' +
                ", orderID=" + orderID +
                '}';
    }

    public int getLineID() {
        return lineID;
    }

    public int getOrderID() {
        return orderID;
    }

    //Returns the stockCount of the order
    public static int loadOrderLineStock(int lineID) {
        try {
            Connection con = database.connect();
            //Get product code from order line
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM orderLines WHERE lineID = " + lineID + " LIMIT 1;");
            ResultSet orderLinesResults = preparedStatement.executeQuery();
            if (orderLinesResults.next()) {
                String productCode = orderLinesResults.getString("productCode");
                //Get stock count from product
                preparedStatement = con.prepareStatement(
                        "SELECT * FROM items WHERE productCode = ? LIMIT 1;");
                preparedStatement.setString(1, productCode);
                ResultSet itemsResults = preparedStatement.executeQuery();
                if (itemsResults.next()) {
                    return itemsResults.getInt("stockCount");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }
}
