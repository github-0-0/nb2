package box.ascension.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    public static class Help {
        public long x;
        public Help(long number) {
            x = number;
        }
        public Help increment() {
            x++;
            return this;
        }
    }
    public static Help counter = new Help(0);
    
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/getHelpCounter")
    @ResponseBody
    public Help getHelpCounter() {
        return counter;
    }

    @PostMapping("/incrementHelpCounter")
    @ResponseBody
    public Help incrementHelpCounter() {
        return counter.increment();
    }

}
