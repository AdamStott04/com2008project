package user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BankDetails {
    private int bankID;
    private int cardNo;
    private String cardName;
    private String expiryDate;
    private int cvv;

    public BankDetails(int bankID, int cardNo, String cardName, String expiryDate, int cvv) {
        this.bankID = bankID;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public int getCardNo() {
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


    public static ArrayList<BankDetails> bankDetails = new ArrayList<>();

    public static void createBankDetails(int bankID, int cardNo, String cardName, String expiryDate, int cvv) {
        BankDetails bank = new BankDetails(bankID, cardNo, cardName, expiryDate, cvv);
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

    public static boolean validBank(int cardNo, String expiryDate, int cvv) {
        if (String.valueOf(cardNo).length() == 12 && isValidExpiry(expiryDate) && String.valueOf(cvv).length() == 3) {
            return true;
        }
        return false;
    }

    public static BankDetails bankExists(int bankID) throws SQLException {
        for (BankDetails BankDetails : BankDetails.bankDetails) {
            if (BankDetails.bankID == bankID) {
                return BankDetails; // Found a matching bank account
            }
        }
        return null; // No matching bank account found
    }
}
