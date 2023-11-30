package items;

import java.util.Arrays;
import java.util.regex.Pattern;

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

    public Gauge getGauge() {
        return gauge;
    }


    public static boolean validTrack(String gaugeString, String brand, String productName,
                                        String productCode, String price, Integer stockCount) {

        try {
            Double parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return false;
        }

        try {
            Gauge gauge = Gauge.valueOf(gaugeString.toUpperCase());
            if (gauge == null || !Arrays.asList(Gauge.OO, Gauge.TT, Gauge.N).contains(gauge)) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        String productCodePattern = "R\\d{3,5}";
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

        return true;

    }







}
