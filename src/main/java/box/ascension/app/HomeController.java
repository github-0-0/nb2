package box.ascension.app;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    public static AtomicLong counter = new AtomicLong(0);
    
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/getHelpCounter")
    @ResponseBody
    public AtomicLong getHelpCounter() {
        return counter;
    }

    @PostMapping("/incrementHelpCounter")
    @ResponseBody
    public AtomicLong incrementHelpCounter() {
        counter.incrementAndGet();
        return counter;
    }

}
