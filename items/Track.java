public class Track extends Item {
    private Gauge gauge;

    public Track(Gauge gauge, String brand, String productName, int productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.gauge = gauge;
    }

}
