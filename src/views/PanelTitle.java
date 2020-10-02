package views;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PanelTitle extends JPanel {

    public PanelTitle() throws MalformedURLException {
        this.setLayout(new GridBagLayout());
        initComponents();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(new Color(172, 172, 172));
    }

    private void initComponents() throws MalformedURLException {
        JLabel lblTitle = new JLabel("RESTAURANT");
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.fill = GridBagConstraints.BOTH;

        add(lblTitle, gbc);
    }

}