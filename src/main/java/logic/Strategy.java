package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import models.Base;
import models.BoardAction;
import models.GameState;
import models.PlayerAction;

import static java.lang.Math.*;

public class Strategy {
    
    public static List<PlayerAction> decide(GameState gameState) {
        int myId = gameState.game.player;
        List<Base> allBases = gameState.bases;
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


        playerActions = defendBases(gameState, allBases, myBases, playerActions);



        //for (BoardAction attack: attacksOnMyBases) {
        //    System.out.println("attack on one of my bases incoming: " + attack);
        //}

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


    public static List<PlayerAction> defendBases(GameState gameState, List<Base> allBases, List<Base> myBases, List<PlayerAction> playerActions) {

        List<BoardAction> attacksOnMe = gameState.actions.stream().filter(action -> {
            AtomicBoolean isTargetingMe = new AtomicBoolean(false);
            myBases.forEach(base -> {
                isTargetingMe.set(base.uid == action.dest || isTargetingMe.get());
            });
            return isTargetingMe.get();
        }).toList();

        for (BoardAction attack: attacksOnMe) {
            Base target = getBaseById(attack.dest, allBases);
            assert target != null;
            if (attack.amount > target.population) {
                // TODO: && attack.progress.distance < ...
                Base sourceBase = getNearestBase(target, myBases);
                playerActions.add(new PlayerAction(sourceBase.uid, target.uid, target.population + 1));
            }
        }

        return playerActions;

    }


    public static double getDistance(Base base1, Base base2) {

        return sqrt( pow(base1.position.x - base2.position.x, 2) + pow(base1.position.y - base2.position.y, 2) + pow(base1.position.z - base2.position.z, 2) );

    }


    public static Base getBaseById(int id, List<Base> allBases) {

        for (Base base: allBases) {
            if (base.uid == id) {
                return base;
            }
        }
        return null;

    }


    public static Base getNearestBase(Base base1, List<Base> otherBases) {

        double min = 999999;
        Base nearestBase = null;
        for (Base base: otherBases) {
            double distance = getDistance(base1, base);
            if (distance < min) {
                min = distance;
                nearestBase = base1;
            }
        }
        return nearestBase;

    }


    public static void consideringGracePeriod(GameState gameState) {

        int gracePeriod = gameState.config.paths.gracePeriod;
        int deathRate = gameState.config.paths.deathRate;

    }

}