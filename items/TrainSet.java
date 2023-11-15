package items;

public class TrainSet extends Item {
    private Gauge gauge;
    private String era;

    public TrainSet(Gauge gauge, String era, String brand, String productName,
                      String productCode, Double price, Integer stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.gauge = gauge;
        this.era = era;
    }
    public String toString() {
        return "TrainSet{" +
                "gauge=" + gauge +
                ", era='" + era + '\'' +
                ", brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", description='" + description + '\'' +
                '}';
    }



}
