package box.ascension.app.nb2.physics.colliders;

import box.ascension.app.Util.JsonWhitelist;
import box.ascension.app.nb2.physics.Vector2d;

import com.fasterxml.jackson.annotation.JsonProperty;

import box.ascension.app.nb2.physics.PhysicsUtil.BoundingBox;

@JsonWhitelist
public class CircleCollider extends Collider {

    public static record CircleColliderState(
        double radius,
        Vector2d position,
        Vector2d velocity,
        double angle,
        double omega,
        String type
    ) implements ColliderState {}

    public double radius;

    public CircleCollider(double radius, Vector2d position, double mass) {
        this.mass = mass;
        this.moment = 0.5 * mass * radius * radius;
        this.radius = radius;
        this.position.set(position);
        this.bounds = new BoundingBox(
            this.position, new Vector2d(radius * 2, radius * 2));
    }

    @Override
    public Collision getCollision(Collider collider) {
        if (collider instanceof CircleCollider) {
            CircleCollider o = (CircleCollider) collider;
            if (bounds.isOverlapping(o.bounds)) {
                Vector2d delta = o.position.subtracted(position);
                double penetration = delta.getMagnitude() - radius - o.radius;
                Vector2d center = position.added(delta.scaled(0.5));
                if (penetration >= 0) {
                    return new Collision(
                        center, 
                        center.added(delta.scaled(0.5)).getNorm(), 
                        collider,
                        penetration);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    @JsonProperty("collider")
    public ColliderState getState() {
        return new CircleColliderState(radius, position, translationVel, angle, omega, "circle");
    }

}
