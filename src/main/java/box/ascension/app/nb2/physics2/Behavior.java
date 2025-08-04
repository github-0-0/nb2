package box.ascension.app.nb2.physics2;

import org.dyn4j.dynamics.Force;

public abstract class Behavior {

    public static class EmptyInput implements NeuronInput {
        public static final EmptyInput EMPTY = new EmptyInput(); 
    }
    
    public static interface NeuronInput {}

    public NeuronBody body;

    public Behavior(NeuronBody body) {
        this.body = body;
    }

    public abstract Force getOutput(NeuronInput input);

    public void apply(NeuronInput input) {
        body.applyForce(getOutput(input));
    }

}
