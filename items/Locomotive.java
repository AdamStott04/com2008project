package items;

public class Locomotive extends Item {
    enum Type {Analogue, DCCReady, DCCFitted, DCCSound}

    private Gauge gauge;
    private Integer era;
    private Type type;
    private String classNumber;

    public Locomotive(Gauge gauge, Integer era, Type type, String classNumber, String brand, String productName,
                      Integer productCode, Double price, Integer stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.gauge = gauge;
        this.era = era;
        this.type = type;
        this.classNumber = classNumber;
    }
}
