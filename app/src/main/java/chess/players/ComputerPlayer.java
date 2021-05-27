package chess.players;

import chess.execution.ChessGame;
import com.google.common.base.Preconditions;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ComputerPlayer extends Player {

    public ComputerPlayer(final PlayerColor playerColor) {
        super(playerColor);
    }

    @Override
    public void play(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);
        this.getPossibleMoves(chessGame);
    }

    @Override
    public String getType() {
        return "COMPUTER";
    }
}
