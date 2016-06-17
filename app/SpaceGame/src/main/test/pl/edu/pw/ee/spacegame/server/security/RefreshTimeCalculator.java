package pl.edu.pw.ee.spacegame.server.security;

import junit.framework.Assert;
import org.junit.Test;

import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.*;

/**
 * Created by Michał on 2016-06-17.
 */
public class RefreshTimeCalculator {

    //okres odświeżania stanu gry
    public long calculateRefreshTime() {
        return (long) (0.5 * (
                (MILLISECONDS_FOR_ONE_RESOURCE_UNIT
                        / Math.max(UNUNTRIUM_MINE_MAX_LEVEL, GADOLIN_MINE_MAX_LEVEL))
        ));
    }

    @Test
    public void calculate() {
        System.out.println(calculateRefreshTime());
        Assert.assertTrue(true);
    }

}
