package chess.space.movement;

import chess.game.ChessGame;
import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public class VerticallyAvailableMovesFinder extends AvailableMovesFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticallyAvailableMovesFinder.class);
    private final Direction direction;

    public VerticallyAvailableMovesFinder(final Piece piece, final int maxSteps, final Direction direction) {
        super(piece, maxSteps);
        Preconditions.checkNotNull(direction);
        Preconditions.checkNotNull(piece);
        this.direction = direction;
    }

    @Override
    public List<Point2D> getAvailableMoves(final ChessGame chessGame) {
        final List<Point2D> availableMoves = new ArrayList<>();

        final Point2D currentPosition = this.piece.getPosition();

        final int firstStep = this.direction.movementModifier;
        final int sign = this.direction.movementModifier;

        // Find possible positions in the vertical pathway
        for (int i = firstStep; sign * i <= this.maxSteps; i += this.direction.movementModifier) {
            final Point2D newPosition = Point2D.from(currentPosition).setY(currentPosition.getY() + i).build();
            if (chessGame.getBoard().isWithinBoard(newPosition)) {
                final Piece pieceAtTargetPosition = chessGame.getBoard().getPiece(newPosition);
                // Check if another piece is placed at the point currently under investigation.
                if (pieceAtTargetPosition != null) {
                    final Player playerOwningPieceTryingToMove = chessGame.getPlayerOwningPiece(this.piece.getId());
                    final Player playerOwningPieceAtTargetPosition = chessGame.getPlayerOwningPiece(pieceAtTargetPosition.getId());

                    // If position contains piece which is of the enemy player, it is lawful move.
                    if (!playerOwningPieceAtTargetPosition.getId().equals(playerOwningPieceTryingToMove.getId())) {
                        availableMoves.add(newPosition);
                    }
                    // If position contains piece, no more positions of same type should be added.
                    break;
                } else {
                    // If target position contains no piece, add it as lawful move.
                    availableMoves.add(newPosition);
                }
            }
        }
        return availableMoves;
    }


    public enum Direction {
        toTop(-1),
        toBottom(1);

        public final int movementModifier;

        private Direction(final int movementModifier) {
            this.movementModifier = movementModifier;
        }
    }
}
