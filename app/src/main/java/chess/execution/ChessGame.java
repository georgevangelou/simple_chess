package chess.execution;

import chess.players.AbstractPlayer;
import chess.players.HumanPlayer;
import chess.players.PlayerColor;
import chess.space.Board2D;
import chess.visualization.console.BoardPrinter;
import chess.utilities.HumanMoveReaderAndExecutor;
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

        final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor = new HumanMoveReaderAndExecutor(this.getBoard());
        playerWhite = new HumanPlayer(PlayerColor.white, humanMoveReaderAndExecutor);
        playerBlack = new HumanPlayer(PlayerColor.black, humanMoveReaderAndExecutor);
        playerNow = playerWhite.getPlayerColor().equals(PlayerColor.white) ? playerWhite : playerBlack; // If player1 is white, they start.

        this.board.fillBoardWithPieces(playerWhite, playerBlack);
        this.boardPrinter = new BoardPrinter(this);
    }


    public void nextPlayersTurn() {
        LOGGER.info("Player [" + playerNow.getPlayerColor() + "] now plays:");
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
}
