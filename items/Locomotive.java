package items;

public class Locomotive extends Item {
    private Gauge gauge;
    private String era;

    public Locomotive(Gauge gauge, String era, String brand, String productName,
                      String productCode, Double price, Integer stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.gauge = gauge;
        this.era = era;
    }
    public String toString() {
        return "Locomotive{" +
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

    public Gauge getGauge() {
        return gauge;
    }
    public String getEra() {
        return era;
    }



}
