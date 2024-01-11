package adapterDesignPattern;

import static java.lang.Math.pow;

// Fitting square pegs into round holes
class RoundPeg {
    int radius;
    public RoundPeg(){}
    public RoundPeg(int r)
    { radius=r;}

    public int getRadius(){return radius;}
}
class RoundHole {
    int radius;
    public RoundHole(int r)
    { radius=r;}

    public int getRadius(){return radius;}
    public boolean pegFits(RoundPeg peg)
    {
        return this.radius > peg.getRadius();
    }
}

class SquarePeg {
    int length;
    public SquarePeg(int l) { length=l;}

    public int getLength(){return length;}

    public int getSquare()
    {
        return (int) pow(length,2);
    }
}

class SquarePegAdapter extends RoundPeg {
    private SquarePeg squarePeg;
    public SquarePegAdapter(SquarePeg peg)
    {  squarePeg=peg;}
    @Override
    public int getRadius()
    {
        return (int) Math.sqrt(Math.pow(((double) squarePeg.getLength() / 2), 2) * 2);
    }
}


public class Demo {

    public static void main( String[] args)
    {
        RoundPeg rp=new RoundPeg(4);
        RoundHole roundHole=new RoundHole(10);
        if(roundHole.pegFits(rp))
            System.out.println("round peg fits");
        else
            System.out.println("round peg doesnt fits");

        SquarePeg squarePeg=new SquarePeg(100);
        SquarePegAdapter adapter=new SquarePegAdapter(squarePeg);

        if(roundHole.pegFits(adapter))
            System.out.println("square peg fits");

        else
            System.out.println("square peg doesnt fits");
    }



}
