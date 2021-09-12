package com.assignment02;

public class Route {
    private City src;
    private City dest;
    private double dist;

    public Route() { }
    public Route(City src, City dest, double dist) {
        this.src = src;
        this.dest = dest;
        this.dist = dist;
    }

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

    public double getDist() {
        return dist;
    }
    public void setDist(double distance) {
        this.dist = distance;
    }
}
