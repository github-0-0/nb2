package box.ascension.app.nb2.physics.math;

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
    
}
