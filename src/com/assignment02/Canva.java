package com.assignment02;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Description
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class Canva extends JPanel {
    private static final double X_GRACE = 20.0;
    private static final double Y_GRACE = 20.0;
    private static final double POINT_DIAMETER = 6.0;

    private ArrayList<City> cityList = new ArrayList<>();
    private ArrayList<Route> routeList = new ArrayList<>();

    private double minX, maxX, minY, maxY = 0.0;

    /**
     * Description Text
     */
    public Canva() {
        setBackground(Color.decode("#F0F8FF"));
        setBorder(BorderFactory.createLineBorder(Color.decode("#002D62")));
    }

    /**
     * Description Text
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double scaleX = (maxX - minX) != 0 ? ((getWidth()  - X_GRACE * 2) / (maxX - minX)) : 1;
        double scaleY = (maxY - minY) != 0 ? ((getHeight() - Y_GRACE * 2) / (maxY - minY)) : 1;

        Graphics2D g2D = (Graphics2D) g;
        if (cityList != null && cityList.size() > 0) {
            g2D.setColor(Color.decode("#00308F"));
            for (City city : cityList) {
                double x1 = (city.getX() - minX) * scaleX + X_GRACE - POINT_DIAMETER / 2;
                double y1 = (city.getY() - minY) * scaleY + Y_GRACE - POINT_DIAMETER / 2;
                g2D.fill(new Ellipse2D.Double(x1, y1, POINT_DIAMETER, POINT_DIAMETER));
            }
        }

        if (routeList != null && routeList.size() > 0) {
            g2D.setColor(Color.decode("#7CB9E8"));
            for (Route route : routeList) {
                double x1 = (route.getSrc().getX()  - minX) * scaleX + X_GRACE;
                double y1 = (route.getSrc().getY()  - minY) * scaleY + Y_GRACE;
                double x2 = (route.getDest().getX() - minX) * scaleX + X_GRACE;
                double y2 = (route.getDest().getY() - minY) * scaleY + Y_GRACE;
                g2D.draw(new Line2D.Double(x1, y1, x2, y2));
            }
        }
    }

    /**
     * Description Text
     * @return
     */
    public ArrayList<City> getCityList() { return cityList; }

    /**
     * Description Text
     * @param cityList
     */
    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
        if (cityList != null && cityList.size() > 0) {
            minX = maxX = cityList.get(0).getX();
            minY = maxY = cityList.get(0).getY();

            for (City city : cityList) {
                if (city.getX() < minX) minX = city.getX();
                if (city.getX() > maxX) maxX = city.getX();

                if (city.getY() < minY) minY = city.getY();
                if (city.getY() > maxY) maxY = city.getY();
            }
        } else minX = maxX = minY = maxY = 0.0;
    }

    /**
     * Description Text
     * @return
     */
    public ArrayList<Route> getRouteList() { return routeList; }

    /**
     * Description Text
     * @param routeList
     */
    public void setRouteList(ArrayList<Route> routeList) {
        this.routeList = routeList;
        this.repaint();
    }
}
