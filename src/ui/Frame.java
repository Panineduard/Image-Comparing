package ui;

import models.ImagesClass;
import service.ComparingService;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class Frame {
    private JFrame mainFrame = new JFrame("Compare images");
    private boolean canStart = true;
    private int rgbFault = 10;

    /**
     * It is a main method for UI
     */
    public void showFrame() {

        /*Components*/
        ImagesClass images = new ImagesClass();
        ImgPanel imgPanel = new ImgPanel();
        JButton firsImageButton = new JButton("Add first image");
        JButton secondImageButton = new JButton("Add second image");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JLabel jlabel = new JLabel("COMPARING PERCENTAGE");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, rgbFault);

        /*Actions handlers*/
        firsImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectedFile = new JFileChooser();
                int ret = selectedFile.showDialog(null, "Add first Image");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    images.putImage(selectedFile.getSelectedFile(), 1);
                    imgPanel.setImage1(images.getFirstImage());
                    mainFrame.repaint();
                }
            }
        });

        secondImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectedFile = new JFileChooser();
                int ret = selectedFile.showDialog(null, "Add second image");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    images.putImage(selectedFile.getSelectedFile(), 2);
                    imgPanel.setImage2(images.getSecondImage());
                    mainFrame.repaint();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (canStart) {
                    if (images.isNotNull()) {
                        BufferedImage image = ComparingService.compare(images, rgbFault);
                        canStart = false;
                        imgPanel.addImage(image);
                        imgPanel.setSwitchTimer(true);
                        mainFrame.repaint();
                    } else JOptionPane.showMessageDialog(new JFrame(), "Please select an images!");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rgbFault = ((JSlider) e.getSource()).getValue();
            }
        });

        /*Sizing*/
        firsImageButton.setBounds(250, 600, 250, 40);
        secondImageButton.setBounds(1100, 600, 250, 40);
        startButton.setBounds(700, 700, 100, 50);
        stopButton.setBounds(800, 700, 100, 50);
        jlabel.setBounds(660, 620, 150, 20);
        slider.setBounds(620, 650, 250, 20);


        imgPanel.setLayout(null);

        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFont(new Font("Serif", Font.ITALIC, 15));
        slider.setSize(250, 50);
        slider.setVisible(true);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1505, 800);
        mainFrame.add(slider);
        mainFrame.add(jlabel);
        mainFrame.add(firsImageButton);
        mainFrame.add(secondImageButton);
        mainFrame.add(startButton);
        mainFrame.add(stopButton);
        mainFrame.add(imgPanel);
        mainFrame.setVisible(true);
    }
}
