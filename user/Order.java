package user;

import database.database;
import items.OrderLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    /*public void addToDb (Order order) {
        int orderID = order.getOrderID();


        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO orders (cardNo, cardName, expiryDate, cvv) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BankDetails bank = new BankDetails(ID, cardNo, cardName, expiryDate, cvv);
        bankDetails.add(bank);
    }*/

}
