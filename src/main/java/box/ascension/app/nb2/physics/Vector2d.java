package box.ascension.app.nb2.physics;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Vector2d implements Cloneable {
    
    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static final Vector2d ZERO = new Vector2d(0, 0);
    public static final Vector2d UNIT = new Vector2d(1, 0);
    public static final Vector2d DIAG = new Vector2d(1, 1);

    public static Vector2d add(Vector2d x, Vector2d y) {
        return new Vector2d(x.x + y.x, x.y + y.y);
    }

    public static Vector2d subtract(Vector2d x, Vector2d y) {
        return add(x, scale(y, -1));
    }

    public static Vector2d scale(Vector2d x, double y) {
        return new Vector2d(x.x * y, x.y * y);
    }

    public Vector2d addBy(Vector2d o) {
        x += o.x;
        y += o.y;
        return this;
    }

    public Vector2d subtractBy(Vector2d o) {
        x -= o.x;
        y -= o.y;
        return this;
    }

    public Vector2d scaleBy(double o) {
        x *= o;
        y *= o;
        return this;
    }

    public Vector2d added(Vector2d o) {
        return add(this, o);
    }

    public Vector2d subtracted(Vector2d o) {
        return subtract(this, o);
    }

    public Vector2d scaled(double o) {
        return scale(this, o);
    }

    @JsonIgnore
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public void set(Vector2d o) {
        this.x = o.x;
        this.y = o.y;
    }

    @JsonIgnore
    public Vector2d getNorm() {
        if (PhysicsUtil.epsilonEquals(0, getMagnitude())) {
            System.out.println("Vector is too close to 0 to get normal");
            return UNIT;
        }
        return scaled(1 / getMagnitude());
    }

    public static double dot(Vector2d x, Vector2d y) {
        return x.x * y.x + x.y * y.y;
    }

    @Override
    public Vector2d clone() {
        return new Vector2d(x, y);
    }

    public double cross(Vector2d o) {
        return x * o.y - y * o.x;
    }

}
