package items;
public class Item {
    public enum Gauge {OO, TT, N}

    public String brand;
    public String productName;
    public String productCode;
    public double price;
    public int stockCount;
    public String description;

    public Item(String brand, String productName, String productCode, double price, int stockNumber, String description) {
        this.brand = brand;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockCount = stockNumber;
        this.description = description;
    }

}
