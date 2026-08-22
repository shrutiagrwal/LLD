package designPatterns;

interface Vehicles{
    void start();
    void stop();
}

class Cars implements Vehicles{
    String colour;
    Cars(String colour){
        this.colour=colour;
    }
    @Override
    public void start() {
        System.out.println("start car");
    }

    @Override
    public void stop() {
        System.out.println("stop car");
    }
}

class Bike implements Vehicles{
    String colour;
    Bike(String c){
        this.colour=c;
    }
    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public void stop() {
        System.out.println("stop");
    }
}
class NullObjectVehicle implements Vehicles{
    String colour;
    NullObjectVehicle(String colour){
        this.colour=colour;
    }
    @Override
    public void start() {
        //do nothing
    }

    @Override
    public void stop() {
        //do nothing
    }
}

class VehiclesFactory{
    public static Vehicles getVehiclesObject(String type, String colour){
       return switch (type){
            case "BIKE" -> new Bike(colour);
            case "CAR" ->  new Cars(colour);
           default -> new NullObjectVehicle(colour);
        };
    }
}

public class NullObjectPattern {
    static void main(String[] args) {
        Vehicles vehicle= VehiclesFactory.getVehiclesObject("truck", "orange");
        vehicle.start();
    }
}
