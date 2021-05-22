package chess.resources.pieces;

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
public final class Knight extends AbstractPiece {

    public Knight(final Point2D position) {
        super("Knight", ValuesOfPieces.KNIGHT, StringVisualRepresentationOfPieces.KNIGHT, position);
    }

    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                if ((x == y) || (x == 0) || (y == 0)) {
                    continue;
                }
                final Point2D point = Point2D.builder()
                        .setX(this.getPosition().getX() + x)
                        .setY(this.getPosition().getY() + y)
                        .build();
                if (game.getBoard().isWithinBoard(point)) {
                    accessiblePositions.add(point);
                }
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
