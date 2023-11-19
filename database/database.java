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
        System.out.println("\nDrivers loaded as properties:");
        System.out.println(System.getProperty("jdbc.drivers"));
        System.out.println("\nDrivers loaded by DriverManager:");
        Enumeration<Driver> list = DriverManager.getDrivers();
        while (list.hasMoreElements())
            System.out.println(list.nextElement());

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team025", "team025", "uChahgh6z");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Statement stmt = null;


        try {
            stmt = con.createStatement();
            int count = stmt.executeUpdate("INSERT INTO users (userID, forename, surname, email, password, houseNo, postcode, cardNo, isStaff, isManager) VALUES (1, 'Admin', 'Admin', 'admin@adminmail.com', 'admin123', NULL, NULL, NULL, 1, 1);");
            System.out.println(count);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }

        con.close();
    }
}
