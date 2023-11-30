package items;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Controller extends Item {


    public Controller(String brand, String productName, String productCode, double price, int stockCount, String description) {
        super(brand, productName, productCode, price, stockCount, description);
    }

    public String toString() {
        return "Controller{" +
                "brand='" + brand + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                ", stockCount=" + stockCount +
                ", description='" + description + '\'' +
                '}';
    }



    public static boolean validController(String brand, String productName,
                                        String productCode, String price, Integer stockCount, String description) {


        try {
            Double parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return false;
        }







        String productCodePattern = "C\\d{3,5}";
        boolean isProductCodeValid = Pattern.matches(productCodePattern, productCode);

        if (!isProductCodeValid) {

            return false;
        }

        if (stockCount == null || stockCount <= 0) {
            return false;
        }


        if (productName != null && productName.length() > 25) {
            return false;
        }

        if (brand != null && brand.length() > 25) {
            return false;
        }

        if (description != null && description.length() > 25) {
            return false;
        }


        return true;

    }



}
