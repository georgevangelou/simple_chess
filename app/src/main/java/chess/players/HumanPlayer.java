package chess.players;

import chess.execution.ChessGame;
import chess.utilities.HumanMoveReaderAndExecutor;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class HumanPlayer extends Player {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanPlayer.class);
    private static PlayerType playerType = PlayerType.human;
    private final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor;

    public HumanPlayer(final PlayerColor playerColor, final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor) {
        super(playerColor);
        Preconditions.checkNotNull(humanMoveReaderAndExecutor);

        this.humanMoveReaderAndExecutor = humanMoveReaderAndExecutor;
    }

    @Override
    public void play(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);
        humanMoveReaderAndExecutor.readExecuteMove(this, chessGame);
    }


    @Override
    public PlayerType getType() {
        return playerType;
    }
}
