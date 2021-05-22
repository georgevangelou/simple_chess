package chess.resources.pieces;

import chess.constants.BoardDimensions;
import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.space.Point2D;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Bishop extends AbstractPiece {

    public Bishop(final Point2D position) {
        super("Bishop", ValuesOfPieces.BISHOP, StringVisualRepresentationOfPieces.BISHOP, position);
    }

    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int i = -BoardDimensions.SIZE_X; i < BoardDimensions.SIZE_X; i++) {
            final int x = getPosition().getX() + i;
            final int y = getPosition().getY() + i;
            final Point2D point1 = Point2D.builder().setX(getPosition().getX() + i).setY(getPosition().getY() + i).build();
            final Point2D point2 = Point2D.builder().setX(getPosition().getX() + i).setY(getPosition().getY() - i).build();
            final Point2D point3 = Point2D.builder().setX(getPosition().getX() - i).setY(getPosition().getY() + i).build();
            final Point2D point4 = Point2D.builder().setX(getPosition().getX() - i).setY(getPosition().getY() - i).build();
            if (game.getBoard().isWithinBoard(point1)) {
                accessiblePositions.add(point1);
            }
            if (game.getBoard().isWithinBoard(point2)) {
                accessiblePositions.add(point2);
            }
            if (game.getBoard().isWithinBoard(point3)) {
                accessiblePositions.add(point3);
            }
            if (game.getBoard().isWithinBoard(point4)) {
                accessiblePositions.add(point4);
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
