package items;
public class Item {
    public enum Gauge {OO, TT, N}

    public String brand;
    public String productName;
    public String productCode;
    public double price;
    public int stockCount;
    public String description;

    public Item(String brand, String productName, String productCode, double price, int stockCount, String description) {
        this.brand = brand;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockCount = stockCount;
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStockCount() { return stockCount; }
}
