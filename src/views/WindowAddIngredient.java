package views;

import com.toedter.calendar.JDateChooser;
import exceptions.EmptyFieldsException;
import models.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class WindowAddIngredient extends JDialog {
    private JLabel lbltitle;
    private JLabel lblname;
    private JLabel lblExpirationDate;
    private JLabel lblQuantity;
    private JLabel lblPrice;
    private JLabel lblKG;
    private JLabel lblPriceByKG;

    private JTextField txtName;
    private JDateChooser jDateChooser;
    private JTextField txtQuantity;
    private JTextField txtPrice;

    private JButton btnAdd;

    public WindowAddIngredient(ActionListener listener, KeyListener kListener, boolean isEditable, Dialog dialog) {
        super(dialog, true);
        this.setLayout(new GridBagLayout());
        this.setSize(350, 400);
        initComponents(listener, kListener, isEditable);
        this.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener, KeyListener kListener, boolean isEditable) {
        if(isEditable){
        lbltitle = new JLabel("EDITAR INGREDIENTE");
        btnAdd = new JButton("Editar Ingrediente");
        btnAdd.setActionCommand("EDIT_INGREDIENT");

        } else {
        lbltitle = new JLabel("NUEVO INGREDIENTE");
        btnAdd = new JButton("Agregar Ingrediente");
        btnAdd.setActionCommand("ADD_INGREDIENT");
        }
        btnAdd.addActionListener(listener);
        lblname = new JLabel("Nombre :");
        lblExpirationDate = new JLabel("Fecha de " +
                                                       "Vencimiento :");
        lblQuantity = new JLabel("Cantidad");
        lblPrice = new JLabel("Precio :");
        lblKG = new JLabel("kg");
        lblPriceByKG = new JLabel("$/kg");

        txtName = new JTextField(10);
        txtPrice = new JTextField(10);
        txtQuantity = new JTextField(10);
        jDateChooser = new JDateChooser();
        positionateComponents();
    }

    private void positionateComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.2;
        gbc.weightx = 1;
        add(lbltitle, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(lblname, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        add(txtName, gbc);

        gbc.gridy++;
        gbc.gridx--;
        gbc.fill = GridBagConstraints.NONE;
        add(lblExpirationDate, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(jDateChooser, gbc);

        gbc.gridx--;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        add(lblQuantity, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtQuantity, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.NONE;
        add(lblKG, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblPrice, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtPrice, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.NONE;
        add(lblPriceByKG, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(btnAdd, gbc);
    }

    public void setData(Consumable consumable) {
        txtName.setText(consumable.getName());
        txtPrice.setText(String.valueOf(consumable.getPrice()));
        txtQuantity.setText(String.valueOf(consumable.getQuantity()));
        jDateChooser.setDate(Date.valueOf(consumable.getExpirationDate()));
    }

    public String getName(){
        return txtName.getText();
    }

    public LocalDate getLocalDate() {
        return jDateChooser.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Double getTxtQuantity() {
        return Double.parseDouble(txtQuantity.getText());
    }

    public Double getTxtPrice() {

        return Double.parseDouble(txtPrice.getText());
    }

    public void clear(){
        txtQuantity.setText("");
        txtPrice.setText("");
        txtName.setText("");
        jDateChooser.cleanup();
        dispose();
    }

    public boolean isEmpty() throws EmptyFieldsException {
        if(txtName.getText().equalsIgnoreCase("") || txtPrice.getText().equalsIgnoreCase("" )|| txtQuantity.getText().equalsIgnoreCase(""))
            throw new EmptyFieldsException();
        else return false;
    }

}
