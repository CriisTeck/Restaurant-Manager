package views;

import exceptions.NotSelectedCellException;
import models.Consumable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class WindowManager extends JDialog {

    private JLabel lblProductTitle;
    private JScrollPane jsPaneTable;
    private DefaultTableModel tblAllProductsModel;
    private JTable tblAllProducts;

    private JLabel lblSearchProductManager;
    private JTextField txtSearchProduct;
    private JButton btnEditProductManager;
    private JButton btnRemoveProductManager;
    private JButton btnAddNewProductManager;

    public WindowManager(ActionListener listener, KeyListener kListener, Frame frame) {
        super(frame, true);
        this.setLayout(new GridBagLayout());
        initComponents(listener, kListener);
        this.setSize(920, 700);
        this.setVisible(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener, KeyListener kListener) {

        lblProductTitle = new JLabel("Productos");
        tblAllProducts = new JTable();
        initTableAllProducts();
        jsPaneTable = new JScrollPane();
        jsPaneTable.setPreferredSize(new Dimension(500, 500));
        jsPaneTable.setViewportView(tblAllProducts);
        lblSearchProductManager = new JLabel("Buscar Producto:");
        btnEditProductManager = new JButton("Editar Producto");
        btnEditProductManager.addActionListener(listener);
        btnEditProductManager.setActionCommand("EDIT_PRODUCT_MANAGER");
        txtSearchProduct = new JTextField(15);
        txtSearchProduct.addKeyListener(kListener);
        txtSearchProduct.setName("TXT_SEARCH_MANAGER");
        btnRemoveProductManager = new JButton("Remover Producto");
        btnRemoveProductManager.setActionCommand("REMOVE_PRODUCT_MANAGER");
        btnRemoveProductManager.addActionListener(listener);
        btnRemoveProductManager.setBackground(Color.white);
        btnAddNewProductManager = new JButton("Añadir Producto");
        btnAddNewProductManager.setActionCommand("ADD_PRODUCT_MANAGER");
        btnAddNewProductManager.addActionListener(listener);
        btnAddNewProductManager.setBackground(Color.white);
        btnEditProductManager.setBackground(Color.white);
        posicionateComponents();
    }

    private void initTableAllProducts() {
        String[] nameColumns = {"Id", "Nombre", "Fecha de Expiracion", "Cantidad", "Precio"};
        tblAllProductsModel = new DefaultTableModel(nameColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAllProducts.setModel(tblAllProductsModel);

    }

    private void posicionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(50, 50, 0, 10);
        gbc.gridx = 0;
        gbc.weighty = 0.001;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblProductTitle, gbc);
        gbc.gridwidth = 1;

        gbc.weighty = 1;
        gbc.insets = new Insets(-20, 50, 20, 10);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        add(jsPaneTable, gbc);

        gbc.weighty = 0;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridheight = 1;
        gbc.insets = new Insets(30, 10, 0, 10);
        add(lblSearchProductManager, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(30, 0, 10, 20);
        add(txtSearchProduct, gbc);
        gbc.insets = new Insets(30, 10, 10, 5);
        gbc.gridy++;
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        add(btnEditProductManager, gbc);
        gbc.insets = new Insets(30, 5, 10, 20);
        gbc.gridx = 2;
        add(btnRemoveProductManager, gbc);

        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 80, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(btnAddNewProductManager, gbc);
        this.setBackground(new Color(172, 172, 172));
    }


    public int showSelectedWindow() {
        return JOptionPane.showOptionDialog(this, "¿Que tipo de producto desea Agregar?", "Seleccion producto a agregar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Ingrediente", "Paquete", "Plato"}, 1);
    }

    public void setDatainTable(ArrayList<Consumable> data) {
        for (Consumable consumable : data) {
            tblAllProductsModel.addRow(new Object[]{consumable.getId(), consumable.getName(), consumable.getExpirationDate().toString(), consumable.getQuantity(), consumable.getPrice()});
        }
    }

    public String getTextToSearch() {
            return txtSearchProduct.getText();
    }


    public void updateTableProducts(ArrayList<Consumable> listProbablyResultsAllProducts) {
        tblAllProductsModel.setRowCount(0);
        setDatainTable(listProbablyResultsAllProducts);
    }

    public String getIdFromProductToEdit() throws NotSelectedCellException {
        if(tblAllProducts.getSelectedRowCount() > 0)
       return (String) tblAllProductsModel.getValueAt(tblAllProducts.getSelectedRow(),0);
        else
            throw new NotSelectedCellException();
    }


    public void clear() {
        tblAllProductsModel.setRowCount(0);
    }
}