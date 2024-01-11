package builderDesignPattern;

class Car{
    private CarType type;
    private int seats;
    private Engine engine;

    public Car(CarType type,int seats, Engine engine)
    {
        this.type=type; this.seats=seats; this.engine=engine;
    }
    public CarType getCarType(){ return this.type;}
    public int getSeats(){ return this.seats;}
    public Engine getEngine(){ return this.engine;}
}

class Manual{
    private CarType type;
    private int seats;
    private Engine engine;

    public Manual(CarType type,int seats, Engine engine)
    {
        this.type=type; this.seats=seats; this.engine=engine;
    }
    public void print(){
        System.out.println("car type -" + type + " seats "+ seats + " engine "+ engine);
    }
}

class Engine {
    private final double volume;
    private double mileage;
    private boolean started;

    public Engine(double volume, double mileage) {
        this.volume = volume;
        this.mileage = mileage;
    }

    public void on() {
        started = true;
    }

    public void off() {
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void go(double mileage) {
        if (started) {
            this.mileage += mileage;
        } else {
            System.err.println("Cannot go(), you must start engine first!");
        }
    }

    public double getVolume() {
        return volume;
    }

    public double getMileage() {
        return mileage;
    }
}

enum CarType {
    CITY_CAR, SPORTS_CAR, SUV
}
interface Builder{
    void setCarType(CarType type);
    void setSeats(int seats);
    void setEngine(Engine engine);
}

class CarBuilder implements Builder{
    private CarType type;
    private int seats;
    private Engine engine;
    @Override
    public void setCarType(CarType type) {
        this.type= type;
    }
    @Override
    public void setSeats(int seats) {
        this.seats=seats;
    }
    @Override
    public void setEngine(Engine engine) {
        this.engine=engine;
    }
    public Car build(){
        return new Car(type, seats, engine);
    }
}

class ManualBuilder implements Builder{
    private CarType type;
    private int seats;
    private Engine engine;
    @Override
    public void setCarType(CarType type) {
        this.type= type;
    }
    @Override
    public void setSeats(int seats) {
        this.seats=seats;
    }
    @Override
    public void setEngine(Engine engine) {
        this.engine=engine;
    }

    public Manual build()
    {
        return new Manual(type, seats, engine);
    }
}

class Director{
    public void createSportsCar(Builder builder){
        builder.setCarType(CarType.SPORTS_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(3.0,0));
    }

    public void createSuvCar(Builder builder)
    {
        builder.setCarType(CarType.SUV);
        builder.setSeats(6);
        builder.setEngine(new Engine(2.0,23));
    }
    public void createPublicCar(Builder builder)
    {
        builder.setCarType(CarType.CITY_CAR);
        builder.setSeats(4);
        builder.setEngine(new Engine(1.0,18));
    }
}

public class CarManualDemo {
    public static void main(String[] args)
    {
        Director director=new Director();
        CarBuilder builder=new CarBuilder();
        director.createSportsCar(builder);

        Car sportsCar=builder.build();
        System.out.println(sportsCar.getCarType());

        ManualBuilder manualBuilder=new ManualBuilder();
        director.createPublicCar(manualBuilder);
        Manual manual=manualBuilder.build();
        manual.print();
    }
}






