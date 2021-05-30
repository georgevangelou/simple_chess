package chess.space.movement;

import chess.execution.ChessGame;
import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-27
 */
public class DiagonallyAvailableMovesFinder extends AvailableMovesFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagonallyAvailableMovesFinder.class);
    private final Direction direction;

    public DiagonallyAvailableMovesFinder(final ChessGame chessGame, final Piece piece, final int maxSteps, final Direction direction) {
        super(chessGame, piece, maxSteps);
        Preconditions.checkNotNull(direction);
        Preconditions.checkNotNull(piece);
        this.direction = direction;
    }


    public enum Direction {
        toTopLeft(-1, -1),
        toTopRight(1, -1),
        toBottomLeft(-1, 1),
        toBottomRight(1, 1);

        public final int xMovementModifier;
        public final int yMovementModifier;

        private Direction(final int xMovementModifier, final int yMovementModifier) {
            this.xMovementModifier = xMovementModifier;
            this.yMovementModifier = yMovementModifier;
        }
    }


    @Override
    public List<Point2D> getAvailableMoves() {
        final List<Point2D> availableMoves = new ArrayList<>();

        final Point2D currentPosition = this.piece.getPosition();

        final int xSign = this.direction.xMovementModifier;
        final int ySign = this.direction.yMovementModifier;

        // Find possible positions in the vertical pathway
        for (int i = 1; i <= this.maxSteps; i++) {
            final Point2D newPosition = Point2D.builder()
                    .setX(currentPosition.getX() + i * xSign)
                    .setY(currentPosition.getY() + i * ySign)
                    .build();
            if (this.chessGame.getBoard().isWithinBoard(newPosition)) {
                // Check if another piece is placed at the point currently under investigation.
                final Piece pieceAtTargetPosition = this.chessGame.getBoard().getPiece(newPosition);
                if (pieceAtTargetPosition != null) {
                    final Player playerOwningPieceTryingToMove = this.chessGame.getPlayerOwningPiece(this.piece.getId());
                    final Player playerOwningPieceAtTargetPosition = this.chessGame.getPlayerOwningPiece(pieceAtTargetPosition.getId());

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
}
