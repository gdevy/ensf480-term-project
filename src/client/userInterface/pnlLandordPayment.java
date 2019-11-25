package client.userInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pnlLandordPayment {
    private JTextField txtCardNumber;
    private JTextField txtCardName;
    private JTextField txtExpirationDate;
    private JTextField txtCCV;
    private JButton submitPaymentButton;
    private JPanel pnlLandlordPayment;
    private GUIController controller;

    public pnlLandordPayment() {
        submitPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewLandlordPayment();
            }
        });
    }

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public JPanel getPnlLandlordPayment() {
        return pnlLandlordPayment;
    }

    public String getTxtCardNumber() {
        return txtCardNumber.getText();
    }

    public String getTxtCardName() {
        return txtCardName.getText();
    }

    public String getTxtExpirationDate() {
        return txtExpirationDate.getText();
    }

    public String getTxtCCV() {
        return txtCCV.getText();
    }
}
