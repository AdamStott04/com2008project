package items;
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

    public int getLineID() { return lineID; }
    public int getOrderID() { return orderID; }
}
