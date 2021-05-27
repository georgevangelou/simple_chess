package chess.space.movement;

import chess.execution.ChessGame;
import chess.players.Player;
import chess.resources.pieces.Piece;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-27
 */
public class GammaAvailableMovesFinder extends AvailableMovesFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(GammaAvailableMovesFinder.class);

    public GammaAvailableMovesFinder(final ChessGame chessGame, final Piece piece) {
        super(chessGame, piece, 1);
        Preconditions.checkNotNull(piece);
    }


    @Override
    public List<Point2D> getAvailableMoves() {
        final List<Point2D> availableMoves = new ArrayList<>();

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                if ((x == y) || (x == 0) || (y == 0)) {
                    continue;
                }
                final Point2D newPosition = Point2D.builder()
                        .setX(this.piece.getPosition().getX() + x)
                        .setY(this.piece.getPosition().getY() + y)
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
                    } else {
                        // If target position contains no piece, add it as lawful move.
                        availableMoves.add(newPosition);
                    }
                }
            }
        }
        return availableMoves;
    }
}
