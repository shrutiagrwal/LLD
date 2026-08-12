interface CarInterior{
    void addInteriorComponents();
}
enum CarType{ LUXURY, ECONOMY }
class LuxuryInterior implements CarInterior{
    @Override
    public void addInteriorComponents() {
        System.out.println("adding luxury interior components");
    }
}
class EconomyInterior implements CarInterior{
    @Override
    public void addInteriorComponents() {
        System.out.println("adding economy interior components");
    }
}

interface CarExterior{
    void addExteriorComponents();
}

class LuxuryExterior implements CarExterior{
    @Override
    public void addExteriorComponents() {
        System.out.println("adding luxury exterior components");
    }
}
class EconomyExterior implements CarExterior{
    @Override
    public void addExteriorComponents() {
        System.out.println("adding economy exterior components");
    }
}

interface CarFactory{
    CarExterior createCarExterior();
    CarInterior createCarInterior();
    default void produceCompleteVehicle(){
        CarExterior carExterior=createCarExterior();
        CarInterior carInterior=createCarInterior();

        System.out.println("starting production");
        carInterior.addInteriorComponents();
        carExterior.addExteriorComponents();
        System.out.println("production complete");
    };
}

class LuxuryCarFactory implements CarFactory{
    String brand;
    LuxuryCarFactory(String brand){
        this.brand=brand;
    }
    @Override
    public CarExterior createCarExterior() {
        return new LuxuryExterior();
    }

    @Override
    public CarInterior createCarInterior() {
        return new LuxuryInterior();
    }
}

class EconomyCarFactory implements CarFactory{
    String brand;
    EconomyCarFactory(String name){
        this.brand=name;
    }
    @Override
    public CarExterior createCarExterior() {
        return new EconomyExterior();
    }

    @Override
    public CarInterior createCarInterior() {
        return new EconomyInterior();
    }
}
class CarFactoryProvider{
    public CarFactory getFactory(String brand, CarType carType){
        return switch (carType){
            case LUXURY ->  new LuxuryCarFactory(brand);
            case ECONOMY -> new EconomyCarFactory(brand);
        };
    }
}

public class AbstractFactory {
    static void main(String[] args) {
        CarFactoryProvider carFactoryProvider=new CarFactoryProvider();
        CarFactory economyCar=carFactoryProvider.getFactory("suzuki", CarType.ECONOMY);
        economyCar.produceCompleteVehicle();

        CarFactory luxuryCar = carFactoryProvider.getFactory("audi", CarType.LUXURY);
        luxuryCar.produceCompleteVehicle();
    }
}
