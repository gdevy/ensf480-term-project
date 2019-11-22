package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DatabaseController {


    public static void main(String[] args) throws SQLException {


        Connection dbConnection = null;
        try {
            switch (args.length) {
                case 0:
                    dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/code/src/sqlite/db.db",
                            "root", "");
                    break;
                case 2:
                    dbConnection = DriverManager.getConnection("jdbc:sqlite:" + args[0],
                            args[1], "");
                    break;
                case 3:
                    dbConnection = DriverManager.getConnection("jdbc:sqlite:" + args[0],
                            args[1], args[2]);
                    break;
                default:
                    System.err.println("Expected 0, 2 or  3 args. path to db, username, password(optional");
                    return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Going to do drop all the tables. Press enter to continue ctrl+d to stop.");
        new Scanner(System.in).nextLine();

        System.err.println("drop");

        Statement statement = dbConnection.createStatement();
        List<String> tableNames = new ArrayList<>(Arrays.asList("property_status", "quadrant", "property_type", "properties", "landlords", "managers", "registered_renters"));
        for (String tableName : tableNames) {
            try {
                statement.executeUpdate("DROP TABLE " + tableName);
            } catch (Exception e) {
                System.err.println(tableName + " doesnt exist yet.");
            }
        }

        statement.executeUpdate("CREATE TABLE property_status\n" +
                "(\n" +
                "    status_id INT  NOT NULL,\n" +
                "    status    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (status_id)\n" +
                ");");

        statement.executeUpdate("CREATE TABLE quadrant\n" +
                "(\n" +
                "    quadrant_id INT  NOT NULL,\n" +
                "    quadrant    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (quadrant_id)\n" +
                ");");

        statement.executeUpdate("CREATE TABLE property_type\n" +
                "(\n" +
                "    property_type_id INT  NOT NULL,\n" +
                "    property_type    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (property_type_id)\n" +
                ");");

        statement.executeUpdate("CREATE TABLE properties\n" +
                "(\n" +
                "    property_id     int                                        NOT NULL,\n" +
                "    address         TEXT                                       NOT NULL,\n" +
                "    quadrant        INT REFERENCES quadrant (quadrant_id)      NOT NULL,\n" +
                "    property_status INT REFERENCES property_status (status_id) NOT NULL,\n" +
                "    property_type   INT                                        NOT NULL,\n" +
                "    bathrooms       INT                                        NOT NULL,\n" +
                "    bedrooms        INT                                        NOT NULL,\n" +
                "    furnished       BOOLEAN                                    NOT NULL,\n" +
                "    square_footage  REAL                                       NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (property_id)\n" +
                ");\n");

        statement.executeUpdate("CREATE TABLE landlords\n" +
                "(\n" +
                "    landlord_id int  NOT NULL,\n" +
                "    name        CHAR NOT NULL,\n" +
                "    username    CHAR NOT NULL,\n" +
                "    password    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (landlord_id)\n" +
                ");\n");

        statement.executeUpdate("CREATE TABLE managers\n" +
                "(\n" +
                "    manager_id int  NOT NULL,\n" +
                "    name       CHAR NOT NULL,\n" +
                "    username   CHAR NOT NULL,\n" +
                "    password   CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (manager_id)\n" +
                ");");

        statement.executeUpdate("CREATE TABLE registered_renters\n" +
                "(\n" +
                "    renter_id int  NOT NULL,\n" +
                "    name      CHAR NOT NULL,\n" +
                "    username  CHAR NOT NULL,\n" +
                "    password  CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (renter_id)\n" +
                ");");

        //populate enum tables
        statement.executeUpdate("BEGIN TRANSACTION;\n" +
                "INSERT INTO property_status VALUES (1, 'AVAILABLE');\n" +
                "INSERT INTO property_status VALUES (2, 'SUSPENDED');\n" +
                "INSERT INTO property_status VALUES (3, 'RENTED');\n" +
                "INSERT INTO property_status VALUES (4, 'REMOVED');\n" +
                "\n" +
                "INSERT INTO quadrant VALUES (1, 'SW');\n" +
                "INSERT INTO quadrant VALUES (2, 'NW');\n" +
                "INSERT INTO quadrant VALUES (3, 'NE');\n" +
                "INSERT INTO quadrant VALUES (4, 'SE');\n" +
                "\n" +
                "INSERT INTO property_type VALUES (1, 'HOUSE');\n" +
                "INSERT INTO property_type VALUES (2, 'DUPLEX');\n" +
                "INSERT INTO property_type VALUES (3, 'TOWNHOUSE');\n" +
                "INSERT INTO property_type VALUES (4, 'APARTMENT');\n" +
                "INSERT INTO property_type VALUES (5, 'CONDO');\n" +
                "INSERT INTO property_type VALUES (6, 'MAINFLOOR');\n" +
                "INSERT INTO property_type VALUES (7, 'BASEMENT');\n" +
                "COMMIT;");

    }
}
