package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Base;
import models.BoardAction;
import models.GameState;
import models.PlayerAction;

public class Strategy {
    
    public static List<PlayerAction> decide(GameState gameState) {
        int myId = gameState.game.player;
        List<Base> myBases = gameState.bases.stream().filter(base -> base.player == myId).toList();
        List<Base> foreignBases = gameState.bases.stream().filter(base -> base.player != myId).toList();
        List<Base> emptyBases = gameState.bases.stream().filter(base -> base.player == 0).toList();

        System.out.println("my ID: " + myId);
        System.out.println("my bases: " + myBases);
        System.out.println("foreign bases: " + foreignBases);
        System.out.println("empty bases: " + emptyBases);

        List<PlayerAction> playerActions = new ArrayList<>();

        playerActions = takeEmptyBases(myBases, emptyBases, playerActions);
        System.out.println("player actions: " + playerActions);

        List<BoardAction> attacksOnMyBases = gameState.actions.stream().filter(action -> {
            AtomicBoolean isOnMe = new AtomicBoolean(false);
            myBases.forEach(base -> {
                isOnMe.set(base.uid == action.dest || isOnMe.get());
            });
            return isOnMe.get();
        }).toList();

        for (BoardAction attack: attacksOnMyBases) {
            System.out.println("attack on one of my bases incoming: " + attack);
        }

        return playerActions;
    }


    public static List<PlayerAction> takeEmptyBases(List<Base> myBases, List<Base> emptyBases, List<PlayerAction> playerActions) {

        for (Base emptyBase: emptyBases) {

            int x = 0;
            while (myBases.get(x).population <= emptyBase.population) {
                x++;
            }

            if (myBases.get(x).population > emptyBase.population) {
                playerActions.add(new PlayerAction(myBases.get(x).uid, emptyBase.uid, emptyBase.population + 1));
            }

        }

        return playerActions;

    }

}