package box.ascension.app.nb2.physics.colliders;

import box.ascension.app.nb2.physics.Vector2d;

public class Collision {
    
    public Vector2d position;
    public Vector2d normal;
    public Collider other;
    
    public Collision(Vector2d position, Vector2d normal, Collider other) {
        this.position = position;
        this.normal = normal;
        this.other = other;
    }

}
