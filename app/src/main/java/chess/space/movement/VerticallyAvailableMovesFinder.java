package chess.space.movement;

import chess.execution.ChessGame;
import chess.execution.PieceToPoint2DMove;
import chess.players.Player;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public class VerticallyAvailableMovesFinder extends AvailableMovesFinder {
    private final Direction direction;

    protected VerticallyAvailableMovesFinder(final ChessGame chessGame, final Piece piece, final int maxSteps, final Direction direction) {
        super(chessGame, piece, maxSteps);
        Preconditions.checkNotNull(direction);
        Preconditions.checkNotNull(piece);
        this.direction = direction;
    }


    enum Direction {
        toTop(-1),
        toBottom(1);

        public final int movementModifier;

        private Direction(final int movementModifier) {
            this.movementModifier = movementModifier;
        }
    }


    @Override
    public List<PieceToPoint2DMove> getAvailableMoves() {
        final List<PieceToPoint2DMove> availableMoves = new ArrayList<>();

        final Point2D currentPosition = this.piece.getPosition();

        final int firstStep = this.direction.movementModifier;
        final int sign = this.direction.movementModifier;

        // Find possible positions in the vertical pathway
        for (int i = firstStep; sign * i < Board2D.LENGTH; i += this.direction.movementModifier) {
            final Point2D newPosition = Point2D.from(currentPosition).setY(currentPosition.getY() + i).build();
            final Piece pieceAtTargetPosition = this.chessGame.getBoard().getPiece(newPosition);
            if (pieceAtTargetPosition != null) {
                final Player playerOwningPieceTryingToMove = this.chessGame.getPlayerOwningPiece(this.piece.getId());
                final Player playerOwningPieceAtTargetPosition = this.chessGame.getPlayerOwningPiece(pieceAtTargetPosition.getId());

                // If position contains piece which is of the enemy player, it is lawful move.
                if (!playerOwningPieceAtTargetPosition.getId().equals(playerOwningPieceTryingToMove.getId())) {
                    availableMoves.add(PieceToPoint2DMove.builder().setPiece(this.piece).setTargetPoint(newPosition).build());
                }
                // If position contains piece, no more positions of same type should be added.
                break;
            } else {
                // If target position contains no piece, add it as lawful move.
                availableMoves.add(PieceToPoint2DMove.builder().setPiece(this.piece).setTargetPoint(newPosition).build());
            }
        }
        return availableMoves;
    }
}
