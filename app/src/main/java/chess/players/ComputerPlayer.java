package chess.players;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ComputerPlayer extends AbstractPlayer {

    public ComputerPlayer(final PlayerColor playerColor) {
        super(playerColor);
    }

    @Override
    public void play() {

    }

    @Override
    public String getType() {
        return "COMPUTER";
    }
}
