package com.assignment02;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TSPAsymmetric extends TSP {
    private static final String CITY_COUNT_TAG = "DIMENSION";
    private static final String DATA_SECTION_START_TAG = "EDGE_WEIGHT_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    private ArrayList<Route> routeList = new ArrayList<>();

    @Override
    public void parseTextFile(File file) throws Exception {
        String fileText = readTextFile(file);
        String[] lineList = fileText.split("\n");

        int lineIdx = 0, cityCount = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_START_TAG)) {
            List<String> tokens = Arrays.stream(lineList[lineIdx].split(":"))
                    .map(String::trim).collect(Collectors.toList());

            if (tokens.size() == 2 && tokens.get(0).equals(CITY_COUNT_TAG))
                cityCount = Integer.parseInt(tokens.get(1));
            lineIdx++;
        }
        lineIdx++;

        cityList = generateRandomCityList(cityCount);

        int srcCityIdx = 0; int destCityIdx = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_END_TAG)) {
            String[] distanceList = lineList[lineIdx].trim().split("\\s+");

            for (int i = 0; i < distanceList.length; i++) {
                int distance = Integer.parseInt(distanceList[i]);
                routeList.add(new Route(cityList.get(srcCityIdx), cityList.get(destCityIdx++), distance));
            }

            if (destCityIdx == cityCount) {
                srcCityIdx++; destCityIdx = 0;
            }
            lineIdx++;
        }
    }

    @Override
    public ArrayList<Route> calculateDummyRoute() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            int colOffset = (i == cityList.size() - 1 ? 0 : i + 1);
            Route route = routeList.get(i * cityList.size() + colOffset);
            result.add(route);
        }
        return result;
    }

    @Override
    public ArrayList<Route> calculateShortestRoute() { return null; }

    private ArrayList<City> generateRandomCityList(int count) {
        ArrayList<City> randomCityList = new ArrayList<>();

        int randMin = 100, randMax = randMin + count * 10;
        Random random = new Random();

        for (int cityNum = 1; cityNum <= count; cityNum++) {
            City city = new City();
            city.setNum(cityNum);
            do {
                city.setX((double) random.nextInt(randMax - randMin + 1) + randMin);
                city.setY((double) random.nextInt(randMax - randMin + 1) + randMin);
            } while (randomCityList.contains(city));
            randomCityList.add(city);
        }
        return randomCityList;
    }
}
