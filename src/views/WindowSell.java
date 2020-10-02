package views;

import exceptions.EmptyFieldsException;
import models.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class WindowSell extends JDialog {
    private PanelHeadSell panelHeadSell;
    private PanelContentSell panelContentSell;

    public WindowSell(ActionListener listener, KeyListener kListener, Frame frame, boolean modal) {
        super(frame, modal);
        this.setLayout(new GridBagLayout());
        initComponents(listener, kListener);
        this.setVisible(false);
        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener, KeyListener kListener) {
        panelHeadSell = new PanelHeadSell(listener, kListener);
        panelContentSell = new PanelContentSell(listener, kListener);
        positionateComponents();
    }

    private void positionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelHeadSell, gbc);
        gbc.weighty = 0.75;
        gbc.gridy = 1;
        add(panelContentSell, gbc);
    }

    public void putDataOnStart(ArrayList<Consumable> data ){
        panelHeadSell.putItemAtStart(data);
    }

    public String getListProductText() {
        return panelHeadSell.getListProductText();
    }

    public void updateListProducts(ArrayList<Consumable> listProbablyResults) {
        panelHeadSell.updateListProducts(listProbablyResults);
    }

    public void setNewProduct_inTable(Consumable consumable){
        panelContentSell.setNewProduct_inTable(consumable);
    }

    public Consumable getProductSelected(){
        return panelHeadSell.getSelectedItem();
    }

    public void close() {
        panelHeadSell.clear();
        panelContentSell.clear();
        this.dispose();
    }

    public void removeRowSelected() {
        panelContentSell.removeProduct_fromTable();
    }

    public boolean validateFields() throws EmptyFieldsException {
        return panelContentSell.validateFields();
    }

    public ArrayList<String> getLisProducts(){
        return panelContentSell.getListProducts();
    }

    public String getDocument(){
        return panelContentSell.getDocument();
    }

    public double getTotalPrice(){
        return panelContentSell.getTotalPrice();

    }
}
