package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonCustomized extends JButton {
    public ButtonCustomized(String text, ActionListener listener, String actionCommand) {
        super(text);
        this.setBackground(new Color(33, 34, 38, 154));
        this.setPreferredSize(new Dimension(150,120));
        this.setForeground(Color.white);
        this.setActionCommand(actionCommand);
        this.addActionListener(listener);
    }
}
