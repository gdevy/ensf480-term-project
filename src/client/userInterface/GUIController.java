package client.userInterface;

import descriptor.*;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import client.mvc.Controller;

import java.awt.*;
import java.util.ArrayList;

public class GUIController {
    private GUIFrame MainFrame;
    private Controller controller;

    private pnlEditProperty pnlEditProperty;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private pnlLandlordMain pnlLandlordMain;
    private pnlLandlordNewProp pnlLandlordNewProp;
    private pnlLogin pnlLogin;
    private pnlManagerMain pnlManagerMain;
    private pnlRenterSearch pnlRenterSearch;
    private pnlStart pnlStart;
    private pnlRenterSearchResult pnlRenterSearchResult;
    private pnlLandlordListings pnlLandlordListings;
    //TODO: Manager and landlord communications

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
        //TODO: Send String to server, Validate Input
        System.out.println("Username="+username+"\n Password:"+password);

        UserTypeLogin userType = controller.sendLoginAttemptAndGetResult(loginInfo);

        switch( userType )
        {
            case LOGIN_FAILED:

                break;
            case REGISTERED_RENTER:

                break;
            case LANDLORD:
                pnlLandlordMain = new pnlLandlordMain();
                pnlLandlordMain.setController(this);
                MainFrame.setContentPane(pnlLandlordMain.getPnlLandlord());
                break;
            case MANAGER:
                //pnlManagerMain = new pnlManagerMain();
                //pnlManagerMain.setController(this);
                //MainFrame.setContentPane(pnlManagerMain.getPnlManagerMain());
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
    }

    public static void main(String[] args) {
        GUIController g = new GUIController();
        g.setController( new Controller() );
    }

    public void PropertySearch() {
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
        if (pnlRenterSearch.getTxtCity().trim().length() != 0){
            //TODO:SearchCriteria City
        }
        if (pnlRenterSearch.getTxtProvince().trim().length() != 0) {

        }
        if (pnlRenterSearch.getTxtRent().trim().length() != 0){
            c.setMaxMonthlyRent(Integer.parseInt(pnlRenterSearch.getTxtRent()));
        }
        //TODO:Send c to server

        ArrayList<Property> p = controller.sendPropertySearchRequestAndGetResults(c);
        //TODO:Fill arrayList

        pnlRenterSearchResult  = new pnlRenterSearchResult();
        pnlRenterSearchResult.setController(this);
        MainFrame.setContentPane(pnlRenterSearchResult.getPnlRenterSearchResult());
        MainFrame.revalidate();
        //TODO:Populate Table
//        pnlRenterSearchResult.fillTable(p);
    }

    public void goToLandlordProperty() {
        pnlLandlordListings = new pnlLandlordListings();
        pnlLandlordListings.setController(this);
        MainFrame.setContentPane(pnlLandlordListings.getPnlLandlordListings());
        MainFrame.revalidate();
    }
}
