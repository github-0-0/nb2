package box.ascension.app.nb2.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import box.ascension.app.nb2.physics.PhysicsSim;

@Controller
public class NB2Controller {
        
    @RequestMapping("/nb2")
    public String page() {
        return "nb2.html";
    }

    @GetMapping("/getGame")
    @ResponseBody
    public PhysicsSim getGame(@RequestParam long id) {
        return GameManager.sims.get(id);
    }

}
