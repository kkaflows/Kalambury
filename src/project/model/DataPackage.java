package project.model;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017-05-05.
 */
public class DataPackage implements Serializable {

    private String text;
    private double x, y;

    public DataPackage(String text, double x, double y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

