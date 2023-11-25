package helper;

import items.*;
import items.Item.Gauge;
import java.sql.*;

public class ItemCreator {
    public static Item createItem(String itemCode, ResultSet resultSet) {
        try {
            switch (itemCode.charAt(0)) {
                case 'L':
                    return createLocomotive(resultSet);
                case 'C':
                    return createController(resultSet);
                case 'R':
                    return createTrack(resultSet);
                case 'S':
                    return createCarriage(resultSet);
                case 'P':
                    return createTrackPack(resultSet);
                case 'M':
                    return createTrainSet(resultSet);
                default:
                    throw new IllegalArgumentException("Unknown item code: " + itemCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or rethrow as needed
            return null;
        }
    }

    private static Locomotive createLocomotive(ResultSet resultSet) throws SQLException {
        return new Locomotive(
                Gauge.valueOf(resultSet.getString("gauge")),
                resultSet.getString("era"),
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }

    private static Controller createController(ResultSet resultSet) throws SQLException {
        return new Controller(
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }

    private static Track createTrack(ResultSet resultSet) throws SQLException {
        return new Track(
                Gauge.valueOf(resultSet.getString("gauge")),
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }

    private static Carriage createCarriage(ResultSet resultSet) throws SQLException {
        return new Carriage(
                resultSet.getString("era"),
                Gauge.valueOf(resultSet.getString("gauge")),
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }

    private static TrackPack createTrackPack(ResultSet resultSet) throws SQLException {
        return new TrackPack(
                Gauge.valueOf(resultSet.getString("gauge")),
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }

    private static TrainSet createTrainSet(ResultSet resultSet) throws SQLException {
        return new TrainSet(
                Gauge.valueOf(resultSet.getString("gauge")),
                resultSet.getString("era"),
                resultSet.getString("brand"),
                resultSet.getString("productName"),
                resultSet.getString("productCode"),
                resultSet.getDouble("price"),
                resultSet.getInt("stockCount"),
                resultSet.getString("description")
        );
    }
}