package box.ascension.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import box.ascension.app.Util.Pair;
import box.ascension.app.nb2.physics.PhysicsSim;
import box.ascension.app.nb2.physics.PhysicsUtil.Impulse;
import box.ascension.app.nb2.physics.Vector2d;
import box.ascension.app.nb2.physics.colliders.CircleCollider;
import box.ascension.app.nb2.ui.GameManager;
import box.ascension.app.nb2.ui.GameManager2;

@SuppressWarnings("unused")
@Controller
public class ConsoleController {

    @FunctionalInterface
    public interface Command {

        String execute(Matcher matcher);
        
    }

    private static final Map<Pattern, Command> commands = new LinkedHashMap<>();

    static {
        commands.put(Pattern.compile("^start$"), matcher -> {
            System.out.println("Starting...");
            var sim = GameManager.startSim(0);
            var circle = new CircleCollider(30, new Vector2d(360, 0), 10);
            circle.impulse(new Impulse(new Vector2d(0, 5), 0));
            var circle2 = new CircleCollider(30, new Vector2d(360, 80), 10);
            sim.addObjects(circle, circle2);
            circle2.impulse(new Impulse(new Vector2d(0, -5), 0));
            circle.bounciness = 1;
            circle2.bounciness = 1;
            var circles3 = new CircleCollider[] {
                new CircleCollider(15, new Vector2d(290, 40), 10),
                new CircleCollider(15, new Vector2d(290, 70), 10),
                new CircleCollider(15, new Vector2d(290, 100), 10),
                new CircleCollider(15, new Vector2d(290, 130), 10)
            };
            sim.addObjects(circles3);
            sim.start();
            return "success";
        });

        commands.put(Pattern.compile("^stop\\s+(\\w+)$"), matcher -> {
            String word = matcher.group(1);
            long num = Long.parseLong(word);
            try {
                GameManager.stopSim(0);
                return "success";
            } catch (Exception e) {
                System.out.println("oh no");
                return "failure";
            }
        });
        
        commands.put(Pattern.compile("^startGame\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)$"), matcher -> {
            String word0 = matcher.group(1);
            String word1 = matcher.group(2);
            String word2 = matcher.group(3);
            long num0 = Long.parseLong(word0);
            long team1Id = DataAccessingService.instance.getTeamId(word1);
            long team2Id = DataAccessingService.instance.getTeamId(word2);
            try {
                GameManager2.startGame(num0, team1Id, team2Id);
                return "success";
            } catch (Exception e) {
                System.out.println("oh no");
                return "failure";
            }
        });

        commands.put(Pattern.compile("^echo\\s+(\\w+)$"), matcher -> {
            String word = matcher.group(1);
            System.out.println("Echo: " + word);
            return word;
        });
    }

    public static String process(String input) {
        for (Map.Entry<Pattern, Command> entry : commands.entrySet()) {
            Matcher matcher = entry.getKey().matcher(input);
            if (matcher.matches()) {
                return entry.getValue().execute(matcher);
            }
        }
        System.out.println("Unknown command: " + input);
        return "failure";
    }

    @RequestMapping("/console")
    public String page() {
        return "console.html";
    }

    @PostMapping("/submit")
    @ResponseBody
    public String receiveString(@RequestParam String textInput) {
        var out = process(textInput); 
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("(yyyy-MM-dd HH:mm:ss) "));
        return time + out;
    }

}
