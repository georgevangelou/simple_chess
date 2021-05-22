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
public final class Pawn extends AbstractPiece {

    public Pawn(final Point2D position) {
        super("Pawn", ValuesOfPieces.PAWN, StringVisualRepresentationOfPieces.PAWN, position);
    }


    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        // TODO: Implement how to chose y+1 or y-1 depending on the player
        final List<Point2D> accessiblePositions = new ArrayList<>();

        {
            final Point2D point = Point2D.builder()
                    .setX(this.getPosition().getX())
                    .setY(this.getPosition().getY() + 1)
                    .build();
            if (game.getBoard().isWithinBoard(point)) {
                accessiblePositions.add(point);
            }
        }
        {
            final Point2D point = Point2D.builder()
                    .setX(this.getPosition().getX())
                    .setY(this.getPosition().getY() - 1)
                    .build();
            if (game.getBoard().isWithinBoard(point)) {
                accessiblePositions.add(point);
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
