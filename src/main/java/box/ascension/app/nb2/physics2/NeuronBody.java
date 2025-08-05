package box.ascension.app.nb2.physics2;

import java.util.ArrayList;
import java.util.List;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;

import box.ascension.app.DataAccessingService;
import box.ascension.app.nb2.game.neurons.Neuron;

public class NeuronBody extends Body {

    public long id;
    public Neuron neuron;
    public double size;

    /**
     * Creates a neuron with such id
     * @param id The neuron id
     * @return
     */
    public static NeuronBody of(long id) {
        var optionalNeuron = DataAccessingService.instance.getNeuron(id);
        if (optionalNeuron.isPresent()) {
            var neuron = optionalNeuron.get();
            return switch (neuron.neuronType) {
                case STANDARD -> new NeuronBody(id, 30, 2.7, 0.5, 0.5);
                default -> new NeuronBody(id, 30, 2.7, 0.5, 0.5);
            };
        } else {
            throw new IllegalArgumentException("Cannot find neuron with such id");
        }
    }

    /**
     * Creates a neuron physics body
     * @param id The neuron id
     * @param size The radius of the neuron
     * @param density The density of the neuron
     * @param friction The friction coefficient
     * @param restitution The bounciness
     */
    private NeuronBody(long id, double size, double density, double friction, double restitution) {
        this.id = id;
        this.size = size;
        BodyFixture fixture = this.addFixture(Geometry.createCircle(size));
        fixture.setDensity(density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        setMass(MassType.NORMAL);
    }

    /**
     * Increases the size
     * @param factor The factor in which the size increases
     */
    public void scale(double factor) {
        size *= factor;
        List<BodyFixture> old = new ArrayList<>(this.getFixtures());
        double density = old.get(0).getDensity() / (factor * factor);
        double friction = old.get(0).getFriction();
        double restitution = old.get(0).getRestitution();
        MassType massType = this.getMass().getType();
        for (BodyFixture f : old) {
            this.removeFixture(f);
        }
        BodyFixture f = this.addFixture(Geometry.createCircle(this.size));
        f.setDensity(density);
        f.setFriction(friction);
        f.setRestitution(restitution);
        setMass(massType);
    }

}
