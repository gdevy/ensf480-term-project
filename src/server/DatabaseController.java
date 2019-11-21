package server;

import java.sql.*;

public class DatabaseController {


    public static void main(String[] args) throws SQLException {

        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/code/src/sqlite/db.db",
                    "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        Statement statement = dbConnection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM properties");

        while (rs.next()) {
            System.out.println("property id: " + rs.getInt("propertyid"));
        }


    }

}
