package box.ascension.app.nb2.physics.collision;

import box.ascension.app.nb2.physics.collision.shapes.Shapes.IShape;
import box.ascension.app.nb2.physics.math.Vector2;

public interface ICollider {
    
    boolean isCollidingWith(ICollider other);

    Vector2 getPosition();

    Collision[] getCollisions(ICollider other);

    Class<? extends IShape> getShape();

    BoundingBox getBoundingBox();

    public static record Collision (Vector2 contactPoint, Vector2 normal, double depth) {}

    public static record BoundingBox (Vector2 min, Vector2 max) {
        public boolean intersects(BoundingBox other) {
            return !(other.min.getX() > this.max.getX() || 
                     other.max.getX() < this.min.getX() || 
                     other.min.getY() > this.max.getY() || 
                     other.max.getY() < this.min.getY());
        }
    }
    
}
