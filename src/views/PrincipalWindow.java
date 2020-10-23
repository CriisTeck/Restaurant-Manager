package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class PrincipalWindow extends JFrame  {

    public PrincipalWindow(ActionListener listener) throws MalformedURLException {
        this.setUndecorated(true);
        this.setSize(800, 800);
        this.setLayout(new GridBagLayout());
        initComponents(listener);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(new Color(0,0,0, 225));
    }

    private void initComponents(ActionListener listener) throws MalformedURLException {
        PanelTitle panelTitle = new PanelTitle();
        PanelContent panelContent = new PanelContent(listener);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10,10,10,10);
        add(panelTitle, gbc);

        gbc.weighty = 0.6;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        add(panelContent, gbc);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccesfull() {
        JOptionPane.showMessageDialog(this,"Operacion Exitosa","EXITO",JOptionPane.INFORMATION_MESSAGE);
    }
}
