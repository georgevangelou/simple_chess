package chess.execution;

import chess.constants.BoardDimensions;
import chess.players.PlayerColor;
import chess.resources.pieces.*;
import chess.space.Point2D;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class PlayerPiecesCreator {
    private final PlayerColor playerColor;
    private final Map<String, Piece> pieces = new HashMap<>();

    /**
     * Constructor which initializes all values on call.
     *
     * @param playerColor
     */
    public PlayerPiecesCreator(final PlayerColor playerColor) {
        this.playerColor = playerColor;
        this.createPawns();
        this.createPiecesExceptFromPawns();
    }


    /**
     * Only creates {@link Pawn}s.
     */
    private void createPawns() {
        for (int x = 0; x < BoardDimensions.SIZE_X; x++) {
            int y = 1;
            if (this.playerColor.equals(PlayerColor.black)) {
                y = BoardDimensions.SIZE_Y - 2;
            }
            final Point2D position = Point2D.builder().setX(x).setY(y).build();
            final Pawn pawn = new Pawn(position);
            pieces.put(pawn.getId(), pawn);
        }
    }

    /**
     * Creates all {@link Piece}s except for {@link Pawn}s.
     */
    private void createPiecesExceptFromPawns() {
        int y = 0;
        if (this.playerColor.equals(PlayerColor.black)) {
            y = BoardDimensions.SIZE_Y - 1;
        }
        {
            final Point2D position = Point2D.builder().setX(0).setY(y).build();
            final Rook piece = new Rook(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(1).setY(y).build();
            final Knight piece = new Knight(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(2).setY(y).build();
            final Bishop piece = new Bishop(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(3).setY(y).build();
            final Queen piece = new Queen(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(4).setY(y).build();
            final King piece = new King(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(5).setY(y).build();
            final Bishop piece = new Bishop(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(6).setY(y).build();
            final Knight piece = new Knight(position);
            pieces.put(piece.getId(), piece);
        }
        {
            final Point2D position = Point2D.builder().setX(7).setY(y).build();
            final Rook piece = new Rook(position);
            pieces.put(piece.getId(), piece);
        }
    }

    public Map<String, Piece> getPieces() {
        return this.pieces;
    }
}