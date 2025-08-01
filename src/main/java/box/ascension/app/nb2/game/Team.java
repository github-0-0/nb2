package box.ascension.app.nb2.game;

import java.util.ArrayList;

import box.ascension.app.nb2.game.hq.Hq;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
    public int rating;
    public int credits;
    public ArrayList<Long> neurons;
    
    @Transient // Temporary transience
    public Hq hq;

    public Team() {
        this("_UNKNOWN");
    }

    public Team(String name) {
        this.name = name;
        rating = 100;
        credits = 25000;
        neurons = new ArrayList<>();
        hq = new Hq();
    }

    @Override
    public String toString() {
        return String.format("Team[id=%d, name='%s', rating=%d, credits=%d]", 
            id, name, rating, credits);
    }

}
