package box.ascension.app.nb2.physics2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.world.World;

import com.fasterxml.jackson.annotation.JsonProperty;

import box.ascension.app.Util.JsonWhitelist;
import box.ascension.app.nb2.physics2.Behavior.EmptyInput;

@JsonWhitelist
public class Game {

    public record GameState (
        long id,
        double timeElapsed,
        List<NeuronState> neuronStates
    ) {}

    public record NeuronState(
        long id,
        double x,
        double y,
        double rotation,
        double vx,
        double vy,
        double omega,
        double size
    ) {}

    public class GameThread extends Thread {

        private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        private Runnable currentTask;

        public void post(Runnable task) {
            taskQueue.add(task);
        }

        @Override
        public void run() {
            while ((currentTask = taskQueue.poll()) != null) {
                currentTask.run();
            }
            for (Behavior behavior : behaviors) {
                behavior.apply(EmptyInput.EMPTY);
            }
            world.update(1.0 / 15.0);
        }

    }

    public long id;
    public List<Long> team1NeuronIds;
    public List<Long> team2NeuronIds;
    public List<Behavior> behaviors;
    public double timeElapsed;
    public World<Body> world;
    public boolean active = false;
    public boolean hasStarted = false;
    public GameThread gameThread;
    public double frameWidth = 10;
    public double frameHeight = 6;

    /**
     * Creates a game
     * @param id The game id
     * @param team1NeuronIds The active neurons on team 1
     * @param team2NeuronIds The active neurons on team 2
     */
    public Game(long id, List<Long> team1NeuronIds, List<Long> team2NeuronIds) {
        this.id = id;
        this.team1NeuronIds = team1NeuronIds;
        this.team2NeuronIds = team2NeuronIds;
        world = new World<>();
        world.setGravity(0, 0);
        addBoundingBox(10, 6);
        
        for (long neuronId : team1NeuronIds) {
            try {
                NeuronBody body = NeuronBody.of(neuronId);
                body.translate(0, frameHeight / 2);
                world.addBody(body);
            } catch (Exception e) {}
        }

        for (long neuronId : team2NeuronIds) {
            try {
                NeuronBody body = NeuronBody.of(neuronId);
                body.translate(frameWidth, frameHeight / 2);
                world.addBody(body);
            } catch (Exception e) {}
        }

        gameThread = new GameThread();
    }

    /**
     * Starts the game
     */
    public void start() {
        if(!active) {
            active = true;
            gameThread.start();
            System.out.println("eh did it start yet");
        }
    }

    /**
     * Stops the game
     */
    public void stop() {
        gameThread.post(() -> active = false);
    }

    private void addBoundingBox(double width, double height) {
        double wallThickness = 0.2;
        double halfW = width / 2.0;
        double halfH = height / 2.0;
        addWall(0, -halfH - wallThickness / 2, width, wallThickness);
        addWall(0, +halfH + wallThickness / 2, width, wallThickness);
        addWall(-halfW - wallThickness / 2, 0, wallThickness, height);
        addWall(+halfW + wallThickness / 2, 0, wallThickness, height);
    }

    private void addWall(double x, double y, double w, double h) {
        Body wall = new Body();
        wall.addFixture(Geometry.createRectangle(w, h));
        wall.setMass(MassType.INFINITE);
        wall.translate(x, y);
        world.addBody(wall);
    }

    @JsonProperty("gameState")
    public GameState getGameState() {
        List<NeuronState> neuronStates = new ArrayList<>(10);
        var bodies = world.getBodies();
        for (Body body : bodies) {
            if (body instanceof NeuronBody) {
                neuronStates.add(new NeuronState(
                    ((NeuronBody) body).id,
                    body.getTransform().getTranslationX(), 
                    body.getTransform().getTranslationX(), 
                    body.getTransform().getRotationAngle(),
                    body.getLinearVelocity().x, 
                    body.getLinearVelocity().y,
                    body.getTransform().getRotationAngle(),
                    ((NeuronBody) body).size));
            }
        }

        return new GameState(id, timeElapsed, neuronStates);
    }
}
