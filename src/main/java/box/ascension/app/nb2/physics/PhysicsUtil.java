package box.ascension.app.nb2.physics;

public class PhysicsUtil {

    public static final double EPS = 1e-6;
    public static boolean epsilonEquals(double x, double y) {
        return -EPS < x - y && EPS > x - y;
    }

    public static double wrap(double value, double min, double max) {
        double range = max - min;
        value = (value - min) % range;
        if (value < 0) {
            value += range;
        }
        return value + min;
    }    
    
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static class BoundingBox {

        public final Vector2d position;
        public final Vector2d dimensions;
    
        public BoundingBox(Vector2d position, Vector2d dimensions) {
            this.position = position;
            this.dimensions = dimensions;
        }
    
        public static BoundingBox fromMinMax(Vector2d min, Vector2d max) {
            return new BoundingBox(min, max.subtracted(min));
        }
    
        public Vector2d getMin() {
            return position;
        }
    
        public Vector2d getMax() {
            return position.added(dimensions);
        }
    
        public boolean isOverlapping(BoundingBox o) {
            return this.position.x <= o.position.x + o.dimensions.x &&
                o.position.x <= this.position.x + this.dimensions.x &&
                this.position.y <= o.position.y + o.dimensions.y &&
                o.position.y <= this.position.y + this.dimensions.y;
        }
    
        public Vector2d getPosition() {
            return position;
        }
    
        public Vector2d getDimensions() {
            return dimensions;
        }

    }
    

    public static record Impulse(Vector2d pos, double alpha) {}
    
}
