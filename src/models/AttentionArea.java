package models;

import java.util.Map;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class AttentionArea {
    private Point pMax;
    private Point pMin;

    public AttentionArea(Map<String, Point> pointsMap) {
        this.pMax = pointsMap.get("max");
        this.pMin = pointsMap.get("min");
    }

    public Point getMaxPoint() {
        return pMax;
    }

    public Point getMinPoint() {
        return pMin;
    }


    public boolean isCrossingArea(AttentionArea polygon){
        if(this.pMin.getX() <= polygon.pMin.getX() && polygon.pMin.getX() <= this.pMax.getX()) {
            if(this.pMin.getY() <= polygon.pMin.getY() && polygon.pMin.getY() <= this.pMax.getY()) return true;
            if(this.pMin.getY() <= polygon.pMin.getY() && polygon.pMax.getY() <= this.pMax.getY()) return true;
        }
        return false;
    }

    public String toString(){
        return "point 1 =" + pMax +"; point 2 =" + pMin;
    }
}
