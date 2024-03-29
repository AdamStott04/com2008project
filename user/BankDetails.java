package user;

import database.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BankDetails {
    private String cardType;
    private int bankID;
    private long cardNo;
    private String cardName;
    private String expiryDate;
    private int cvv;

    // Constructor for BankDetails
    public BankDetails(int bankID, long cardNo, String cardName, String expiryDate, int cvv, String cardType) {
        this.bankID = bankID;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
    }

    // Getters for BankDetails
    public long getCardNo() {
        return cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCardName() {
        return cardName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCardType() {
        return cardType;
    }

    // Array of all BankDetails
    public static ArrayList<BankDetails> bankDetails = new ArrayList<>();

    // Method to reload the BankDetails array from the database
    public static void reloadBankDetailsArray() throws SQLException {
        bankDetails.clear();
        Connection con = null;
        try {
            con = DriverManager.getConnection(database.url, database.username, database.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        String sql = "SELECT * FROM bankDetails";
        ResultSet bankDetailsSet = null;
        PreparedStatement preparedStatement = null;
        preparedStatement = con.prepareStatement(sql);
        bankDetailsSet = preparedStatement.executeQuery();
        while (bankDetailsSet.next()) {
            long cardNum = bankDetailsSet.getLong("cardNo");
            String cardName = bankDetailsSet.getString("cardName");
            String expiryDate = bankDetailsSet.getString("expiryDate");
            int bankID = bankDetailsSet.getInt("bankID");
            int cvv = bankDetailsSet.getInt("cvv");
            String cardType = bankDetailsSet.getString("cardType");
            BankDetails.bankDetails.add(new BankDetails(bankID, cardNum, cardName, expiryDate, cvv, cardType));
        }
    }

    public static void createBankDetails(int bankID, long cardNo, String cardName, String expiryDate, int cvv, String cardType) {
        BankDetails bank = new BankDetails(bankID, cardNo, cardName, expiryDate, cvv, cardType);
        bankDetails.add(bank);
    }

    // Method to check if a string is a valid expiry date
    public static boolean isValidExpiry(String input) {
        // Define a regular expression pattern for "xx/xx" where 'x' is a digit, and the first two digits represent a valid month (1-12)
        String pattern = "(0[1-9]|1[0-2])/\\d{2}";

        // Create a Pattern object with the pattern
        Pattern regexPattern = Pattern.compile(pattern);

        // Create a Matcher object and apply the pattern to the input string
        Matcher matcher = regexPattern.matcher(input);

        // Check if the input matches the pattern
        return matcher.matches();
    }

    // Method to check if a card number, expiry date, cvv and card type are valid
    public static boolean validBank(long cardNo, String expiryDate, int cvv, String cardType) {
        return String.valueOf(cardNo).length() == 16 && isValidExpiry(expiryDate) && String.valueOf(cvv).length() == 3 && (cardType.equals("Visa") || cardType.equals("Mastercard"));
    }

    // Method to check if bank details exist for a given bankID and if so returns the bank details
    public static BankDetails bankExists(int bankID) {
        for (BankDetails BankDetails : BankDetails.bankDetails) {
            if (BankDetails.bankID == bankID) {
                return BankDetails; // Found a matching bank account
            }
        }
        return null; // No matching bank account found
    }

    // Method to check a card number, card name, expiry date and cvv and if they exist in the database together, returns the bankID
    public static int findBankID(long cardNo, String cardName, String expiryDate, int cvv) {
        ResultSet result = null;
        int bankId = 0;
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "SELECT bankID FROM bankDetails WHERE cardNo = ? AND cardName = ? AND expiryDate = ? AND cvv = ?;")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);

            result = preparedStatement.executeQuery();
            if (result.next()) {
                bankId = result.getInt("bankID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankId;
    }

    // Method to add a new bank account to the database
    public static void addNewBankDetails(long cardNo, String cardName, String expiryDate, int cvv, String cardType) {
        int ID = 0;
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "INSERT INTO bankDetails (cardNo, cardName, expiryDate, cvv, cardType) VALUES (?, ?, ?, ?, ?);")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);
            preparedStatement.setString(5, cardType);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BankDetails bank = new BankDetails(ID, cardNo, cardName, expiryDate, cvv, cardType);
        bankDetails.add(bank);
    }

    // Method to update bank details in the database
    public static void updateBankDetails(int bankID, long cardNo, String cardName, String expiryDate, int cvv, String cardType) throws SQLException {
        try (Connection con = database.connect();
             PreparedStatement preparedStatement = con.prepareStatement(
                     "UPDATE bankDetails SET cardNo = ?, cardName = ?, expiryDate = ?, cvv = ?, cardType = ? " +
                             "WHERE bankID = ?")) {
            preparedStatement.setLong(1, cardNo);
            preparedStatement.setString(2, cardName);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setInt(4, cvv);
            preparedStatement.setString(5, cardType);
            preparedStatement.setInt(6, bankID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadBankDetailsArray();
    }
}
