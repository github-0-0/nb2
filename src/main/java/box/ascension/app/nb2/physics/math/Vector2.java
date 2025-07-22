package box.ascension.app.nb2.physics.math;

import org.ejml.simple.SimpleMatrix;

public class Vector2 {
    
    public SimpleMatrix vector;

    public Vector2(SimpleMatrix vector) {
        if (vector.getNumRows() != 2 && vector.getNumCols() != 1) {
            throw new IllegalArgumentException(
                "Tried to make a Vector2 with a matrix of the wrong dimensions!");
        }
        this.vector = vector;
    }

    public Vector2(double x, double y) {
        vector = new SimpleMatrix(
            2, 1, true, new double[] {x, y});
    }

    public Vector2 scale(double factor) {
        return new Vector2(vector.scale(factor));
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(vector.plus(other.vector));
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(vector.minus(other.vector));
    }

    public Vector2 rotate(Rotation2 rotation) {
        return new Vector2(rotation.getRotationMatrix().mult(vector));   
    }

    public Vector2 scaleThis(double factor) {
        vector = vector.scale(factor);
        return this;
    }

    public Vector2 addThis(Vector2 other) {
        vector = vector.plus(other.vector);
        return this;
    }

    public Vector2 subtractThis(Vector2 other) {
        vector = vector.minus(other.vector);
        return this;
    }

    public Vector2 rotateThis(Rotation2 rotation) {
        vector = rotation.getRotationMatrix().mult(vector);
        return this;
    }

    public Vector2 getNormalized() {
        return new Vector2(getX(), getY()).scaleThis(1.0 / Math.max(PhysicsUtil.EPS, getMagnitude()));
    }

    public double getMagnitude() {
        return Math.hypot(getX(), getY());
    }

    public double getX() {
        return vector.get(0, 0);
    }

    public double getY() {
        return vector.get(1, 0);
    }

    @Override
    public String toString() {
        return "Vector2(" + getX() + ", " + getY() + ")";
    }

}
