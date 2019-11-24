package client.userInterface;

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

    }
}
