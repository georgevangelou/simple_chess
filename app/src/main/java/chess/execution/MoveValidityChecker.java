package chess.execution;

import chess.constants.BoardDimensions;
import chess.players.AbstractPlayer;
import chess.resources.pieces.AbstractPiece;
import chess.space.Point2D;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class MoveValidityChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveValidityChecker.class);
    private final ChessGame chessGame;

    public MoveValidityChecker(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        this.chessGame = chessGame;
    }

    public boolean isMoveValid(final PieceToPoint2DMove move) {
        Preconditions.checkNotNull(move);

        return (pointIsInsideTheBoard(move) &&
                pointIsWithinReachOfPiece(move) &&
                pointIsIsNotOccupiedBySamePlayersPiece(move) &&
                pathIsNotBlockedAndPieceIsNotKnight() &&
                kingWillNotBeInDangerAfterMove()
        );
    }


    private boolean pointIsInsideTheBoard(final PieceToPoint2DMove move) {
        Preconditions.checkNotNull(move);

        final int x = move.getTargetPoint().getX();
        final int y = move.getTargetPoint().getY();
        if (!(0 <= x && x < BoardDimensions.SIZE_X && 0 <= y && y < BoardDimensions.SIZE_Y)) {
            LOGGER.warn("INVALID MOVE: Chosen point is out of bounds: [x,y]=[" + x + "," + y + "]");
            return false;
        }
        return true;
    }


    /**
     * @param pieceToPoint2DMove
     * @return true if the point chosen is within the list of points that the piece can reach ignoring collisions.
     */
    private boolean pointIsWithinReachOfPiece(final PieceToPoint2DMove pieceToPoint2DMove) {
        Preconditions.checkNotNull(pieceToPoint2DMove);

        final AbstractPiece piece = pieceToPoint2DMove.getPiece();
        for (final Point2D point2D : piece.getAccessiblePositionsIgnoringCollisions(chessGame)) {
            if (point2D.isEquivalent(pieceToPoint2DMove.getTargetPoint())) {
                return true;
            }
        }
        LOGGER.warn("INVALID MOVE: Point is not within reach of the piece.");
        return false;
    }


    private boolean pointIsIsNotOccupiedBySamePlayersPiece(final PieceToPoint2DMove pieceToPoint2DMove) {
        Preconditions.checkNotNull(pieceToPoint2DMove);

        final AbstractPlayer player1 = this.chessGame.getPlayerOwningPiece(pieceToPoint2DMove.getPiece().getId());
        final AbstractPiece piece = this.chessGame.getBoard().getPiece(pieceToPoint2DMove.getTargetPoint());
        if (piece != null) {
            final AbstractPlayer player2 = this.chessGame.getPlayerOwningPiece(piece.getId());
            if (player1 == player2) {
                LOGGER.warn("INVALID MOVE: Point is of the same player.");
            }
            return player1 != player2;
        }
        return true;
    }


    private boolean kingWillNotBeInDangerAfterMove() {
        return true;
    }


    private boolean pathIsNotBlockedAndPieceIsNotKnight() {
        return true;
    }
}
