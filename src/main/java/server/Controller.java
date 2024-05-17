package server;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import models.GameState;
import models.PlayerAction;

@RestController
public class Controller {

    @GetMapping("/")
    public String identify() {
        return "Hallo";
    }
    @PostMapping("/")
    public List<PlayerAction> index(@RequestBody GameState payload){
        return logic.Strategy.decide(payload);
    }
}
