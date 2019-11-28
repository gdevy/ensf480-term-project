package server;

import descriptor.Address;
import descriptor.LoginInfo;
import descriptor.UserTypeLogin;
import entity.socket.ManagerReport;
import entity.socket.PropertySearchCriteria;
import entity.socket.User;
import entity.socket.property.*;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatabaseHelper {

    private Connection dbConnection;

    private static DatabaseHelper instance;

    private DatabaseHelper() throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/ensf480-term-project/src/sqlite/db.db",
                "root", "");
    }

    public static DatabaseHelper getInstance() throws SQLException {
        if (instance == null) instance = new DatabaseHelper();
        return instance;
    }

    public static void main(String[] args) throws SQLException {

//        PropertyTraits pt = new PropertyTraits(PropertyType.APARTMENT, 2, 2, 100, false);
//        Address ad = new Address(3307, "24 Street NW", "Calgary", "AB", "T2M3Z8");
//        Property object = new Property(1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt);

        PropertySearchCriteria psc = new PropertySearchCriteria();
        psc.addType(PropertyType.HOUSE);
        psc.addQuadrant(Quadrant.NW);

        PropertyTraits pt = new PropertyTraits( PropertyType.CONDO, 2, 2, 1000, false );
        Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        Property object = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

        ArrayList<PropertySearchCriteria> results = DatabaseHelper.getInstance().getSavedSearches("greg.devyatov@gmail.com");
        for (PropertySearchCriteria result : results) {
            System.out.println("entry");
        }
    }

    //works
    public void registerProperty(Property property, String landlordInfo) throws SQLException {
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

        ResultSet rs = DatabaseHelper.getInstance().dbConnection.createStatement().executeQuery("SELECT last_insert_rowid()");
        int propertyID = rs.getInt("last_insert_rowid()");

        DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate("INSERT INTO landlord_property (landlord_id, property_id)\n" +
                "VALUES\n" +
                "((SELECT user_id FROM users WHERE email = '" + landlordInfo + "'), " + propertyID + ")");


    }

    //works
    public ArrayList<String> checkSavedSearches(Property property) throws SQLException {
        PreparedStatement ps = DatabaseHelper.getInstance().dbConnection.prepareStatement("SELECT * FROM saved_search_criteria\n" +
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
        Statement tempStatement = DatabaseHelper.getInstance().dbConnection.createStatement();
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (Integer searchID : searchIDS) {
            boolean quadrantMatches = false;
            boolean typeMatches = false;
            boolean rsEmpty = true;
            tempStatement = DatabaseHelper.getInstance().dbConnection.createStatement();
            rs = tempStatement.executeQuery("SELECT * FROM search_quadrant\n" +
                    "INNER JOIN quadrant ON search_quadrant.quadrant_id = quadrant.quadrant_id\n" +
                    "WHERE search_id = " + searchID);

            ArrayList<Quadrant> qs = new ArrayList<>();
            while (rs.next()) {
                qs.add(Quadrant.valueOf(rs.getString("quadrant_name")));
            }

            if (!qs.contains(property.getQuadrant()) && !qs.isEmpty()) {
                toRemove.add(searchID);
            }

            tempStatement = DatabaseHelper.getInstance().dbConnection.createStatement();
            rs = tempStatement.executeQuery("SELECT * FROM search_property_type\n" +
                    "INNER JOIN property_type ON search_property_type.property_type_id = property_type.type_id\n" +
                    "WHERE search_id = " + searchID);


            ArrayList<PropertyType> pts = new ArrayList<>();
            while (rs.next()) {
                pts.add(PropertyType.valueOf(rs.getString("type")));
            }

            if (!pts.contains(property.getTraits().getType()) && !pts.isEmpty()) {
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

        ArrayList<String> temp = new ArrayList<>();
        for (String result : results) {
            if (!temp.contains(result)) {
                temp.add(result);
            }
        }

        return temp;

    }

    //works
    public void saveSearchCriteria(PropertySearchCriteria psc, String userInfo) throws SQLException {
        PreparedStatement statement = DatabaseHelper.getInstance().dbConnection.prepareStatement("INSERT INTO saved_search_criteria (search_id, user_id, max_monthly_rent, min_bathrooms, min_bedrooms, min_square_footage) " +
                "values (null, (SELECT user_id FROM users WHERE email = ?), ?, ?, ?, ?)");
        statement.setString(1, userInfo);
        statement.setInt(2, psc.getMaxMonthlyRent());
        statement.setInt(3, psc.getMinBathrooms());
        statement.setInt(4, psc.getMinBedrooms());
        statement.setInt(5, psc.getMinSquareFootage());
        statement.executeUpdate();
        ResultSet rs = DatabaseHelper.getInstance().dbConnection.createStatement().executeQuery("SELECT last_insert_rowid()");
        int searchID = rs.getInt("last_insert_rowid()");

        StringBuilder insertUpdate;
        boolean firstRow;
        if (psc.hasType()) {
            System.out.println(psc.getTypes().get(0));

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
            DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate(insertUpdate.toString());
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
            DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate(insertUpdate.toString());
        }

    }

    //works
    public ArrayList<Property> searchProperty(PropertySearchCriteria psc) throws SQLException {
        ArrayList<Property> results = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM properties\n" +
                "INNER JOIN property_status ON property_status = property_status.status_id\n" +
                "INNER JOIN property_type ON property_type = property_type.type_id\n" +
                "INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n");


//        query.append("INNER JOIN property_status ON property_status = property_status.status_id\n");
//        if (psc.hasType()) {
//            query.append("INNER JOIN property_type ON property_type = property_type.type_id\n");
//        }
//
//        if (psc.hasQuadrant()) {
//            query.append("INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n");
//        }

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
        System.out.println(query);
        ResultSet rs = DatabaseHelper.getInstance().dbConnection.prepareStatement(query.toString()).executeQuery();


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
            int pid = rs.getInt("property_id");
            property.setId(pid);

            results.add(property);
        }
        for (Property result : results) {
            System.out.println(result.getId());
        }

        return results;
    }

    public ManagerReport createPropertyReport() throws SQLException {

        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String timeMonthAgo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT * FROM properties\n" +
                "  INNER JOIN property_status ON property_status = property_status.status_id\n" +
                "  INNER JOIN property_type ON property_type = property_type.type_id\n" +
                " INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n" +
                " WHERE date_created BETWEEN '" + timeMonthAgo + "' AND '" + timeNow + "'");
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

    //works
    public ArrayList<Property> viewProperties(String userName) throws SQLException {
        ResultSet rs = DatabaseHelper.getInstance().dbConnection.createStatement().executeQuery("SELECT * FROM properties\n" +
                "INNER JOIN landlord_property ON properties.property_id = landlord_property.property_id\n" +
                "INNER JOIN users ON users.user_id = landlord_property.landlord_id\n" +
                "INNER JOIN user_type ON users.user_type = user_type_id\n" +
                "INNER JOIN property_type ON properties.property_type = property_type.type_id\n" +
                "INNER JOIN quadrant ON properties.quadrant = quadrant.quadrant_id\n" +
                "INNER JOIN property_status ON property_status = property_status.status_id\n" +
                "WHERE users.email = '" + userName + "'");

        ArrayList<Property> results = new ArrayList<>();
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
            Quadrant q = Quadrant.valueOf(quad);
            PropertyStatus ps = PropertyStatus.valueOf(rs.getString("status"));
            Property property = new Property(monthlyRent, address, q, ps, pt);
            property.setId(rs.getInt("property_id"));
            results.add(property);
        }

        for (Property result : results) {
            System.out.println(result.getTraits());
        }

        return results;
    }

    //works
    public ArrayList<PropertySearchCriteria> getSavedSearches(String userName) throws SQLException {
        ArrayList<PropertySearchCriteria> results = new ArrayList<>();
        Statement stm = DatabaseHelper.getInstance().dbConnection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT user_id FROM users\n" +
                "WHERE email = '" + userName + "'");

        int userID = rs.getInt("user_id");

        rs = DatabaseHelper.getInstance().dbConnection.createStatement().executeQuery("SELECT * from saved_search_criteria\n" +
                "WHERE user_id = " + userID + "");

        int lastSearchID;
        while (rs.next()) {
            lastSearchID = rs.getInt("search_id");

            PropertySearchCriteria psc = new PropertySearchCriteria();
            psc.setID(rs.getInt("search_id"));
            psc.setMaxMonthlyRent(rs.getInt("max_monthly_rent"));
            psc.setMinBathrooms(rs.getInt("min_bathrooms"));
            psc.setMinBedrooms(rs.getInt("min_bedrooms"));
            psc.setMinSquareFootage(rs.getInt("min_square_footage"));
            psc.setFurnished(rs.getInt("furnished") == 1);

            ResultSet tempRS = stm.executeQuery("SELECT * FROM search_quadrant\n" +
                    "LEFT JOIN quadrant ON search_quadrant.quadrant_id = quadrant.quadrant_id\n" +
                    "WHERE search_id = " + lastSearchID);
            while (tempRS.next()) {
                psc.addQuadrant(Quadrant.valueOf(tempRS.getString("quadrant_name")));
            }

            tempRS = stm.executeQuery("SELECT * FROM search_property_type\n" +
                    "LEFT JOIN property_type ON search_property_type.property_type_id = property_type.type_id\n" +
                    "WHERE search_id = " + lastSearchID);
            while (tempRS.next()) {
                psc.addType(PropertyType.valueOf(tempRS.getString("type")));
            }
            results.add(psc);
        }
        System.out.println(results.size());
        return results;
    }

    //works
    public String getLandlordEmail(int propertyID) throws SQLException {
        System.out.println("peid: " + propertyID);
        ResultSet rs = DatabaseHelper.getInstance().dbConnection.createStatement().executeQuery("SELECT * FROM users\n" +
                "INNER JOIN landlord_property ON user_id = landlord_property.landlord_id\n" +
                "WHERE property_id = " + propertyID);

        String email = "";
        if (rs.next()) {
            email =  rs.getString("email");
            System.out.println(email);
        }
        return email;
    }

    //works
    public void editStatus(int propertyID, PropertyStatus newStatus) throws SQLException {
        if (newStatus == PropertyStatus.RENTED) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate("UPDATE properties\n" +
                    "   SET (property_status, date_rented) = ((SELECT status_id FROM property_status WHERE status = '" + newStatus.name() + "'), '" + time + "')\n" +
                    "WHERE property_id = " + propertyID);
        } else {
            DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate("UPDATE properties\n" +
                    "SET property_status = (SELECT status_id FROM property_status WHERE status = '" + newStatus.name() + "')\n" +
                    "WHERE\n" +
                    "    property_id = " + propertyID);
        }
    }

    public void deleteSavedSearch(int searchID) throws SQLException {
        DatabaseHelper.getInstance().dbConnection.createStatement().executeUpdate("DELETE FROM saved_search_criteria \n" +
                "WHERE search_id = " + searchID);

    }
    //works
    public UserTypeLogin attemptLogin(LoginInfo info) throws SQLException {
        Statement stm = DatabaseHelper.getInstance().dbConnection.createStatement();


        ResultSet rs = stm.executeQuery("SELECT user_type.user_type_name from users\n" +
                "INNER JOIN user_type ON users.user_type = user_type.user_type_id \n" +
                "WHERE email = '" + info.username + "'\n" +
                "AND   password = '" + info.password + "'");

        if (rs.next()) {
            return UserTypeLogin.valueOf(rs.getString("user_type_name"));

        } else {
            return UserTypeLogin.LOGIN_FAILED;
        }

    }

    public ArrayList<User> getAllUsers() throws SQLException {
        ResultSet rs = dbConnection.createStatement().executeQuery("SELECT email, user_type_name  FROM users\n" +
                "INNER JOIN user_type on users.user_type = user_type.user_type_id");

        ArrayList<User> results = new ArrayList();
        while (rs.next()) {
            results.add(new User(UserTypeLogin.valueOf(rs.getString("user_type_name")), rs.getString("email")));
        }
        return results;
    }
}
