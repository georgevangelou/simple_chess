package chess.execution;

import chess.players.AbstractPlayer;
import chess.players.HumanPlayer;
import chess.players.PlayerColor;
import chess.resources.pieces.AbstractPiece;
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
    private final AbstractPlayer playerWhite;
    private final AbstractPlayer playerBlack;
    private final Board2D board;
    private final BoardPrinter boardPrinter;
    private AbstractPlayer playerNow;


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


    public AbstractPlayer getPlayerWhite() {
        return playerWhite;
    }


    public AbstractPlayer getPlayerBlack() {
        return playerBlack;
    }


    public Board2D getBoard() {
        return board;
    }


    public BoardPrinter getBoardPrinter() {
        return boardPrinter;
    }


    /**
     * @param pieceId matching a {@link AbstractPiece#getId()}
     * @return which {@link AbstractPlayer} owns this {@link AbstractPiece}
     */
    public AbstractPlayer getPlayerOwningPiece(final String pieceId) {
        if (this.playerWhite.pieceBelongsToPlayer(pieceId)) {
            return this.playerWhite;
        } else {
            return this.playerBlack;
        }
    }
}
