package views;

import exceptions.EmptyFieldsException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelHeadDelivery extends JPanel {
    private JLabel lblUser;
    private JLabel lblDocument;
    private JLabel lblAddress;
    private JTextField txtDocument;
    private JTextField txtAddress;

    public PanelHeadDelivery() {
        this.setLayout(new GridBagLayout());
        initComponents();
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents() {
        lblUser = new JLabel("Usuario");
        lblDocument = new JLabel("Documento");
        lblAddress = new JLabel("Dirección:");
        txtAddress = new JTextField(10);
        txtDocument = new JTextField(10);
        posicionateComponents();
    }

    private void posicionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        add(lblUser, gbc);

        gbc.gridy = 1;
        add(lblDocument,gbc);
        gbc.gridx = 1;
        add(txtDocument,gbc);
        gbc.gridx = 2;
        add(lblAddress,gbc);
        gbc.gridx = 3;
        add(txtAddress, gbc);
    }

    public boolean validateFields() throws EmptyFieldsException {
        if(txtDocument.getText().equalsIgnoreCase("") || txtAddress.getText().equalsIgnoreCase(""))
            throw new EmptyFieldsException();
        else
            return true;
    }

    public String getDocument() {
        return txtDocument.getText();
    }

    public String getAddress(){
        return txtAddress.getText();
    }
}
