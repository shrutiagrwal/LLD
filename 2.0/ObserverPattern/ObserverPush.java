import java.util.ArrayList;
import java.util.List;

class WeatherData{
    public int temp;
}
interface WeatherObserver{
   void update(WeatherData data);
}

class MobileObserver implements WeatherObserver{
    @Override
    public void update(WeatherData data) {
        System.out.println("weather data for mobile: " + data.temp);
    }
}
class TVObserver implements WeatherObserver{
    @Override
    public void update(WeatherData data) {
        System.out.println("weather data for TV: " + data.temp);
    }
}

interface WeatherObservable{
    void addDevice(WeatherObserver weatherObserver);
    void removeDevice(WeatherObserver weatherObserver);
    void notifyAllDevices();
    void updateWeatherData(WeatherData weatherData);
}

class WeatherStation implements WeatherObservable{
    private final List<WeatherObserver> observers=new ArrayList<>();;
    private WeatherData weatherData;
    @Override
    public void addDevice(WeatherObserver weatherObserver) {
        observers.add(weatherObserver);
    }

    @Override
    public void removeDevice(WeatherObserver weatherObserver) {
        observers.remove(weatherObserver);
    }

    @Override
    public void notifyAllDevices() {
        for( WeatherObserver observer: observers){
            observer.update(weatherData);
        }
    }

    @Override
    public void updateWeatherData(WeatherData weatherData) {
        this.weatherData=weatherData;
        this.notifyAllDevices();
    }
}

public class ObserverPush {
    public static void main(String[] args){
        WeatherStation weatherStation=new WeatherStation();

        WeatherObserver samsungPhone = new MobileObserver();
        WeatherObserver samsungTV= new TVObserver();

        weatherStation.addDevice(samsungPhone);
        WeatherData data= new WeatherData();
        data.temp= 20;
        weatherStation.updateWeatherData(data);

        weatherStation.addDevice(samsungTV);

        data.temp = 30;
        weatherStation.updateWeatherData(data);
    }

}
