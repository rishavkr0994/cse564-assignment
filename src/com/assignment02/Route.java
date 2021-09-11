package com.assignment02;

public class Route {
    public City getSrc() {
        return src;
    }

    public void setSrc(City src) {
        this.src = src;
    }

    public City getDest() {
        return dest;
    }

    public void setDest(City dest) {
        this.dest = dest;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private City src;
    private City dest;
    private double distance;
}
