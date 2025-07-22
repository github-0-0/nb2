package box.ascension.app.nb2.physics.collision.colliders;

import box.ascension.app.nb2.physics.collision.shapes.Shapes.IShape;
import box.ascension.app.nb2.physics.collision.ICollider;
import box.ascension.app.nb2.physics.collision.shapes.Shapes.Circle;
import box.ascension.app.nb2.physics.collision.shapes.Shapes.Rectangle;
import box.ascension.app.nb2.physics.math.Vector2;

public class RectangleCollider implements ICollider {

    private Vector2 position;
    private Rectangle rectangle;

    public RectangleCollider(Vector2 position, double width, double height) {
        this.position = position;
        this.rectangle = new Rectangle(width, height);
    }

    @Override
    public boolean isCollidingWith(ICollider other) {
        return getCollisions(other) != null;
    }

    @Override
    public Collision[] getCollisions(ICollider other) {
        Class<? extends IShape> otherShape = other.getShape();
        if (otherShape.isAssignableFrom(Circle.class)) {
            return new Collision[] {CircleCollider.collideCircleVsRectangle((CircleCollider) other, this)};
        } else if (otherShape.isAssignableFrom(Rectangle.class)) {
            return new Collision[] {collideRectangleVsRectangle(this, (RectangleCollider) other)};
        }
        return null;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Class<? extends IShape> getShape() {
        return Rectangle.class;
    }

    @Override
    public BoundingBox getBoundingBox() {
        double hw = rectangle.width() / 2;
        double hh = rectangle.height() / 2;
        return new BoundingBox(
            new Vector2(position.getX() - hw, position.getY() - hh),
            new Vector2(position.getX() + hw, position.getY() + hh));
    }

    public static Collision collideRectangleVsRectangle(RectangleCollider r1, RectangleCollider r2) {
        BoundingBox b1 = r1.getBoundingBox();
        BoundingBox b2 = r2.getBoundingBox();

        if (!b1.intersects(b2)) return null;

        Vector2 diff = r2.position.subtract(r1.position);

        double overlapX = (r1.rectangle.width() / 2 + r2.rectangle.width() / 2) - Math.abs(diff.getX());
        double overlapY = (r1.rectangle.height() / 2 + r2.rectangle.height() / 2) - Math.abs(diff.getY());

        if (overlapX < overlapY) {
            double normalX = diff.getX() < 0 ? -1 : 1;
            return new Collision(
                r1.position.add(new Vector2(normalX * r1.rectangle.width() / 2, 0)),
                new Vector2(normalX, 0),
                overlapX);
        } else {
            double normalY = diff.getY() < 0 ? -1 : 1;
            return new Collision(
                r1.position.add(new Vector2(0, normalY * r1.rectangle.height() / 2)),
                new Vector2(0, normalY),
                overlapY);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}


