import functions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Function test ===\n");

        int pointsCount = 5;
        TabulatedFunction parabola = new TabulatedFunction(-2, 2, pointsCount);

        for (int i = 0; i < parabola.getPointsCount(); i++) {
            double x = parabola.getPointX(i);
            parabola.setPointY(i, x*x);
        }

        System.out.println("y = x^2 on [-2, 2]");
        System.out.println("Points: " + parabola.getPointsCount());

        System.out.println("\nAll points:");
        for (int i = 0; i < parabola.getPointsCount(); i++) {
            System.out.printf("Point %d: x=%.2f, y=%.2f\n", i, parabola.getPointX(i), parabola.getPointY(i));
        }

        System.out.printf("\nDomain: [%.2f, %.2f]\n",
                parabola.getLeftDomainBorder(), parabola.getRightDomainBorder());

        System.out.println("\nFunction values:");
        double[] testPoints = {-3, -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2, 3};
        for (double x : testPoints) {
            double y = parabola.getFunctionValue(x);
            if (Double.isNaN(y)) {
                System.out.printf("f(%.2f) = undefined\n", x);
            } else {
                System.out.printf("f(%.2f) = %.3f\n", x, y);
            }
        }

        System.out.println("\nChanging point:");
        int idx = 2;
        double newX = parabola.getPointX(idx);
        double newY = 10.0;
        System.out.printf("Change point %d to (%.2f, %.2f)\n",
                idx, newX, newY);
        parabola.setPoint(idx, new FunctionPoint(newX, newY));
        System.out.printf("f(%.2f) now = %.3f\n", newX, parabola.getFunctionValue(newX));

        System.out.println("\nTrying to change X coordinate:");
        int changeIdx = 1;
        double oldX = parabola.getPointX(changeIdx);
        double tryX = oldX + 0.3;
        System.out.printf("Try to change x of point %d from %.2f to %.2f\n", changeIdx, oldX, tryX);
        parabola.setPointX(changeIdx, tryX);
        double actualX = parabola.getPointX(changeIdx);
        if (Math.abs(tryX - actualX) < 1e-10) {
            System.out.printf("Changed, now x=%.2f\n", actualX);
        } else {
            System.out.printf("Not changed, x still %.2f\n", actualX);
        }

        System.out.println("\nChanging Y coordinate:");
        int yIdx = 3;
        double oldY = parabola.getPointY(yIdx);
        double newYVal = 7.5;
        System.out.printf("Change y of point %d from %.2f to %.2f\n", yIdx, oldY, newYVal);
        parabola.setPointY(yIdx, newYVal);
        System.out.printf("y of point %d now = %.2f\n", yIdx, parabola.getPointY(yIdx));

        System.out.println("\nAdding point:");
        FunctionPoint newPoint = new FunctionPoint(1.7, 3.0);
        System.out.printf("Add (%.2f, %.2f)\n", newPoint.getX(), newPoint.getY());
        parabola.addPoint(newPoint);
        System.out.println("Points after add: " + parabola.getPointsCount());

        System.out.println("\nPoints after adding:");
        for (int i = 0; i < parabola.getPointsCount(); i++) {
            System.out.printf("Point %d: x=%.2f, y=%.2f\n", i, parabola.getPointX(i), parabola.getPointY(i));
        }

        System.out.printf("\nf(%.2f) = %.3f\n", newPoint.getX(), parabola.getFunctionValue(newPoint.getX()));

        System.out.println("\nDeleting point:");
        int delIdx = 2;
        System.out.printf("Delete point %d (x=%.2f, y=%.2f)\n",
                delIdx, parabola.getPointX(delIdx), parabola.getPointY(delIdx));
        parabola.deletePoint(delIdx);
        System.out.println("Points after delete: " + parabola.getPointsCount());

        System.out.println("\nRemaining points:");
        for (int i = 0; i < parabola.getPointsCount(); i++) {
            System.out.printf("Point %d: x=%.2f, y=%.2f\n", i, parabola.getPointX(i), parabola.getPointY(i));
        }

        System.out.println("\nInterpolation check:");
        double[] interpPoints = {-1.2, 0.3, 1.1, 1.8};
        for (double x : interpPoints) {
            double y = parabola.getFunctionValue(x);
            if (Double.isNaN(y)) {
                System.out.printf("f(%.2f) = out of domain\n", x);
            } else {
                System.out.printf("f(%.2f) = %.3f\n", x, y);
            }
        }

        System.out.println("\n=== Done ===");
    }
}