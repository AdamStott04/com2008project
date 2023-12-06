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
import java.util.List;

import items.Item;

import javax.swing.*;

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

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public static void addToDb(ArrayList<OrderLine> currentOrder, User user) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO orders (orderID, status, orderDate, userID) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setInt(1, Order.loadOrdersCount() + 1);
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
            int lineID = Order.loadOrderLinesCount() + 1;
            int orderID = Order.loadOrdersCount();

            try (Connection con = database.connect();
                 PreparedStatement preparedStatement = con.prepareStatement(
                         "INSERT INTO orderLines (lineID, productCode, quantity, orderID) VALUES (?, ?, ?, ?);")) {
                preparedStatement.setInt(1, lineID);
                preparedStatement.setString(2, productCode);
                preparedStatement.setInt(3, quantity);
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

    public static void updateStock(Order order) {
        ArrayList<OrderLine> orderItems = Order.loadOrderLines(order.getOrderID());
        for (OrderLine item : orderItems) {
            try (Connection con = database.connect();
                 PreparedStatement preparedStatement = con.prepareStatement(
                         "UPDATE items SET stockCount = stockCount - ? WHERE productCode = ?;")) {
                preparedStatement.setInt(1, item.getQuantity());
                preparedStatement.setString(2, item.getProductCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int loadOrdersCount() {
        Connection con = null;
        int ordersCount = 0;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT COUNT(*) FROM orders;";
        ResultSet orderSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            orderSet = preparedStatement.executeQuery();
            orderSet.next();

            ordersCount = orderSet.getInt(1);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return ordersCount;
    }

    public static int loadOrderLinesCount() {
        Connection con = null;
        int ordersCount = 0;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Create the sql to gather all user data from sql table.
        String sql = "SELECT COUNT(*) FROM orderLines;";
        ResultSet orderSet = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            orderSet = preparedStatement.executeQuery();
            orderSet.next();

            ordersCount = orderSet.getInt(1);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return ordersCount;
    }

    public static ArrayList<OrderLine> loadOrderLines(int orderID) {
        ArrayList<OrderLine> orderLines = new ArrayList<>();

        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM orderLines WHERE orderID = ?")) {
            preparedStatement.setInt(1, orderID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderLine orderLine = new OrderLine(resultSet.getString("productCode"), resultSet.getInt("quantity"), resultSet.getInt("lineID"), orderID);

                    orderLines.add(orderLine);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orderLines;
    }

    public static ArrayList<Order> loadUserPastOrders(User user) {
        ArrayList<Order> orders = new ArrayList<>();
        int userID = user.getId();

        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM orders WHERE userID = ?")) {
            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(resultSet.getInt("orderID"), Order.Status.valueOf(resultSet.getString("status")), resultSet.getDate("orderDate"), user.getId());

                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    public static ArrayList<Order> loadAllPastOrders(String status) {
        ArrayList<Order> orders = new ArrayList<>();

        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM orders WHERE status = ?")) {
            preparedStatement.setString(1, status);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(resultSet.getInt("orderID"), Order.Status.valueOf(resultSet.getString("status")), resultSet.getDate("orderDate"), resultSet.getInt("userID"));
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return orders;
    }

    //Tries to fulfill and order - returns true if successful.
    public static boolean fulfill(Order order) {
        boolean allInStock = true;
        //Check if order items are in stock
        ArrayList<OrderLine> orderLines = Order.loadOrderLines(order.getOrderID());
        for (OrderLine line : orderLines) {
            int stockCount = OrderLine.loadOrderLineStock(line.getLineID());
            if (!(stockCount >= line.getQuantity())) {
                //An item is not in stock
                allInStock = false;
            }
        }

        //If all items are found to be in stock - fulfill order
        if (allInStock) {
            try (Connection con = database.connect();
                 PreparedStatement preparedStatement = con.prepareStatement(
                         "UPDATE orders SET status = ? WHERE orderID = ?;")) {
                preparedStatement.setString(1, "Fulfilled");
                preparedStatement.setInt(2, order.getOrderID());
                preparedStatement.executeUpdate();
                Order.updateStock(order);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "An item in this order is out of stock.");
            return false;
        }
    }

    public static void delete(Order order) {
        try {
            Connection con = database.connect();
            //Delete order lines
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM orderLines WHERE orderID = " + order.getOrderID() + ";");
            preparedStatement.executeUpdate();
            //Delete order
            preparedStatement = con.prepareStatement(
                    "DELETE FROM orders WHERE orderID = " + order.getOrderID() + ";");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
