package database;

import helper.passwordHash;

import java.sql.*;
import java.util.Enumeration;

public class database {

    public static String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team025";
    public static String username = "team025";
    public static String password = "uChahgh6z";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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

        String basicAddress = "INSERT INTO addresses VALUES (1, '11111', 'Basic Street', 'UK')";
        String addStaff = "INSERT INTO users (userID, forename, surname, email, password, houseNo, postcode, isStaff, isManager) VALUES (1, 'Staff', 'Staff' 'staff@staffmail.com', " + passwordHash.hashPassword("password") + "1, '11111', 1, 0)";
        String addManager = "INSERT INTO users (forename, surname, email, password, houseNo, postcode, isStaff, isManager) VALUES ('Manager', 'Manager' 'manager@managermail.com', " + passwordHash.hashPassword("password") + "1, '11111', 1, 1)";
        String firstController = "INSERT INTO items VALUES ('C001', 'Hornby', 'Standard Controller', 9.99, 10, 'standard controller', NULL, NULL)";
        String secondController = "INSERT INTO items VALUES ('C002', 'Dapol', 'Super Controller', 19.99, 10, 'super controller', NULL, NULL)";
        String firstLocomotive = "INSERT INTO items VALUES ('L001', 'Hornby', 'Class A4 Mallard', 39.99, 5, 'description', 4, 'N')";
        String secondLocomotive = "INSERT INTO items VALUES ('L002', 'Bachmann', 'Class 5MT Black Five', 49.99, 5, 'description', 11, 'TT')";
        String firstTrainSet = "INSERT INTO items VALUES ('M001', 'Hornby', 'EuroStar Trainset', 139.99, 25, 'description', 10, 'OO')";
        String secondTrainSet = "INSERT INTO items VALUES ('M002', 'Hornby', 'Flying Scotsman Trainset', 125.99, 5, 'description', 8, 'OO')";
        String firstTrackPack = "INSERT INTO items VALUES ('P001', 'Dapol', '2nd Radius Starter Oval', 25.99, 5, 'description', NULL, 'TT')";
        String secondTrackPack = "INSERT INTO items VALUES ('P002', 'Bachmann', '3rd Radius Starter Oval', 26.99, 5, 'description', NULL, 'O')";
        String firstTrack = "INSERT INTO items VALUES ('R001', 'Dapol', 'Single Straight', 10.99, 5, 'description', NULL, 'OO')";
        String secondTrack = "INSERT INTO items VALUES ('R002', 'Dapol', 'Double Straight', 12.99, 53, 'description', NULL, 'N')";
        String firstWagon = "INSERT INTO items VALUES ('S001', 'Dapol', 'Cattle Wagon', 15, 73, 'description', 8, 'OO')";
        String secondWagon = "INSERT INTO items VALUES ('S002', 'Dapol', 'LNER Open First', 19.99, 5, 'description', 9, 'OO')";


        PreparedStatement address = con.prepareStatement(addresses);
        PreparedStatement bank = con.prepareStatement(bankDetails);
        PreparedStatement item = con.prepareStatement(items);
        PreparedStatement orderLine = con.prepareStatement(orderLines);
        PreparedStatement order = con.prepareStatement(orders);
        PreparedStatement user = con.prepareStatement(users);
        PreparedStatement addAddress = con.prepareStatement(basicAddress);
        PreparedStatement addStaffMember = con.prepareStatement(addStaff);
        PreparedStatement addManagerMember = con.prepareStatement(addManager);
        PreparedStatement controller1 = con.prepareStatement(firstController);
        PreparedStatement controller2 = con.prepareStatement(secondController);
        PreparedStatement locomotive1 = con.prepareStatement(firstLocomotive);
        PreparedStatement locomotive2 = con.prepareStatement(secondLocomotive);
        PreparedStatement trainset1 = con.prepareStatement(firstTrainSet);
        PreparedStatement trainset2 = con.prepareStatement(secondTrainSet);
        PreparedStatement trackpack1 = con.prepareStatement(firstTrackPack);
        PreparedStatement trackpack2 = con.prepareStatement(secondTrackPack);
        PreparedStatement track1 = con.prepareStatement(firstTrack);
        PreparedStatement track2 = con.prepareStatement(secondTrack);
        PreparedStatement wagon1 = con.prepareStatement(firstWagon);
        PreparedStatement wagon2 = con.prepareStatement(secondWagon);

        address.executeUpdate();
        bank.executeUpdate();
        item.executeUpdate();
        order.executeUpdate();
        orderLine.executeUpdate();
        user.executeUpdate();
        addAddress.executeUpdate();
        addStaffMember.executeUpdate();
        addManagerMember.executeUpdate();
        controller1.executeUpdate();
        controller2.executeUpdate();
        locomotive1.executeUpdate();
        locomotive2.executeUpdate();
        trainset2.executeUpdate();
        trainset1.executeUpdate();
        trackpack1.executeUpdate();
        trackpack2.executeUpdate();
        track1.executeUpdate();
        track2.executeUpdate();
        wagon1.executeUpdate();
        wagon2.executeUpdate();


        con.close();
    }
}
