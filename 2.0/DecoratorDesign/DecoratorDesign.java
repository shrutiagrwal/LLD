interface BasePizza{
    void getDescription();
    int getPrice();
}

class RegularPizza implements BasePizza{
    @Override
    public void getDescription() {
        System.out.println("this is a regular Pizza");
    }
    @Override
    public int getPrice() {
        return 200;
    }
}

class MargeritaPizza implements BasePizza{
    @Override
    public void getDescription() {
        System.out.println(" this is a margerita pizza");
    }
    @Override
    public int getPrice() {
        return 300;
    }
}

class FarmhousePizza implements BasePizza{
    @Override
    public void getDescription() {
        System.out.println("this is a farmhouse pizza");
    }
    @Override
    public int getPrice() {
        return 400;
    }
}

abstract class ToppingDecorator implements BasePizza{
    BasePizza basePizza;

    public ToppingDecorator(BasePizza basePizza) {
        this.basePizza=basePizza;
    }
}

class PannerTopping extends ToppingDecorator {
    PannerTopping(BasePizza basePizza) {
        super(basePizza);
    }
    @Override
    public void getDescription() {
        basePizza.getDescription();
        System.out.println(" added Paneer topping");
    }
    @Override
    public int getPrice() {
        return basePizza.getPrice()+20;
    }
}

class MushroomTopping extends ToppingDecorator{
    public MushroomTopping(BasePizza basePizza) {
        super(basePizza);
    }
    @Override
    public void getDescription() {
        basePizza.getDescription();
        System.out.println(" added Paneer topping");
    }
    @Override
    public int getPrice() {
        return basePizza.getPrice()+30;
    }
}

public class DecoratorDesign {
    public static void main(String[] args) {
        BasePizza pizza1= new MushroomTopping(new FarmhousePizza());
        System.out.println("price of order 1: " +pizza1.getPrice());

        BasePizza pizza2=new PannerTopping(new MushroomTopping(new RegularPizza()));
        System.out.println("price of order 2: "+ pizza2.getPrice());
    }
}
