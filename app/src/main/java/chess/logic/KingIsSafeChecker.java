package chess.logic;

import chess.game.ChessGame;
import chess.game.VirtualChessGameCreator;
import chess.players.Player;
import chess.resources.immutables.PieceToPoint2DMove;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * Methods to check if a {@link King} is safe under various game states.
 *
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public class KingIsSafeChecker implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(KingIsSafeChecker.class);
    private final boolean isSilenced;

    public KingIsSafeChecker(final boolean isSilenced) {
        this.isSilenced = isSilenced;
    }

    public boolean isKingSafe(final ChessGame chessGame, final King king) {
        Preconditions.checkNotNull(king);

        final Player thisPlayer = king.getOwner();
        final Player enemyPlayer = (thisPlayer == chessGame.getPlayerBlack()) ? chessGame.getPlayerWhite() : chessGame.getPlayerBlack();
        final Collection<Piece> enemyPieces = enemyPlayer.getPieces().values();
        for (final Piece enemyPiece : enemyPieces) {
            final Collection<Point2D> tilesThatThisPieceCanAttack = enemyPiece.getLawfulMoves(chessGame);
            if (tilesThatThisPieceCanAttack.contains(king.getPosition())) {
                return false;
            }
        }
        return true;
    }


    public boolean willKingBeSafeAfterMove(final ChessGame chessGame, final PieceToPoint2DMove move) {
        final Point2D initialPosition = move.getPiece().getPosition();
        final Point2D targetPosition = move.getTargetPoint();

        // Temporarily create a copy of the game so that the move can be done without affecting the actual board
        final ChessGame virtualChessGame = new VirtualChessGameCreator(chessGame).getVirtualGame();
        final Player tempPlayerNow = virtualChessGame.getPlayerNow();
//        final Piece tempPieceWhichMayBeMoved = virtualChessGame.getBoard().getPiece(pieceToPoint2DMove.getPiece().getPosition());
        final Piece tempPieceWhichMayBeMoved = virtualChessGame.getBoard().getPiece(initialPosition);
        final PieceToPoint2DMove tempMove = PieceToPoint2DMove.builder()
                .setPiece(tempPieceWhichMayBeMoved)
                .setTargetPoint(targetPosition)
                .build();

        // The following Precondition can be removed. It is jusy for double-checking.
        Preconditions.checkState(tempPlayerNow.getId().equals(virtualChessGame.getPlayerOwningPiece(tempPieceWhichMayBeMoved.getId()).getId()));

        final PieceDestroyer pieceDestroyer = new PieceDestroyer();
        pieceDestroyer.destroyPieceIfExistsInPosition(virtualChessGame, tempMove, true);
        tempPieceWhichMayBeMoved.setPosition(tempMove.getTargetPoint());
        final boolean kingWillBeSafeAftermove = isKingSafe(virtualChessGame, tempPlayerNow.getKing());
        if (!kingWillBeSafeAftermove && !this.isSilenced) {
            LOGGER.warn("INVALID MOVE: King would be in check after move!");
        }
        return kingWillBeSafeAftermove;
    }
}
