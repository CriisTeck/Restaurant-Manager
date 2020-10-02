package views;

import models.Consumable;
import models.Dish;
import models.Ingredient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WindowAddDish extends JDialog implements WindowListener {
    private JLabel lblTitle;
    private JLabel lblName;
    private JTextField txtName;
    private JComboBox<Object> cmbSelectIngredients;
    private JButton btnAddIngredient;
    private JScrollPane jspTable;
    private JTable tblIngredients;
    private JButton btnAddDish;
    private DefaultTableModel tblModel;
    private String textToSearch = "";

    public WindowAddDish(ActionListener listener, KeyListener kListener, boolean isForEditProduct, Dialog dialog) {
        super(dialog, true);
        this.setLayout(new GridBagLayout());
        this.setSize(350, 400);
        initComponents(listener, kListener, isForEditProduct);
        this.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(172, 172, 172));
        this.addWindowListener(this);
    }

    private void initComponents(ActionListener listener, KeyListener kListener, boolean isForEditProduct) {
        cmbSelectIngredients = new JComboBox<>();
        if (isForEditProduct) {
            lblTitle = new JLabel("EDITAR PLATO");

            btnAddDish = new JButton("Editar Plato");
            btnAddDish.setActionCommand("EDIT_DISH");
            btnAddDish.addActionListener(listener);
            cmbSelectIngredients.getEditor().getEditorComponent().setName("CMB_LIST_PRODUCTS_MANAGER_EDIT");
        } else {
            lblTitle = new JLabel("AGREGAR PLATO");
            btnAddDish = new JButton("Agregar Plato");
            btnAddDish.setActionCommand("ADD_DISH");
            btnAddDish.addActionListener(listener);
            cmbSelectIngredients.getEditor().getEditorComponent().setName("CMB_LIST_PRODUCTS_MANAGER_ADD");
        }

        lblName = new JLabel("Nombre: ");
        txtName = new JTextField(10);
        cmbSelectIngredients.setPreferredSize(new Dimension(150, (int) cmbSelectIngredients.getPreferredSize().getHeight()));
        cmbSelectIngredients.setEditable(true);
        cmbSelectIngredients.getEditor().getEditorComponent().addKeyListener(kListener);
        btnAddIngredient = new JButton("Agregar");
        btnAddIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNewProduct_inTable(getSelectedItem());
            }
        });

        jspTable = new JScrollPane();
        tblIngredients = new JTable();
        jspTable.setPreferredSize(new Dimension(300, 120));
        jspTable.setViewportView(tblIngredients);
        initTableModel();
        positionateComponents();

        cmbSelectIngredients.getEditor().getEditorComponent().requestFocus();
        cmbSelectIngredients.getEditor().setItem(textToSearch);
        cmbSelectIngredients.getEditor().selectAll();
    }

    private void positionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        add(lblName, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx++;
        gbc.gridwidth = 2;
        add(txtName, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        add(cmbSelectIngredients, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(btnAddIngredient, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(jspTable, gbc);

        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy++;
        add(btnAddDish, gbc);
    }

    public void putItemAtStart(ArrayList<Ingredient> data){
        for (Consumable consumable : data){
            cmbSelectIngredients.addItem(consumable);
        }
        cmbSelectIngredients.getEditor().selectAll();
    }

    private void initTableModel() {
        String[] columnNames = {"Id", "Nombre", "Fecha de Vencimiento"};
        tblModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblIngredients.setModel(tblModel);
    }

    private void updateTableData(ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients)
            tblModel.addRow(new Object[]{ingredient.getId(), ingredient.getName(), ingredient.getExpirationDate()});

    }

    public void setData(Consumable consumable) {
        txtName.setText(consumable.getName());
        updateTableData(((Dish) consumable).getIngredients());
        cmbSelectIngredients.requestFocus();
    }

    //COMBOBOX AUTOMATICO
    public Ingredient getSelectedItem() {
        cmbSelectIngredients.getEditor().selectAll();
        cmbSelectIngredients.showPopup();
        if (cmbSelectIngredients.getSelectedItem().getClass() != Ingredient.class && !cmbSelectIngredients.getEditor().getItem().toString().equals(""))
            return (Ingredient) cmbSelectIngredients.getItemAt(1);
        else if (cmbSelectIngredients.getSelectedItem().getClass() == Ingredient.class)
            return (Ingredient) cmbSelectIngredients.getSelectedItem();
        else
            return null;
    }

    public String getListProductText() {
        textToSearch = cmbSelectIngredients.getEditor().getItem().toString();
        cmbSelectIngredients.addItem(textToSearch);
        return textToSearch;
    }

    public void updateListProducts(ArrayList<Ingredient> listProbablyResults) {

        cmbSelectIngredients.removeAllItems();
        cmbSelectIngredients.addItem(textToSearch);
        for (Ingredient consumable : listProbablyResults) {
            cmbSelectIngredients.addItem(consumable);
            cmbSelectIngredients.hidePopup();
            cmbSelectIngredients.showPopup();
        }
    }

    public void setNewProduct_inTable(Ingredient consumable) {
        tblModel.addRow(new Object[]{consumable.getId(), consumable.getName(), consumable.getExpirationDate()});
    }

    public boolean isFillAlCamps() {
        return !txtName.equals("") && tblIngredients.getRowCount() != 0;
    }

    public ArrayList<String> getIngredients(){
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i <tblIngredients.getRowCount(); i++) {
            data.add((String) tblModel.getValueAt(i,0));
        }
        return  data;
    }

    public String getNameIng(){
        return txtName.getText();
    }

    public void clear(){
        txtName.setText("");
        tblModel.setRowCount(0);
        cmbSelectIngredients.removeAllItems();
        textToSearch = "Ingrese un Ingrediente";
        dispose();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        clear();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}