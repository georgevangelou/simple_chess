package chess.players;

import chess.utilities.HumanMoveReaderAndExecutor;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class HumanPlayer extends AbstractPlayer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanPlayer.class);
    private final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor;

    public HumanPlayer(final PlayerColor playerColor, final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor) {
        super(playerColor);
        Preconditions.checkNotNull(humanMoveReaderAndExecutor);

        this.humanMoveReaderAndExecutor = humanMoveReaderAndExecutor;
    }

    @Override
    public void play() {
        humanMoveReaderAndExecutor.readExecuteMove(this);
    }


    @Override
    public String getType() {
        return "HUMAN";
    }
}
