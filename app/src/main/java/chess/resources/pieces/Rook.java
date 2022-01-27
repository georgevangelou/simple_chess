package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.resources.immutables.Point2D;
import chess.space.environment.Board2D;
import chess.space.movement.HorizontallylAvailableMovesFinder;
import chess.space.movement.VerticallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Rook extends Piece {

    public Rook(final Point2D position) {
        super("Rook", ValuesOfPieces.ROOK, StringVisualRepresentationOfPieces.ROOK, position);
    }


    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final HorizontallylAvailableMovesFinder leftMover = new HorizontallylAvailableMovesFinder(this, Board2D.LENGTH, HorizontallylAvailableMovesFinder.Direction.toLeft);
        final HorizontallylAvailableMovesFinder rightMover = new HorizontallylAvailableMovesFinder(this, Board2D.LENGTH, HorizontallylAvailableMovesFinder.Direction.toRight);
        final VerticallyAvailableMovesFinder downMover = new VerticallyAvailableMovesFinder(this, Board2D.LENGTH, VerticallyAvailableMovesFinder.Direction.toBottom);
        final VerticallyAvailableMovesFinder upMover = new VerticallyAvailableMovesFinder(this, Board2D.LENGTH, VerticallyAvailableMovesFinder.Direction.toTop);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(leftMover.getAvailableMoves(game));
        accessiblePositions.addAll(rightMover.getAvailableMoves(game));
        accessiblePositions.addAll(downMover.getAvailableMoves(game));
        accessiblePositions.addAll(upMover.getAvailableMoves(game));
        return List.copyOf(accessiblePositions);
    }
}
