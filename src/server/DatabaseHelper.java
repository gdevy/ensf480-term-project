package server;

import descriptor.Address;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseHelper {

    private Connection dbConnection;

    DatabaseHelper() throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Greg/Documents/School/ENSF480/project/code/src/sqlite/db.db",
                "root", "");
    }

    public static void main(String[] args) throws SQLException {

        PropertyTraits pt = new PropertyTraits(PropertyType.HOUSE, 1, 1, 1000, true);
        Address ad = new Address(3307, "24 Street NW", "Calgary", "AB", "T2M3Z8");
        Property object = new Property(1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt);

        PropertySearchCriteria psc = new PropertySearchCriteria();
        psc.setMaxMonthlyRent( 2000 );
        psc.setMinBathrooms( 2 );
        psc.addQuadrant( Quadrant.NW );
        psc.addQuadrant( Quadrant.SW );
        psc.addType( PropertyType.HOUSE );
        psc.addType( PropertyType.APARTMENT );
        psc.setFurnished( true );

        DatabaseHelper dbHelper = new DatabaseHelper();

        //dbHelper.registerProperty(object);
        dbHelper.searchProperty(psc);
    }

    boolean registerProperty(Property property) throws SQLException {
        StringBuilder sb = new StringBuilder();
        Statement stm = dbConnection.createStatement();
        ResultSet rs = null;
        rs = stm.executeQuery("SELECT quadrant_id\n" +
                "from quadrant\n" +
                "WHERE quadrant = '" + property.getQuadrant().name() + "';");

        int quadrantID = rs.getInt("quadrant_id");

        rs = stm.executeQuery("SELECT status_id\n" +
                "from property_status\n" +
                "WHERE status = '" + property.getStatus().name() + "';\n");

        int propertyStatusID = rs.getInt("status_id");

        rs = stm.executeQuery("SELECT type_id\n" +
                "from property_type\n" +
                "WHERE type = '" + property.getTraits().getType().name() + "';");

        int propertyTypeID = rs.getInt("type_id");


        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO properties (property_id, quadrant, property_status, property_type, bathrooms, bedrooms, furnished, square_footage, monthly_rent, streetNumber, street, city, province, postal_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 4);
        statement.setInt(2, quadrantID);
        statement.setInt(3, propertyStatusID);
        statement.setInt(4, propertyTypeID);
        statement.setInt(5, property.getTraits().getBathrooms());
        statement.setInt(6, property.getTraits().getBedrooms());
        statement.setBoolean(7, property.getTraits().getFurnished());
        statement.setDouble(8, property.getTraits().getSquareFootage());
        statement.setInt(9, property.getMonthlyRent());
        statement.setInt(10, property.getAddress().getStreetNumber());
        statement.setString(11, property.getAddress().getStreet());
        statement.setString(12, property.getAddress().getCity());
        statement.setString(13, property.getAddress().getProvince());
        statement.setString(14, property.getAddress().getPostalCode());
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
                query.append("quadrant.quadrant = '" + psc.getQuadrants().get(0).name() + "'");
            } else {
                query.append("(");
                Iterator iter = psc.getQuadrants().iterator();

                while (iter.hasNext()) {
                    query.append("quadrant.quadrant = '" + iter.next() + "'");
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


//        PropertyTraits pt = new PropertyTraits(PropertyType.HOUSE, 1, 1, 1000, true);
//        Address ad = new Address(3307, "24 Street NW", "Calgary", "AB", "T2M3Z8");
//        Property object = new Property(1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt);



        return results;
    }
}
