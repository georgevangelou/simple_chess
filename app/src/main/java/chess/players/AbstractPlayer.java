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

    public boolean pieceBelongsToPlayer(final String pieceId) {
        return this.getPieces().containsKey(pieceId);
    }
}
