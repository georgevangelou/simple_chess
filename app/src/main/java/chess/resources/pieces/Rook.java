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
        for (int i = 1 - BoardDimensions.SIZE_X; i < BoardDimensions.SIZE_X; i++) {
            {
                final Point2D point = Point2D.builder()
                        .setX(i)
                        .setY(this.getPosition().getY())
                        .build();
                if (game.getBoard().isWithinBoard(point)) {
                    accessiblePositions.add(point);
                }
            }
            {
                final Point2D point = Point2D.builder()
                        .setX(this.getPosition().getX())
                        .setY(i)
                        .build();
                if (game.getBoard().isWithinBoard(point)) {
                    accessiblePositions.add(point);
                }
            }
        }
            accessiblePositions.remove(this.getPosition());
        return List.copyOf(accessiblePositions);
    }
}
