package chess.players;

import chess.execution.PlayerPiecesCreator;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class Player {
    private final Map<String, Piece> pieces;
    private final PlayerColor playerColor;


    protected Player(final PlayerColor playerColor) {
        Preconditions.checkNotNull(playerColor);

        this.playerColor = playerColor;
        final PlayerPiecesCreator playerPiecesCreator = new PlayerPiecesCreator(this.playerColor);
        this.pieces = playerPiecesCreator.getPieces();
    }


    public abstract void play();


    public Map<String, Piece> getPieces() {
        return this.pieces;
    }


    public PlayerColor getPlayerColor() {
        return playerColor;
    }


    public abstract String getType();


    public boolean pieceBelongsToPlayer(final String pieceId) {
        return this.getPieces().containsKey(pieceId);
    }


    public int getCurrentPoints() {
        int points = 0;
        for (final Piece piece : this.getPieces().values()) {
            points += piece.getValue();
        }
        return points;
    }


    /**
     * Destroy an {@link Piece} belonging to this {@link Player}.
     * This {@link Piece} must also be removed from {@link chess.space.Board2D} explicitly.
     *
     * @param pieceId {@link Piece#getId()}.
     */
    public void destroyPiece(final String pieceId) {
        this.getPieces().remove(pieceId);
    }
}
