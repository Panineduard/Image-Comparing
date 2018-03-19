package service;

import models.*;
import models.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class ComparingService {
    /**
     * this method comparing two Images
     */

    public static BufferedImage compare(ImagesClass images, int rgbFault) {
        BufferedImage image1 = images.getFirstImage();
        BufferedImage image2 = images.getSecondImage();
        List<Point> points = new ArrayList<>();

        if (image1.getHeight() > image2.getHeight()) {
            image1 = resizeImage(image1, image2.getWidth(), image2.getHeight());
        }
        if (image1.getHeight() < image2.getHeight()) {
            image2 = resizeImage(image2, image1.getWidth(), image1.getHeight());
        }

        for (int y = 0; y < image1.getHeight(); y++) {
            for (int x = 0; x < image1.getWidth(); x++) {
                if (compareRGB(image1.getRGB(x, y), image2.getRGB(x, y)) >= rgbFault) {
                    points.add(new Point(x, y));
                }
            }
        }
        List<AttentionArea> attentionAreas=new ArrayList<>();

        findWrongArea(points, 45, 15).forEach(pointList -> {
            attentionAreas.add(new AttentionArea(getMinMaxPoint(pointList)));
        });

        return getImageWithFault(image1, filteredWrongPolygons(attentionAreas));
    }

    private static Map<String, Point> getMinMaxPoint (List<Point> points){
        Map<String, Point> result = new HashMap<>();
        Point pointMAX = new Point(0, 0);
        Point pointMIN = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        points.forEach(h -> {
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
        result.put("max", pointMAX);
        result.put("min", pointMIN);
        return result;
    }

    private static List<List<Point>> findWrongArea(List<Point> points, int distance, int countOfMinPixels) {
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
        for (List<Point> point : result){
            if(point.size() < countOfMinPixels){
                error.add(point);
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

    private static int compareRGB(int RGB1, int RGB2) {
        Color color1 = new Color(RGB1);
        Color color2 = new Color(RGB2);
        int[] rgb = new int[3];
        rgb[0] = Math.abs(color1.getRed() - color2.getRed());
        rgb[1] = Math.abs(color1.getBlue() - color2.getBlue());
        rgb[2] = Math.abs(color1.getGreen() - color2.getGreen());
        return (int) Math.round(IntStream.of(rgb).average().getAsDouble());
    }

    private static List<AttentionArea> filteredWrongPolygons (List<AttentionArea> polygons) {
        List<AttentionArea> result = new ArrayList<>(polygons);
        List<AttentionArea> removedAreas = new ArrayList<>();
        for (int i = 0; i < polygons.size(); i++){
            for (int y = i+1; y < polygons.size(); y++) {
                if (polygons.get(i).isCrossingArea(polygons.get(y))){
                    List<Point> points = new ArrayList<>();
                    points.add(polygons.get(i).getMinPoint());
                    points.add(polygons.get(i).getMaxPoint());
                    points.add(polygons.get(y).getMaxPoint());
                    points.add(polygons.get(y).getMinPoint());
                    result.add(new AttentionArea(getMinMaxPoint(points)));
                    removedAreas.add(polygons.get(i));
                    removedAreas.add(polygons.get(y));
                }
            }
        }
        result.removeAll(removedAreas);

        return result;
    }

    private static BufferedImage getImageWithFault(BufferedImage image, List<AttentionArea> attentionAreas) {
        BufferedImage result = createNewImg(image);
        attentionAreas.forEach(area -> {
            Graphics2D g2d = result.createGraphics();
            BasicStroke newStroke = new BasicStroke(3.0F);
            g2d.setStroke(newStroke);
            g2d.setColor(Color.red);
            g2d.drawRoundRect(area.getMinPoint().getX() - 10, area.getMinPoint().getY() - 10,
                    area.getMaxPoint().getX() - area.getMinPoint().getX() + 40
                    , area.getMaxPoint().getY() - area.getMinPoint().getY() + 40, 5, 5);
        });
        return result;
    }
}

