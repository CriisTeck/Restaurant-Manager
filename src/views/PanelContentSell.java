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

public class PanelContentSell extends JPanel {
    private JScrollPane jspTableProducts;
    private JLabel lblTotal;
    private JTable tableProducts;
    private JLabel lblDocument;
    private JButton btnSell;
    private JTextField txtDocument;
    private JLabel lblSubTotal;
    private JLabel lblList;
    private JLabel lblIva;
    private DefaultTableModel tableDataModel;


    public PanelContentSell(ActionListener listener, KeyListener kListener) {
        this.setLayout(new GridBagLayout());
        initComponents(listener, kListener);
        this.setBackground(new Color(172, 172, 172));

    }

    private void initComponents(ActionListener listener, KeyListener kListener) {
        lblList = new JLabel("Productos");
        lblDocument = new JLabel("Documento:");
        lblDocument.setBackground(Color.BLUE);
        txtDocument = new JTextField(10);
        txtDocument.addKeyListener(kListener);
        txtDocument.setName("TXT_DOCUMENT");

        jspTableProducts = new JScrollPane();
        tableProducts = new JTable();
        tableProducts.addKeyListener(kListener);
        jspTableProducts.setViewportView(tableProducts);
        jspTableProducts.setPreferredSize(new Dimension(300, 400));
        initTableData();

        btnSell = new JButton("Vender");
        btnSell.addActionListener(listener);
        btnSell.setActionCommand("SELL_PRODUCT");
        btnSell.setBackground(Color.white);

        lblSubTotal = new JLabel(Constants.DEFAULT_VALUE_LBL_SUBTOTAL);
        lblIva = new JLabel("IVA: $0.00");
        lblTotal = new JLabel("Total: $0.00");
        posicionateComponents();
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
        tableProducts.setName("TABLE_PRODUCTS");
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

    private void posicionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblList, gbc);

        gbc.gridx = 2;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.9;
        add(lblDocument, gbc);
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        add(txtDocument, gbc);

        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(jspTableProducts, gbc);

        gbc.weightx = 0;
        gbc.insets = new Insets(0, 20, 20, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridheight = 3;
        add(btnSell, gbc);

        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        gbc.gridx = 3;
        add(lblSubTotal, gbc);
        gbc.weighty = 0;
        gbc.gridy = 4;
        add(lblIva, gbc);
        gbc.gridy = 5;
        add(lblTotal, gbc);
    }

    public void clear(){
        lblSubTotal.setText(Constants.DEFAULT_VALUE_LBL_SUBTOTAL);
        lblTotal.setText(Constants.DEFAULT_VALUE_LBL_TOTAL);
        lblIva.setText(Constants.DEFAULT_VALUE_LBL_IVA);
        tableProducts.removeAll();
        while(tableDataModel.getRowCount() > 0)
            tableDataModel.removeRow(0);
        txtDocument.setText("");
    }

    public void removeProduct_fromTable(){
        for (int i = 0; i < tableProducts.getSelectedRows().length; i++) {
            tableDataModel.removeRow(tableProducts.getSelectedRow());
            removeProduct_fromTable();
        }
    }

    public boolean validateFields() throws EmptyFieldsException {
        if(tableProducts.getRowCount() == 0 || txtDocument.getText().equalsIgnoreCase(""))
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

    public String getDocument() {
        return txtDocument.getText();
    }
}