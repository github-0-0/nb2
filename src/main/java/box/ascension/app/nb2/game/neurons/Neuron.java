package box.ascension.app.nb2.game.neurons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Neuron {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @Transient // Temporary transience
    private INeuronType type;
    private int accel;
    private int speed;
    private int strength;

    public Neuron() {
        this("_UNKNOWN");
    }

    public Neuron(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Neuron[id=%d, name='%s'," 
            + "type='%s', accel=%d, speed=%d, strength=%d]", 
            name, type.toString(), accel, speed, strength);
    }

}
