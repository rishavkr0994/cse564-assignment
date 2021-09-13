package com.assignment02;

public class City {
    private int num;
    private double x;
    private double y;

    public City() { }
    public City(int num, double x, double y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof City city) {
            return city.getX() == this.getX() && city.getY() == this.getY();
        } else return false;
    }
}
