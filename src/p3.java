import java.sql.*;
import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter USERID: ");
        String USERID = scanner.nextLine();

        System.out.print("Enter PASSWORD: ");
        String PASSWORD = scanner.nextLine();

        System.out.print("Enter CONNECTIONSTRING: ");
        String CONNECTIONSTRING = scanner.nextLine(); // jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl

        System.out.println("-------Oracle JDBC Connection Testing ---------");
        try {
            // Register the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            // create the connection string
            connection = DriverManager.getConnection(
                    CONNECTIONSTRING, USERID, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Connected!");

        System.out.println("Choose a menu item: \n1 - Report Location Information\n2 - Report Edge Information\n3 - Report CS Staff Information\n4 - Insert New Phone Extension");
        String menuItem = scanner.nextLine();
        switch (menuItem) {
            case "1":
                locations(connection);
                break;
            case "2":
                edges(connection);
                break;
            case "3":
                csstaff(connection);
                break;
            case "4":
                insertPhone(connection);
                break;
            default:
                System.out.println("Syntax: java p3 <username> <password> <connection string> <menu item> \n \nInclude the following number of the menu item as your fourth parameter:\n1 - Report Location Information\n2 - Report Edge Information\n3 - Report CS Staff Information\n4 - Insert New Phone Extension");
                break;
        }
    }

    public static void locations(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Location ID: ");
            String locationID = scanner.nextLine();

            Statement stmt = connection.createStatement();
            String str = "SELECT * FROM LOCATIONS";
            ResultSet rset = stmt.executeQuery(str);

            String locID = "";
            String locationName = "";
            String locationType = "";
            int xCoord = 0;
            int yCoord = 0;
            String floor = "";
            // Process the results
            while (rset.next()) {
                locID = rset.getString("LOCATIONID");
                locationName = rset.getString("LOCATIONNAME");
                locationType = rset.getString("LOCATIONTYPE");
                xCoord = rset.getInt("XCOORD");
                yCoord = rset.getInt("YCOORD");
                floor = rset.getString("MAPFLOOR");
                System.out.println("Location Information\nLocation ID: " + locID + "\nLocation Name: " + locationName + "\nLocation Type: " + locationType + "\nX-Coordinate: " + xCoord + "\nY-Coordinate: " + yCoord + "\nFloor: " + floor);
            } // end while

            rset.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Get Data Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }

    public static void edges(Connection connection) {

    }

    public static void csstaff(Connection connection) {

    }

    public static void insertPhone(Connection connection) {

    }
}
