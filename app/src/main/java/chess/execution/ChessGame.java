package chess.execution;

import chess.players.HumanPlayer;
import chess.players.Player;
import chess.players.PlayerColor;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.space.Board2D;
import chess.utilities.HumanMoveReaderAndExecutor;
import chess.visualization.console.BoardPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ChessGame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChessGame.class);
    private final Player playerWhite;
    private final Player playerBlack;
    private final Board2D board;
    private final BoardPrinter boardPrinter;
    private Player playerNow;


    public ChessGame() {
        this.board = new Board2D();

        // TODO: Passing 'this' as a parameter while 'this' is not initialized yet, is bad.
        final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor = new HumanMoveReaderAndExecutor(this);
        playerWhite = new HumanPlayer(PlayerColor.white, humanMoveReaderAndExecutor);
        playerBlack = new HumanPlayer(PlayerColor.black, humanMoveReaderAndExecutor);
        playerNow = playerWhite.getPlayerColor().equals(PlayerColor.white) ? playerWhite : playerBlack; // If player1 is white, they start.

        this.board.fillBoardWithPieces(playerWhite, playerBlack);
        this.boardPrinter = new BoardPrinter(this);
    }


    public void nextPlayersTurn() {
        LOGGER.info("Player " + playerNow.getPlayerColor() + " (" + playerNow.getType() + ") now plays");

        playerNow.play();
        playerNow = (playerNow == playerBlack) ? playerWhite : playerBlack; // Change player at the end of the turn.
    }


    public Player getPlayerWhite() {
        return playerWhite;
    }


    public Player getPlayerBlack() {
        return playerBlack;
    }


    public Board2D getBoard() {
        return board;
    }


    public BoardPrinter getBoardPrinter() {
        return boardPrinter;
    }


    /**
     * @param pieceId matching a {@link Piece#getId()}
     * @return which {@link Player} owns this {@link Piece}
     */
    public Player getPlayerOwningPiece(final String pieceId) {
        if (this.playerWhite.pieceBelongsToPlayer(pieceId)) {
            return this.playerWhite;
        } else {
            return this.playerBlack;
        }
    }


    /**
     * TODO: Check if one of the {@link King}s is in check and cannot escape its doom.
     * TODO: Check if there is a stalemate.
     *
     * @return
     */
    public boolean isGameFinished() {
        return false;
    }
}
