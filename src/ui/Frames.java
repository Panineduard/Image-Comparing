package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class Frames {
    private JFrame mainFrame = new JFrame("Compare images");
    private boolean startCant=true;
    private JComponent positionComponent(JComponent component, int positionX, int positionY) {
        Dimension size1 = component.getPreferredSize();
        component.setBounds(positionX, positionY, size1.width, size1.height);
        return component;
    }
    /**
     * It is a main method for UI*/
    public void showFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1505, 800);
        final ImgPanel imgPanel = new ImgPanel();
        JButton firsImage = new JButton("Add first image");
        JButton secondImage = new JButton("Add second image");
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        imgPanel.add(firsImage);
        firsImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Add first Image");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImgPanel.images.add(img);
                    ImgPanel.showImage = img;
                    ComparingService.IMAGE1 = img;
                    mainFrame.repaint();
                }
            }
        });
        secondImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Add second image");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ComparingService.IMAGE2 = img;
                    mainFrame.repaint();
                }
            }
        });
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(startCant){
                    if(ComparingService.IMAGE1 !=null|| ComparingService.IMAGE2 !=null){
                        startCant=false;
                        ComparingService.compare();
                        ImgPanel.swithTimer = true;
                        mainFrame.repaint();
                    }
                    else JOptionPane.showMessageDialog(new JFrame(), "Please select an image!");
                }
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        firsImage.setBounds(250, 600, 250, 40);
        secondImage.setBounds(1100, 600, 250, 40);
        start.setBounds(700, 700, 100, 50);
        stop.setBounds(800,700,100,50);
        mainFrame.add(firsImage);
        mainFrame.add(secondImage);
        mainFrame.add(start);
        mainFrame.add(stop);
        imgPanel.setLayout(null);

        JLabel jlabel = new JLabel("COMPARING PERCENTAGE");
        jlabel.setBounds(660, 620, 150, 20);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -1500000, -300000, -900000);
        slider.setBounds(620, 650, 250, 20);
        slider.setMajorTickSpacing(100000);
        slider.setPaintTicks(true);
        slider.setSize(250, 50);
        slider.setVisible(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComparingService.RGBfault = -((JSlider) e.getSource()).getValue();
            }
        });
        mainFrame.add(slider);
        mainFrame.add(jlabel);
        mainFrame.add(imgPanel);
        mainFrame.setVisible(true);
    }
}
