package user;

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
}
