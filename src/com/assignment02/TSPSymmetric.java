package com.assignment02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSPSymmetric extends TSP {
    private static ArrayList<City> listCity = new ArrayList<>();
    
    @Override
    public void readFile(String path) throws IOException {
        String lineText = null;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null)
        {
           if (lineText.equals("NODE_COORD_SECTION"))
           {
               lineText = br.readLine();
               while (!lineText.equals("EOF")) {
                   String[] text = lineText.split(" ");
                   City city = new City();
                   city.setNum(Integer.parseInt(text[0]));
                   city.setX(Double.valueOf(text[1]));
                   city.setY(Double.valueOf(text[2]));
                   listCity.add(city);
                   lineText = br.readLine();
               }
           }
        }
    }
    @Override
    public ArrayList<Route> calculateShortestPath() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < listCity.size(); i++) {
            City city = listCity.get(i);
            double x1 = city.getX();
            double y1 = city.getY();
            Route route = new Route();
            route.setSrc(city);
            if (listCity.size() == i + 1)
            {
                city = listCity.get(0);
            }else
                city = listCity.get(++i);
            double x2 = city.getX();
            double y2 = city.getY();
            route.setDest(city);
            double distance = Math.sqrt((x1 + x2) * Math.abs(x2 - x1) + (y1 + y2) * Math.abs(y2-y1));
            route.setDistance(distance);
            result.add(route);
        }
        return result;
    }
}
