package items;

public class Track extends Item {
    private Gauge gauge;

    public Track(Gauge gauge, String brand, String productName, String productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.gauge = gauge;
    }

    public String toString() {
        return "Track{" +
                "gauge=" + gauge +
                ", brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", description='" + description + '\'' +
                '}';
    }

}
