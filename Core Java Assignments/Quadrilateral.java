package corejava1;


public abstract class Quadrilateral {
	
	
	public Quadrilateral(int base, int height) {
		super();
		this.base = base;
		this.height = height;
	}


	protected int base;
	protected int height;
	
	public abstract double area();
	
	
	

	public int getBase() {
		return base;
	}


	public void setBase(int base) {
		this.base = base;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public static void main(String[] args) {

	}

}
class Square extends Quadrilateral {
    public Square(int side) {
        super(side, side); // Square has equal base and height
    }

    @Override
    public double area() {
        return base * base; // Area of a square
    }
}

// Rectangle
class Rectangle extends Quadrilateral {
    public Rectangle(int length, int width) {
        super(length, width);
    }

    @Override
    public double area() {
        return base * height; // Area of a rectangle
    }
}

// Parallelogram
class Parallelogram extends Quadrilateral {
	public Parallelogram(int length, int width) {
        super(length, width);
    }

    @Override
    public double area() {
        return base * height; // Area of a rectangle
    }
}