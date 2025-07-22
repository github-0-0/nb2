package box.ascension.app.nb2.physics.math;

import org.ejml.simple.SimpleMatrix;

public class Vector2 {
    
    public SimpleMatrix vector;

    public Vector2(double x, double y) {
        vector = new SimpleMatrix(
            2, 1, true, new double[] {x, y});
    }

    public Vector2 scale(double factor) {
        vector.scale(factor);
        return this;
    }

    public Vector2 add(Vector2 other) {
        vector = vector.plus(other.vector);
        return this;
    }

    public Vector2 subtract(Vector2 other) {
        return add(other.scale(-1));
    }

    public Vector2 rotate(Rotation2 rotation) {
        vector = rotation.getRotationMatrix().mult(vector);
        return this;
    }

    public Vector2 getNormalized() {
        return new Vector2(getX(), getY()).scale(getMagnitude());
    }

    public double getMagnitude() {
        return Math.hypot(getX(), getY());
    }

    public double getX() {
        return vector.get(0, 0);
    }

    public double getY() {
        return vector.get(0, 1);
    }

    @Override
    public String toString() {
        return "Vector2(" + getX() + ", " + getY() + ")";
    }

}
