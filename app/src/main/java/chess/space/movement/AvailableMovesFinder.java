package chess.space.movement;

import chess.execution.ChessGame;
import chess.execution.PieceToPoint2DMove;
import chess.resources.pieces.Piece;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public abstract class AvailableMovesFinder {
    protected final ChessGame chessGame;
    protected final Piece piece;
    protected final int maxSteps;


    protected AvailableMovesFinder(final ChessGame chessGame, final Piece piece, final int maxSteps) {
        Preconditions.checkNotNull(chessGame);
        Preconditions.checkNotNull(piece);
        Preconditions.checkState(maxSteps > 0);

        this.chessGame = chessGame;
        this.piece = piece;
        this.maxSteps = maxSteps;
    }


    public abstract List<PieceToPoint2DMove> getAvailableMoves();
}
