package chess.utilities;

import chess.execution.ChessGame;
import chess.execution.PieceToPoint2DMove;
import chess.players.Player;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-27
 */
public class PieceDestroyer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PieceDestroyer.class);
    private final ChessGame chessGame;

    public PieceDestroyer(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        this.chessGame = chessGame;
    }

    /**
     * If an {@link Piece} resides at the {@link Point2D} of interest, remove it from {@link Player} and {@link Board2D}.
     *
     * @param pieceToPoint2DMove
     */
    public long destroyPieceIfPreexistentInPosition(final PieceToPoint2DMove pieceToPoint2DMove, final boolean isScenarioHypothetical) {
        Preconditions.checkNotNull(pieceToPoint2DMove);

        final Piece preexistingPiece = this.chessGame.getBoard().getPiece(pieceToPoint2DMove.getTargetPoint());
        if (preexistingPiece != null) {
            this.chessGame.getBoard().removePiece(preexistingPiece.getId());
            final Player playerWhosePieceWasTheKiller = this.chessGame.getPlayerOwningPiece(pieceToPoint2DMove.getPiece().getId());
            final Player playerWhosePieceWasDestroyed = this.chessGame.getPlayerOwningPiece(preexistingPiece.getId());
            if (playerWhosePieceWasDestroyed == playerWhosePieceWasTheKiller) {
                LOGGER.error("EXCEPTION: Tried to destroy piece of the same player.");
                return -10000000;
            }
            playerWhosePieceWasDestroyed.destroyPiece(preexistingPiece.getId());
            if (!isScenarioHypothetical) {
                LOGGER.warn("PIECE CAPTURED: " + preexistingPiece.getName() + " [" + playerWhosePieceWasDestroyed.getPlayerColor() + "] was captured by " + pieceToPoint2DMove.getPiece().getName() + " [" + playerWhosePieceWasTheKiller.getPlayerColor() + "].");
                LOGGER.info("POINTS: White: " + chessGame.getPlayerWhite().getCurrentPoints() + ", Black: " + chessGame.getPlayerBlack().getCurrentPoints());
            }
            return preexistingPiece.getValue();
        }
        return 0;
    }
}
