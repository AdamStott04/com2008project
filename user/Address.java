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

    public static void createAddress(int houseNumber, String streetName, String postcode, String country) throws SQLException {
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

    public static Address validAddress(int houseNumber, String postcode) throws SQLException {
        for (Address address : Address.addresses) {
            if (address.houseNumber == houseNumber && address.postcode.equals(postcode)) {
                return address; // Found a matching user
            }
        }
        return null; // No matching user found
    }

    public Address updateAddress(int oldHouseNo, String oldPostcode, int newHouseNo, String newStreet, String newPostcode, String newCountry) throws SQLException {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "UPDATE addresses SET houseNo = ?, postcode = ?, street = ?, country = ? " +
                     "WHERE houseNo = ? AND postcode = ?")) {
            preparedStatement.setInt(1, newHouseNo);
            preparedStatement.setString(2, newPostcode);
            preparedStatement.setString(3, newStreet);
            preparedStatement.setString(4, newCountry);
            preparedStatement.setInt(5, oldHouseNo);
            preparedStatement.setString(6, oldPostcode);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return validAddress(newHouseNo, newPostcode);
    }
}
