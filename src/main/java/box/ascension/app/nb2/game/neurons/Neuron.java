package box.ascension.app.nb2.game.neurons;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Neuron {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
    @Enumerated(EnumType.STRING)
    public NeuronType neuronType = NeuronType.STANDARD;
    public int accel = 15;
    public int speed = 15;
    public int strength = 15;

    public Neuron() {
        this("_UNKNOWN");
    }

    public Neuron(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Neuron[id=%d, name='%s', " 
            + "type='%s', accel=%d, speed=%d, strength=%d]", 
            id, name, neuronType.name(), accel, speed, strength);
    }

}
