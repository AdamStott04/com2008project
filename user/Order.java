package user;

import database.database;
import items.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import App.*;

import items.Item;

public class Order {

    public enum Status {Pending, Confirmed, Fulfilled}

    private int orderID;
    private Status status;
    private LocalDateTime orderDate;
    private int userID;

    public Order(int orderID, Status status, LocalDateTime orderDate, int userID) {
        this.orderID = orderID;
        this.status = status;
        this.orderDate = LocalDateTime.now();
        this.userID = userID;
    }

    private int getOrderID () {
        return orderID;
    }
    private int getUserID () {
        return userID;
    }
    private LocalDateTime getOrderDate () {
        return orderDate;
    }
    private Status getStatus () {
        return status;
    }

    public static void addToDb (ArrayList<OrderLine> currentOrder, User user) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO orders (orderID, status, orderDate, userID) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setLong(1, App.loadOrders()+1);
            preparedStatement.setString(2, Status.Confirmed.name());
            preparedStatement.setString(3, LocalDateTime.now().toString());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (OrderLine item : currentOrder) {
            String productCode = item.getProductCode();
            int quantity = item.getQuantity();
            int lineID = App.loadOrderLines()+1;
            int orderID = App.loadOrders();

            try (Connection con = database.connect();
                 PreparedStatement preparedStatement = con.prepareStatement(
                         "INSERT INTO orderLines (productCode, quantity, lineID, orderID) VALUES (?, ?, ?, ?);")) {
                preparedStatement.setString(1, productCode);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setInt(3, lineID);
                preparedStatement.setInt(4, orderID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
