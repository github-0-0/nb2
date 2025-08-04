package box.ascension.app.nb2.ui;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import box.ascension.app.DataAccessingService;
import box.ascension.app.nb2.physics2.Game;

public class GameManager2 {
    
    public static ConcurrentHashMap<Long, Game> sims 
        = new ConcurrentHashMap<>(256);

    public static synchronized Game startGame(long id, long team1Id, long team2Id) {
        clean();
        if (sims.containsKey(id)) {
            return sims.get(id);
        }
        try {
            var team1 = DataAccessingService.instance.getTeam(team1Id).get();
            var team2 = DataAccessingService.instance.getTeam(team2Id).get();
            var team1NeuronIds = team1.neurons;
            var team2NeuronIds = team2.neurons;
            var game = new Game(id, team1NeuronIds, team2NeuronIds);
            game.start();
            return game;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized void stopSim(long id) {
        if (sims.containsKey(id)) {
            sims.get(id).stop();
        }
        clean();
    }

    public static void clean() {
        for (Entry<Long, Game> entry : sims.entrySet()) {
            var sim = entry.getValue();
            if (!sim.active && sim.hasStarted) {
                sims.remove(entry.getKey());
            }
        }
    }

    public static final Thread cleanerThread = new Thread(() -> {
        while(true) {
            try {
                clean();
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("oh no something happened");
            }
        }
    });

    public static void initCleanerThread() {
        if (!cleanerThread.isAlive()) {
            cleanerThread.start();
        }
    }

}
