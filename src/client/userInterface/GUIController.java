package client.userInterface;

import descriptor.Address;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;

import java.awt.*;

public class GUIController {
    private GUIFrame MainFrame;

    private pnlEditProperty pnlEditProperty;
    private pnlLandlordMain pnlLandlordMain;
    private pnlLandlordNewProp pnlLandlordNewProp;
    private pnlLogin pnlLogin;
    private pnlManagerMain pnlManagerMain;
    private pnlRenterSearch pnlRenterSearch;
    private pnlStart pnlStart;
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

    public static void main(String[] args) {
		GUIController g = new GUIController();
	}

    public void ValidateLogin(String username, String password) {
        //TODO: make login info class
        //TODO: Send String to server, Validate Input
        System.out.println("Username="+username+"\n Password:"+password);
        //Assume Landlord
        pnlLandlordMain = new pnlLandlordMain();
        pnlLandlordMain.setController(this);
        MainFrame.setContentPane(pnlLandlordMain.getPnlLandlord());
        MainFrame.revalidate();

    }

    public void goToNewProp() {
        pnlLandlordNewProp = new pnlLandlordNewProp();
        MainFrame.setContentPane(pnlLandlordNewProp.getPnlLandlordNewProp());
        MainFrame.revalidate();
    }

    public void NewLandlordProperty() {
        int rent = Integer.parseInt(pnlLandlordNewProp.getTxtRent());
        int streetNumber = Integer.parseInt(pnlLandlordNewProp.getTxtStreetNum());
        String street = pnlLandlordNewProp.getTxtStreet();
        String city = pnlLandlordNewProp.getTxtCity();
        String province = pnlLandlordNewProp.getTxtProvince();
        String postalCode = pnlLandlordNewProp.getTxtPostalCode();
        Quadrant q = Quadrant.valueOf(pnlLandlordNewProp.getCmbQuadrant());
        Address a = new Address(streetNumber,street,city,province,postalCode);
        PropertyType type = PropertyType.valueOf(pnlLandlordNewProp.getCmbPropertyType());
        int bedrooms = Integer.parseInt(pnlLandlordNewProp.getTxtBedrooms());
        int bathrooms = Integer.parseInt(pnlLandlordNewProp.getTxtBathrooms());
        double squareFootage = Double.parseDouble(pnlLandlordNewProp.getTxtSquareFootage());
        boolean furnished = pnlLandlordNewProp.getChkFurnished();
        PropertyTraits t = new PropertyTraits(type,bedrooms,bathrooms,squareFootage,furnished);
        PropertyStatus s = PropertyStatus.valueOf(pnlLandlordNewProp.getCmbPropertyStatus());
        Property p = new Property(rent,a,q,s,t);
    }
}
