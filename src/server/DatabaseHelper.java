package server;

import descriptor.Address;
import descriptor.LoginInfo;
import descriptor.UserTypeLogin;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import org.sqlite.SQLiteDataSource;

import javax.naming.directory.SearchControls;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseHelper {

    private Connection dbConnection;

    DatabaseHelper() throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/ensf480-term-project/src/sqlite/db.db",
                "root", "");
    }

    public static void main(String[] args) throws SQLException {

        PropertyTraits pt = new PropertyTraits(PropertyType.HOUSE, 2, 2, 100, true);
        Address ad = new Address(3307, "24 Street NW", "Calgary", "AB", "T2M3Z8");
        Property object = new Property(1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt);

        PropertySearchCriteria psc = new PropertySearchCriteria();
        psc.setMaxMonthlyRent(150);
        psc.setMinBathrooms(2);
        psc.addQuadrant(Quadrant.NW);
        psc.addQuadrant(Quadrant.SW);
        psc.addType(PropertyType.HOUSE);
        psc.addType(PropertyType.APARTMENT);
        psc.setFurnished(true);

        DatabaseHelper dbHelper = new DatabaseHelper();

        dbHelper.registerProperty(object, "jed");
        dbHelper.searchProperty(psc);
        dbHelper.saveSearchCriteria(psc, "greg");
        LoginInfo info = new LoginInfo("greg", "abc123");
        System.out.println(dbHelper.attemptLogin(info));


    }

    void registerProperty(Property property, String landlordInfo) throws SQLException {
        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO properties (property_id, quadrant, property_status, property_type, bathrooms, bedrooms, furnished, square_footage, monthly_rent, streetNumber, street, city, province, postal_code) values" +
                " (null, (SELECT quadrant_id from quadrant WHERE quadrant_name = ?), (SELECT status_id from property_status WHERE status = ?), (SELECT type_id from property_type WHERE type =?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, property.getQuadrant().name());
        statement.setString(2, property.getStatus().name());
        statement.setString(3, property.getTraits().getType().name());
        statement.setInt(4, property.getTraits().getBathrooms());
        statement.setInt(5, property.getTraits().getBedrooms());
        statement.setBoolean(6, property.getTraits().getFurnished());
        statement.setDouble(7, property.getTraits().getSquareFootage());
        statement.setInt(8, property.getMonthlyRent());
        statement.setInt(9, property.getAddress().getStreetNumber());
        statement.setString(10, property.getAddress().getStreet());
        statement.setString(11, property.getAddress().getCity());
        statement.setString(12, property.getAddress().getProvince());
        statement.setString(13, property.getAddress().getPostalCode());
        statement.executeUpdate();

        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT last_insert_rowid()");
        int propertyID = rs.getInt("last_insert_rowid()");

        dbConnection.createStatement().executeUpdate("INSERT INTO landlord_property (landlord_id, property_id)\n" +
                "VALUES\n" +
                "((SELECT user_id FROM users WHERE email = '" + landlordInfo + "'), " + propertyID + ")");
    }

    void saveSearchCriteria(PropertySearchCriteria psc, String userInfo) throws SQLException {
        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO saved_search_criteria (search_id, user_id, max_monthly_rent, min_bathrooms, min_bedrooms, min_square_footage) " +
                "values (null, (SELECT user_id FROM users WHERE email = ?), ?, ?, ?, ?)");
        statement.setString(1, userInfo);
        statement.setInt(2, psc.getMaxMonthlyRent());
        statement.setInt(3, psc.getMinBathrooms());
        statement.setInt(4, psc.getMinBedrooms());
        statement.setInt(5, psc.getMinSquareFootage());
        statement.executeUpdate();
        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT last_insert_rowid()");
        int searchID = rs.getInt("last_insert_rowid()");

        StringBuilder insertUpdate;
        boolean firstRow;
        if (psc.hasType()) {
            firstRow = true;
            insertUpdate = new StringBuilder("INSERT INTO search_property_type (search_id, property_type_id)\n" +
                    "VALUES\n");
            for (PropertyType type : psc.getTypes()) {
                if (!firstRow) {
                    insertUpdate.append(",\n");
                }
                insertUpdate.append("(" + searchID + ", (SELECT type_id from property_type WHERE type = '" + type.name() + "'))");
                firstRow = false;
            }
            dbConnection.createStatement().executeUpdate(insertUpdate.toString());
        }
        if (psc.hasQuadrant()) {
            firstRow = true;
            insertUpdate = new StringBuilder("INSERT INTO search_quadrant (search_id, quadrant_id)\n" +
                    "VALUES\n");
            for (Quadrant quadrant : psc.getQuadrants()) {
                if (!firstRow) {
                    insertUpdate.append(",\n");
                }
                insertUpdate.append("(" + searchID + ", (SELECT type_id from property_type WHERE type = '" + quadrant.name() + "'))");
                firstRow = false;
            }
            dbConnection.createStatement().executeUpdate(insertUpdate.toString());
        }

    }

    ArrayList<Property> searchProperty(PropertySearchCriteria psc) throws SQLException {
        ArrayList<Property> results = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM properties\n");

        query.append("INNER JOIN property_status ON property_status = property_status.status_id\n");
        if (psc.hasType()) {
            query.append("INNER JOIN property_type ON property_type = property_type.type_id\n");
        }

        if (psc.hasQuadrant()) {
            query.append("INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n");
        }

        query.append("WHERE ");
        query.append("status = '" + PropertyStatus.AVAILABLE.name() + "'");

        if (psc.hasMaxMonthlyRent()) {
            query.append("\n AND ");
            query.append("monthly_rent <= " + psc.getMaxMonthlyRent());
        }

        if (psc.hasMinBathrooms()) {
            query.append("\n AND ");
            query.append("bathrooms >= " + psc.getMinBathrooms());
        }

        if (psc.hasMinBedrooms()) {
            query.append("\n AND ");
            query.append("bedrooms >= " + psc.getMinBedrooms());
        }

        if (psc.hasMinSquareFootage()) {
            query.append("\n AND ");
            query.append("square_footage >= " + psc.getMinSquareFootage());
        }

        if (psc.hasQuadrant()) {
            query.append("\n AND ");
            if (psc.getQuadrants().size() == 1) {
                query.append("quadrant.quadrant_name = '" + psc.getQuadrants().get(0).name() + "'");
            } else {
                query.append("(");
                Iterator iter = psc.getQuadrants().iterator();

                while (iter.hasNext()) {
                    query.append("quadrant.quadrant_name = '" + iter.next() + "'");
                    if (iter.hasNext()) {
                        query.append("\n OR ");
                    }
                }

                query.append(")");
            }
        }

        if (psc.hasType()) {
            query.append("\n AND ");
            if (psc.getTypes().size() == 1) {
                query.append("type = '" + psc.getTypes().get(0).name() + "'");
            } else {
                query.append("(");
                Iterator<PropertyType> iter = psc.getTypes().iterator();

                while (iter.hasNext()) {
                    query.append("type = '" + iter.next().name() + "'");
                    if (iter.hasNext()) {
                        query.append("\n OR ");
                    }
                }
                query.append(")");
            }
        }

        if (psc.getFurnished()) {
            query.append("\n AND ");
            query.append("furnished = " + 1);
        }

        ResultSet rs = dbConnection.prepareStatement(query.toString()).executeQuery();

        while (rs.next()) { //createProperty
            PropertyType type = PropertyType.valueOf(rs.getString("type"));
            int bedrooms = rs.getInt("bedrooms");
            int bathrooms = rs.getInt("bathrooms");
            int squareFootage = rs.getInt("square_footage");
            boolean furnished = rs.getInt("furnished") == 1;
            PropertyTraits pt = new PropertyTraits(type, bedrooms, bathrooms, squareFootage, furnished);
            int num = rs.getInt("streetNumber");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String province = rs.getString("province");
            String postalCode = rs.getString("postal_code");
            Address address = new Address(num, street, city, province, postalCode);
            int monthlyRent = rs.getInt("monthly_rent");
            String quad = rs.getString("quadrant_name");
            System.out.println(quad);
            Quadrant q = Quadrant.valueOf(quad);
            PropertyStatus ps = PropertyStatus.AVAILABLE;
            Property property = new Property(monthlyRent, address, q, ps, pt);

            results.add(property);
        }

        return results;
    }

    ArrayList<PropertySearchCriteria> searchSavedSearches(String userName) throws SQLException {
        ArrayList<PropertySearchCriteria> results = new ArrayList<>();
        Statement stm = dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT user_id FROM users\n" +
                "WHERE email = '" + userName + "'");

        int userID = rs.getInt("user_id");

        rs = stm.executeQuery("SELECT * from saved_search_criteria\n" +
                "WHERE user_id = " + userID + "");

        while (rs.next()) {

        }
        return results;
    }

    UserTypeLogin attemptLogin(LoginInfo info) throws SQLException {
        Statement stm = dbConnection.createStatement();


        ResultSet rs = stm.executeQuery("SELECT user_type.user_type_name from users\n" +
                "INNER JOIN user_type ON user_id = user_type.user_type_id \n" +
                "WHERE email = '" + info.username + "'\n" +
                "AND   password = '" + info.password + "'");

        if (rs.next()) {
            return UserTypeLogin.valueOf(rs.getString("user_type_name"));

        } else {
            return UserTypeLogin.LOGIN_FAILED;
        }

    }
}
