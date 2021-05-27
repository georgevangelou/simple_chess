package chess.utilities;

import chess.execution.ChessGame;
import chess.execution.MoveValidityChecker;
import chess.execution.PieceToPoint2DMove;
import chess.players.Player;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public class KingIsSafeChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(KingIsSafeChecker.class);
    private final boolean isSilenced;

    public KingIsSafeChecker(final boolean isSilenced) {
        this.isSilenced = isSilenced;
    }

    public boolean isKingSafe(final ChessGame chessGame, final King king) {
        Preconditions.checkNotNull(chessGame);
        Preconditions.checkNotNull(king);

        final Player thisPlayer = chessGame.getPlayerOwningPiece(king.getId());
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


    public boolean willKingBeSafeAfterMove(final ChessGame chessGame, final PieceToPoint2DMove pieceToPoint2DMove) {
        Preconditions.checkNotNull(chessGame);
        Preconditions.checkNotNull(pieceToPoint2DMove);

        // Temporarily create a copy of the game so that the move can be done without affecting the actual board
        final ChessGame tempChessGame = (ChessGame) SerializationUtils.clone(chessGame);
        final Player tempPlayerNow = tempChessGame.getPlayerNow();
        final Piece tempPieceWhichMayBeMoved = tempChessGame.getBoard().getPiece(pieceToPoint2DMove.getPiece().getPosition());
        final PieceToPoint2DMove tempMove = PieceToPoint2DMove.builder()
                .setPiece(tempPieceWhichMayBeMoved)
                .setTargetPoint(pieceToPoint2DMove.getTargetPoint())
                .build();

        // The following Precondition can be removed. It is jusy for double-checking.
        Preconditions.checkState(tempPlayerNow.getId().equals(tempChessGame.getPlayerOwningPiece(tempPieceWhichMayBeMoved.getId()).getId()));

        final PieceDestroyer pieceDestroyer = new PieceDestroyer(tempChessGame);
        pieceDestroyer.destroyPieceIfExistsInPosition(tempMove, true);
        tempPieceWhichMayBeMoved.setPosition(tempMove.getTargetPoint());
        final boolean kingWillBeSafeAftermove = isKingSafe(tempChessGame, tempPlayerNow.getKing());
        if (!kingWillBeSafeAftermove && !this.isSilenced) {
            LOGGER.warn("INVALID MOVE: King would be in check after move!");
        }
        return kingWillBeSafeAftermove;
    }
}
