package database;

import java.sql.*;
import java.util.Enumeration;

public class database {

    public static String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team025";
    public static String username = "team025";
    public static String password = "uChahgh6z";
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
            ResultSet count = stmt.executeQuery("SELECT * FROM users WHERE email = 'admin@adminmail.com' AND password = 'admin123';");
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
