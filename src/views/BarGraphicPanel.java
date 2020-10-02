package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class BarGraphicPanel extends JPanel{

    private static final long serialVersionUID = 1L;
    private String[][] data;
    private String[] header;
    private JDialog conventions;
    private JPanel panel = new JPanel();
    private ArrayList<Color> colorConventions;

    public BarGraphicPanel() {
        super();
        this.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
        this.setBackground(new Color(172, 172, 172));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        getData();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        paintBarGraphic(g2d);

        g2d.setColor(Color.black);

        int counter = findScaleGraphic(findMaximumData())*10;
        for (int i = 0; i < 10; i++) {
            g2d.drawString(String.valueOf(counter), 25,(i*(getHeight()-50)/10)+18);
            counter-=findScaleGraphic(findMaximumData());

        }
        char[] word = "Total Gastado".toCharArray();
        for (int i = 0; i < word.length; i++) {
            g2d.drawString(String.valueOf(word[i]), 5, i*((getWidth()-50)/20)+50);
        }

        g2d.drawString("Clientes(Documento)", (getWidth()/2)-(getWidth()/32), getHeight()-5);

        if(this.isVisible() == false)
            conventions.dispose();
    }

    private void getData() {
        data = WIndowReport.getDataL();
        header = WIndowReport.getHeader();

    }

    private void paintBarGraphic(Graphics2D g2d) {
        g2d.drawLine(50, 0, 50,this.getHeight());
        int heightLastLine = 10*((getHeight()-50)/10)+15;
        int heightTest = ((getHeight()-50)/10);
        g2d.drawLine(0, heightLastLine, getWidth(), heightLastLine);

        for (int i = 0; i <= 10; i++) {
            g2d.drawLine(50, i*((getHeight()-50)/10)+15, 40, i*((getHeight()-50)/10)+15);
        }

        int halfWidth = (((getWidth()-50)/(data.length+1))+100)/2;

        colorConventions = new ArrayList<Color>();
        for (int i = 0; i < data.length; i++) {
            Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
            colorConventions.add(color);
            g2d.setColor(color);
            g2d.fillRect(((i+1)*((getWidth()-50)/(data.length+1))+100) - halfWidth,heightLastLine,((getWidth()-50)/(data.length+1)) , (int) -((double)heightTest*(Double.parseDouble(data[i][1])/(findScaleGraphic(findMaximumData())*10))*10.0));
            g2d.setColor(Color.black);
            g2d.drawString(data[i][1], i*((getWidth()-50)/(data.length+1))+85, (int)(heightLastLine-((double)heightTest*(Double.parseDouble(data[i][1])/(findScaleGraphic(findMaximumData())*10)*10.0)))-3);
        }

        for (int i = 0; i < data.length; i++) {
            g2d.setColor(Color.black);
            g2d.drawLine(i*((getWidth()-50)/(data.length+1))+90, heightLastLine, i*((getWidth()-50)/(data.length+1))+90, (heightLastLine+10));
            g2d.setColor(colorConventions.get(i));
            g2d.fillRect(i*((getWidth()-50)/(data.length+1))+88, (heightLastLine+15), 5, 5);
        }

        panel.repaint();
    }

    public void closeConventions() {
        conventions.dispose();
    }

    public void createConventions(double x,double y, int width) {
        conventions = new JDialog();
        conventions.setLayout(new BorderLayout());
        conventions.setSize(160,300);
        conventions.setLocation((int)x + width,(int)y);
        conventions.setVisible(true);
        conventions.setResizable(false);
        conventions.setTitle("Convenciones");
        conventions.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        conventions.getContentPane().setBackground(new Color(172,172,172));
        conventions.setBackground(new Color(172,172,172));

        panel = new JPanel() {

            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < data.length; i++) {
                    g.setColor(colorConventions.get(i));
                    g.fillRect(10, i*(this.getHeight()/data.length)+10, 10, 10);
                    g.setColor(Color.black);
                    g.drawString(data[i][0], 25, i*(this.getHeight()/data.length)+20);
                }
            }
        };

        panel.setBackground(new Color(172,172,172));

        conventions.add(panel);
    }


    private int findScaleGraphic(double maximumData) {
        return maximumData > 10 ? (int) (maximumData % 10 == 0 ? maximumData / 10 : (maximumData + (10 - (maximumData % 10))) / 10) : 1;
    }

    private double findMaximumData() {
        double maximum = Double.parseDouble(data[0][1]);
        for (int i = 0; i < data.length; i++) {
            if(Double.parseDouble(data[i][1]) > maximum)
                maximum = Double.parseDouble(data[i][1]);
        }
        return maximum;
    }

    public void setVisibleFalseWindow(boolean b) {
        this.setVisible(b);
        if(this.isVisible() == false)
            conventions.dispose();
    }



}