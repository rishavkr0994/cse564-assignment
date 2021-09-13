package com.assignment02;

/**
 * Description Text
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class City {
    private int num;
    private double x;
    private double y;

    /**
     * Description Text
     * @return
     */
    public int getNum() {
        return num;
    }

    /**
     * Description Text
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Description Text
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Description Text
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Description Text
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Description Text
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Description Text
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof City city) {
            return city.getX() == this.getX() && city.getY() == this.getY();
        } else return false;
    }
}
