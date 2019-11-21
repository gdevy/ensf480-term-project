package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {


    public static void main(String[] args) {

        Connection dbConnection;
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproject",
                    "root", "ensf409");     //this may have to be changed depending on how you set it up
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
