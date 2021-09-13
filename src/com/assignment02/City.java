package com.assignment02;

/**
 * This class is the data structure for storing information about a city i.e. the city number and its x, y co-ordinates
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
     * Get the city number
     * @return city number
     */
    public int getNum() {
        return num ;
    }

    /**
     * Set the city number
     * @param num city number
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Get the x co-ordinate of the city
     * @return x co-ordinate of the city
     */
    public double getX() {
        return x;
    }

    /**
     * Set the x co-ordinate of the city
     * @param x x co-ordinate of the city
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the y co-ordinate of the city
     * @return y co-ordinate of the city
     */
    public double getY() {
        return y;
    }

    /**
     * Set the y co-ordinate of the city
     * @param y y co-ordinate of the city
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Compares the current object with another object for value equality and returns true only when <code>obj</code>
     * object is of type City and has the same x and y co-ordinates.
     *
     * @param obj object to be compared
     * @return boolean value indicating whether <code>obj</code> value equals the current object
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof City city) {
            return city.getX() == this.getX() && city.getY() == this.getY();
        } else return false;
    }
}
