package box.ascension.app.nb2.physics.collision.colliders;

import box.ascension.app.nb2.physics.collision.ICollider;
import box.ascension.app.nb2.physics.collision.shapes.Shapes;
import box.ascension.app.nb2.physics.collision.shapes.Shapes.Circle;
import box.ascension.app.nb2.physics.collision.shapes.Shapes.Rectangle;
import box.ascension.app.nb2.physics.collision.shapes.Shapes.IShape;
import box.ascension.app.nb2.physics.math.Vector2;

public class CircleCollider implements ICollider {
    
    private Vector2 position;
    private Circle circle;

    public CircleCollider(Vector2 position, double radius) {
        this.position = position;
        this.circle = new Circle(radius);
    }

    @Override
    public boolean isCollidingWith(ICollider other) {
        return getCollisions(other) != null;
    }

    @Override
    public Collision[] getCollisions(ICollider other) {
        if (other.getShape().isAssignableFrom(Shapes.Circle.class)) {
            return new Collision[] {collideCircleVsCircle(this, (CircleCollider) other)};
        } else if (other.getShape().isAssignableFrom(Shapes.Rectangle.class)) {
            return new Collision[] {collideCircleVsRectangle(this, (RectangleCollider) other)};
        }
        // Extend here for other shapes
        return null;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Class<? extends IShape> getShape() {
        return Circle.class;
    }

    @Override
    public BoundingBox getBoundingBox() {
        double r = circle.radius();
        return new BoundingBox(
            new Vector2(position.getX() - r, position.getX() - r),
            new Vector2(position.getX() + r, position.getX() + r)
        );
    }

    public static Collision collideCircleVsCircle(CircleCollider c1, CircleCollider c2) {
        Vector2 diff = c2.position.subtract(c1.position);
        double dist = diff.getMagnitude();
        double radiusSum = c1.circle.radius() + c2.circle.radius();

        if (dist >= radiusSum) return null;

        double penetration = radiusSum - dist;
        Vector2 normal = dist == 0 ? new Vector2(1, 0) : diff.getNormalized();
        Vector2 contactPoint = c1.position.add(normal.scale(c1.circle.radius() - penetration / 2));

        return new Collision(contactPoint, normal, penetration);
    }

    public static Collision collideCircleVsRectangle(CircleCollider circleCol, RectangleCollider rectCol) {
        Vector2 circlePos = circleCol.getPosition();
        Vector2 rectPos = rectCol.getPosition();
        Rectangle rect = rectCol.getRectangle();

        // Find closest point on rectangle to circle center
        double rectHalfWidth = rect.width() / 2;
        double rectHalfHeight = rect.height() / 2;

        double closestX = clamp(circlePos.getX(), rectPos.getX() - rectHalfWidth, rectPos.getX() + rectHalfWidth);
        double closestY = clamp(circlePos.getY(), rectPos.getY() - rectHalfHeight, rectPos.getY() + rectHalfHeight);
        Vector2 closestPoint = new Vector2(closestX, closestY);

        Vector2 diff = circlePos.subtract(closestPoint);
        double dist = diff.getMagnitude();

        if (dist > circleCol.circle.radius()) return null; // no collision

        double penetration = circleCol.circle.radius() - dist;
        Vector2 normal = dist == 0 ? new Vector2(1, 0) : diff.getNormalized();
        Vector2 contactPoint = closestPoint;

        return new Collision(contactPoint, normal, penetration);
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
