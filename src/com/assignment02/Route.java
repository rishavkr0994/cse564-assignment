package com.assignment02;

/**
 * Description Text
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class Route {
    private City src;
    private City dest;
    private double dist;

    /**
     * Description Text
     * @return
     */
    public City getSrc() {
        return src;
    }

    /**
     * Description Text
     * @param src
     */
    public void setSrc(City src) {
        this.src = src;
    }

    /**
     * Description Text
     * @return
     */
    public City getDest() {
        return dest;
    }

    /**
     * Description Text
     * @param dest
     */
    public void setDest(City dest) {
        this.dest = dest;
    }

    /**
     * Description Text
     * @return
     */
    public double getDist() {
        return dist;
    }

    /**
     * Description Text
     * @param distance
     */
    public void setDist(double distance) {
        this.dist = distance;
    }
}
