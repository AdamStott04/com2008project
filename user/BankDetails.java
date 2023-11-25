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

    public BankDetails(int bankID, long cardNo, String cardName, String expiryDate, int cvv, String cardType) {
        this.bankID = bankID;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
    }

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

    public String getCardType() { return cardType; }


    public static ArrayList<BankDetails> bankDetails = new ArrayList<>();

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

    public static boolean validBank(long cardNo, String expiryDate, int cvv) {
        return String.valueOf(cardNo).length() == 16 && isValidExpiry(expiryDate) && String.valueOf(cvv).length() == 3;
    }

    public static BankDetails bankExists(int bankID) throws SQLException {
        for (BankDetails BankDetails : BankDetails.bankDetails) {
            if (BankDetails.bankID == bankID) {
                return BankDetails; // Found a matching bank account
            }
        }
        return null; // No matching bank account found
    }

    public static int findBankID(long cardNo, String cardName, String expiryDate, int cvv) throws SQLException {
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
    public static void addNewBankDetails(long cardNo, String cardName, String expiryDate, int cvv, String cardType) throws SQLException {
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
