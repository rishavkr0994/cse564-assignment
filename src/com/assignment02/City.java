package com.assignment02;

public class City {
    private double x;
    private double y;
    private int num;

    public City() { }
    public City(double x, double y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
    }

    public double getX() {
        return x;
    }
    public void setX(Double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(Double y) { this.y = y; }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
}
