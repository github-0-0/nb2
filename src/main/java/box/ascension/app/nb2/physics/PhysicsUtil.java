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

        public Vector2d min;
        public Vector2d max;

        public Vector2d position;
        public Vector2d dimensions;

        public BoundingBox(Vector2d min, Vector2d max, boolean alt) {
            if (alt) {
                this.min = min;
                this.max = max;
                this.position = min;
                this.dimensions = max.subtracted(min);            
            } else {
                throw new IllegalArgumentException("What do you think that boolean was for");
            }
        }

        public BoundingBox(Vector2d position, Vector2d dimensions) {
            this.min = position;
            this.max = position.added(dimensions);
            this.position = position;
            this.dimensions = dimensions;
        }

        public boolean isOverlapping(BoundingBox o) {
            move();
            return !(o.min.x > this.max.x 
                || o.max.x < this.min.x 
                || o.min.y > this.max.y 
                || o.max.y < this.min.y);
        }

        public void move() {
            this.min = position;
            this.max = position.added(dimensions);
        }

    }

    public static record Impulse(Vector2d pos, double alpha) {}
    
}
