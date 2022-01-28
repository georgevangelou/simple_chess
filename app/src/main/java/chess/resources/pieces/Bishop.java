package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.game.ChessGame;
import chess.players.Player;
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

    public Bishop(final Player owner, final Point2D position) {
        super("Bishop", owner, ValuesOfPieces.BISHOP, StringVisualRepresentationOfPieces.BISHOP, position);
    }

    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final DiagonallyAvailableMovesFinder topLeftMovesFinder = new DiagonallyAvailableMovesFinder(this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toTopLeft);
        final DiagonallyAvailableMovesFinder topRightMovesFinder = new DiagonallyAvailableMovesFinder(this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toTopRight);
        final DiagonallyAvailableMovesFinder bottomLeftMovesFinder = new DiagonallyAvailableMovesFinder(this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toBottomLeft);
        final DiagonallyAvailableMovesFinder bottomRightMovesFinder = new DiagonallyAvailableMovesFinder(this, game.getBoard().getLength(), DiagonallyAvailableMovesFinder.Direction.toBottomRight);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(topLeftMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(topRightMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(bottomLeftMovesFinder.getAvailableMoves(game));
        accessiblePositions.addAll(bottomRightMovesFinder.getAvailableMoves(game));
        return List.copyOf(accessiblePositions);
    }
}
