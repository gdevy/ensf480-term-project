package client.userInterface;

import javax.swing.*;
import java.awt.*;

public class pnlManagerMain {
    private JButton viewSummaryOfListingsButton;
    private JButton editAListingButton;
    private JButton viewLandlordMessagesButton;
    private JButton manageFeesButton;
    private JButton viewLandlordsButton;
    private JButton viewRentersButton;
    private JPanel pnlManagerMain;

    public JButton getViewSummaryOfListingsButton() {
        return viewSummaryOfListingsButton;
    }

    public void setViewSummaryOfListingsButton(JButton viewSummaryOfListingsButton) {
        this.viewSummaryOfListingsButton = viewSummaryOfListingsButton;
    }

    public JButton getEditAListingButton() {
        return editAListingButton;
    }

    public void setEditAListingButton(JButton editAListingButton) {
        this.editAListingButton = editAListingButton;
    }

    public JButton getViewLandlordMessagesButton() {
        return viewLandlordMessagesButton;
    }

    public void setViewLandlordMessagesButton(JButton viewLandlordMessagesButton) {
        this.viewLandlordMessagesButton = viewLandlordMessagesButton;
    }

    public JButton getManageFeesButton() {
        return manageFeesButton;
    }

    public void setManageFeesButton(JButton manageFeesButton) {
        this.manageFeesButton = manageFeesButton;
    }

    public JButton getViewLandlordsButton() {
        return viewLandlordsButton;
    }

    public void setViewLandlordsButton(JButton viewLandlordsButton) {
        this.viewLandlordsButton = viewLandlordsButton;
    }

    public JButton getViewRentersButton() {
        return viewRentersButton;
    }

    public void setViewRentersButton(JButton viewRentersButton) {
        this.viewRentersButton = viewRentersButton;
    }

    public JPanel getPnlManagerMain() {
        return pnlManagerMain;
    }

    public void setPnlManagerMain(JPanel pnlManagerMain) {
        this.pnlManagerMain = pnlManagerMain;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pnlManagerMain = new JPanel();
        pnlManagerMain.setLayout(new GridBagLayout());
        viewSummaryOfListingsButton = new JButton();
        viewSummaryOfListingsButton.setText("View Summary of listings");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(viewSummaryOfListingsButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlManagerMain.add(spacer2, gbc);
        editAListingButton = new JButton();
        editAListingButton.setText("Edit a listing");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(editAListingButton, gbc);
        viewLandlordMessagesButton = new JButton();
        viewLandlordMessagesButton.setText("View Landlord Messages");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(viewLandlordMessagesButton, gbc);
        manageFeesButton = new JButton();
        manageFeesButton.setText("Manage Fees");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(manageFeesButton, gbc);
        viewLandlordsButton = new JButton();
        viewLandlordsButton.setText("View Landlords");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(viewLandlordsButton, gbc);
        viewRentersButton = new JButton();
        viewRentersButton.setText("View Renters");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(viewRentersButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlManagerMain.add(spacer3, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlManagerMain;
    }

}