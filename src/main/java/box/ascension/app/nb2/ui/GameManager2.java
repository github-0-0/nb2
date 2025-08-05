package box.ascension.app.nb2.ui;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import box.ascension.app.DataAccessingService;
import box.ascension.app.nb2.physics2.Game;

public class GameManager2 {
    
    public static ConcurrentHashMap<Long, Game> games 
        = new ConcurrentHashMap<>(256);

    /**
     * Starts a game
     * @param id The game id
     * @param team1Id Team 1's id
     * @param team2Id Team 2's id
     * @return The started game
     */
    public static synchronized Game startGame(long id, long team1Id, long team2Id) {
        clean();
        if (games.containsKey(id)) {
            return games.get(id);
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

    /**
     * Stops a game
     * @param id The game id
     */
    public static synchronized void stopGame(long id) {
        if (games.containsKey(id)) {
            games.get(id).stop();
        }
        clean();
    }

    /**
     * Removes all games that have stopped
     */
    public static void clean() {
        for (Entry<Long, Game> entry : games.entrySet()) {
            var sim = entry.getValue();
            if (!sim.active && sim.hasStarted) {
                games.remove(entry.getKey());
            }
        }
    }

    /**
     * The thread to clean finished games.
     */
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

    /**
     * Initializes the cleaner thread
     */
    public static void initCleanerThread() {
        if (!cleanerThread.isAlive()) {
            cleanerThread.start();
        }
    }

}
