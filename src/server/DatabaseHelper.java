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

        PropertyTraits pt = new PropertyTraits(PropertyType.HOUSE, 1, 1, 1000, true);
        Address ad = new Address(3307, "24 Street NW", "Calgary", "AB", "T2M3Z8");
        Property object = new Property(1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt);

        PropertySearchCriteria psc = new PropertySearchCriteria();
        psc.setMaxMonthlyRent(2000);
        psc.setMinBathrooms(2);
        psc.addQuadrant(Quadrant.NW);
        psc.addQuadrant(Quadrant.SW);
        psc.addType(PropertyType.HOUSE);
        psc.addType(PropertyType.APARTMENT);
        psc.setFurnished(true);

        DatabaseHelper dbHelper = new DatabaseHelper();

        dbHelper.registerProperty(object);
        //dbHelper.searchProperty(psc);
        LoginInfo info = new LoginInfo("greg", "abc123");
        System.out.println(dbHelper.attemptLogin(info));

    }

    boolean registerProperty(Property property) throws SQLException {

        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO properties (property_id, quadrant, property_status, property_type, bathrooms, bedrooms, furnished, square_footage, monthly_rent, streetNumber, street, city, province, postal_code) values" +
                " (null, (SELECT quadrant_id from quadrant WHERE quadrant = ?), (SELECT status_id from property_status WHERE status = ?), (SELECT type_id from property_type WHERE type =?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
        System.out.println(statement);
        return statement.executeUpdate() == 1;  //double check this. javaDocs is a little ambiguous on this
    }

    List<Property> searchProperty(PropertySearchCriteria psc) throws SQLException {
        boolean first = true;
        ArrayList<Property> results = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM properties\n");

        if (psc.hasType()) {
            query.append("INNER JOIN property_type ON property_type = property_type.type_id\n");
        }

        if (psc.hasQuadrant()) {
            query.append("INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n");
        }

        query.append("WHERE ");

        if (psc.hasMaxMonthlyRent()) {
            query.append("monthly_rent <= " + psc.getMaxMonthlyRent());
            first = false;
        }

        if (psc.hasMinBathrooms()) {
            if (!first) {
                query.append("\n AND ");
            }
            query.append("bathrooms >= " + psc.getMinBathrooms());
            first = false;
        }

        if (psc.hasMinBedrooms()) {
            if (!first) {
                query.append("\n AND ");
            }
            query.append("bedrooms >= " + psc.getMinBedrooms());
            first = false;
        }

        if (psc.hasMinSquareFootage()) {
            if (!first) {
                query.append("\n AND ");
            }
            query.append("square_footage >= " + psc.getMinSquareFootage());
            first = false;
        }

        if (psc.hasQuadrant()) {
            if (!first) {
                query.append("\n AND ");
            }
            if (psc.getQuadrants().size() == 1) {
                query.append("quadrant.quad = '" + psc.getQuadrants().get(0).name() + "'");
            } else {
                query.append("(");
                Iterator iter = psc.getQuadrants().iterator();

                while (iter.hasNext()) {
                    query.append("quadrant.quad = '" + iter.next() + "'");
                    if (iter.hasNext()) {
                        query.append("\n OR ");
                    }
                }

                query.append(")");
            }
        }

        if (psc.hasType()) {
            if (!first) {
                query.append("\n AND ");
            }
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
            first = false;
        }

        if (psc.getFurnished()) {
            if (!first) {
                query.append("\n AND ");
            }
            query.append("furnished = " + 1);
            first = false;
        }

        System.out.println(query);
        PreparedStatement statement = dbConnection.prepareStatement(query.toString());

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
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
            Quadrant q = Quadrant.valueOf(rs.getString("quad"));
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

    void saveSearchCriteria(PropertySearchCriteria psc, String userInfo) throws SQLException {
        Statement stm = dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT user_id FROM users\n" +
                "WHERE email = '" + userInfo + "'");

        int userID = rs.getInt("user_id");

        rs = stm.executeQuery("SELECT * from saved_search_criteria\n" +
                "WHERE user_id = " + userID + "");

        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO saved_search_criteria (search_id, user_id, max_monthly_rent, min_bathrooms, min_bedrooms, min_square_footage) values (null, ?, ?, ?, ?, ?)");
        statement.setInt(1, userID);
        if (psc.hasMaxMonthlyRent()) {
            statement.setInt(2, psc.getMaxMonthlyRent());
        } else {
            statement.setNull(2, Types.INTEGER);
        }
        if (psc.hasMinBathrooms()) {
            statement.setInt(3, psc.getMinBathrooms());
        } else {
            statement.setNull(3, Types.INTEGER);
        }
        if (psc.hasMinBedrooms()) {
            statement.setInt(4, psc.getMinBedrooms());
        } else {
            statement.setNull(4, Types.INTEGER);
        }
        if (psc.hasMinSquareFootage()) {
            statement.setInt(5, psc.getMinSquareFootage());
        } else {
            statement.setNull(5, Types.INTEGER);
        }

        rs = stm.executeQuery("SELECT last_insert_rowid()");

        int searchID = rs.getInt("last_insert_rowid()");

        StringBuilder insertUpdate;
        boolean firstRow;
        if (psc.hasType()) {
            firstRow = true;
            insertUpdate = new StringBuilder("INSERT INTO search_property_type (search_id, property_type_id)\n" +
                    "VALUES\n");
            for (PropertyType type : psc.getTypes()) {
                int propertyTypeID = 1;
                if (!firstRow) {
                    insertUpdate.append(",\n");
                }
                insertUpdate.append("(" + searchID + ", (SELECT type_id from property_type WHERE type = '" + type.name() + "'))");
                firstRow = false;
            }
        }
        if (psc.hasQuadrant()) {
            firstRow = true;
            insertUpdate = new StringBuilder("INSERT INTO search_quadrant (search_id, quadrant_id)\n" +
                    "VALUES\n");
            for (Quadrant quadrant : psc.getQuadrants()) {
                int quadrantID = 1;
                if (!firstRow) {
                    insertUpdate.append(",\n");
                }
                insertUpdate.append("(" + searchID + ", (SELECT type_id from property_type WHERE type = '" + quadrant.name() + "'))");
                firstRow = false;
            }
        }

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
