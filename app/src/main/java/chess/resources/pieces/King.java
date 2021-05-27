package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import chess.space.movement.DiagonallyAvailableMovesFinder;
import chess.space.movement.HorizontallylAvailableMovesFinder;
import chess.space.movement.VerticallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class King extends Piece {

    public King(final Point2D position) {
        super("King", ValuesOfPieces.KING, StringVisualRepresentationOfPieces.KING, position);
    }

    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final HorizontallylAvailableMovesFinder leftMover = new HorizontallylAvailableMovesFinder(game, this, 1, HorizontallylAvailableMovesFinder.Direction.toLeft);
        final HorizontallylAvailableMovesFinder rightMover = new HorizontallylAvailableMovesFinder(game, this, 1, HorizontallylAvailableMovesFinder.Direction.toRight);
        final VerticallyAvailableMovesFinder downMover = new VerticallyAvailableMovesFinder(game, this, 1, VerticallyAvailableMovesFinder.Direction.toBottom);
        final VerticallyAvailableMovesFinder upMover = new VerticallyAvailableMovesFinder(game, this, 1, VerticallyAvailableMovesFinder.Direction.toTop);
        final DiagonallyAvailableMovesFinder topLeftMovesFinder = new DiagonallyAvailableMovesFinder(game, this, 1, DiagonallyAvailableMovesFinder.Direction.toTopLeft);
        final DiagonallyAvailableMovesFinder topRightMovesFinder = new DiagonallyAvailableMovesFinder(game, this, 1, DiagonallyAvailableMovesFinder.Direction.toTopRight);
        final DiagonallyAvailableMovesFinder bottomLeftMovesFinder = new DiagonallyAvailableMovesFinder(game, this, 1, DiagonallyAvailableMovesFinder.Direction.toBottomLeft);
        final DiagonallyAvailableMovesFinder bottomRightMovesFinder = new DiagonallyAvailableMovesFinder(game, this, 1, DiagonallyAvailableMovesFinder.Direction.toBottomRight);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(leftMover.getAvailableMoves());
        accessiblePositions.addAll(rightMover.getAvailableMoves());
        accessiblePositions.addAll(downMover.getAvailableMoves());
        accessiblePositions.addAll(upMover.getAvailableMoves());
        accessiblePositions.addAll(topLeftMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(topRightMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(bottomLeftMovesFinder.getAvailableMoves());
        accessiblePositions.addAll(bottomRightMovesFinder.getAvailableMoves());
        return List.copyOf(accessiblePositions);
    }
}
