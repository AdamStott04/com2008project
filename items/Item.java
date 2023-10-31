package items;
public class Item {
    public enum Gauge {OO, TT, N}

    private String brand;
    private String productName;
    private int productCode;
    private double price;
    private int stockCount;
    private String description;

    public Item(String brand, String productName, int productCode, double price, int stockNumber, String description) {
        this.brand = brand;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockCount = stockNumber;
        this.description = description;
    }

}
