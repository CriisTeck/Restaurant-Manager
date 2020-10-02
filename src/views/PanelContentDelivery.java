package views;

import exceptions.EmptyFieldsException;
import models.Consumable;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PanelContentDelivery extends JPanel {
    private JLabel lblProduct;
    private JComboBox<Object> cmbListProducts;
    private JButton btnAddProduct;
    private String textToSearch;

    private JScrollPane jspTableProducts;
    private JLabel lblTotal;
    private JTable tableProducts;
    private JButton btnSell;
    private JLabel lblSubTotal;
    private JLabel lblList;
    private JLabel lblIva;
    private DefaultTableModel tableDataModel;

    public PanelContentDelivery(ActionListener listener, KeyListener kListener) {
        this.setLayout(new GridBagLayout());
        initComponents(listener,kListener);
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener, KeyListener kListener) {
        lblProduct = new JLabel("Productos:");
        cmbListProducts = new JComboBox<>();
        cmbListProducts.getEditor().getEditorComponent().setName("CMB_LIST_PRODUCTS_DELIVERY");
        cmbListProducts.setEditable(true);
        cmbListProducts.getEditor().getEditorComponent().addKeyListener(kListener);
        btnAddProduct = new JButton("Agregar");
        btnAddProduct.addActionListener(listener);
        btnAddProduct.setActionCommand("addProductToDelivery");
        btnAddProduct.setBackground(Color.white);

        lblList = new JLabel("Productos");

        jspTableProducts = new JScrollPane();
        tableProducts = new JTable();
        tableProducts.addKeyListener(kListener);
        jspTableProducts.setViewportView(tableProducts);
        jspTableProducts.setPreferredSize(new Dimension(300, 400));
        initTableData();

        btnSell = new JButton("Vender");
        btnSell.addActionListener(listener);
        btnSell.setActionCommand("DELIVERY_PRODUCT");
        btnSell.setBackground(Color.white);

        lblSubTotal = new JLabel(Constants.DEFAULT_VALUE_LBL_SUBTOTAL);
        lblIva = new JLabel("IVA: $0.00");
        lblTotal = new JLabel("Total: $0.00");
        positionateComponents();
    }

    private void initTableData() {
        String[] nameColumns = {"Id", "Nombre", "Precio"};
        tableDataModel = new DefaultTableModel(nameColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableProducts.setModel(tableDataModel);
        tableProducts.getColumnModel().getColumn(0).setPreferredWidth(5);
        tableProducts.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableProducts.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableProducts.setName("TABLE_PRODUCTS_DELIVERY");
    }

    private void positionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblProduct, gbc);
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        add(cmbListProducts, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.2;
        add(btnAddProduct, gbc);

        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblList, gbc);

        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(jspTableProducts, gbc);

        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 20, 20, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.gridheight = 3;
        add(btnSell, gbc);

        gbc.insets = new Insets(0, 20, 20, 20);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        gbc.gridx = 3;
        add(lblSubTotal, gbc);
        gbc.weighty = 0;
        gbc.gridy = 5;
        add(lblIva, gbc);
        gbc.gridy = 6;
        add(lblTotal, gbc);
    }

    public void putItemAtStart(ArrayList<Consumable> data){
        for (Consumable consumable : data){
            cmbListProducts.addItem(consumable);
        }
        cmbListProducts.getEditor().selectAll();
    }

    public Consumable getSelectedItemDelivery() {
        cmbListProducts.getEditor().selectAll();
        cmbListProducts.showPopup();
        if (cmbListProducts.getSelectedItem().getClass().getSuperclass() != Consumable.class && !cmbListProducts.getEditor().getItem().toString().equals(""))
            return (Consumable) cmbListProducts.getItemAt(1);
        else if (cmbListProducts.getSelectedItem().getClass().getSuperclass() == Consumable.class)
            return (Consumable) cmbListProducts.getSelectedItem();
        else
            return null;
    }

    public String getListProductTextDelivery() {
        textToSearch = cmbListProducts.getEditor().getItem().toString();
        cmbListProducts.addItem(textToSearch);
        return textToSearch;
    }

    public void updateListProductsDelivery(ArrayList<Consumable> listProbablyResults) {

        cmbListProducts.removeAllItems();
        cmbListProducts.addItem(textToSearch);
        for (Consumable consumable : listProbablyResults) {
            cmbListProducts.addItem(consumable);
            cmbListProducts.hidePopup();
            cmbListProducts.showPopup();
        }
    }

    public void setNewProduct_inTable(Consumable consumable) {
        tableDataModel.addRow(new Object[]{consumable.getId(), consumable.getName(), consumable.getPrice()});
        updateLabelPrice();
    }

    private void updateLabelPrice(){
        double totalPrice = getTotalPrice();
        lblTotal.setText("Total: $" + totalPrice);
        lblIva.setText("IVA: $" + (totalPrice * Constants.IVA2020));
        lblSubTotal.setText("Subtotal: $" + (totalPrice - (totalPrice * Constants.IVA2020)));
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < tableDataModel.getRowCount(); i++) {
            totalPrice += (double) tableDataModel.getValueAt(i,2);
        }
        return totalPrice;
    }

    public void clear(){
        cmbListProducts.removeAllItems();
        textToSearch = "";

        lblSubTotal.setText(Constants.DEFAULT_VALUE_LBL_SUBTOTAL);
        lblTotal.setText(Constants.DEFAULT_VALUE_LBL_TOTAL);
        lblIva.setText(Constants.DEFAULT_VALUE_LBL_IVA);
        tableProducts.removeAll();
        while(tableDataModel.getRowCount() > 0)
            tableDataModel.removeRow(0);
    }

    public void removeProduct_fromTable(){
        for (int i = 0; i < tableProducts.getSelectedRows().length; i++) {
            tableDataModel.removeRow(tableProducts.getSelectedRow());
            removeProduct_fromTable();
        }
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

    public boolean validateFields() throws EmptyFieldsException {
        if(tableProducts.getRowCount() == 0)
            throw new EmptyFieldsException();
        else
            return true;
    }

    public ArrayList<String> getListProducts() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < tableProducts.getRowCount(); i++) {
            ids.add((String) tableDataModel.getValueAt(i,0));
        }
        return ids;
    }

}