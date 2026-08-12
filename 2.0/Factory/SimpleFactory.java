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

class SimpleShapeFactory{
    public static Shape getShapeInstance(ShapeType shapeType){
        return switch (shapeType){
            case CIRCLE -> new Circle();
            case SQUARE -> new Square();
        };
    }
}

public class SimpleFactory {
    static void main(String[] args) {
        ShapeType shapeType=ShapeType.CIRCLE;
        Shape shape=SimpleShapeFactory.getShapeInstance(shapeType);
        shape.draw();
        shape.computeArea();
    }
}
