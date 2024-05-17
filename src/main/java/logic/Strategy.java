package logic;

import java.util.ArrayList;
import java.util.List;

import models.Base;
import models.GameState;
import models.PlayerAction;

public class Strategy{
    
    public static List<PlayerAction> decide(GameState gameState){
        System.out.println("Test");
        Base base = gameState.bases.get(0);
        List<PlayerAction> playerAction = new ArrayList<>();
        playerAction.add(new PlayerAction(base.uid, base.uid, 1));
        return playerAction;
    }
}
