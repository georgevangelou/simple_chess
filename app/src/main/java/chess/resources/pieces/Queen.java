package chess.resources.pieces;

import chess.constants.BoardDimensions;
import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.space.environment.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Queen extends Piece {

    public Queen(final Point2D position) {
        super("Queen", ValuesOfPieces.QUEEN, StringVisualRepresentationOfPieces.QUEEN, position);
    }


    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        final List<Point2D> accessiblePositions = new ArrayList<>();
        for (int i = 0; i < BoardDimensions.SIZE_X; i++) {
            for (int j = -1; j <= 1; j += 2) {
                // Diagonal movement
                for (int k = -1; k <= 1; k += 2) {
                    {
                        final int x = getPosition().getX() + j * i;
                        final int y = getPosition().getY() + k * i;
                        final Point2D point = Point2D.builder().setX(x).setY(y).build();
                        if (game.getBoard().isWithinBoard(point)) {
                            accessiblePositions.add(point);
                        }
                    }
                }
                // Vertical movement (x axis)
                {
                    final int x = getPosition().getX() + j * i;
                    final int y = getPosition().getY();
                    final Point2D point = Point2D.builder().setX(x).setY(y).build();
                    if (game.getBoard().isWithinBoard(point)) {
                        accessiblePositions.add(point);
                    }
                }
                // Vertical movement (y axis)
                {
                    final int x = getPosition().getX();
                    final int y = getPosition().getY() + j * i;
                    final Point2D point = Point2D.builder().setX(x).setY(y).build();
                    if (game.getBoard().isWithinBoard(point)) {
                        accessiblePositions.add(point);
                    }
                }
            }
        }
        accessiblePositions.remove(this.getPosition());
        return List.copyOf(accessiblePositions);
    }

}
