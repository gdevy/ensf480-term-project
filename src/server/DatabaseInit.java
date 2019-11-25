package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DatabaseInit {

    public static void main(String[] args) throws SQLException {

        Connection dbConnection = null;
        try {
            switch (args.length) {
                case 0:
                    dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/ensf480-term-project/src/sqlite/db.db",
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



        Statement statement = dbConnection.createStatement();
        List<String> tableNames = new ArrayList<>(Arrays.asList("property_status", "quadrant", "property_type", "furnishing",
                "properties", "landlords", "managers", "registered_renters", "saved_search_criteria", "search_property_type", "search_quadrant"));

        for (String tableName : tableNames) {
            try {
                statement.executeUpdate("DROP TABLE " + tableName);
            } catch (Exception e) {
                System.err.println(tableName + " doesnt exist yet.");
            }
        }

        statement.executeUpdate("BEGIN TRANSACTION;\n" +
                "CREATE TABLE property_status\n" +
                "(\n" +
                "    status_id INT  NOT NULL,\n" +
                "    status    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (status_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE quadrant\n" +
                "(\n" +
                "    quadrant_id INT  NOT NULL,\n" +
                "    quadrant    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (quadrant_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE property_type\n" +
                "(\n" +
                "    type_id INT  NOT NULL,\n" +
                "    type    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (type_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE furnishing\n" +
                "(\n" +
                "    furnishing_id INTEGER NOT NULL,\n" +
                "    furnishing    CHAR    NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (furnishing_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE properties\n" +
                "(\n" +
                "    property_id     INTEGER                                        NOT NULL,\n" +
                "    quadrant        INTEGER REFERENCES quadrant (quadrant_id)      NOT NULL,\n" +
                "    property_status INTEGER REFERENCES property_status (status_id) NOT NULL,\n" +
                "    property_type   INTEGER REFERENCES property_type (type_id)     NOT NULL,\n" +
                "    bathrooms       INTEGER                                        NOT NULL,\n" +
                "    bedrooms        INTEGER                                        NOT NULL,\n" +
                "    furnished       INTEGER                                        NOT NULL,\n" +
                "    square_footage  INTEGER                                        NOT NULL,\n" +
                "    monthly_rent    INTEGER,\n" +
                "    streetNumber    INTEGER                                        NOT NULL,\n" +
                "    street          CHAR                                           NOT NULL,\n" +
                "    city            CHAR                                           NOT NULL,\n" +
                "    province        CHAR                                           NOT NULL,\n" +
                "    postal_code     CHAR                                           NOT NULL,\n" +
                "    PRIMARY KEY (property_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE landlords\n" +
                "(\n" +
                "    landlord_id int  NOT NULL,\n" +
                "    name        CHAR NOT NULL,\n" +
                "    username    CHAR NOT NULL,\n" +
                "    password    CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (landlord_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE managers\n" +
                "(\n" +
                "    manager_id int  NOT NULL,\n" +
                "    name       CHAR NOT NULL,\n" +
                "    username   CHAR NOT NULL,\n" +
                "    password   CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (manager_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE registered_renters\n" +
                "(\n" +
                "    renter_id int  NOT NULL,\n" +
                "    name      CHAR NOT NULL,\n" +
                "    username  CHAR NOT NULL,\n" +
                "    password  CHAR NOT NULL,\n" +
                "\n" +
                "    PRIMARY KEY (renter_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE saved_search_criteria\n" +
                "(\n" +
                "    search_id          INTEGER NOT NULL,\n" +
                "    user_id            INTEGER REFERENCES registered_renters (renter_id),\n" +
                "    max_monthly_rent   INTEGER,\n" +
                "    types              INTEGER REFERENCES search_property_type (search_id),\n" +
                "    min_bedrooms       INTEGER,\n" +
                "    min_bathrooms      INTEGER,\n" +
                "    min_square_footage INTEGER,\n" +
                "    furnishing         INTEGER REFERENCES furnishing (furnishing_id),\n" +
                "    quadrants          INTEGER REFERENCES search_quadrant (search_id),\n" +
                "    PRIMARY KEY (search_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE search_property_type\n" +
                "(\n" +
                "    search_id        INTEGER NOT NULL,\n" +
                "    property_type_id INT REFERENCES property_type (type_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE search_quadrant\n" +
                "(\n" +
                "    search_id   INTEGER NOT NULL,\n" +
                "    quadrant_id INT REFERENCES quadrant (quadrant_id)\n" +
                ");\n" +
                "COMMIT;");
        //TODO add rented properties table


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
                "\n" +
                "INSERT INTO furnishing values (1, 'FURNISHED');\n" +
                "INSERT INTO furnishing values (2, 'UNFURNISHED');\n" +
                "INSERT INTO furnishing values (3, 'EITHER');\n" +
                "\n" +
                "COMMIT;");
    }
}
