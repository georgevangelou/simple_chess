package chess.space;

import chess.constants.BoardDimensions;
import chess.players.AbstractPlayer;
import chess.resources.pieces.AbstractPiece;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class Board2D {
    private final Map<String, AbstractPiece> idToPiece;


    public Board2D() {
        this.idToPiece = new HashMap<>();
    }


    /**
     * Must be called in order to fill this {@link Board2D} with the players' pieces.
     * @param player1
     * @param player2
     */
    public void fillBoardWithPieces(final AbstractPlayer player1, final AbstractPlayer player2) {
        Preconditions.checkNotNull(player1);
        Preconditions.checkNotNull(player2);

        this.idToPiece.putAll(player1.getPieces());
        this.idToPiece.putAll(player2.getPieces());
    }


    public AbstractPiece getPiece(final Point2D point) {
        for (final AbstractPiece piece : this.idToPiece.values()) {
            if (piece.getPosition().isEqual(point)) {
                return piece;
            }
        }
        return null;
    }


    public long getSizeX() {
        return BoardDimensions.SIZE_X;
    }

    public long getSizeY() {
        return BoardDimensions.SIZE_Y;
    }


    public boolean isWithinBoard(final Point2D point) {
        return !((point.getX() < 0) ||
                (point.getX() > this.getSizeX() - 1) ||
                (point.getY() < 0) ||
                (point.getY() > this.getSizeY() - 1)
        );
    }

}
