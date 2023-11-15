package items;

public class Carriage extends Item {

    private String era;
    private Gauge gauge;

    public Carriage(String era, Gauge gauge, String brand, String productName, String productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.era = era;
        this.gauge = gauge;
    }

    public String toString() {
        return "Carriage{" +
                "era='" + era + '\'' +
                ", gauge=" + gauge +
                ", brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", description='" + description + '\'' +
                '}';
    }

    public Gauge getGauge() {
        return gauge;
    }
    public String getEra() {
        return era;
    }


}