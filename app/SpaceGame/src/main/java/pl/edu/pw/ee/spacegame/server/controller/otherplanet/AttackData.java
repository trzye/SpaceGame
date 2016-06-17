package pl.edu.pw.ee.spacegame.server.controller.otherplanet;

/**
 * Created by Michał on 2016-06-17.
 */
public class AttackData {
    private Long secondsToAttack;
    private Long secondsToComeBack;

    public Long getSecondsToAttack() {
        return secondsToAttack;
    }

    public void setSecondsToAttack(Long secondsToAttack) {
        this.secondsToAttack = secondsToAttack;
    }

    public Long getSecondsToComeBack() {
        return secondsToComeBack;
    }

    public void setSecondsToComeBack(Long secondsToComeBack) {
        this.secondsToComeBack = secondsToComeBack;
    }
}
