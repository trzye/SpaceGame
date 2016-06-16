package pl.edu.pw.ee.spacegame.server.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;

import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.REFRESH_GAME_TIME;

/**
 * Created by Michał on 2016-06-16.
 */
@Component
public class ScheduleRefresherComponent extends BaseAbstractComponent {

    @Scheduled(fixedRate = REFRESH_GAME_TIME)
    @Transactional
    public void reportCurrentTime() {
        Refresher.refreshAll(this);
    }

}
