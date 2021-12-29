package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.resources.immutables.Point2D;
import chess.space.movement.DiagonallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Bishop extends Piece {

    public Bishop(final Point2D position) {
        super("Bishop", ValuesOfPieces.BISHOP, StringVisualRepresentationOfPieces.BISHOP, position);
    }

    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final DiagonallyAvailableMovesFinder topLeftMovesFinder = new DiagonallyAvailableMovesFinder(game, this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toTopLeft);
        final DiagonallyAvailableMovesFinder topRightMovesFinder = new DiagonallyAvailableMovesFinder(game, this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toTopRight);
        final DiagonallyAvailableMovesFinder bottomLeftMovesFinder = new DiagonallyAvailableMovesFinder(game, this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toBottomLeft);
        final DiagonallyAvailableMovesFinder bottomRightMovesFinder = new DiagonallyAvailableMovesFinder(game, this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toBottomRight);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(topLeftMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(topRightMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(bottomLeftMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(bottomRightMovesFinder.getAvailableMoves());
        return List.copyOf(accessiblePositions);
    }
}
