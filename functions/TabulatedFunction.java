package functions;

public class TabulatedFunction {
    private FunctionPoint[] points;
    private int pointsCount;
    private static final double EPSILON = 1e-10;
    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.pointsCount = pointsCount;
        this.points = new FunctionPoint[pointsCount + 10];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, 0.0);
        }
    }
    public TabulatedFunction(double leftX, double rightX, double[] values) {

        this.pointsCount = values.length;
        this.points = new FunctionPoint[pointsCount + 10];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() - EPSILON || x > getRightDomainBorder() + EPSILON) {
            return Double.NaN;
        }
        for (int i = 0; i < pointsCount; i++) {
            if (Math.abs(points[i].getX() - x) < EPSILON) {
                return points[i].getY();
            }
        }
        for (int i = 0; i < pointsCount - 1; i++) {
            double x1 = points[i].getX();
            double x2 = points[i + 1].getX();
            double y1 = points[i].getY();
            double y2 = points[i + 1].getY();
            return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(points[index]);
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= pointsCount) {
            return;
        }

        double newX = point.getX();
        double leftBound = (index > 0) ? points[index - 1].getX() : -Double.MAX_VALUE;
        double rightBound = (index < pointsCount - 1) ? points[index + 1].getX() : Double.MAX_VALUE;

        if (newX > leftBound + EPSILON && newX < rightBound - EPSILON) {
            points[index] = new FunctionPoint(point);
        }
    }

    public double getPointX(int index) {
        return points[index].getX();
    }

    public void setPointX(int index, double x) {
        if (index < 0 || index >= pointsCount) {
            return;
        }

        double leftBound = (index > 0) ? points[index - 1].getX() : -Double.MAX_VALUE;
        double rightBound = (index < pointsCount - 1) ? points[index + 1].getX() : Double.MAX_VALUE;

        if (x > leftBound + EPSILON && x < rightBound - EPSILON) {
            points[index].setX(x);
        }
    }

    public double getPointY(int index) {
        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index >= 0 && index < pointsCount) {
            points[index].setY(y);
        }
    }

    public void deletePoint(int index) {
        if (pointsCount <= 2 || index < 0 || index >= pointsCount) {
            return;
        }

        System.arraycopy(points, index + 1, points, index, pointsCount - index - 1);
        pointsCount--;
    }

    public void addPoint(FunctionPoint point) {
        if (pointsCount == points.length) {
            FunctionPoint[] newArray = new FunctionPoint[points.length * 2];
            System.arraycopy(points, 0, newArray, 0, pointsCount);
            points = newArray;
        }

        int insertIndex = 0;
        while (insertIndex < pointsCount && points[insertIndex].getX() < point.getX() - EPSILON) {
            insertIndex++;
        }

        if (insertIndex < pointsCount && Math.abs(points[insertIndex].getX() - point.getX()) < EPSILON) {
            points[insertIndex].setY(point.getY());
            return;
        }
        if (insertIndex < pointsCount) {
            System.arraycopy(points, insertIndex, points, insertIndex + 1, pointsCount - insertIndex);
        }
        points[insertIndex] = new FunctionPoint(point);
        pointsCount++;
    }
}