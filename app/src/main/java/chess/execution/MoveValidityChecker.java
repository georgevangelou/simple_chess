package chess.execution;

import chess.constants.BoardDimensions;
import chess.players.Player;
import chess.resources.pieces.Piece;
import chess.space.Point2D;
import chess.utilities.KingIsSafeChecker;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class MoveValidityChecker implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveValidityChecker.class);
    private final ChessGame chessGame;

    public MoveValidityChecker(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        this.chessGame = chessGame;
    }

    public boolean isMoveValid(final PieceToPoint2DMove move) {
        Preconditions.checkNotNull(move);

        // The following must be executed in this order
        return (pointIsInsideTheBoard(move)   // TODO: Remove this. Each piece will be responsible to return points within the board
                && pointIsWithinReachOfPiece(move)
                && pointIsIsNotOccupiedBySamePlayersPiece(move)
//                && pathIsNotBlockedAndPieceIsNotKnight()
                && kingWillNotBeInDangerAfterMove(move)
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

        final Piece piece = pieceToPoint2DMove.getPiece();
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

        final Player playerNow = this.chessGame.getPlayerNow();
        // TODO: The following Precondition can be removed
        Preconditions.checkState(playerNow.getId().equals(this.chessGame.getPlayerOwningPiece(pieceToPoint2DMove.getPiece().getId()).getId()));

        final Piece piece = this.chessGame.getBoard().getPiece(pieceToPoint2DMove.getTargetPoint());
        if (piece != null) {
            final Player otherPlayer = this.chessGame.getPlayerOwningPiece(piece.getId());
            if (playerNow == otherPlayer) {
                LOGGER.warn("INVALID MOVE: Target occupied by the same player.");
            }
            return playerNow != otherPlayer;
        }
        return true;
    }


    private boolean kingWillNotBeInDangerAfterMove(final PieceToPoint2DMove pieceToPoint2DMove) {
        Preconditions.checkNotNull(pieceToPoint2DMove);

        // Temporarily create a copy of the game so that the move can be done without affecting the actual board
        final ChessGame tempChessGame = (ChessGame) SerializationUtils.clone(this.chessGame);
        final Player tempPlayerNow = tempChessGame.getPlayerNow();
        final Piece tempPieceWhichMayBeMoved = tempChessGame.getBoard().getPiece(pieceToPoint2DMove.getPiece().getPosition());
        // The following Precondition can be removed. It is jusy for double-checking.
        Preconditions.checkState(tempPlayerNow.getId().equals(tempChessGame.getPlayerOwningPiece(tempPieceWhichMayBeMoved.getId()).getId()));

        tempPieceWhichMayBeMoved.setPosition(pieceToPoint2DMove.getTargetPoint());
        return new KingIsSafeChecker().isKingSafe(tempChessGame, tempPlayerNow.getKing());
    }


    private boolean pathIsNotBlockedAndPieceIsNotKnight() {
        return true;
    }
}
