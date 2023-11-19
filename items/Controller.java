package items;

public class Controller extends Item {


    public Controller(String brand, String productName, String productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
    }

    public String toString() {
        return "Controller{" +
                "brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", description='" + description + '\'' +
                '}';
    }



}
