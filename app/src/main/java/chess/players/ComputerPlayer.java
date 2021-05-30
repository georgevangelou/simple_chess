package chess.players;

import chess.execution.ChessGame;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ComputerPlayer extends Player {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPlayer.class);
    private static PlayerType playerType = PlayerType.computer;

    public ComputerPlayer(final PlayerColor playerColor) {
        super(playerColor);
        throw new RuntimeException("Computer Player not implemented yet.");
    }

    @Override
    public void play(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);
        this.getPossibleMoves(chessGame);
    }

    @Override
    public PlayerType getType() {
        return playerType;
    }
}
