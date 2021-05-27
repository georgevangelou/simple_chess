package chess.players;

import chess.execution.PlayerPiecesCreator;
import chess.resources.interfaces.Identifiable;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.resources.utilities.IdAutogenerator;
import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class Player implements Identifiable {
    private final Map<String, Piece> pieces;
    private final String id;
    private final PlayerColor playerColor;
    private final King king;


    protected Player(final PlayerColor playerColor) {
        Preconditions.checkNotNull(playerColor);

        this.id = IdAutogenerator.generateId();
        this.playerColor = playerColor;
        final PlayerPiecesCreator playerPiecesCreator = new PlayerPiecesCreator(this.playerColor);
        this.pieces = playerPiecesCreator.getPieces();
        this.king = playerPiecesCreator.getKing();
    }


    public String getId() {
        return this.id;
    }


    public abstract void play();


    public Map<String, Piece> getPieces() {
        return this.pieces;
    }


    public King getKing() {
        return this.king;
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
