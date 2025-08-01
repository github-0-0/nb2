package box.ascension.app.nb2.ui;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import box.ascension.app.nb2.physics.PhysicsSim;

public class GameManager {
    
    public static ConcurrentHashMap<Long, PhysicsSim> sims 
        = new ConcurrentHashMap<>(256);

    public static synchronized PhysicsSim startSim(long id) {
        if (sims.containsKey(id)) {
            return sims.get(id);
        }
        var sim = new PhysicsSim(id);
        sims.put(id, sim);
        sim.start();
        return sim;
    }

    public static void clean() {
        for (Entry<Long, PhysicsSim> entry : sims.entrySet()) {
            var sim = entry.getValue();
            if (!sim.active && sim.hasStarted) {
                sims.remove(entry.getKey());
            }
        }
    }

}
