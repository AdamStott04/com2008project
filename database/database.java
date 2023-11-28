package database;

import java.sql.*;
import java.util.Enumeration;

public class database {

    public static String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team025";
    public static String username = "team025";
    public static String password = "uChahgh6z";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Drivers loaded as properties:");
        System.out.println(System.getProperty("jdbc.drivers"));
        System.out.println("Drivers loaded by DriverManager:");
        Enumeration<Driver> list = DriverManager.getDrivers();
        while (list.hasMoreElements())
            System.out.println(list.nextElement());

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team025", "team025", "uChahgh6z");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Commands to create the full database structure.
        String addresses = "CREATE TABLE `addresses` ("
                + "`houseNo` int NOT NULL,"
                + "`postcode` varchar(10) NOT NULL,"
                + "`street` varchar(45) NOT NULL,"
                + "`country` varchar(45) NOT NULL,"
                + "PRIMARY KEY (`houseNo`,`postcode`)"
                + ");";

        String bankDetails = "CREATE TABLE `bankDetails` ("
                + "`bankID` int NOT NULL AUTO_INCREMENT,"
                + "`cardNo` varchar(16) NOT NULL,"
                + "`cardName` varchar(45) NOT NULL,"
                + "`expiryDate` varchar(5) NOT NULL,"
                + "`cvv` int NOT NULL,"
                + "`cardType` varchar(25) NOT NULL,"
                + "PRIMARY KEY (`bankID`)"
                + ");";

        String items = "CREATE TABLE `items` ("
                + "`productCode` varchar(6) NOT NULL UNIQUE,"
                + "`brand` varchar(45) NOT NULL,"
                + "`productName` varchar(45) NOT NULL,"
                + "`price` float NOT NULL,"
                + "`stockCount` int NOT NULL,"
                + "`description` varchar(255) DEFAULT NULL,"
                + "`era` varchar(45) DEFAULT NULL,"
                + "`gauge` varchar(45) DEFAULT NULL,"
                + "PRIMARY KEY (`productCode`)"
                + ");";

        String orderLines = "CREATE TABLE `orderLines` ("
                + "`lineID` int NOT NULL AUTO_INCREMENT,"
                + "`productCode` varchar(6) NOT NULL,"
                + "`quantity` int NOT NULL,"
                + "`orderID` int NOT NULL,"
                + "PRIMARY KEY (`lineID`),"
                + "KEY `orderID_idx` (`orderID`),"
                + "KEY `productCode_idx` (`productCode`),"
                + "CONSTRAINT `orderID` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),"
                + "CONSTRAINT `productCode` FOREIGN KEY (`productCode`) REFERENCES `items` (`productCode`)"
                + ");";

        String orders = "CREATE TABLE `orders` ("
                + "`orderID` int NOT NULL AUTO_INCREMENT,"
                + "`status` varchar(45) NOT NULL,"
                + "'orderDate` date NOT NULL,"
                + "`userID` int NOT NULL,"
                + "PRIMARY KEY (`orderID`),"
                + "KEY `userID` (`userID`),"
                + "CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)"
                + ");";

        String users = "CREATE TABLE `users` ("
                + "`userID` int NOT NULL AUTO_INCREMENT,"
                + "`forename` varchar(45) NOT NULL,"
                + "`surname` varchar(45) NOT NULL,"
                + "`email` varchar(255) NOT NULL,"
                + "`password` varchar(255) NOT NULL,"
                + "`houseNo` int NOT NULL,"
                + "`postcode` varchar(15) NOT NULL,"
                + "`isStaff` tinyint NOT NULL,"
                + "`isManager` tinyint NOT NULL,"
                + "`bankID` int DEFAULT NULL,"
                + "PRIMARY KEY (`userID`),"
                + "UNIQUE KEY `userID_UNIQUE` (`userID`),"
                + "UNIQUE KEY `email_UNIQUE` (`email`),"
                + "KEY `houseNo` (`houseNo`,`postcode`),"
                + "KEY `bankID` (`bankID`),"
                + "CONSTRAINT `users_ibfk_1` FOREIGN KEY (`houseNo`, `postcode`) REFERENCES `addresses` (`houseNo`, `postcode`),"
                + "CONSTRAINT `users_ibfk_2` FOREIGN KEY (`bankID`) REFERENCES `bankDetails` (`bankID`)"
                + ")";

        PreparedStatement address = con.prepareStatement(addresses);
        PreparedStatement bank = con.prepareStatement(bankDetails);
        PreparedStatement item = con.prepareStatement(items);
        PreparedStatement orderLine = con.prepareStatement(orderLines);
        PreparedStatement order = con.prepareStatement(orders);
        PreparedStatement user = con.prepareStatement(users);

        address.executeUpdate();
        bank.executeUpdate();
        item.executeUpdate();
        order.executeUpdate();
        orderLine.executeUpdate();
        user.executeUpdate();



        con.close();
    }
}
