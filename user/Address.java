package user;

import database.database;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Address {
    private int houseNumber;
    private String streetName;
    private String postcode;
    private String city;

    // Constructor
    public Address(int houseNumber, String streetName, String postcode, String city) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.postcode = postcode;
        this.city = city;
    }

    // Getters
    public int getHouseNo() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    // Array for the addresses
    public static ArrayList<Address> addresses = new ArrayList<>();

    // Create address instance in db
    public static void createAddress(int houseNumber, String postcode, String streetName, String city) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO addresses VALUES (?, ?, ?, ?);")) {
            preparedStatement.setInt(1, houseNumber);
            preparedStatement.setString(2, postcode);
            preparedStatement.setString(3, streetName);
            preparedStatement.setString(4, city);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        Address address = new Address(houseNumber, streetName, postcode, city);
        addresses.add(address);
    }

    // Check if address is valid given a house number and postcode and returns the address object if a match is found.
    public static Address validAddress(int houseNumber, String postcode) {
        for (Address address : Address.addresses) {
            if (address.houseNumber == houseNumber && address.postcode.equals(postcode)) {
                return address; // Found a matching address
            }
        }
        return null; // No matching address found
    }

    // Update address in db
    public static void updateAddress(int oldHouseNo, String oldPostcode, String newStreet, String newCity) {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "UPDATE addresses SET street = ?, city = ? " +
                             "WHERE houseNo = ? AND postcode = ?")) {
            preparedStatement.setString(1, newStreet);
            preparedStatement.setString(2, newCity);
            preparedStatement.setInt(3, oldHouseNo);
            preparedStatement.setString(4, oldPostcode);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
