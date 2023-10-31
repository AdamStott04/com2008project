package user;

import items.OrderLine;

import java.time.LocalDateTime;

public class Order {

    enum Status {Pending, Confirmed, Fulfilled}

    private int orderID;
    private Status status;
    private LocalDateTime orderDate;
    private OrderLine[] lines;

    public Order(int orderID, Status status, OrderLine[] lines) {
        this.orderID = orderID;
        this.status = status;
        this.orderDate = LocalDateTime.now();
        this.lines = lines;
    }

}
