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

        List<Base> myBases = gameState.bases.stream().filter(base -> base.player == myId).toList();
        List<Base> foreignBases = gameState.bases.stream().filter(base -> base.player != myId).toList();
        List<Base> emptyBases = gameState.bases.stream().filter(base -> base.player == 0).toList();

        Base myFirstBase = myBases.get(0);
        System.out.println("My first base: " + myFirstBase.uid);

        // Get empty bases
        for (Base emptyBase: emptyBases) {
            System.out.println("Empty base: " + emptyBase);
            int population = emptyBase.population;
            System.out.println("population: " + population);
            new PlayerAction(myFirstBase.uid, emptyBase.uid, population + 1);
        }

        List<BoardAction> attacksOnMyBases = gameState.actions.stream().filter(action -> {
            AtomicBoolean isOnMe = new AtomicBoolean(false);
            myBases.forEach(base -> {
                isOnMe.set(base.uid == action.dest || isOnMe.get());
            });
            return isOnMe.get();
        }).toList();

        for (BoardAction attack: attacksOnMyBases) {
            System.out.println(attack);
        }




        Base base = gameState.bases.get(0);
        List<PlayerAction> playerAction = new ArrayList<>();
        playerAction.add(new PlayerAction(base.uid, base.uid, 1));
        return playerAction;
    }
}
