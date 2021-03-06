package chess.space.environment;

import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class Board2D implements Serializable {
    public static final int LENGTH = 8;
    private final Map<String, Piece> idToPiece;


    public Board2D() {
        this.idToPiece = new HashMap<>();
    }


    /**
     * Must be called in order to fill this {@link Board2D} with the players' pieces.
     *
     * @param player1
     * @param player2
     */
    public void fillBoardWithPieces(final Player player1, final Player player2) {
        Preconditions.checkNotNull(player1);
        Preconditions.checkNotNull(player2);

        this.idToPiece.putAll(player1.getPieces());
        this.idToPiece.putAll(player2.getPieces());
    }


    /**
     * @param point a {@link Point2D} of {@link Board2D}
     * @return {@link Piece} residing in {@link Point2D} of {@link Board2D}, or null if none resides there
     */
    public Piece getPiece(final Point2D point) {
        Preconditions.checkNotNull(point);

        for (final Piece piece : this.idToPiece.values()) {
            if (piece.getPosition().isEquivalent(point)) {
                return piece;
            }
        }
        return null;
    }


    public Piece getPiece(int x, int y) {
        final Point2D pointOnBoard = Point2D.builder().setX(x).setY(y).build();
        return this.getPiece(pointOnBoard);
    }


    public int getLength() {
        return LENGTH;
    }


    public boolean isWithinBoard(final Point2D point) {
        return !((point.getX() < 0) ||
                (point.getX() > this.getLength() - 1) ||
                (point.getY() < 0) ||
                (point.getY() > this.getLength() - 1)
        );
    }


    /**
     * Destroy an {@link Piece} placed on this {@link Board2D}.
     * This {@link Piece} must also be removed from {@link Player} explicitly.
     *
     * @param pieceId {@link Piece#getId()}.
     */
    public void removePiece(final String pieceId) {
        this.idToPiece.remove(pieceId);
    }
}
