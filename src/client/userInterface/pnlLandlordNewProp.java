package client.userInterface;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class pnlLandlordNewProp {
    private JPanel pnlLandlordNewProp;
    private JTextField txtStreet;
    private JFormattedTextField txtStreetNum;
    private JComboBox cmbQuadrant;
    private JComboBox cmbPropertyType;
    private JFormattedTextField txtBedrooms;
    private JFormattedTextField txtBathrooms;
    private JFormattedTextField txtSquareFootage;
    private JCheckBox chkFurnished;
    private JComboBox cmbPropertyStatus;
    private JButton btnSubmit;
    private JTextField txtCity;
    private JTextField txtProvince;
    private JTextField txtPostalCode;
    private JFormattedTextField txtRent;
    private GUIController controller;

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public pnlLandlordNewProp() {
        $$$setupUI$$$();
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewLandlordProperty();
            }
        });
    }

    public JPanel getPnlLandlordNewProp() {
        return pnlLandlordNewProp;
    }

    public String getTxtStreet() {
        return txtStreet.getText();
    }

    public String getTxtStreetNum() {
        return txtStreetNum.getText();
    }

    public String getCmbQuadrant() {
        return String.valueOf(cmbQuadrant.getSelectedItem());
    }

    public String getCmbPropertyType() {
        return String.valueOf(cmbPropertyType.getSelectedItem());
    }

    public String getTxtBedrooms() {
        return txtBedrooms.getText();
    }

    public String getTxtBathrooms() {
        return txtBathrooms.getText();
    }

    public String getTxtSquareFootage() {
        return txtSquareFootage.getText();
    }

    public boolean getChkFurnished() {
        return chkFurnished.isSelected();
    }

    public String getCmbPropertyStatus() {
        return String.valueOf(cmbPropertyStatus.getSelectedItem());
    }

    public String getTxtCity() {
        return txtCity.getText();
    }

    public String getTxtProvince() {
        return txtProvince.getText();
    }

    public String getTxtPostalCode() {
        return txtPostalCode.toString();
    }

    public String getTxtRent() {
        return txtRent.getText();
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
        pnlLandlordNewProp = new JPanel();
        pnlLandlordNewProp.setLayout(new GridBagLayout());
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlLandlordNewProp.add(spacer2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Enter New Property Information:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Street name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Street Number:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("City:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Quadrant:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Province: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label6, gbc);
        txtStreet = new JTextField();
        txtStreet.setMinimumSize(new Dimension(120, 30));
        txtStreet.setPreferredSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtStreet, gbc);
        txtCity = new JTextField();
        txtCity.setMinimumSize(new Dimension(120, 30));
        txtCity.setOpaque(false);
        txtCity.setPreferredSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtCity, gbc);
        txtProvince = new JTextField();
        txtProvince.setMinimumSize(new Dimension(120, 30));
        txtProvince.setOpaque(false);
        txtProvince.setPreferredSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtProvince, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Property Type:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Bedrooms:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Bathrooms:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Square Footage:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Furnished:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label11, gbc);
        final JLabel label12 = new JLabel();
        label12.setText("Property Status:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label12, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlLandlordNewProp.add(spacer4, gbc);
        final JLabel label13 = new JLabel();
        label13.setText("Postal Code:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label13, gbc);
        txtPostalCode = new JTextField();
        txtPostalCode.setMinimumSize(new Dimension(120, 30));
        txtPostalCode.setOpaque(false);
        txtPostalCode.setPreferredSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtPostalCode, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(spacer5, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(cmbPropertyType, gbc);
        txtBedrooms.setMinimumSize(new Dimension(120, 30));
        txtBedrooms.setPreferredSize(new Dimension(120, 30));
        txtBedrooms.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtBedrooms, gbc);
        txtBathrooms.setMinimumSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtBathrooms, gbc);
        txtSquareFootage.setMinimumSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtSquareFootage, gbc);
        chkFurnished = new JCheckBox();
        chkFurnished.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(chkFurnished, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(cmbPropertyStatus, gbc);
        btnSubmit = new JButton();
        btnSubmit.setText("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(btnSubmit, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlLandlordNewProp.add(spacer6, gbc);
        final JLabel label14 = new JLabel();
        label14.setText("Monthly Rent:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        pnlLandlordNewProp.add(label14, gbc);
        txtRent.setMinimumSize(new Dimension(120, 30));
        txtRent.setOpaque(false);
        txtRent.setPreferredSize(new Dimension(120, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtRent, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(txtStreetNum, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlLandlordNewProp.add(cmbQuadrant, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlLandlordNewProp;
    }

    private void createUIComponents() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        txtStreetNum = new JFormattedTextField(formatter);

        txtBathrooms = new JFormattedTextField(formatter);
        txtBedrooms = new JFormattedTextField(formatter);
        NumberFormat formatD = new DecimalFormat();
        formatD.setMinimumFractionDigits(0);
        formatD.setMinimumFractionDigits(2);

        NumberFormatter formatterD = new NumberFormatter(formatD);
        //formatterD.setValueClass(Double.class);
//        formatterD.setMinimum(0);
//        formatterD.setMaximum(Double.MAX_VALUE);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatterD.setCommitsOnValidEdit(true);
        txtRent = new JFormattedTextField(formatterD);
        //Square feet is int
        txtSquareFootage = new JFormattedTextField(formatter);
        cmbPropertyType = new JComboBox();
        cmbPropertyType.addItem("House");
        cmbPropertyType.addItem("Duplex");
        cmbPropertyType.addItem("Townhouse");
        cmbPropertyType.addItem("Apartment");
        cmbPropertyType.addItem("Condo");
        cmbPropertyType.addItem("Mainfloor");
        cmbPropertyType.addItem("Basement");
        cmbPropertyStatus = new JComboBox();
        cmbPropertyStatus.addItem("AVAILABLE");
        cmbPropertyStatus.addItem("SUSPENDED");
        cmbPropertyStatus.addItem("RENTED");
        cmbPropertyStatus.addItem("REMOVED");
        cmbQuadrant = new JComboBox();
        cmbQuadrant.addItem("NW");
        cmbQuadrant.addItem("NE");
        cmbQuadrant.addItem("SW");
        cmbQuadrant.addItem("SE");
    }
}
