package items;
public class OrderLine<T extends Item> {

    private T item;
    private int quantity;

    public OrderLine(T item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String toString() {
        return "OrderLine{" +
                "item='" + item + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
