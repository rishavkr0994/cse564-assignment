import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * As an observer observe and receive data from observable. Using algorithms to find the shortest route
 * (traveling salesman greedy algorithm) between the cities.
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-10-02
 */
public class TSP implements Observer {
    private int cityNum;
    private double[][] distanceMatrix;
    private int[] colFlag;
    private int[] rowFlag;

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        List<City> cityList = ((WorkSpace)o).getCityList();
        if (cityList.size() > 0) {
            ArrayList<Route> routeList = calculateShortestRoute(cityList);
            WorkSpace.getInstance().setRouteList(routeList);
        } else WorkSpace.getInstance().setRouteList(new ArrayList<>());
    }

    /**
     * Evaluate the shortest path (traveling salesman greedy algorithm) for traveling from one city to another and
     * returning to the starting city.
     * </p>
     * Source: https://blog.csdn.net/wangqiuyun/article/details/38680151
     *
     * @return shortest route list
     */
    private ArrayList<Route> calculateShortestRoute(List<City> cityList) {
        initDistanceMatrix(cityList);
        ArrayList<Route> result = new ArrayList<>();

        int i = 0;
        while (rowFlag[i] == 1) {
            int j = getNearestCityIndex(distanceMatrix[i]);
            if (j == -1) j = 0;

            rowFlag[i] = 0; colFlag[j] = 0;

            Route route = new Route();
            route.setSrc(cityList.get(i));
            route.setDest(cityList.get(j));
            route.setDist(distanceMatrix[i][j]);
            result.add(route);

            i = j;
        }
        return result;
    }

    private void initDistanceMatrix(List<City> cityList) {
        cityNum = cityList.size();

        distanceMatrix = new double[cityNum][cityNum];
        for (int i = 0; i < cityNum; i++) {
            distanceMatrix[i][i] = 0;
            for (int j = i + 1; j < cityNum; j++) {
                double distance = getEuclideanDistance(cityList.get(i), cityList.get(j));
                distanceMatrix[i][j] = distanceMatrix[j][i] = distance;
            }
        }

        colFlag = new int[cityNum];
        colFlag[0] = 0;
        for (int i = 1; i < cityNum; i++)
            colFlag[i] = 1;

        rowFlag = new int[cityNum];
        for (int i = 0; i < cityNum; i++)
            rowFlag[i] = 1;
    }

    private int getNearestCityIndex(double[] distance) {
        int nearestCityIndex = -1; double minDistance = 0;
        for (int j = 0; j < cityNum; j++) {
            if (colFlag[j] == 1) {
                if (nearestCityIndex == -1 || minDistance >= distance[j]) {
                    minDistance = distance[j];
                    nearestCityIndex = j;
                }
            }
        }
        return nearestCityIndex;
    }

    private double getEuclideanDistance(City src, City dest) {
        double x1 = src.getX(), y1 = src.getY(), x2 = dest.getX(), y2 = dest.getY();
        return Math.sqrt((x1 + x2) * Math.abs(x1 - x2) + (y1 + y2) * Math.abs(y1 - y2));
    }
}
