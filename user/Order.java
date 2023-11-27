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
            preparedStatement.setLong(1, Order.loadOrdersCount()+1);
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
            int lineID = Order.loadOrderLinesCount()+1;
            int orderID = Order.loadOrdersCount();

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

}
