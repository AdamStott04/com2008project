package user;

import items.OrderLine;

import java.time.LocalDateTime;

public class Order {

    public enum Status {Pending, Confirmed, Fulfilled}

    private int orderID;
    private Status status;
    private LocalDateTime orderDate;
    private String userID;

    public Order(int orderID, Status status, LocalDateTime orderDate, String userID) {
        this.orderID = orderID;
        this.status = status;
        this.orderDate = LocalDateTime.now();
        this.userID = userID;
    }

}
