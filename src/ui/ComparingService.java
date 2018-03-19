package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class ComparingService {
    static int RGBfault = 1000000;
    static BufferedImage IMAGE1;
    static BufferedImage IMAGE2;

    private List<List<Point>> findImagesArea(List<Point> points, int distance,int countOfMinPixels) {
        List<List<Point>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.get(0).add(points.get(0));
        outer:
        for (Point point : points) {
            for (List<Point> po : result) {
                for (Point point2 : po) {
                    if ((int) Math.round(point2.distance(point)) > 0) {
                        for (List<Point> p2 : result) {
                            for (Point point3 : p2) {
                                if ((int) Math.round(point.distance(point3)) < distance) {
                                    p2.add(point);
                                    continue outer;
                                }
                            }
                        }
                        List<Point> points1 = new ArrayList<>();
                        points1.add(point);
                        result.add(points1);
                        continue outer;
                    }
                }
            }
        }
        List<List<Point>>error=new ArrayList<>();
        for (List<Point>p:result){
            if(p.size()<countOfMinPixels){
                error.add(p);
            }
        }
        result.removeAll(error);
        return result;
    }

    private static BufferedImage createNewImg(BufferedImage image) {
        BufferedImage returnImg = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = returnImg.createGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        return returnImg;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    /**
     * this method comparing two Images
     */
    public static void compare() {
        if(IMAGE1 !=null|| IMAGE2 !=null){
            if (IMAGE1.getHeight() > IMAGE2.getHeight()) {
                BufferedImage imageHelp = resizeImage(IMAGE1, IMAGE2.getWidth(), IMAGE2.getHeight());
                IMAGE1 = imageHelp;
            }
            if (IMAGE1.getHeight() < IMAGE2.getHeight()) {
                BufferedImage imageHelp = resizeImage(IMAGE2, IMAGE1.getWidth(), IMAGE1.getHeight());
                IMAGE2 = imageHelp;
            }
            BufferedImage helpImg1 = createNewImg(IMAGE1);
            BufferedImage helpImg2 = createNewImg(IMAGE1);
            final List<Point> list = new ArrayList<Point>();
            List<Point> points = new ArrayList<>();
            for (int y = 0; y < IMAGE1.getHeight(); y++) {
                for (int x = 0; x < IMAGE1.getWidth(); x++) {
                    int RGBhelp = IMAGE1.getRGB(x, y) - IMAGE2.getRGB(x, y);
                    if (RGBhelp < -RGBfault || RGBhelp > RGBfault) {
                        points.add(new Point(x, y));
//                    helpImg1.setRGB(x,y,Color.black.getRGB());
//                    helpImg2.setRGB(x, y, Color.blue.getRGB());
//                    helpImg3.setRGB(x, y, Color.yellow.getRGB());
                    }

                }
            }
            List<AttentionArea>attentionAreas=new ArrayList<>();
            new ComparingService().findImagesArea(points, 150,15).forEach(f -> {
                Point pointMAX = new Point(0, 0);
                Point pointMIN = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
                f.forEach(h -> {
                    if (h.getX() > pointMAX.getX()) {
                        pointMAX.setX(h.getX());
                    }
                    if (h.getY() > pointMAX.getY()) {
                        pointMAX.setY(h.getY());
                    }
                    if (h.getX() < pointMIN.getX()) {
                        pointMIN.setX(h.getX());
                    }
                    if (h.getY() < pointMIN.getY()) {
                        pointMIN.setY(h.getY());
                    }
                });
                attentionAreas.add(new AttentionArea(pointMAX, pointMIN));
            });
//            System.out.println(attentionAreas);
            attentionAreas.forEach(area -> {
//        ui.AttentionArea area=attentionAreas.get(0);
                Graphics2D g2d = helpImg1.createGraphics();
                BasicStroke newStroke = new BasicStroke(4.0F);
                g2d.setStroke(newStroke);
                g2d.setColor(Color.red);
                g2d.drawRoundRect(area.getP2().getX() - 10, area.getP2().getY() - 10,
                        area.getP1().getX() - area.getP2().getX() + 40
                        , area.getP1().getY() - area.getP2().getY() + 40, 5, 5);
            });

//        g2d.fill(new Rectangle2D.Float(20, 40, 340, 20));
            ImgPanel.images.add(helpImg1);
            ImgPanel.images.add(helpImg2);
        }

    }
}
