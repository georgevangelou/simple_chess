package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.game.ChessGame;
import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.space.movement.DiagonallyAvailableMovesFinder;
import chess.space.movement.HorizontallylAvailableMovesFinder;
import chess.space.movement.VerticallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class King extends Piece {

    public King(final Player owner, final Point2D position) {
        super("King", owner, ValuesOfPieces.KING, StringVisualRepresentationOfPieces.KING, position);
    }

    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final HorizontallylAvailableMovesFinder leftMover = new HorizontallylAvailableMovesFinder(this, 1, HorizontallylAvailableMovesFinder.Direction.toLeft);
        final HorizontallylAvailableMovesFinder rightMover = new HorizontallylAvailableMovesFinder(this, 1, HorizontallylAvailableMovesFinder.Direction.toRight);
        final VerticallyAvailableMovesFinder downMover = new VerticallyAvailableMovesFinder(this, 1, VerticallyAvailableMovesFinder.Direction.toBottom);
        final VerticallyAvailableMovesFinder upMover = new VerticallyAvailableMovesFinder(this, 1, VerticallyAvailableMovesFinder.Direction.toTop);
        final DiagonallyAvailableMovesFinder topLeftMovesFinder = new DiagonallyAvailableMovesFinder(this, 1, DiagonallyAvailableMovesFinder.Direction.toTopLeft);
        final DiagonallyAvailableMovesFinder topRightMovesFinder = new DiagonallyAvailableMovesFinder(this, 1, DiagonallyAvailableMovesFinder.Direction.toTopRight);
        final DiagonallyAvailableMovesFinder bottomLeftMovesFinder = new DiagonallyAvailableMovesFinder(this, 1, DiagonallyAvailableMovesFinder.Direction.toBottomLeft);
        final DiagonallyAvailableMovesFinder bottomRightMovesFinder = new DiagonallyAvailableMovesFinder(this, 1, DiagonallyAvailableMovesFinder.Direction.toBottomRight);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(leftMover.getAvailableMoves(game));
        accessiblePositions.addAll(rightMover.getAvailableMoves(game));
        accessiblePositions.addAll(downMover.getAvailableMoves(game));
        accessiblePositions.addAll(upMover.getAvailableMoves(game));
        accessiblePositions.addAll(topLeftMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(topRightMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(bottomLeftMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(bottomRightMovesFinder.getAvailableMoves(game));
        return List.copyOf(accessiblePositions);
    }
}
