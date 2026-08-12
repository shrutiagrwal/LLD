interface Shape{
    void computeArea();
    void draw();
}

class Circle implements Shape{
    @Override
    public void computeArea() {
        System.out.println("area for circle shape");
    }
    @Override
    public void draw() {
        System.out.println("drawing circle");
    }
}

class Square implements Shape{
    @Override
    public void computeArea() {
        System.out.println("area for square shape");
    }
    @Override
    public void draw() {
        System.out.println("drawing square");
    }
}

enum ShapeType{
    CIRCLE, SQUARE
}
abstract class ShapeFactory{
    abstract Shape createShape();
}

class CircleFactory extends ShapeFactory{
    @Override
    Shape createShape() {
        return new Circle();
    }
}

class SquareFactory extends ShapeFactory{
    @Override
    Shape createShape() {
        return new Square();
    }
}
public class FactoryPattern {
    private static Shape getShapeInstance(ShapeType shapeType){
        Shape shape=null;
        ShapeFactory shapeFactory;
        switch (shapeType){
            case CIRCLE:
                shapeFactory=new CircleFactory();
                shape=shapeFactory.createShape();
                break;
            case SQUARE:
                shapeFactory=new SquareFactory();
                shape = shapeFactory.createShape();
                break;
            case null, default:
                return null;
        }
        return shape;
    }

    static void main(String[] args) {
        ShapeType shapeType=ShapeType.CIRCLE;
        Shape shape=getShapeInstance(shapeType);
        shape.draw();
        shape.computeArea();
    }
}
