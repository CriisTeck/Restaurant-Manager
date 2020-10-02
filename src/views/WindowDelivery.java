package views;

import exceptions.EmptyFieldsException;
import models.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class WindowDelivery extends JDialog {

    private PanelHeadDelivery panelHeadDelivery;
    private PanelContentDelivery panelContentDelivery;

    public WindowDelivery(ActionListener listener, KeyListener kListener, Frame frame, boolean modal) {
        super(frame, modal);
        this.setLayout(new GridBagLayout());
        initComponents(listener, kListener);
        this.setVisible(false);
        this.setSize(600,800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener, KeyListener kListener) {
        panelContentDelivery = new PanelContentDelivery(listener,kListener);
        panelHeadDelivery = new PanelHeadDelivery();
        posicionateComponents();
    }

    private void posicionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelHeadDelivery, gbc);
        gbc.weighty = 0.75;
        gbc.gridy = 1;
        add(panelContentDelivery, gbc);
    }

    public void putItemAtStart(ArrayList<Consumable> data){
        panelContentDelivery.putItemAtStart(data);
    }


    public String getListProductText() {
        return panelContentDelivery.getListProductText();
    }

    public void updateListProducts(ArrayList<Consumable> listProbablyResults) {
        panelContentDelivery.updateListProducts(listProbablyResults);
    }

    public void setNewProduct_inTable(Consumable consumable){
        panelContentDelivery.setNewProduct_inTable(consumable);
    }

    public Consumable getProductSelected(){
        return panelContentDelivery.getSelectedItem();
    }

    public void close() {
        panelContentDelivery.clear();
        this.dispose();
    }

    public void removeRowSelected() {
        panelContentDelivery.removeProduct_fromTable();
    }

    public boolean validateFields() throws EmptyFieldsException {
        return panelContentDelivery.validateFields();
    }

    public ArrayList<String> getLisProducts(){
        return panelContentDelivery.getListProducts();
    }

    public String getDocument(){
        return panelHeadDelivery.getDocument();
    }
    public String getAddress(){
        return panelHeadDelivery.getAddress();
    }

    public double getTotalPrice(){
        return panelContentDelivery.getTotalPrice();

    }
}