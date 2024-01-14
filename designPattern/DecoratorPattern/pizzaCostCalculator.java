package designPattern.DecoratorPattern;

abstract class Pizza{
    int cost;
    public abstract int cost();
}
class MargheritaPizza extends Pizza{
    @Override
    public int cost() {
        return 200;
    }
}
class VegDelight extends Pizza{

    @Override
    public int cost() {
        return 100;
    }
}

 abstract class Toppings extends Pizza{
}

class ExtraCheese extends Toppings{
Pizza pizza;
    ExtraCheese(Pizza pizza){this.pizza=pizza;}
    @Override
    public int cost() {
        return this.pizza.cost()+10;
    }
}
class jalepeno extends Toppings{
    Pizza pizza;
    jalepeno(Pizza pizza){this.pizza=pizza;}
    @Override
    public int cost() {
        return this.pizza.cost()+20;
    }
}

public class pizzaCostCalculator {
    public static void main(String[] args){
        Pizza pizza=new ExtraCheese(new jalepeno(new VegDelight()));
        System.out.println(pizza.cost());

    }
}
