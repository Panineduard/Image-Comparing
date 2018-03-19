package ui;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class ImgPanel extends JPanel implements ActionListener {
    private JLabel headerLabel;
    private JLabel statusLabel;

    static java.util.List<BufferedImage>images=new ArrayList<>();
    private int count;
    static BufferedImage showImage;
    static boolean swithTimer=false;
    private final int DELAY = 500;
    private Timer mainTimer ;
    private   void initTimer() {
        mainTimer = new Timer(DELAY, this);
        mainTimer.start();
    }

    public void paint(Graphics g) {
        if(swithTimer){
            initTimer();
            swithTimer=false;
        }
        int height=550;
//        if(showImage!=null){
//                   height=showImage.getHeight()*735/showImage.getWidth();
//
//        }
        g.drawImage(showImage, 5, 10, 735, height, null);
        g.drawImage(ComparingService.IMAGE2, 745, 10, 735, height, null);
    }

    private void showSliderDemo(){
        headerLabel.setText("Control in action: JSlider");
        JSlider slider= new JSlider();
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusLabel.setText("Value : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });
        this.add(slider);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        showImage=images.get(count);
        count++;
        repaint();
        if(count==images.size()-1)count=0;
    }
    public static void addImages(BufferedImage helpImg1){images.add(helpImg1);}
}
