import java.sql.*;
import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter with syntax: java p3 <username> <password> <connection string> <menu item>");
        String userInput = scanner.nextLine();
        String[] tokens = userInput.split(" ");

        String USERID = tokens[2];

        String PASSWORD = tokens[3];

        String CONNECTIONSTRING = tokens[4]; // jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl
//        java p3 ajkraemling MelonFighter1*1 jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl 2
        String menuItem = tokens[5];

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

            String query = "SELECT * FROM LOCATIONS WHERE LOCATIONID = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, locationID);
            ResultSet rset = pstmt.executeQuery();

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
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: Could not find location ID");
            e.printStackTrace();
            return;
        }
    }

    public static void edges(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Edge ID: ");
            String edgeIN = scanner.nextLine();

            String query = "SELECT EDGES.EDGEID, L1.LOCATIONNAME AS StartLocationName, L1.MAPFLOOR AS StartLocationFloor, L2.LOCATIONNAME AS EndLocationName, L2.MAPFLOOR AS EndLocationFloor " +
                                "FROM EDGES " +
                                "JOIN LOCATIONS L1 ON L1.LOCATIONID = EDGES.STARTINGLOCATIONID " +
                                "JOIN LOCATIONS L2 ON L2.LOCATIONID = EDGES.ENDINGLOCATIONID " +
                                "WHERE EDGES.EDGEID = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, edgeIN);
            ResultSet rset = pstmt.executeQuery();

            String edgeID = "";
            String stLocationName = "";
            String stMapFloor = "";
            String endLocationName = "";
            String endMapFloor = "";
            // Process the results
            while (rset.next()) {
                edgeID = rset.getString("EDGEID");
                stLocationName = rset.getString("StartLocationName");
                stMapFloor = rset.getString("StartLocationFloor");
                endLocationName = rset.getString("EndLocationName");
                endMapFloor = rset.getString("EndLocationFloor");
                System.out.println("Edges Information\nEdge ID: " + edgeID + "\nStarting Location Name: " + stLocationName + "\nStarting Location Floor: " + stMapFloor + "\nEnding Location Name: " + endLocationName + "\nEnding Location Floor: " + endMapFloor);
            } // end while

            rset.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: Could not find edge ID");
            e.printStackTrace();
            return;
        }
    }

    public static void csstaff(Connection connection) {

    }

    public static void insertPhone(Connection connection) {

    }
}
