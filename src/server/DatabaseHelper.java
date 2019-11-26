package server;

import descriptor.Address;
import descriptor.LoginInfo;
import descriptor.UserTypeLogin;
import entity.socket.ManagerReport;
import entity.socket.PropertySearchCriteria;
import entity.socket.User;
import entity.socket.property.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatabaseHelper {

    private Connection dbConnection;

    DatabaseHelper() throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/ensf480-term-project/src/sqlite/db.db",
                "root", "");
    }

    public static void main(String[] args) throws SQLException {

        PropertyTraits pt = new PropertyTraits(PropertyType.APARTMENT, 2, 2, 100, false);
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
//        dbHelper.searchProperty(psc);
//        dbHelper.saveSearchCriteria(psc, "greg");
//        dbHelper.searchSavedSearches("greg");
//        LoginInfo info = new LoginInfo("greg", "abc123");
//        System.out.println(dbHelper.attemptLogin(info));
//        System.out.println(dbHelper.getLandlordEmail(1008));
//        dbHelper.editStatus(1008, PropertyStatus.REMOVED);
//        System.out.println(dbHelper.checkSavedSearches(object));

    }

    void registerProperty(Property property, String landlordInfo) throws SQLException {
        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO properties (property_id, quadrant, property_status, property_type, bathrooms, bedrooms, furnished, square_footage, monthly_rent, streetNumber, street, city, province, postal_code, date_created) values" +
                " (null, (SELECT quadrant_id from quadrant WHERE quadrant_name = ?), (SELECT status_id from property_status WHERE status = ?), (SELECT type_id from property_type WHERE type =?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        statement.setString(14, time);
        statement.executeUpdate();

        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT last_insert_rowid()");
        int propertyID = rs.getInt("last_insert_rowid()");

        dbConnection.createStatement().executeUpdate("INSERT INTO landlord_property (landlord_id, property_id)\n" +
                "VALUES\n" +
                "((SELECT user_id FROM users WHERE email = '" + landlordInfo + "'), " + propertyID + ")");
    }

    ArrayList<String> checkSavedSearches(Property property) throws SQLException {
        PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM saved_search_criteria\n" +
                "WHERE (max_monthly_rent >= ?\n" +
                "OR max_monthly_rent = -1)\n" +
                "AND (min_bathrooms <= ?\n" +
                "OR min_bathrooms = -1)\n" +
                "AND (min_bedrooms <= ?\n" +
                "OR min_bedrooms = -1)\n" +
                "AND (min_square_footage <= ?\n" +
                "OR min_bathrooms = -1)\n" +
                "AND (furnished = 0\n" +
                "OR furnished = ?)");

        ps.setInt(1, property.getMonthlyRent());
        ps.setInt(2, property.getTraits().getBathrooms());
        ps.setInt(3, property.getTraits().getBedrooms());
        ps.setInt(4, (int) property.getTraits().getSquareFootage()); //TODO change to int
        ps.setInt(5, property.getTraits().getFurnished() ? 1 : 0);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> searchIDS = new ArrayList<>();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            searchIDS.add(rs.getInt(1));
        }

        System.out.println(searchIDS);
        Statement tempStatement = dbConnection.createStatement();
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (Integer searchID : searchIDS) {
            boolean quadrantMatches = false;
            boolean typeMatches = false;
            boolean rsEmpty = true;
            rs = tempStatement.executeQuery("SELECT * FROM search_quadrant\n" +
                    "INNER JOIN quadrant ON search_quadrant.quadrant_id = quadrant.quadrant_id\n" +
                    "WHERE search_id = " + searchID);

            Quadrant q;
            while (rs.next()) {
                rsEmpty = false;
                q = Quadrant.valueOf(rs.getString("quadrant_name"));
                if (property.getQuadrant() == q) {
                    quadrantMatches = true;
                    break;
                }
            }
            if (rsEmpty) {
                continue;
            }
            if (!quadrantMatches) {
                toRemove.add(searchID);
                continue;
            }

            rs = tempStatement.executeQuery("SELECT * FROM search_property_type\n" +
                    "INNER JOIN property_type ON search_property_type.property_type_id = property_type.type_id\n" +
                    "WHERE search_id = " + searchID);


            PropertyType pt;
            rsEmpty = true;
            while (rs.next()) {
                rsEmpty = false;
                pt = PropertyType.valueOf(rs.getString("type"));
                if (property.getTraits().getType() == pt) {
                    typeMatches = true;
                    break;
                }
            }
            if (rsEmpty) {
                continue;
            }
            if (!typeMatches) {
                toRemove.add(searchID);
            }

        }
        for (Integer integer : toRemove) {
            searchIDS.remove(integer);
        }

        ArrayList<String> results = new ArrayList<>();
        for (Integer searchID : searchIDS) {

            rs = tempStatement.executeQuery("SELECT email FROM saved_search_criteria\n" +
                    "INNER JOIN users ON saved_search_criteria.user_id = users.user_id\n" +
                    "WHERE saved_search_criteria.search_id = " + searchID);
            if (rs.next()) {
                results.add(rs.getString("email"));
            }
        }

        return results;

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

    ManagerReport createPropertyReport() throws SQLException {

        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT * FROM properties\n" +
                "INNER JOIN property_status ON property_status = property_status.status_id\n" +
                "INNER JOIN property_type ON property_type = property_type.type_id\n" +
                "INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n");
        ArrayList<Property> results = new ArrayList<>();

        int available = 0;
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
            String quad = rs.getString("quadrant_name");
            System.out.println(quad);
            Quadrant q = Quadrant.valueOf(quad);
            PropertyStatus ps = PropertyStatus.valueOf(rs.getString("status"));
            if (ps == PropertyStatus.AVAILABLE) {
                available++;
            }
            Property property = new Property(monthlyRent, address, q, ps, pt);

            results.add(property);
        }
        return new ManagerReport(results.size(), results.size(), available, results);
    }

    ArrayList<PropertySearchCriteria> getSavedSearches(String userName) throws SQLException {
        ArrayList<PropertySearchCriteria> results = new ArrayList<>();
        Statement stm = dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT user_id FROM users\n" +
                "WHERE email = '" + userName + "'");

        int userID = rs.getInt("user_id");

        rs = stm.executeQuery("SELECT * from saved_search_criteria\n" +
                "WHERE user_id = " + userID + "");

        int lastSearchID = -1;
        while (rs.next()) {
            lastSearchID = rs.getInt("search_id");

            PropertySearchCriteria psc = new PropertySearchCriteria();

            psc.setMaxMonthlyRent(rs.getInt("max_monthly_rent"));
            psc.setMinBathrooms(rs.getInt("min_bathrooms"));
            psc.setMinBedrooms(rs.getInt("min_bedrooms"));
            psc.setMinSquareFootage(rs.getInt("min_square_footage"));
            psc.setFurnished(rs.getInt("furnished") == 1);

            ResultSet tempRS = stm.executeQuery("SELECT * FROM search_quadrant\n" +
                    "LEFT JOIN quadrant ON search_quadrant.quadrant_id = quadrant.quadrant_id\n" +
                    "WHERE search_id = " + lastSearchID);
            while (tempRS.next()) {
                psc.addQuadrant(Quadrant.valueOf(rs.getString("quadrant_name")));
            }

            rs = stm.executeQuery("SELECT * FROM search_property_type\n" +
                    "LEFT JOIN property_type ON search_property_type.property_type_id = property_type.type_id\n" +
                    "WHERE search_id = " + lastSearchID);
            while (tempRS.next()) {
                psc.addType(PropertyType.valueOf(rs.getString("quadrant_name")));
            }
            results.add(psc);
        }

        System.out.println(results.size());
        return results;
    }

    String getLandlordEmail(int propertyID) throws SQLException {
        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT email FROM users\n" +
                "INNER JOIN landlord_property ON user_id = landlord_property.landlord_id\n" +
                "WHERE property_id = '" + propertyID + "'");

        return rs.getString(1);
    }

    void editStatus(int propertyID, PropertyStatus newStatus) throws SQLException {
        dbConnection.createStatement().executeUpdate("UPDATE properties\n" +
                "SET property_status = (SELECT status_id FROM property_status WHERE status = '" + newStatus.name() + "')\n" +
                "WHERE\n" +
                "    property_id = " + propertyID);
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

    ArrayList<User> getAllUsers() throws SQLException {
        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT email, user_type_name  FROM users\n" +
                "INNER JOIN user_type on users.user_type = user_type.user_type_id");

        ArrayList<User> results = new ArrayList();
        while (rs.next()) {
            results.add(new User(UserTypeLogin.valueOf(rs.getString("user_type_name")), rs.getString("email")));
        }
        return results;
    }
}
