interface DriveStrategy{
    public void drive();
}

class SportsDrive implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("sports driving capability");
    }
}

class NormalDrive implements DriveStrategy{
    @Override
    public void drive() {
        System.out.println("normal driving capability");
    }
}

class Vehicle{
    DriveStrategy driveStrategy;
    public Vehicle(DriveStrategy driveStrategy){
        this.driveStrategy=driveStrategy;
    }
    public void drive(){
        driveStrategy.drive();
    }
}

class PassengerVehicle extends Vehicle{
    public PassengerVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}

class SportsVehicle extends Vehicle{
    public SportsVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}

public class SampleStrategyDesignPattern{
    static void main(String[] args){
        Vehicle vehicle= new SportsVehicle(new SportsDrive());
        vehicle.drive();
        
        Vehicle goodsVehicle = new PassengerVehicle(new NormalDrive());
        goodsVehicle.drive();
    }
}0
