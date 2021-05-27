package chess.players;

import chess.execution.ChessGame;
import chess.execution.PieceToPoint2DMove;
import chess.execution.PlayerPiecesCreator;
import chess.resources.interfaces.Identifiable;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.resources.utilities.IdAutogenerator;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import chess.utilities.KingIsSafeChecker;
import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class Player implements Identifiable, Serializable {
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


    public abstract void play(final ChessGame chessGame);


    public Map<String, Piece> getPieces() {
        return this.pieces;
    }


    public List<PieceToPoint2DMove> getPossibleMoves(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        final List<PieceToPoint2DMove> possibleMoves = new ArrayList<>();
        for (final Piece piece : this.getPieces().values()) {
            for (final Point2D point : piece.getLawfulMoves(chessGame)) {
                possibleMoves.add(PieceToPoint2DMove.builder()
                        .setPiece(piece)
                        .setTargetPoint(point)
                        .build()
                );
            }
        }
        return possibleMoves;
    }


    public King getKing() {
        return this.king;
    }


    public boolean isKingSafe(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);
        return new KingIsSafeChecker(true).isKingSafe(chessGame, this.king);
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
     * This {@link Piece} must also be removed from {@link Board2D} explicitly.
     *
     * @param pieceId {@link Piece#getId()}.
     */
    public void destroyPiece(final String pieceId) {
        this.getPieces().remove(pieceId);
    }
}
