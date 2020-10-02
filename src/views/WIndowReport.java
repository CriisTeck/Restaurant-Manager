package views;

import models.Sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class WIndowReport extends JDialog implements WindowListener{
    private static String[][] dataL;
    private static String[] header;
    private BarGraphicPanel barsGraphicPanel;

    public WIndowReport(Window owner, ArrayList<Sale> data) {
        super(owner);
        this.setSize(1000, 1000);
        initComponents();
        initData(data);
        this.setVisible(true);
        barsGraphicPanel.createConventions(this.getLocationOnScreen().getX(), this.getLocationOnScreen().getY(), this.getWidth());
        barsGraphicPanel.setVisible(true);
        this.addWindowListener(this);
        this.getContentPane().setBackground(new Color(172, 172, 172));
    }

    private static void initData(ArrayList<Sale> data) {
        dataL = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataL[i] = data.get(i).toArray();
        }
        header = new String[]{"Clientes(Documento)", "Total Gastado"};

    }

    public static String[][] getDataL() {
        return dataL;
    }

    public static String[] getHeader() {
        return header;
    }

    private void initComponents() {
        barsGraphicPanel = new BarGraphicPanel();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        barsGraphicPanel.setLayout(new GridBagLayout());

        contentPane.add(barsGraphicPanel, new GridBagConstraints(1, 1, 1, GridBagConstraints.REMAINDER, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(20, 20, 20, 20), 0, 0));

        setLocationRelativeTo(null);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        barsGraphicPanel.closeConventions();
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
