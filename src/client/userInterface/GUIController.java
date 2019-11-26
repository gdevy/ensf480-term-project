package client.userInterface;

import descriptor.*;
import entity.socket.EmailInfo;

import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import client.mvc.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIController {
    private GUIFrame MainFrame;
    private Controller controller;

    private pnlEditProperty pnlEditProperty;


    private pnlLandlordMain pnlLandlordMain;
    private pnlLandlordNewProp pnlLandlordNewProp;
    private pnlLogin pnlLogin;
    private pnlManagerMain pnlManagerMain;
    private pnlRenterSearch pnlRenterSearch;
    private pnlStart pnlStart;
    private pnlRenterSearchResult pnlRenterSearchResult;
    private pnlLandlordListings pnlLandlordListings;
    private pnlLandordPayment pnlLandordPayment;
    private pnlManagerReport pnlManagerReport;
    private LoginInfo userInfo;
    private pnlViewUsers pnlViewUsers;
    private pnlManagerFees pnlManagerFees;
    private pnlManagerViewProperties pnlManagerViewProperties;
    private pnlSendEmail pnlSendEmail;
    private pnlSavedSearches pnlSavedSearches;
    private pnlRegRenterMain pnlRegRenterMain;


    public static void main(String[] args) {
        GUIController g = new GUIController();
        g.setController( new Controller() );
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public GUIController() {
        InitializeGUI();
        MainFrame.setVisible(true);
        MainFrame.setContentPane(pnlStart.getPnlStart());
    }

    private void InitializeGUI() {
        MainFrame = new GUIFrame();
        pnlStart = new pnlStart();
        pnlStart.setController(this);
    }

    public void skipLogin(){
        pnlRenterSearch = new pnlRenterSearch();
        pnlRenterSearch.setController(this);
        MainFrame.setContentPane(pnlRenterSearch.getPnlRenterSearch());
        MainFrame.revalidate();

    }

    public void goToLogin() {
        pnlLogin = new pnlLogin();
        pnlLogin.setController(this);
        MainFrame.setContentPane(pnlLogin.getPnlLogin());
        MainFrame.revalidate();
    }

    public void ValidateLogin(String username, String password) {
        LoginInfo loginInfo= new LoginInfo(username, password);

        System.out.println("Username="+username+"\n Password:"+password);

        UserTypeLogin userType = controller.sendLoginAttemptAndGetResult(loginInfo);

        switch( userType )
        {
            case LOGIN_FAILED:
            //TODO:Error Message
                break;
            case REGISTERED_RENTER:
                pnlRegRenterMain = new pnlRegRenterMain();
                pnlRegRenterMain.setController(this);
                MainFrame.setContentPane(pnlRegRenterMain.getPnlRegRenterMain());
                MainFrame.revalidate();
                userInfo = loginInfo;


                break;
            case LANDLORD:
                pnlLandlordMain = new pnlLandlordMain();
                pnlLandlordMain.setController(this);
                MainFrame.setContentPane(pnlLandlordMain.getPnlLandlord());
                userInfo = loginInfo;
                break;
            case MANAGER:
                pnlManagerMain = new pnlManagerMain();
                pnlManagerMain.setController(this);
                MainFrame.setContentPane(pnlManagerMain.getPnlManagerMain());
                userInfo = loginInfo;
                break;
        }

        MainFrame.revalidate();

    }

    public void goToNewProp() {
        pnlLandlordNewProp = new pnlLandlordNewProp();
        pnlLandlordNewProp.setController(this);
        MainFrame.setContentPane(pnlLandlordNewProp.getPnlLandlordNewProp());
        MainFrame.revalidate();
    }

    public void NewLandlordProperty() {
        try {
            int streetNumber = Integer.parseInt(pnlLandlordNewProp.getTxtStreetNum().replace(",","") );
            String street = pnlLandlordNewProp.getTxtStreet();
            String city = pnlLandlordNewProp.getTxtCity();
            String province = pnlLandlordNewProp.getTxtProvince();
            String postalCode = pnlLandlordNewProp.getTxtPostalCode();
            Quadrant q = Quadrant.valueOf(pnlLandlordNewProp.getCmbQuadrant());
            Address a = new Address(streetNumber, street, city, province, postalCode);
            PropertyType type = PropertyType.valueOf(pnlLandlordNewProp.getCmbPropertyType());
            int bedrooms = Integer.parseInt(pnlLandlordNewProp.getTxtBedrooms().replace(",","") );
            int bathrooms = Integer.parseInt(pnlLandlordNewProp.getTxtBathrooms().replace(",","") );
            int squareFootage = Integer.parseInt(pnlLandlordNewProp.getTxtSquareFootage().replace(",","") );
            int rent = Integer.parseInt(pnlLandlordNewProp.getTxtRent().replace(",","") );
            boolean furnished = pnlLandlordNewProp.getChkFurnished();
            PropertyTraits t = new PropertyTraits(type, bedrooms, bathrooms, squareFootage, furnished);
            PropertyStatus s = PropertyStatus.valueOf(pnlLandlordNewProp.getCmbPropertyStatus());
            Property p = new Property(rent, a, q, s, t);

            controller.sendNewProperty(p);
            //TODO:Get Something back
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            //TODO:Error Message
        }
        MainFrame.setContentPane(pnlLandlordMain.getPnlLandlord());
        MainFrame.validate();

    }


    public void PropertySearch() {
        PropertySearchCriteria c = new PropertySearchCriteria();
        if(pnlRenterSearch.getTxtSquareFootage().trim().length() != 0){
            c.setMinSquareFootage(Integer.parseInt(pnlRenterSearch.getTxtSquareFootage().replace(",","")));
        }
        if (pnlRenterSearch.getTxtBathrooms().trim().length() != 0){
            c.setMinBathrooms(Integer.parseInt(pnlRenterSearch.getTxtBathrooms().replace(",","")));
        }
        if (pnlRenterSearch.getTxtBedrooms().trim().length() != 0){
            c.setMinBedrooms(Integer.parseInt(pnlRenterSearch.getTxtBedrooms().replace(",","")));
        }
        if (pnlRenterSearch.getTxtRent().trim().length() != 0){
            c.setMaxMonthlyRent(Integer.parseInt(pnlRenterSearch.getTxtRent().replace(",","").replace(".","")));
        }
        if(String.valueOf(pnlRenterSearch.getCmbFurnished().getSelectedItem())=="FURNISHED"){
            c.setFurnished(true);
        }
        if(String.valueOf(pnlRenterSearch.getCmbFurnished().getSelectedItem())=="UNFURNISHED"){
            c.setFurnished(false);
        }

        ArrayList<Property> p = controller.sendPropertySearchRequestAndGetResults(c);

        pnlRenterSearchResult  = new pnlRenterSearchResult();
        pnlRenterSearchResult.setController(this);
        pnlRenterSearchResult.fillTable(p);
        MainFrame.setContentPane(pnlRenterSearchResult.getPnlRenterSearchResult());
        MainFrame.revalidate();
    }

    public void goToLandlordProperty() {
        pnlLandlordListings = new pnlLandlordListings();
        pnlLandlordListings.setController(this);
        pnlLandlordListings.fillTable(controller.getLandlordProperties());
        MainFrame.setContentPane(pnlLandlordListings.getPnlLandlordListings());
        MainFrame.revalidate();
    }

    public void NewLandlordPayment() {
        String name = pnlLandordPayment.getTxtCardName();
        String num = pnlLandordPayment.getTxtCardNumber();
        String ccv = pnlLandordPayment.getTxtCCV();
        String date = pnlLandordPayment.getTxtExpirationDate();
        MainFrame.setContentPane(pnlLandlordMain.getPnlLandlord());
        MainFrame.revalidate();
    }

    public void goToEditProperty(Property property) {
        pnlEditProperty = new pnlEditProperty();
        pnlEditProperty.setProperty(property);
        pnlEditProperty.setController(this);
        MainFrame.setContentPane(pnlEditProperty.getPnlEditProperty());
        MainFrame.revalidate();
    }

    public void generateManagerReport() {
        controller.getManagerReport();
        pnlManagerReport = new pnlManagerReport();
        pnlManagerReport.setController(this);
        pnlManagerReport.fillTable(controller.getManagerReport());
        pnlManagerReport.setLabels();
        MainFrame.setContentPane(pnlManagerReport.getPnlManagerReport());
        MainFrame.revalidate();
    }

    public void updateProperty(Property property) {
        controller.editCurrentProperty(property);
    }

    public void viewAllProperties() {
        PropertySearchCriteria c = new PropertySearchCriteria();
        ArrayList<Property> p = controller.sendPropertySearchRequestAndGetResults(c);
        pnlManagerViewProperties = new pnlManagerViewProperties();
        pnlManagerViewProperties.setController(this);
        pnlManagerViewProperties.fillTable(p);
    }

    public void updateFees(int Fee, int period) {
        //TODO:Send Info TO Controller
        MainFrame.setContentPane(pnlManagerMain.getPnlManagerMain());
        MainFrame.revalidate();
    }

    public void goToViewUsers() {
        pnlViewUsers = new pnlViewUsers();
        pnlViewUsers.setController(this);
        //TODO:THIS IS WRONG, SHOULD FILL WITH USERDATA
        pnlViewUsers.fillTable(controller.getLandlordProperties());
        MainFrame.setContentPane(pnlViewUsers.getPnlViewUsers());
        MainFrame.revalidate();
    }

    public void goToSetFees() {
        pnlManagerFees = new pnlManagerFees();
        pnlManagerFees.setController(this);
        MainFrame.setContentPane(pnlManagerFees.getPnlManagerFees());
        MainFrame.revalidate();
    }

    public void goToRegRenterSearch() {
        pnlRenterSearch = new pnlRenterSearch();
        pnlRenterSearch.setController(this);
        JPanel p = pnlRenterSearch.getPnlRenterSearch();
        JButton b = new JButton();
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSearch();
            }
        });
        b.setText("Search");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p.add(b, gbc);
        MainFrame.setContentPane(p);

    }

    private void saveSearch() {
        PropertySearchCriteria c = new PropertySearchCriteria();
        if(pnlRenterSearch.getTxtSquareFootage().trim().length() != 0){
            c.setMinSquareFootage(Integer.parseInt(pnlRenterSearch.getTxtSquareFootage()));
        }
        if (pnlRenterSearch.getTxtBathrooms().trim().length() != 0){
            c.setMinBathrooms(Integer.parseInt(pnlRenterSearch.getTxtBathrooms()));
        }
        if (pnlRenterSearch.getTxtBedrooms().trim().length() != 0){
            c.setMinBedrooms(Integer.parseInt(pnlRenterSearch.getTxtBedrooms()));
        }
        if (pnlRenterSearch.getTxtRent().trim().length() != 0){
            c.setMaxMonthlyRent(Integer.parseInt(pnlRenterSearch.getTxtRent()));
        }
        //TODO:Save Searches
    }

    public void deleteSearch(PropertySearchCriteria propertySearchCriteria) {
        //TODO:Send Search to be deleted
        //controller.
    }

    public void goToEmail(int id){
        pnlSendEmail = new pnlSendEmail();
        pnlSendEmail.setController(this);
        pnlSendEmail.setPropID(id);
        MainFrame.setContentPane(pnlSendEmail.getPnlSendEmail());
        MainFrame.revalidate();
    }
    public void SendEmail(EmailInfo ei) {
        controller.sendEmailToLandlord(ei);
    }

    public void goToSavedSearches() {
        pnlSavedSearches = new pnlSavedSearches();
        pnlSavedSearches.setController(this);
        MainFrame.setContentPane(pnlSavedSearches.getPnlSavedSearches());
        MainFrame.revalidate();
    }

    public void goToPayment() {
        pnlLandordPayment = new pnlLandordPayment();
        pnlLandordPayment.setController(this);
        MainFrame.setContentPane(pnlLandordPayment.getPnlLandlordPayment());
        MainFrame.revalidate();
    }
}
