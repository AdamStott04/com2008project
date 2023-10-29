public class Controller extends Item {
    enum Type {Analogue, DCC}

    private Type type;

    public Controller(Type type, String brand, String productName, int productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
        this.type = type;
    }

}
