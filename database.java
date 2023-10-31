import java.sql.*;
import java.util.*;

public class database {
    public static void main(String[] args) throws SQLException {
        System.out.println("\nDrivers loaded as properties:");
        System.out.println(System.getProperty("jdbc.drivers"));
        System.out.println("\nDrivers loaded by DriverManager:");
        Enumeration<Driver> list = DriverManager.getDrivers();
        while (list.hasMoreElements())
            System.out.println(list.nextElement());

    }
    public static void databaseSQL(String statement) throws SQLException {

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team025", "team025", "uChahgh6z");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        Statement stmt = null;


        try {
            stmt = con.createStatement();
            int count = stmt.executeUpdate(statement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null)
                stmt.close();
        }

        con.close();
    }
}
