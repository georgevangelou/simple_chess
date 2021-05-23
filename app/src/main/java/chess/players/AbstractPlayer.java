package chess.players;

import chess.execution.PlayerPiecesCreator;
import chess.resources.pieces.AbstractPiece;
import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class AbstractPlayer {
    private final Map<String, AbstractPiece> pieces;
    private final PlayerColor playerColor;


    protected AbstractPlayer(final PlayerColor playerColor) {
        Preconditions.checkNotNull(playerColor);

        this.playerColor = playerColor;
        final PlayerPiecesCreator playerPiecesCreator = new PlayerPiecesCreator(this.playerColor);
        this.pieces = playerPiecesCreator.getPieces();
    }


    public abstract void play();


    public Map<String, AbstractPiece> getPieces() {
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
        for (final AbstractPiece piece : this.getPieces().values()) {
            points += piece.getValue();
        }
        return points;
    }


    /**
     * Destroy an {@link AbstractPiece} belonging to this {@link AbstractPlayer}.
     * This {@link AbstractPiece} must also be removed from {@link chess.space.Board2D} explicitly.
     *
     * @param pieceId {@link AbstractPiece#getId()}.
     */
    public void destroyPiece(final String pieceId) {
        this.getPieces().remove(pieceId);
    }
}
