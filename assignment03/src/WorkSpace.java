import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WorkSpace extends Observable {
    private List<City> cityList = new ArrayList<>();

    public void addNewCity(City city) {
        cityList.add(city);
        setChanged();
        notifyObservers();
    }
    public List<City> getCityList()
    {
        return cityList;
    }
}