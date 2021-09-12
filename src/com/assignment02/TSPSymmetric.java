package com.assignment02;

import java.io.File;
import java.util.ArrayList;

public class TSPSymmetric extends TSP {
    private static final String DATA_SECTION_START_TAG = "NODE_COORD_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";
    
    @Override
    public void parseTextFile(File file) throws Exception {
        String fileText = readTextFile(file);
        String[] lineList = fileText.split("\n");

        int lineIdx = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_START_TAG))
            lineIdx++;
        lineIdx++;

        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_END_TAG)) {
            String[] tokens = lineList[lineIdx].split(" ");
            City city = new City();
            city.setNum(Integer.parseInt(tokens[0]));
            city.setX(Double.parseDouble(tokens[1]));
            city.setY(Double.parseDouble(tokens[2]));
            cityList.add(city);
            lineIdx++;
        }
    }

    @Override
    public ArrayList<Route> calculateShortestPath() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);
            double x1 = city.getX();
            double y1 = city.getY();
            Route route = new Route();
            route.setSrc(city);
            if (cityList.size() == i + 1)
                city = cityList.get(0);
            else city = cityList.get(++i);
            double x2 = city.getX();
            double y2 = city.getY();
            route.setDest(city);
            double distance = Math.sqrt((x1 + x2) * Math.abs(x2 - x1) + (y1 + y2) * Math.abs(y2-y1));
            route.setDist(distance);
            result.add(route);
        }
        return result;
    }
}
