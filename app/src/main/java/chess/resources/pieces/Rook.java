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
public final class Rook extends AbstractPiece {

    public Rook(final Point2D position) {
        super("Rook", ValuesOfPieces.ROOK, StringVisualRepresentationOfPieces.ROOK, position);
    }


    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int x = 0; x < BoardDimensions.SIZE_X; x++) {
            if (x != this.getPosition().getX()) {
                continue;
            }
            final Point2D point = Point2D.builder()
                    .setX(x)
                    .setY(this.getPosition().getY())
                    .build();
            if (game.getBoard().isWithinBoard(point)) {
                accessiblePositions.add(point);
            }
        }
        for (int y = 0; y < BoardDimensions.SIZE_Y; y++) {
            if (y != this.getPosition().getY()) {
                continue;
            }
            final Point2D point = Point2D.builder()
                    .setX(this.getPosition().getX())
                    .setY(y)
                    .build();
            if (game.getBoard().isWithinBoard(point)) {
                accessiblePositions.add(point);
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
