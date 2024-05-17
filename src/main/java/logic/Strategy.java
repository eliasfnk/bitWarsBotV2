package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Base;
import models.BoardAction;
import models.GameState;
import models.PlayerAction;

public class Strategy{
    
    public static List<PlayerAction> decide(GameState gameState){
        int myId = gameState.game.player;

        System.out.println("Test");

        List<Base> myBases = gameState.bases.stream().filter(base -> base.player == myId).toList();
        List<Base> foreignBases = gameState.bases.stream().filter(base -> base.player != myId).toList();

        List<BoardAction> attacksOnMe = gameState.actions.stream().filter(action -> {
            AtomicBoolean isOnMe = new AtomicBoolean(false);
            myBases.forEach(base -> {
                isOnMe.set(base.uid == action.dest || isOnMe.get());
            });
            return isOnMe.get();
        }).toList();


        Base base = gameState.bases.get(0);
        List<PlayerAction> playerAction = new ArrayList<>();
        playerAction.add(new PlayerAction(base.uid, base.uid, 1));
        return playerAction;
    }
}
