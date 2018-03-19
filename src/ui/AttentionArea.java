package ui;

/**
 * Created by Panin Eduard on 15.09.2016.
 */
public class AttentionArea {
    private Point p1;
    private Point p2;

    public AttentionArea(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }
    public String toString(){
        return "point 1 ="+p1+";point 2 ="+p2;
    }
}
