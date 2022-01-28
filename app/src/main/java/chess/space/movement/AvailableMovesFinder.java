package chess.space.movement;

import chess.game.ChessGame;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public abstract class AvailableMovesFinder {
    protected final Piece piece;
    protected final int maxSteps;


    protected AvailableMovesFinder(final Piece piece, final int maxSteps) {
        Preconditions.checkNotNull(piece);
        Preconditions.checkState(maxSteps > 0);

        this.piece = piece;
        this.maxSteps = maxSteps;
    }


    public abstract List<Point2D> getAvailableMoves(final ChessGame chessGame);
}
