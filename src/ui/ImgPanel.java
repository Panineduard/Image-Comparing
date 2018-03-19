package ui;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class ImgPanel extends JPanel implements ActionListener {
    private static boolean switchTimer = false;
    private static int count;
    private static List<BufferedImage> images = new ArrayList<>();
    private static BufferedImage image1;
    private static BufferedImage image2;
    private final int DELAY = 500;
    private Timer mainTimer;

    public static void setImage1(BufferedImage image1) {
        if (!images.contains(image1)) {
            images.add(image1);
        }
        ImgPanel.image1 = image1;
    }

    public static void setImage2(BufferedImage image2) {
        ImgPanel.image2 = image2;
    }

    private void initTimer() {
        mainTimer = new Timer(DELAY, this);
        mainTimer.start();
    }

    public void paint(Graphics g) {
        if (switchTimer) {
            initTimer();
            switchTimer = false;
        }
        int height = 550;
        g.drawImage(image1, 5, 10, 735, height, null);
        g.drawImage(image2, 745, 10, 735, height, null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ImgPanel.image1 = images.get(count);
        count++;
        repaint();
        if (count == images.size()) count = 0;
    }

    public static void addImage(BufferedImage image) {
        images.add(image);
    }

    public static void setSwitchTimer (boolean value) {
        switchTimer = value;
    }
}
