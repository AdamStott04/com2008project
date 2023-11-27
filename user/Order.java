package user;

import database.database;
import items.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import App.*;
import java.sql.*;

import items.Item;

public class Order {


    public enum Status {Pending, Confirmed, Fulfilled}

    private int orderID;
    private Status status;
    private Date orderDate;
    private int userID;

    public Order(int orderID, Status status, Date orderDate, int userID) {
        this.orderID = orderID;
        this.status = status;
        this.orderDate = orderDate;
        this.userID = userID;
    }

    public int getOrderID () {
        return orderID;
    }
    public int getUserID () {
        return userID;
    }
    public Date getOrderDate () {
        return orderDate;
    }
    public Status getStatus () {
        return status;
    }

    public static void addToDb (ArrayList<OrderLine> currentOrder, User user) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO orders (orderID, status, orderDate, userID) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setLong(1, App.loadOrders()+1);
            preparedStatement.setString(2, Status.Confirmed.name());
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
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

    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", userID=" + userID + '\'' +
                ", orderDate=" + orderDate + '\'' +
                ", status=" + status +
                '}';
    }
    public static void updateStock (ArrayList<OrderLine> orderItems) {
        for (OrderLine item : orderItems) {
            try (Connection con = database.connect();
                 PreparedStatement preparedStatement = con.prepareStatement(
                         "UPDATE items SET stockCount = stockCount - ? WHERE productCode = ?;")) {
                preparedStatement.setLong(1, item.getQuantity());
                preparedStatement.setString(2, item.getProductCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
