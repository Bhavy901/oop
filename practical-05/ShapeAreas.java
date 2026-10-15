public class ShapeAreas {

    public abstract static class Shape {
        private final String name;

        public Shape(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract double area();
    }

    public static class Circle extends Shape {
        private final double radius;

        public Circle(double radius) {
            super("Circle");
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    public static class Rectangle extends Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            super("Rectangle");
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    public static class Triangle extends Shape {
        private final double base;
        private final double height;

        public Triangle(double base, double height) {
            super("Triangle");
            this.base = base;
            this.height = height;
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Polymorphic Shape Areas ===");

        Shape[] shapes = {
            new Circle(3.0),
            new Rectangle(4.0, 5.0),
            new Triangle(6.0, 4.0),
            new Circle(5.0),
            new Rectangle(2.0, 8.0)
        };

        double runningTotal = 0;
        Shape largestShape = null;
        double maxArea = -1;

        for (Shape shape : shapes) {
            double currentArea = shape.area();
            runningTotal += currentArea;

            System.out.printf("Shape: %-10s | Area: %8.2f | Running Total: %8.2f%n",
                    shape.getName(), currentArea, runningTotal);

            if (currentArea > maxArea) {
                maxArea = currentArea;
                largestShape = shape;
            }
        }

        System.out.printf("%nTotal Area: %.2f%n", runningTotal);
        if (largestShape != null) {
            System.out.printf("Largest Shape: %s with area %.2f%n", largestShape.getName(), maxArea);
        }
    }
}
