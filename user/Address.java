package user;

import database.database;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Address {
    private int houseNumber;
    private String streetName;
    private String postcode;
    private String country;

    public Address(int houseNumber, String streetName, String postcode, String country) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.postcode = postcode;
        this.country = country;
    }

    public int getHouseNo() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public static ArrayList<Address> addresses = new ArrayList<>();

    public static void createAddress(int houseNumber, String postcode, String streetName, String country) throws SQLException {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO addresses VALUES (?, ?, ?, ?);")) {
            preparedStatement.setInt(1, houseNumber);
            preparedStatement.setString(2, postcode);
            preparedStatement.setString(3, streetName);
            preparedStatement.setString(4, country);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

    }
        Address address = new Address(houseNumber, streetName, postcode, country);
        addresses.add(address);
    }

    public static Address validAddress(int houseNumber, String postcode) {
        for (Address address : Address.addresses) {
            if (address.houseNumber == houseNumber && address.postcode.equals(postcode)) {
                return address; // Found a matching address
            }
        }
        return null; // No matching address found
    }

    public static void updateAddress(int oldHouseNo, String oldPostcode, String newStreet, String newCountry) throws SQLException {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "UPDATE addresses SET street = ?, country = ? " +
                     "WHERE houseNo = ? AND postcode = ?")) {
            preparedStatement.setString(1, newStreet);
            preparedStatement.setString(2, newCountry);
            preparedStatement.setInt(3, oldHouseNo);
            preparedStatement.setString(4, oldPostcode);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
