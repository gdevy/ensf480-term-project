package client.userInterface;

import entity.socket.property.Property;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class pnlManagerViewProperties {
    private JTable tblResults;
    private JButton editPropertyButton;
    private JPanel pnlManagerViewProperties;
    private ArrayList<Property> properties;
    private GUIController controller;

    public pnlManagerViewProperties() {
        $$$setupUI$$$();
        tblResults.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                editPropertyButton.setEnabled(true);
                //System.out.println(tblResults.getValueAt(tblResults.getSelectedRow(), 0).toString());
            }
        });
        editPropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.goToEditProperty(properties.get(tblResults.getSelectedRow()));
            }
        });
    }

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public void fillTable(ArrayList<Property> properties) {
        this.properties = properties;
        DefaultTableModel d = (DefaultTableModel) tblResults.getModel();
        d.setRowCount(0);
        for (Property p : properties) {
            String[] data = {p.getAddress().getStreetNumber() + "", p.getAddress().getStreet(),
                    p.getQuadrant().toString(), p.getTraits().getFurnished() + "", p.getTraits().getBedrooms() + "", p.getTraits().getBathrooms() + ""};
            d.addRow(data);
        }
        //Todo:Clear table, Add more Data
    }

    private void createUIComponents() {
        DefaultTableModel d = new DefaultTableModel();
        String[] columns = {"Street number", "Street Name", "Quadrant", "Is Furnished", "Bedrooms", "Bathrooms"};
        d.setColumnCount(6);
        d.setColumnIdentifiers(columns);
        tblResults = new JTable(d);
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
        pnlManagerViewProperties = new JPanel();
        pnlManagerViewProperties.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("All Properties");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        pnlManagerViewProperties.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlManagerViewProperties.add(spacer1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        pnlManagerViewProperties.add(scrollPane1, gbc);
        scrollPane1.setViewportView(tblResults);
        editPropertyButton = new JButton();
        editPropertyButton.setEnabled(false);
        editPropertyButton.setText("Edit Property");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        pnlManagerViewProperties.add(editPropertyButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlManagerViewProperties;
    }
}
