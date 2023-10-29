public class Carriage extends Item {

    private int era;
    private Gauge gauge;

    public Carriage(int era, Gauge gauge, String brand, String productName, int productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.era = era;
        this.gauge = gauge;
    }
}