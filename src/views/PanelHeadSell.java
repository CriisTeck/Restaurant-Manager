package views;

import models.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PanelHeadSell extends JPanel {

    private JLabel lblProduct;
    private JComboBox<Object> cmbListProducts;
    private JButton btnAddProduct;
    private String textToSearch;

    public PanelHeadSell(ActionListener listener, KeyListener kListener) {
        this.setLayout(new GridBagLayout());
        initComponents(listener, kListener);
        this.setBackground(new Color(172, 172, 172));

    }

    private void initComponents(ActionListener listener, KeyListener kListener) {
        lblProduct = new JLabel("Productos:");
        cmbListProducts = new JComboBox<>();
        cmbListProducts.getEditor().getEditorComponent().setName("CMB_LIST_PRODUCTS");
        cmbListProducts.setEditable(true);
        cmbListProducts.getEditor().getEditorComponent().addKeyListener(kListener);
        btnAddProduct = new JButton("Agregar");
        btnAddProduct.setActionCommand("AddProduct");
        btnAddProduct.addActionListener(listener);
        btnAddProduct.setBackground(Color.white);
        positionateComponents();
    }

    public void putItemAtStart(ArrayList<Consumable> data){
        for (Consumable consumable : data)
            cmbListProducts.addItem(consumable);
        cmbListProducts.getEditor().selectAll();
    }

    private void positionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblProduct, gbc);
        gbc.weightx = 0.8;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(cmbListProducts, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.2;
        add(btnAddProduct, gbc);
    }

    public Consumable getSelectedItem() {
        cmbListProducts.getEditor().selectAll();
        cmbListProducts.showPopup();
        if (cmbListProducts.getSelectedItem().getClass().getSuperclass() != Consumable.class && !cmbListProducts.getEditor().getItem().toString().equals(""))
            return (Consumable) cmbListProducts.getItemAt(1);
        else if (cmbListProducts.getSelectedItem().getClass().getSuperclass() == Consumable.class)
            return (Consumable) cmbListProducts.getSelectedItem();
        else
            return null;
    }

    public String getListProductText() {
        textToSearch = cmbListProducts.getEditor().getItem().toString();
        cmbListProducts.addItem(textToSearch);
        return textToSearch;
    }

    public void updateListProducts(ArrayList<Consumable> listProbablyResults) {

        cmbListProducts.removeAllItems();
        cmbListProducts.addItem(textToSearch);
        for (Consumable consumable : listProbablyResults) {
            cmbListProducts.addItem(consumable);
            cmbListProducts.hidePopup();
            cmbListProducts.showPopup();
        }
    }

    public void clear(){
        cmbListProducts.removeAllItems();
        textToSearch = "";
    }
}