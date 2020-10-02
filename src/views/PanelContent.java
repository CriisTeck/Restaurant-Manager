package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelContent extends JPanel {
    private ButtonCustomized btnSell;

    public PanelContent(ActionListener listener) {
        this.setLayout(new GridBagLayout());
        initComponents(listener);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents(ActionListener listener) {
        btnSell = new ButtonCustomized("Vender", listener, "Sell");
        ButtonCustomized btnDelivery;
        btnDelivery = new ButtonCustomized("Domicilio", listener, "Delivery");
        ButtonCustomized btnInventoryManager = new ButtonCustomized("<html><p style='text-align:center'>Administrar Inventario</p></html>", listener, "Manager");
        ButtonCustomized btnReports = new ButtonCustomized("<html><p style='text-align:center'>Reporte de Dinero Gastado por los clientes</p></html>", listener, "Reports");
        ButtonCustomized exit = new ButtonCustomized("Salir", listener,"Exit");
        exit.setPreferredSize(new Dimension(80,50));



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0.4;
        gbc.insets = new Insets(80,20,0,20);
        add(btnSell, gbc);

        gbc.gridx = 2;
        add(btnDelivery, gbc);

        gbc.gridx = 3;
        add(btnInventoryManager, gbc);

        gbc.insets = new Insets(20,10,0,10);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.NORTH;
        add(btnReports, gbc);

        gbc.weighty = 0.1;
        gbc.insets = new Insets(0,0,20,20);
        gbc.gridx = 5;
        gbc.gridy = 2;
        add(exit, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(0,0,20,0);
        add(new JLabel(), gbc);
    }
}
