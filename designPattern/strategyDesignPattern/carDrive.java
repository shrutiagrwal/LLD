package designPattern.strategyDesignPattern;

public class carDrive {
    public static void main(String[] args)
    {
        Bmw bmw=new Bmw();
        bmw.drive();
    }
}

interface Drive {
    void drive();
}
class SportsDrive implements Drive{
    @Override
    public void drive() {
        System.out.println("sports drive capability");
    }
}

class NormalDrive implements Drive{
    @Override
    public void drive(){
        System.out.println("normal practice.strategyDesignPattern.Drive capability");
    }
}

class Vehicle{
    Drive d;
    public Vehicle(Drive d)
    {   this.d=d;}
    void drive(){
        d.drive();
    }
}

class Bmw extends Vehicle {
    public Bmw() {
        super(new SportsDrive());
    }
}
class Passenger extends Vehicle{
    public Passenger() {
        super(new NormalDrive());
    }
}
