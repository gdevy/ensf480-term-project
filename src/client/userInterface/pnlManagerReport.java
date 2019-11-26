package client.userInterface;

import entity.socket.ManagerReport;
import entity.socket.property.Property;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class pnlManagerReport {
    private JPanel pnlManagerReport;
    private JTable tblActiveListings;
    private JLabel txtPeriod;
    private JLabel txtNumRentedHouses;
    private JLabel txtNumHousesListed;
    private JLabel txtNumActiveListings;
    private GUIController controller;
    private ManagerReport properties;

    public JPanel getPnlManagerReport() {
        return pnlManagerReport;
    }

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public void fillTable(ManagerReport properties) {
        this.properties = properties;
        DefaultTableModel d = (DefaultTableModel) tblActiveListings.getModel();
        d.setRowCount(0);
        for (Property p : properties.listPropertiesRented) {
            String[] data = {p.getAddress().getStreetNumber() + "", p.getAddress().getStreet(),
                    p.getQuadrant().toString(), p.getTraits().getFurnished() + "", p.getTraits().getBedrooms() + "", p.getTraits().getBathrooms() + ""};
            d.addRow(data);
        }
        //Todo:Make Manager Report
    }

    public void setLabels() {
        txtNumActiveListings.setText(properties.propertiesActive + "");
        txtNumHousesListed.setText(properties.propertiesListed + "");
        txtNumRentedHouses.setText(properties.propertiesRented + "");

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
        createUIComponents();
        pnlManagerReport = new JPanel();
        pnlManagerReport.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        pnlManagerReport.add(panel1, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("Number of Houses Rented:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel1.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Number of Houses Listed:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Active Listings:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel1.add(label3, gbc);
        txtPeriod = new JLabel();
        txtPeriod.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(txtPeriod, gbc);
        txtNumRentedHouses = new JLabel();
        txtNumRentedHouses.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(txtNumRentedHouses, gbc);
        txtNumHousesListed = new JLabel();
        txtNumHousesListed.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(txtNumHousesListed, gbc);
        txtNumActiveListings = new JLabel();
        txtNumActiveListings.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(txtNumActiveListings, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(500, 350));
        pnlManagerReport.add(scrollPane1, BorderLayout.CENTER);
        scrollPane1.setViewportView(tblActiveListings);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlManagerReport;
    }

    private void createUIComponents() {
        DefaultTableModel d = new DefaultTableModel();
        String[] columns = {"Street number", "Street Name", "Quadrant", "Is Furnished", "Bedrooms", "Bathrooms"};
        d.setColumnCount(6);
        d.setColumnIdentifiers(columns);
        tblActiveListings = new JTable(d);
    }
}
