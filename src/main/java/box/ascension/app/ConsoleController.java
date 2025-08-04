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

@SuppressWarnings("unused")
@Controller
public class ConsoleController {

    @FunctionalInterface
    public interface Command {
        void execute(Matcher matcher);
    }

    private static final Map<Pattern, Command> commands = new LinkedHashMap<>();

    static {
        commands.put(Pattern.compile("^start$"), matcher -> {
            System.out.println("Starting...");
            var sim = GameManager.startSim(0);
            var circle = new CircleCollider(30, new Vector2d(60, 0), 10);
            circle.impulse(new Impulse(new Vector2d(0, 1), 0));
            var circle2 = new CircleCollider(30, new Vector2d(60, 80), 10);
            sim.addObjects(circle, circle2);
            circle2.impulse(new Impulse(new Vector2d(0, -1), 0));
            circle.bounciness = 1;
            circle2.bounciness = 1;
            sim.start();
        });

        commands.put(Pattern.compile("^echo\\s+(\\w+)$"), matcher -> {
            String word = matcher.group(1);
            System.out.println("Echo: " + word);
        });
    }

    public static void process(String input) {
        for (Map.Entry<Pattern, Command> entry : commands.entrySet()) {
            Matcher matcher = entry.getKey().matcher(input);
            if (matcher.matches()) {
                entry.getValue().execute(matcher);
                return;
            }
        }
        System.out.println("Unknown command: " + input);
    }

    @RequestMapping("/console")
    public String page() {
        return "console.html";
    }

    @PostMapping("/submit")
    @ResponseBody
    public String receiveString(@RequestParam String textInput) {
        process(textInput); 
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("(yyyy-MM-dd HH:mm:ss)"));
        return time + " success";
    }

}
