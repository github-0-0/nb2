package box.ascension.app.nb2.physics.math;

import org.ejml.simple.SimpleMatrix;

public class Rotation2 {
    
    public double x;
    public double y;
    public double rads;
    public double degs;

    public Rotation2() {
        this.x = 1;
        this.y = 0;
        this.rads = 0;
        this.degs = PhysicsUtil.wrap(rads * 180 / Math.PI, -180, 180);
    }

    public Rotation2(double rads) {
        this.x = Math.cos(rads);
        this.y = Math.sin(rads);
        this.rads = PhysicsUtil.wrap(rads, -Math.PI, Math.PI);
        this.degs = PhysicsUtil.wrap(rads * 180 / Math.PI, -180, 180);
    }

    public Rotation2(double x, double y) {
        if (!PhysicsUtil.epsilonEquals(Math.hypot(x, y), 0)) {
            throw new IllegalArgumentException("X and y components of rotation are not on the unit circle!");
        }
        this.x = x;
        this.y = y;
        this.rads = Math.atan2(y, x);
        this.degs = PhysicsUtil.wrap(rads * 180 / Math.PI, -180, 180);
    }

    public static Rotation2 fromDegs(double degs) {
        return new Rotation2(degs * Math.PI / 180);
    }

    public Rotation2 rotateBy(Rotation2 other) {
        this.rads += other.rads;
        this.rads = PhysicsUtil.wrap(rads, -Math.PI, Math.PI);
        updateXYDegs();
        return this;
    }

    public SimpleMatrix getRotationMatrix() {
        return new SimpleMatrix(
            new double[][] {{x, -y}, {y, x}});
    }

    private void updateXYDegs() {
        this.x = Math.cos(rads);
        this.y = Math.sin(rads);
        this.degs = PhysicsUtil.wrap(rads * 180 / Math.PI, -180, 180);
    }

}
