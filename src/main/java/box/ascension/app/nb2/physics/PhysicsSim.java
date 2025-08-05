package box.ascension.app.nb2.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonProperty;

import box.ascension.app.Util.JsonWhitelist;
import box.ascension.app.nb2.physics.colliders.Collider;
import box.ascension.app.nb2.physics.colliders.Collider.ColliderState;
import box.ascension.app.nb2.physics.colliders.Collision;

@Deprecated
@JsonWhitelist
public class PhysicsSim {

    public static record SimState(
        long id,
        double dt,
        double timeElapsed,
        ArrayList<Collider> colliderStates
    ) {}

    public static AtomicLong instances = new AtomicLong(0);
    public long id;
    public final double dt = 1.0 / 25.0;
    public PhysicsThread physicsThread;
    public double timeElapsed = 0;
    public ArrayList<Collider> colliders = new ArrayList<>();
    public boolean active = false;
    public boolean hasStarted = false;
    private static final double MAX_SIM_TIME = 60.0 * 1.0;

    public PhysicsSim(long id) {
        this.id = id;
        instances.incrementAndGet();
        physicsThread = new PhysicsThread();
    }

    @JsonProperty("simState")
    public SimState getState() {
        return new SimState(id, dt, timeElapsed, colliders);
    }

    public void start() {
        if(!active) {
            active = true;
            physicsThread.start();
            System.out.println("eh did it start yet");
        }
    }

    public void stop() {
        physicsThread.post(() -> active = false);
    }

    public Collider addObject(Collider o) {
        physicsThread.post(() -> colliders.add(o));
        return o;
    }

    public Collider[] addObjects(Collider... o) {
        physicsThread.post(() -> colliders.addAll(List.of(o)));
        return o;
    }

    public class PhysicsThread extends Thread {
        
        private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        private Runnable currentTask;

        public void post(Runnable task) {
            taskQueue.add(task);
        }

        @Override
        public void run() {
            hasStarted = true;
            while (active) {
                try {
                    active = timeElapsed < MAX_SIM_TIME;
                    while ((currentTask = taskQueue.poll()) != null) {
                        currentTask.run();
                    }
                    for (Collider x : colliders) {
                        for (Collider y : colliders) {
                            if (!x.equals(y)) {
                                x.collide(x.getCollision(y));
                            }
                        }
                    }
                    for (Collider collider : colliders) {
                        collider.updatePose();
                    }
                    System.out.println(timeElapsed);
                    timeElapsed += dt;
                    Thread.sleep((long) (dt * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("oh it stopped");
        }

    }

}
