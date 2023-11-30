package items;


import java.util.Arrays;
import java.util.regex.Pattern;
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

    public Gauge getGauge() {
        return gauge;
    }



    public static boolean validTrainSet(String gaugeString, String era, String brand, String productName,
                                        String productCode, String price, Integer stockCount, String description) {



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
        String productCodePattern = "M\\d{3,5}";
        boolean isProductCodeValid = Pattern.matches(productCodePattern, productCode);

        if (!isProductCodeValid) {

            return false;
        }

        if (stockCount == null || stockCount <= 0) {
            return false;
        }

        try {
            int eraValue = Integer.parseInt(era);
            if (eraValue < 1 || eraValue > 11) {
                return false;
            }
        } catch (NumberFormatException e) {
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
