package chess.resources.pieces;

import chess.constants.BoardDimensions;
import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.space.Point2D;

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
        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int i = -BoardDimensions.SIZE_X; i < BoardDimensions.SIZE_X; i++) {
            {
                final int x = getPosition().getX() + i;
                final int y = getPosition().getY() + i;
                final Point2D point = Point2D.builder().setX(x).setY(y).build();
                if (game.getBoard().isWithinBoard(point)) {
                    accessiblePositions.add(point);
                }
            }
            {
                final int x = getPosition().getX() + i;
                final int y = getPosition().getY() + i;
                final Point2D point = Point2D.builder().setX(x).setY(y).build();
                if (game.getBoard().isWithinBoard(point)) {
                    accessiblePositions.add(point);
                }
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
