package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.space.environment.Point2D;
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
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                final Point2D point = Point2D.builder()
                        .setX(this.getPosition().getX() + x)
                        .setY(this.getPosition().getY() + y)
                        .build();
                if (game.getBoard().isWithinBoard(point) && (!point.isEquivalent(this.getPosition()))) {
                    accessiblePositions.add(point);
                }
            }
        }
        return List.copyOf(accessiblePositions);
    }
}
