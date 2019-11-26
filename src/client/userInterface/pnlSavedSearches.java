package client.userInterface;

import entity.socket.PropertySearchCriteria;
import entity.socket.property.Property;
import entity.socket.property.PropertyType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class pnlSavedSearches {
    private JTable tblResults;
    private JButton deleteSearchButton;
    private JPanel pnlSavedSearches;
    private GUIController controller;
    private ArrayList<PropertySearchCriteria> c;

    public JPanel getPnlSavedSearches() {
        return pnlSavedSearches;
    }

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public pnlSavedSearches() {
        $$$setupUI$$$();
        tblResults.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                deleteSearchButton.setEnabled(true);
                //System.out.println(tblResults.getValueAt(tblResults.getSelectedRow(), 0).toString());
            }
        });
        deleteSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteSearch(c.get(tblResults.getSelectedRow()));
            }
        });
    }

    public void fillTable(ArrayList<PropertySearchCriteria> c) {
        this.c = c;
        DefaultTableModel d = (DefaultTableModel) tblResults.getModel();
        d.setRowCount(0);
        for (PropertySearchCriteria p : c) {
            if (p.getTypes().isEmpty()){
                String[] data = { "N/A", p.getMaxMonthlyRent() + "",
                        p.getMinBedrooms() + "", p.getMinBathrooms() + "", p.getMinSquareFootage() + "", p.getMinSquareFootage() + ""};
                for(int i = 0;i<data.length;i++){
                    data[i] = data[i].replaceAll("-1","N/A");
                }
                d.addRow(data);
            }
            for (PropertyType pt : p.getTypes()) {
                String[] data = {pt + "", p.getMaxMonthlyRent() + "",
                        p.getMinBedrooms() + "", p.getMinBathrooms() + "", p.getMinSquareFootage() + "", p.getMinSquareFootage() + ""};
                for(int i = 0;i<data.length;i++){
                    data[i] = data[i].replaceAll("-1","N/A");
                }
                d.addRow(data);
            }
        }
    }

    private void createUIComponents() {
        DefaultTableModel d = new DefaultTableModel();
        String[] columns = {"Property Type", "Max Rent", "Min Bedrooms", "Min Bathrooms", "Min Square Footage", "Is Furnished"};
        d.setColumnCount(6);
        d.setColumnIdentifiers(columns);
        tblResults = new JTable(d);
        // TODO: place custom component creation code here
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
        pnlSavedSearches = new JPanel();
        pnlSavedSearches.setLayout(new GridBagLayout());
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlSavedSearches.add(spacer1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Your Searches");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pnlSavedSearches.add(label1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlSavedSearches.add(spacer2, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(500, 350));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        pnlSavedSearches.add(scrollPane1, gbc);
        scrollPane1.setViewportView(tblResults);
        deleteSearchButton = new JButton();
        deleteSearchButton.setEnabled(false);
        deleteSearchButton.setText("Delete Search");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        pnlSavedSearches.add(deleteSearchButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlSavedSearches;
    }

}
