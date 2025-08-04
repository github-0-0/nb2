package box.ascension.app.nb2.physics.colliders;

import box.ascension.app.nb2.physics.Vector2d;
import box.ascension.app.nb2.physics.PhysicsUtil.BoundingBox;
import box.ascension.app.nb2.physics.PhysicsUtil.Impulse;
import box.ascension.app.nb2.physics.PhysicsSim;
import box.ascension.app.nb2.physics.PhysicsUtil;

public abstract class Collider {

    public static interface ColliderState {}

    public PhysicsSim scene;
    
    public double mass;
    public double moment;
    public double staticFriction = 0;
    public double dynamicFriction = 0;
    public double bounciness = 0;
    public double percentPenetration = 0.8;
    public double slop = 0.01;
    public final Vector2d translationVel = new Vector2d(0, 0);
    public double omega = 0;
    public final Vector2d position = new Vector2d(0, 0);
    public double angle;
    public BoundingBox bounds;
    
    public abstract Collision getCollision(Collider other);

    public abstract ColliderState getState();
    
    public void collide(Collision c) {
        if (c == null) {
            return;
        }
    
        Collider B = c.other;
    
        // 1. Radii from centers to contact
        Vector2d radA = c.position.subtracted(this.position);
        Vector2d radB = c.position.subtracted(B.position);
    
        // 2. Rotational velocities at contact point
        Vector2d rotA = new Vector2d(-radA.y, radA.x).scaled(this.omega);
        Vector2d rotB = new Vector2d(-radB.y, radB.x).scaled(B.omega);
    
        // 3. Velocities at contact point
        Vector2d vA = this.translationVel.added(rotA);
        Vector2d vB = B.translationVel.added(rotB);
        Vector2d vRel = vA.subtracted(vB);
    
        // 4. Normal & tangent vectors
        Vector2d normal = c.normal.getNorm();
        Vector2d tangent = new Vector2d(-normal.y, normal.x);
    
        double vRelNorm = Vector2d.dot(vRel, normal);
        double vRelTang = Vector2d.dot(vRel, tangent);
    
        // 5. Effective mass along normal
        double radCrossNormA = radA.cross(normal);
        double radCrossNormB = radB.cross(normal);
        double invMassNorm = (1.0 / this.mass) + (1.0 / B.mass)
            + radCrossNormA * radCrossNormA / this.moment
            + radCrossNormB * radCrossNormB / B.moment;
    
        // 6. Restitution
        double e = Math.min(this.bounciness, B.bounciness);
        double impulseNormal = 0;
        if (vRelNorm < -PhysicsUtil.EPS * 10) {
            impulseNormal = -(1 + e) * vRelNorm / invMassNorm;
        }
    
        // 7. Normal impulse vector
        Vector2d Jn = normal.scaled(impulseNormal);
    
        // 8. Tangential (friction) impulse
        double radCrossTangA = radA.cross(tangent);
        double radCrossTangB = radB.cross(tangent);
        double invMassTang = (1.0 / this.mass) + (1.0 / B.mass)
            + radCrossTangA * radCrossTangA / this.moment
            + radCrossTangB * radCrossTangB / B.moment;
    
        double frictionImpulse = -vRelTang / invMassTang;
    
        // 9. Clamp to Coulomb friction cone
        double mu = Math.sqrt(this.staticFriction * B.staticFriction);
        double maxFriction = mu * impulseNormal;
    
        if (Math.abs(frictionImpulse) > maxFriction) {
            double muK = Math.sqrt(this.dynamicFriction * B.dynamicFriction);
            frictionImpulse = -vRelTang * muK;
        }
    
        Vector2d Jt = tangent.scaled(frictionImpulse);
    
        // 10. Total impulse
        Vector2d J = Jn.added(Jt);

        impulse(
            new Impulse(J.scaled(1.0 / this.mass), radA.cross(J) / this.moment));
        c.other.impulse(
            new Impulse(J.scaled(-1.0 / B.mass), -radB.cross(J) / B.moment));

        double penetration = Math.max(c.penetration - slop, 0.0);

        double invMassSum = (1.0 / this.mass) + (1.0 / B.mass);
        if (invMassSum > 0) {
            double correctionMag = (percentPenetration * penetration) / invMassSum;
            Vector2d correction = normal.scaled(correctionMag);

            position.subtractBy(correction.scaled(1.0 / this.mass));
            B.position.addBy(correction.scaled(1.0 / B.mass));
        }
    }
    

    public void impulse(Impulse impulse) {
        translationVel.addBy(impulse.pos());
        omega += impulse.alpha();
    }

    public void accelerate(Vector2d ta, double alpha) {
        double dt = 1 / 15;
        if (scene != null) {
            dt = scene.dt;
        }
        translationVel.addBy(ta.scaled(dt));
        omega += alpha * dt;
    }

    public void updatePose() {
        position.addBy(translationVel);
        angle += omega;
    }

}
