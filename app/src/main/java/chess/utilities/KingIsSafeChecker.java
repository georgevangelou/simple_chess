package chess.utilities;

import chess.execution.ChessGame;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;

import java.util.Collection;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-26
 */
public class KingIsSafeChecker {
    public boolean isKingSafe(final ChessGame chessGame, final King king) {
        Preconditions.checkNotNull(chessGame);
        Preconditions.checkNotNull(king);

        final Collection<Piece> enemyPieces = chessGame.getPlayerResting().getPieces().values();
        for (final Piece enemyPiece : enemyPieces) {
            final Collection<Point2D> tilesThatThisPieceCanAttack = enemyPiece.getAccessiblePositionsIgnoringCollisions(chessGame);
            if (tilesThatThisPieceCanAttack.contains(king.getPosition())) {
                return false;
            }
        }
        return true;
    }
}
