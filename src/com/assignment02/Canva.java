package com.assignment02;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canva extends JPanel {
    public void drawPoint(Graphics g, ArrayList<Route> result) {
        for (Route route : result) {
            City src = route.getSrc();
            City dest = route.getDest();

        }
    }

    public void drawLine(Graphics g, ArrayList<Route> result) {
        for (int i = 0; i < result.size(); i++) {
            Route route = result.get(i);
            City city = route.getSrc();
            double x1 = city.getX()/300;
            double y1 = city.getY()/300;
            city = route.getDest();
            double x2 = city.getX()/300;
            double y2 = city.getY()/300;
            g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
        }
    }
}
